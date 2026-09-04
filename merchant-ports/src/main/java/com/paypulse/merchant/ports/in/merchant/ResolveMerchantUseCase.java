package com.paypulse.merchant.ports.in.merchant;

import com.paypulse.merchant.domain.entity.Merchant;

/**
 * Resolves a merchant from either its case-insensitive {@code merchant_name} or
 * its {@code merchantId}. Used by transaction-service's synchronous,
 * get-or-create flow (01-PHASE0-CONVENTIONS.md §7).
 */
public interface ResolveMerchantUseCase {

    Merchant resolve(String name, String merchantId);

    default Merchant resolveByName(String name) {
        return resolve(name, null);
    }

    default Merchant resolveById(String merchantId) {
        return resolve(null, merchantId);
    }
}