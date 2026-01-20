package com.paypulse.application.service.merchant;

import com.paypulse.domain.entity.Merchant;
import com.paypulse.ports.in.merchant.RegisterMerchantUseCase;
import com.paypulse.ports.out.merchant.MerchantPersistencePort;
import exception.IllegalStateException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import static com.paypulse.domain.enums.MerchantStatus.ACTIVE;
import static java.time.LocalTime.now;
import static java.util.UUID.randomUUID;

@ApplicationScoped
public class RegisterMerchantService implements RegisterMerchantUseCase {

    @Inject
    MerchantPersistencePort merchantPersistencePort;


    @Override
    public Merchant registerMerchant(Merchant merchant) {
        if (merchantPersistencePort.existsByEmail(merchant.getMerchantEmail())) {
            throw new IllegalStateException("Merchant already exists with", merchant.getMerchantEmail(), "403");
        }
        merchant.setMerchantId(randomUUID().toString());
        merchant.setCreatedAt(now().toString());
        merchant.setMerchantStatus(ACTIVE);
        return merchantPersistencePort.save(merchant);
    }
}
