package com.paypulse.merchant.ports.out.event;

import com.paypulse.merchant.domain.entity.Merchant;

/**
 * Driven port for event publication: records a merchant-registered event for
 * asynchronous delivery (outbox pattern, 01-PHASE0-CONVENTIONS.md §5).
 * Envelope construction (eventId, occurredAt, correlationId) is an adapter
 * concern.
 */
public interface MerchantEventPublisherPort {

    void publish(Merchant merchant);
}
