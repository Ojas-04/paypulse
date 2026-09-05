package com.paypulse.merchant.adapters.out.outbox;

import java.util.UUID;

import com.paypulse.merchant.adapters.mapper.MerchantEventMapper;
import com.paypulse.merchant.domain.entity.Merchant;
import com.paypulse.merchant.ports.out.event.MerchantEventPublisherPort;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Outbox implementation of the event publisher port (§5): "publishing" means
 * writing an outbox row inside the caller's transaction; the {@link OutboxRelay}
 * delivers it to Kafka asynchronously.
 */
@ApplicationScoped
public class OutboxEventPublisherAdapter implements MerchantEventPublisherPort {

    @Inject
    OutboxRepository outboxRepository;

    @Inject
    MerchantEventMapper eventMapper;

    @Override
    public void publish(Merchant merchant) {
        UUID eventId = UUID.randomUUID();
        outboxRepository.save(eventMapper.toEnvelope(merchant, eventId, merchant.getCreatedAt()));
    }
}
