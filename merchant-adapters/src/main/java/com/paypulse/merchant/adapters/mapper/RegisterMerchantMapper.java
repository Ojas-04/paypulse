package com.paypulse.merchant.adapters.mapper;

import com.paypulse.merchant.model.command.RegisterMerchantCommand;
import com.paypulse.merchant.model.dto.RegisterMerchantRequest;
import jakarta.enterprise.context.ApplicationScoped;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
@ApplicationScoped
public interface RegisterMerchantMapper {

    @Mapping(source = "name", target = "merchantName")
    @Mapping(source = "email", target = "merchantEmail")
    @Mapping(source = "phone", target = "merchantPhone")
    RegisterMerchantCommand mapToCommand(RegisterMerchantRequest registerMerchantRequest);

}
