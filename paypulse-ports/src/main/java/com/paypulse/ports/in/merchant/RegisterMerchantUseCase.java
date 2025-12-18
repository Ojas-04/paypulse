package com.paypulse.ports.in.merchant;

import com.paypulse.domain.entity.Merchant;

public interface RegisterMerchantUseCase {

    Merchant registerMerchant(Merchant merchant);

}
