package com.paypulse.adapters.mapper;

import com.paypulse.adapters.out.MerchantJpaEntity;
import com.paypulse.domain.entity.Merchant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface MerchantPersistenceMapper {

    MerchantJpaEntity toEntity(Merchant merchant);

    Merchant toDomain(MerchantJpaEntity entity);
}
