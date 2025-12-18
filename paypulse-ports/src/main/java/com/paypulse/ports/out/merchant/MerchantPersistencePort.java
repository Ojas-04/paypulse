package com.paypulse.ports.out.merchant;

import com.paypulse.domain.entity.Merchant;

public interface MerchantPersistencePort {

    Merchant save(Merchant merchant);
}
