package com.paypulse.merchant.application.service.merchant;

import java.time.Instant;
import java.util.UUID;

import com.paypulse.merchant.application.exception.InvalidRequestException;
import com.paypulse.merchant.domain.entity.Merchant;
import com.paypulse.merchant.domain.enums.MerchantStatus;
import com.paypulse.merchant.model.command.RegisterMerchantCommand;
import com.paypulse.merchant.ports.in.merchant.RegisterMerchantUseCase;
import com.paypulse.merchant.ports.out.event.MerchantEventPublisherPort;
import com.paypulse.merchant.ports.out.merchant.MerchantPersistencePort;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

/**
 * Registration is idempotent on {@code merchantName}: a duplicate returns the
 * existing merchant, and the event is published (via the outbox, in the same
 * DB transaction) only for genuinely new merchants — §6.
 */
@ApplicationScoped
public class RegisterMerchantService implements RegisterMerchantUseCase {

    private final MerchantPersistencePort merchantPersistencePort;
    private final MerchantEventPublisherPort merchantEventPublisherPort;

    @Inject
    public RegisterMerchantService(MerchantPersistencePort merchantPersistencePort,
                                   MerchantEventPublisherPort merchantEventPublisherPort) {
        this.merchantPersistencePort = merchantPersistencePort;
        this.merchantEventPublisherPort = merchantEventPublisherPort;
    }

    @Override
    @Transactional
    public Merchant registerMerchant(RegisterMerchantCommand command) {
        if (command == null) {
            throw new InvalidRequestException("command is required");
        }

        String name = normalize(command.getMerchantName());
        if (name == null) {
            throw new InvalidRequestException("merchantName is required");
        }

        return merchantPersistencePort.findByMerchantName(name)
                .orElseGet(() -> createMerchant(command, name));
    }

    private Merchant createMerchant(RegisterMerchantCommand command, String name) {
        Instant now = Instant.now();
        Merchant merchant = Merchant.builder()
                .merchantId(UUID.randomUUID())
                .merchantName(name)
                .merchantEmail(normalize(command.getMerchantEmail()))
                .merchantPhone(normalize(command.getMerchantPhone()))
                .createdAt(now)
                .updatedAt(now)
                .merchantStatus(MerchantStatus.ACTIVE)
                .build();

        merchantPersistencePort.save(merchant);
        merchantEventPublisherPort.publish(merchant);
        return merchant;
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}