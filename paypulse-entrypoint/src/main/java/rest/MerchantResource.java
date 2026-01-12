package rest;

import api.MerchantApi;
import com.paypulse.adapters.mapper.RegisterMerchantMapper;
import com.paypulse.adapters.mapper.RegisterMerchantDomainMapper;
import com.paypulse.model.dto.RegisterMerchantRequest;
import com.paypulse.ports.in.merchant.RegisterMerchantUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class MerchantResource implements MerchantApi {

    @Inject
    RegisterMerchantUseCase registerMerchantUseCase;

    @Inject
    RegisterMerchantMapper registerMerchantMapper;

    @Inject
    RegisterMerchantDomainMapper registerMerchantDomainMapper;

    /**
     * @return
     */
    @Override
    public Response registerMerchant(RegisterMerchantRequest registerMerchantRequest) {
        var command = registerMerchantMapper.mapToCommand(registerMerchantRequest);
        var merchant = registerMerchantDomainMapper.toDomain(command);
        var result = registerMerchantUseCase.registerMerchant(merchant);
        return Response.ok(result).build();
    }
}
