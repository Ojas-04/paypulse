package com.paypulse.merchant.adapters;

import java.util.Optional;
import java.util.UUID;

import com.paypulse.merchant.adapters.mapper.MerchantPersistenceMapper;
import com.paypulse.merchant.adapters.out.MerchantJpaEntity;
import com.paypulse.merchant.domain.entity.Merchant;
import com.paypulse.merchant.ports.out.merchant.MerchantPersistencePort;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class MerchantRepositoryAdapter implements MerchantPersistencePort {

    @Inject
    MerchantPersistenceMapper mapper;

    @Override
    @Transactional
    public void save(Merchant merchant) {
        mapper.toEntity(merchant).persist();
    }

    @Override
    @Transactional
    public Optional<Merchant> findByMerchantId(UUID merchantId) {
        return MerchantJpaEntity.find("merchantId", merchantId)
                .firstResultOptional()
                .map(entity -> mapper.toDomain((MerchantJpaEntity) entity));
    }

    @Override
    @Transactional
    public Optional<Merchant> findByMerchantName(String merchantName) {
        return MerchantJpaEntity.find("merchantName", merchantName)
                .firstResultOptional()
                .map(entity -> mapper.toDomain((MerchantJpaEntity) entity));
    }

    @Override
    @Transactional
    public Optional<Merchant> findByNameCaseInsensitive(String merchantName) {
        return MerchantJpaEntity.find("lower(merchantName) = lower(?1)", merchantName)
                .firstResultOptional()
                .map(entity -> mapper.toDomain((MerchantJpaEntity) entity));
    }
}
