package com.paypulse.merchant.adapters.mapper;

import com.paypulse.merchant.adapters.out.MerchantJpaEntity;
import com.paypulse.merchant.domain.entity.Merchant;
import com.paypulse.merchant.domain.enums.MerchantStatus;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MerchantPersistenceMapper {

    public MerchantJpaEntity toEntity(Merchant merchant) {
        MerchantJpaEntity entity = new MerchantJpaEntity();
        entity.setMerchantId(merchant.getMerchantId());
        entity.setMerchantName(merchant.getMerchantName());
        entity.setMerchantEmail(merchant.getMerchantEmail());
        entity.setMerchantPhone(merchant.getMerchantPhone());
        entity.setMerchantStatus(MerchantStatus.valueOf(merchant.getMerchantStatus().name()));
        entity.setCreatedAt(merchant.getCreatedAt());
        entity.setUpdatedAt(merchant.getUpdatedAt());
        return entity;
    }

    public Merchant toDomain(MerchantJpaEntity merchantJpaEntity) {
        Merchant merchant = new Merchant();
        merchant.setMerchantId(merchantJpaEntity.getMerchantId());
        merchant.setMerchantName(merchantJpaEntity.getMerchantName());
        merchant.setMerchantEmail(merchantJpaEntity.getMerchantEmail());
        merchant.setMerchantPhone(merchantJpaEntity.getMerchantPhone());
        merchant.setMerchantStatus(com.paypulse.merchant.domain.enums.MerchantStatus.valueOf(merchantJpaEntity.getMerchantStatus().name()));
        merchant.setCreatedAt(merchantJpaEntity.getCreatedAt());
        merchant.setUpdatedAt(merchantJpaEntity.getUpdatedAt());
        return merchant;
    }
}
