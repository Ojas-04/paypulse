package com.paypulse.adapters;

import com.paypulse.adapters.mapper.MerchantPersistenceMapper;
import com.paypulse.adapters.out.MerchantJpaEntity;
import com.paypulse.domain.entity.Merchant;
import com.paypulse.ports.out.merchant.MerchantPersistencePort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
public class MerchantRepositoryAdapter implements MerchantPersistencePort {

    @Inject
    MerchantPersistenceMapper mapper;

    /**
     * @param merchant
     * @return
     */
    @Override
    public Merchant save(Merchant merchant) {
        MerchantJpaEntity entity = mapper.toEntity(merchant);
        entity.persist();
        return mapper.toDomain(entity);
    }

    /**
     * @param merchantId
     * @return
     */
    @Override
    public Optional<Merchant> findByMerchantId(String merchantId) {
        return MerchantJpaEntity
                .find("merchantId", merchantId)
                .firstResultOptional()
                .map(entity ->mapper.toDomain((MerchantJpaEntity) entity));
    }

    /**
     * @param email
     * @return
     */
    @Override
    public boolean existsByEmail(String email) {
        return MerchantJpaEntity
                .count("merchantEmail", email) > 0;
    }
}
