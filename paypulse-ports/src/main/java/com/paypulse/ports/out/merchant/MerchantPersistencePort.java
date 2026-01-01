package com.paypulse.ports.out.merchant;

import com.paypulse.domain.entity.Merchant;

import java.util.Optional;

public interface MerchantPersistencePort {

    Merchant save(Merchant merchant);

    Optional<Merchant> findByMerchantId(String merchantId);

    boolean existsByEmail(String email);
}
