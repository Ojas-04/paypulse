package com.paypulse.merchant.ports.out.merchant;

import java.util.Optional;
import java.util.UUID;

import com.paypulse.merchant.domain.entity.Merchant;

public interface MerchantPersistencePort {

    void save(Merchant merchant);

    Optional<Merchant> findByMerchantId(UUID merchantId);

    Optional<Merchant> findByMerchantName(String merchantName);

    Optional<Merchant> findByNameCaseInsensitive(String merchantName);
}
