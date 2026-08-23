package com.paypulse.merchant.adapters.mapper;

import com.paypulse.merchant.domain.entity.Merchant;
import com.paypulse.merchant.model.command.RegisterMerchantCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface RegisterMerchantDomainMapper {
    @Mapping(source = "merchantName", target = "merchantName")
    @Mapping(source = "merchantEmail", target = "merchantEmail")
    @Mapping(source = "merchantPhone", target = "merchantPhone")
    @Mapping(target = "merchantId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "merchantStatus", ignore = true)
    Merchant toDomain(RegisterMerchantCommand command);
}
