package com.paypulse.merchant.adapters.out.outbox;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypulse.merchant.model.event.EventEnvelope;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

/** Data access for outbox rows. */
@ApplicationScoped
public class OutboxRepository {

    public static final int DEFAULT_BATCH_SIZE = 50;

    private static final String QUERY_UNPUBLISHED = "publishedAt IS NULL ORDER BY createdAt ASC";

    @Inject
    ObjectMapper objectMapper;

    @Transactional
    public UUID save(EventEnvelope<?> envelope) {
        OutboxJpaEntity row = new OutboxJpaEntity(
                UUID.randomUUID(),
                envelope.getEventId(),
                envelope.getEventType(),
                envelope.getCorrelationId(),
                toJson(envelope),
                Instant.now(),
                null);
        row.persist();
        return row.getId();
    }

    /**
     * Unpublished rows in creation order. Called outside a transaction so the
     * relay can publish each row on its own short transaction.
     */
    public List<OutboxJpaEntity> findUnpublished(int limit) {
        return OutboxJpaEntity.find(QUERY_UNPUBLISHED)
                .page(0, limit)
                .list();
    }

    public Optional<OutboxJpaEntity> findById(UUID rowId) {
        return OutboxJpaEntity.findByIdOptional(rowId);
    }

    @Transactional
    public void markPublished(UUID rowId) {
        OutboxJpaEntity.update("publishedAt = ?1 where id = ?2", Instant.now(), rowId);
    }

    private String toJson(EventEnvelope<?> envelope) {
        try {
            return objectMapper.writeValueAsString(envelope);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to serialize event " + envelope.getEventId(), e);
        }
    }
}
