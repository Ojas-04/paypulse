package com.paypulse.merchant.entrypoint.rest;

import com.paypulse.merchant.adapters.mapper.RegisterMerchantMapper;
import com.paypulse.merchant.entrypoint.api.MerchantApi;
import com.paypulse.merchant.model.dto.RegisterMerchantRequest;
import com.paypulse.merchant.ports.in.merchant.RegisterMerchantUseCase;
import com.paypulse.merchant.ports.in.merchant.ResolveMerchantUseCase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class MerchantResource implements MerchantApi {

    @Inject
    RegisterMerchantUseCase registerMerchantUseCase;

    @Inject
    ResolveMerchantUseCase resolveMerchantUseCase;

    @Inject
    RegisterMerchantMapper registerMerchantMapper;

    /**
     * Maps the request to a command and delegates to the use case. Payload
     * validation is declarative (bean validation on {@link RegisterMerchantRequest},
     * enforced via {@code @Valid} on {@link MerchantApi}).
     */
    @Override
    public Response registerMerchant(RegisterMerchantRequest registerMerchantRequest) {
        var command = registerMerchantMapper.mapToCommand(registerMerchantRequest);
        var merchant = registerMerchantUseCase.registerMerchant(command);
        return Response.ok(merchant).build();
    }

    @Override
    public Response resolveMerchant(String name, String merchantId) {
        var merchant = resolveMerchantUseCase.resolve(name, merchantId);
        return Response.ok(merchant).build();
    }
}
