package com.paypulse.merchant.ports.in.merchant;

import com.paypulse.merchant.domain.entity.Merchant;
import com.paypulse.merchant.model.command.RegisterMerchantCommand;

public interface RegisterMerchantUseCase {

    Merchant registerMerchant(RegisterMerchantCommand command);
}
