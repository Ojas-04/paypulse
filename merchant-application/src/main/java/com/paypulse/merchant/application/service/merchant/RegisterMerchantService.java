package com.paypulse.merchant.application.service.merchant;

import com.paypulse.merchant.domain.entity.Merchant;
import com.paypulse.merchant.ports.in.merchant.RegisterMerchantUseCase;
import com.paypulse.merchant.ports.out.merchant.MerchantPersistencePort;
import com.paypulse.merchant.application.exception.IllegalStateException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import static com.paypulse.merchant.domain.enums.MerchantStatus.ACTIVE;
import static java.time.LocalTime.now;
import static java.util.UUID.randomUUID;

@ApplicationScoped
public class RegisterMerchantService implements RegisterMerchantUseCase {

    @Inject
    MerchantPersistencePort merchantPersistencePort;


    @Override
    public Merchant registerMerchant(Merchant merchant) {
        if (merchantPersistencePort.existsByEmail(merchant.getMerchantEmail())) {
            throw new IllegalStateException("Merchant already exists with", merchant.getMerchantEmail(), "409");
        }
        merchant.setMerchantId(randomUUID().toString());
        merchant.setCreatedAt(now().toString());
        merchant.setMerchantStatus(ACTIVE);
        return merchantPersistencePort.save(merchant);
    }
}
