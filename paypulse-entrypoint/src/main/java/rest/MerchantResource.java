package rest;

import api.ErrorResponse;
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

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @Inject
    RegisterMerchantUseCase registerMerchantUseCase;

    @Inject
    RegisterMerchantMapper registerMerchantMapper;

    @Inject
    RegisterMerchantDomainMapper registerMerchantDomainMapper;

    /**
     * Accepts a request to register new merchant,
     * maps the request to a domain command,
     * invokes the use case to register the merchant,
     * and returns an appropriate HTTP response.
     */
    @Override
    public Response registerMerchant(RegisterMerchantRequest registerMerchantRequest) {
        if (isInvalidRequest(registerMerchantRequest)) {
            ErrorResponse error = new ErrorResponse();
            error.errorCode = "400";
            error.message = "Invalid request data";
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(error)
                    .build();
        }

        var command = registerMerchantMapper.mapToCommand(registerMerchantRequest);
        var merchant = registerMerchantDomainMapper.toDomain(command);
        var result = registerMerchantUseCase.registerMerchant(merchant);
        return Response.ok(result).build();
    }

    private boolean isInvalidRequest(RegisterMerchantRequest request) {
        if (request == null || isBlank(request.name)) {
            return true;
        }
        return request.email == null || !request.email.matches(EMAIL_PATTERN);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
