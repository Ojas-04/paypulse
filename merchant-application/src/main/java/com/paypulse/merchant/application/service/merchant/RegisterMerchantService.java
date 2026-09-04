package com.paypulse.merchant.application.service.merchant;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.paypulse.merchant.application.exception.InvalidRequestException;
import com.paypulse.merchant.domain.entity.Merchant;
import com.paypulse.merchant.model.command.RegisterMerchantCommand;
import com.paypulse.merchant.ports.in.merchant.RegisterMerchantUseCase;
import com.paypulse.merchant.ports.out.event.MerchantEventPublisherPort;
import com.paypulse.merchant.ports.out.merchant.MerchantPersistencePort;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import static com.paypulse.merchant.domain.enums.MerchantStatus.ACTIVE;

/**
 * Registration is idempotent on {@code merchant_name}: a duplicate returns the
 * existing merchant, and the event is published (via the outbox, in the same
 * DB transaction) only for genuinely new merchants — §6.
 */
@ApplicationScoped
public class RegisterMerchantService implements RegisterMerchantUseCase {

    @Inject
    MerchantPersistencePort merchantPersistencePort;

    @Inject
    MerchantEventPublisherPort merchantEventPublisherPort;

    @Override
    @Transactional
    public Merchant registerMerchant(RegisterMerchantCommand command) {
        String name = normalize(command.getMerchantName());
        if (name == null) {
            throw new InvalidRequestException("merchantName is required");
        }

        Optional<Merchant> existing = merchantPersistencePort.findByMerchantName(name);
        if (existing.isPresent()) {
            return existing.get();
        }

        Instant now = Instant.now();
        Merchant merchant = Merchant.builder()
                            .merchantId(UUID.randomUUID())
                            .merchantName(command.getMerchantName())
                            .merchantEmail(normalize(command.getMerchantEmail()))
                            .merchantPhone(normalize(command.getMerchantPhone()))
                            .createdAt(now)
                            .updatedAt(now)
                            .merchantStatus(ACTIVE)
                            .build();

        merchantPersistencePort.save(merchant);
        merchantEventPublisherPort.publish(merchant);
        return merchant;
    }

    private String normalize(String value) {
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
