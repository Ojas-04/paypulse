package com.paypulse.merchant.model.event;

/**
 * Canonical topic names and event types for events produced by this service.
 *
 * Topic convention: {@code paypulse.<domain>.<event-name>.v<version>}
 * (see 01-PHASE0-CONVENTIONS.md §2).
 */
public final class EventTypes {

    /** Producer identity written into every {@link EventEnvelope}. */
    public static final String PRODUCER_MERCHANT_SERVICE = "merchant-service";

    /** eventType for a newly registered merchant. */
    public static final String MERCHANT_REGISTERED = "merchant.registered.v1";

    /** Topic carrying {@link #MERCHANT_REGISTERED} events. */
    public static final String TOPIC_MERCHANT_REGISTERED_V1 = "paypulse.merchant.registered.v1";

    private EventTypes() {
    }
}
