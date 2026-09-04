package com.paypulse.merchant.model.event;

import java.time.Instant;
import java.util.UUID;

import lombok.Builder;
import lombok.Value;

/**
 * Payload of {@link EventTypes#MERCHANT_REGISTERED}: emitted whenever a
 * merchant is registered. Optional fields (email, phone) remain {@code null}
 * when absent — never a blank/whitespace string.
 */
@Value
@Builder
public class MerchantRegisteredEvent {

    UUID merchantId;

    String merchantName;

    String merchantEmail;

    String merchantPhone;

    String status;

    Instant createdAt;

    Instant updatedAt;
}
