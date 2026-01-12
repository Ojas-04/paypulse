package com.paypulse.application.service.merchant;

import com.paypulse.domain.entity.Merchant;
import com.paypulse.ports.in.merchant.RegisterMerchantUseCase;
import com.paypulse.ports.out.merchant.MerchantPersistencePort;
import exception.IllegalStateException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import static com.paypulse.domain.enums.MerchantStatus.ACTIVE;

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
            throw new IllegalStateException("Merchant already exists with", new IllegalStateException() ,merchant.getMerchantEmail());
        }
        merchant.setMerchantId(java.util.UUID.randomUUID().toString());
        String now = java.time.Instant.now().toString();
        merchant.setCreatedAt(now);
        merchant.setUpdatedAt(now);
        merchant.setMerchantStatus(ACTIVE);
        return merchantPersistencePort.save(merchant);
    }
}
