package com.paypulse.merchant.application.service.merchant;

import java.util.Optional;
import java.util.UUID;

import com.paypulse.merchant.application.exception.InvalidRequestException;
import com.paypulse.merchant.application.exception.NotFoundException;
import com.paypulse.merchant.domain.entity.Merchant;
import com.paypulse.merchant.ports.in.merchant.ResolveMerchantUseCase;
import com.paypulse.merchant.ports.out.merchant.MerchantPersistencePort;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

/**
 * Resolves a merchant by case-insensitive name, by merchantId, or by both when
 * they agree. Without any resolvable identifier, or when the two identifiers
 * resolve to different merchants, the request is rejected (§4).
 */
@ApplicationScoped
public class ResolveMerchantService implements ResolveMerchantUseCase {

    @Inject
    MerchantPersistencePort merchantPersistencePort;

    @Override
    @Transactional
    public Merchant resolve(String name, String merchantId) {
        String normalizedName = normalize(name);
        UUID id = parseId(merchantId);

        boolean hasName = normalizedName != null;
        boolean hasId = id != null;

        if (!hasName && !hasId) {
            throw new InvalidRequestException("At least one of name or merchantId must be provided");
        }

        if (hasName && hasId) {
            return resolveBoth(normalizedName, id);
        }
        return resolveSingle(hasName ? normalizedName : null, hasId ? id : null);
    }

    private Merchant resolveBoth(String name, UUID id) {
        Merchant byName = merchantPersistencePort.findByNameCaseInsensitive(name)
                .orElseThrow(() -> new NotFoundException("No merchant found for name '" + name + "'"));
        Merchant byId = merchantPersistencePort.findByMerchantId(id)
                .orElseThrow(() -> new NotFoundException("No merchant found for id '" + id + "'"));

        if (!byName.getMerchantId().equals(byId.getMerchantId())) {
            throw new InvalidRequestException(
                    "name and merchantId resolve to different merchants (name -> " + byName.getMerchantId()
                            + ", id -> " + byId.getMerchantId() + ")");
        }
        return byName;
    }

    private Merchant resolveSingle(String name, UUID id) {
        Optional<Merchant> match = name != null
                ? merchantPersistencePort.findByNameCaseInsensitive(name)
                : merchantPersistencePort.findByMerchantId(id);
        return match.orElseThrow(() -> name != null
                ? new NotFoundException("No merchant found for name '" + name + "'")
                : new NotFoundException("No merchant found for id '" + id + "'"));
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private UUID parseId(String merchantId) {
        if (merchantId == null || merchantId.isBlank()) {
            return null;
        }
        try {
            return UUID.fromString(merchantId.trim());
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("merchantId must be a valid UUID, but was '" + merchantId + "'");
        }
    }
}
