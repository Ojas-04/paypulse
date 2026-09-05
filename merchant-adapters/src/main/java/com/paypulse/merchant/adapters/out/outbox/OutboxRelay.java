package com.paypulse.merchant.adapters.out.outbox;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.jboss.logging.Logger;

import com.paypulse.merchant.model.event.EventTypes;

import io.quarkus.scheduler.Scheduled;
import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Background relay implementing the delivery half of the outbox pattern (§5):
 * polls unpublished rows and publishes each to Kafka, marking the row
 * published once the broker has acknowledged it. At-least-once semantics —
 * consumers must be idempotent (§6).
 */
@ApplicationScoped
public class OutboxRelay {

    private static final Logger LOG = Logger.getLogger(OutboxRelay.class);

    static final String CHANNEL_MERCHANT_REGISTERED = "merchant-registered";

    @ConfigProperty(name = "outbox.relay.batch-size", defaultValue = "50")
    int batchSize;

    @ConfigProperty(name = "outbox.relay.publish-timeout", defaultValue = "3s")
    Duration publishTimeout;

    @Inject
    OutboxRepository outboxRepository;

    @Inject
    @Channel(CHANNEL_MERCHANT_REGISTERED)
    Emitter<String> merchantRegisteredEmitter;

    @Scheduled(every = "{outbox.relay.interval:5s}")
    void poll() {
        List<OutboxJpaEntity> batch = outboxRepository.findUnpublished(batchSize);
        for (OutboxJpaEntity row : batch) {
            try {
                publish(row);
                outboxRepository.markPublished(row.getId());
            } catch (Exception e) {
                LOG.warnf(e, "Outbox row %s (eventId=%s) not published yet; will retry",
                        row.getId(), row.getEventId());
            }
        }
    }

    private void publish(OutboxJpaEntity row) {
        // §1: log identifiers before looking at the payload
        LOG.infof("Publishing event eventId=%s eventType=%s correlationId=%s",
                row.getEventId(), row.getEventType(), row.getCorrelationId());

        // All events of one business flow share correlationId → same Kafka key →
        // same partition → per-flow ordering preserved.
        OutgoingKafkaRecordMetadata<String> metadata = OutgoingKafkaRecordMetadata.<String>builder()
                .withKey(row.getCorrelationId().toString())
                .build();

        CompletableFuture<Void> acknowledged = new CompletableFuture<>();
        emitterFor(row.getEventType()).send(Message.of(row.getPayload())
                .addMetadata(metadata)
                .withAck(() -> {
                    acknowledged.complete(null);
                    return CompletableFuture.completedFuture(null);
                })
                .withNack(cause -> {
                    acknowledged.completeExceptionally(cause);
                    return CompletableFuture.completedFuture(null);
                }));

        try {
            acknowledged.get(publishTimeout.toMillis(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while publishing event " + row.getEventId(), e);
        } catch (ExecutionException | TimeoutException e) {
            throw new IllegalStateException("Broker did not acknowledge event " + row.getEventId(), e);
        }
    }

    private Emitter<String> emitterFor(String eventType) {
        if (EventTypes.MERCHANT_REGISTERED.equals(eventType)) {
            return merchantRegisteredEmitter;
        }
        throw new IllegalArgumentException("No channel configured for eventType=" + eventType);
    }
}
