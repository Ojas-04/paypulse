package com.paypulse.merchant.ports.in.merchant;

import com.paypulse.merchant.domain.entity.Merchant;

public interface RegisterMerchantUseCase {

    Merchant registerMerchant(Merchant merchant);

}
