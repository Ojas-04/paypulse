package com.paypulse.merchant.model.event;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * Shared outer shape wrapping every event published by any PayPulse service
 * (01-PHASE0-CONVENTIONS.md §1).
 *
 * @param <T> type of the event-specific payload
 */
@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class EventEnvelope<T> {

    /** Unique ID for this specific event instance. */
    UUID eventId;

    /** Matches the topic's event name and version, e.g. "merchant.registered.v1". */
    String eventType;

    /** When the event happened (UTC). */
    Instant occurredAt;

    /**
     * Shared across every event/call belonging to the same business flow.
     * For a root event this equals {@code eventId}.
     */
    UUID correlationId;

    /** Which service published this, e.g. "merchant-service". */
    String producerService;

    /** The event-specific data. */
    T payload;
}
