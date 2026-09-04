package com.paypulse.merchant.adapters.mapper;

import java.time.Instant;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.paypulse.merchant.domain.entity.Merchant;
import com.paypulse.merchant.model.event.EventEnvelope;
import com.paypulse.merchant.model.event.EventTypes;
import com.paypulse.merchant.model.event.MerchantRegisteredEvent;

/**
 * Builds the §1 event envelope for {@link EventTypes#MERCHANT_REGISTERED}.
 * The caller supplies a single generated eventId; correlationId mirrors it
 * because merchant registration is always a root event of its flow.
 */
@Mapper(componentModel = "cdi")
public interface MerchantEventMapper {

    @Mapping(target = "eventId", source = "envelopeEventId")
    @Mapping(target = "eventType", constant = EventTypes.MERCHANT_REGISTERED)
    @Mapping(target = "correlationId", source = "envelopeEventId")
    @Mapping(target = "producerService", constant = EventTypes.PRODUCER_MERCHANT_SERVICE)
    @Mapping(target = "payload", source = "merchant")
    EventEnvelope<MerchantRegisteredEvent> toEnvelope(Merchant merchant, UUID envelopeEventId, Instant occurredAt);

    @Mapping(target = "status", source = "merchantStatus")
    MerchantRegisteredEvent toPayload(Merchant merchant);
}
