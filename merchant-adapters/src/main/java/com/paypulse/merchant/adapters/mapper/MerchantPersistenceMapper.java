package com.paypulse.merchant.adapters.mapper;

import org.mapstruct.Mapper;

import com.paypulse.merchant.adapters.out.MerchantJpaEntity;
import com.paypulse.merchant.domain.entity.Merchant;

@Mapper(componentModel = "cdi")
public interface MerchantPersistenceMapper {

    MerchantJpaEntity toEntity(Merchant merchant);

    Merchant toDomain(MerchantJpaEntity merchantJpaEntity);
}
