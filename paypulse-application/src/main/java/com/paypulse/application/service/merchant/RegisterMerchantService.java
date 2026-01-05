package com.paypulse.application.service.merchant;

import com.paypulse.domain.entity.Merchant;
import com.paypulse.ports.in.merchant.RegisterMerchantUseCase;
import com.paypulse.ports.out.merchant.MerchantPersistencePort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RegisterMerchantService implements RegisterMerchantUseCase {

    private final MerchantPersistencePort merchantPersistencePort;

    @Inject
    public RegisterMerchantService(MerchantPersistencePort merchantPersistencePort){
        this.merchantPersistencePort = merchantPersistencePort;
    }

    @Override
    public Merchant registerMerchant(Merchant merchant) {
        if (merchantPersistencePort.existsByEmail(merchant.getMerchantEmail())) {
            throw new IllegalStateException("Merchant already exists");
        }
        return merchantPersistencePort.save(merchant);
    }
}
