package com.paypulse.merchant.entrypoint.api;

import com.paypulse.merchant.domain.entity.Merchant;
import com.paypulse.merchant.model.dto.RegisterMerchantRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Path("/merchants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "merchant", description = "Operations related to merchants")
public interface MerchantApi {

    @Path("/register")
    @POST
    @Operation(summary = "Register a new merchant", description = "Creates a new merchant in the system.")
    @RequestBody(description = "Merchant registration request", required = true)
    @APIResponse(
        responseCode = "200",
        description = "Merchant registered (or already existed) — returns the merchant"
    )
    @APIResponse(
        responseCode = "400",
        description = "Validation failed (errorCode: VALIDATION_FAILED)",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ErrorResponse.class)
        )
    )
    @APIResponse(
        responseCode = "409",
        description = "Merchant already exists but could not be re-fetched (errorCode: MERCHANT_ALREADY_EXISTS)",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ErrorResponse.class)
        )
    )
    @APIResponse(
        responseCode = "500",
        description = "Internal server error",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ErrorResponse.class)
        )
    )
    Response registerMerchant(RegisterMerchantRequest registerMerchantRequest);

    @Path("/resolve")
    @GET
    @Operation(
        summary = "Resolve a merchant",
        description = "Resolves a merchant by its case-insensitive merchant_name, by merchantId, " +
            "or by both when they agree. At least one of name/id is required."
    )
    @APIResponse(
        responseCode = "200",
        description = "The resolved merchant",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = Merchant.class)
        )
    )
    @APIResponse(
        responseCode = "400",
        description = "Neither name nor id supplied, or they resolve to different merchants (errorCode: VALIDATION_FAILED)",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ErrorResponse.class)
        )
    )
    @APIResponse(
        responseCode = "404",
        description = "Merchant not found for the supplied name/id (errorCode: MERCHANT_NOT_FOUND)",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ErrorResponse.class)
        )
    )
    Response resolveMerchant(
        @Parameter(description = "Merchant name (case-insensitive)") @QueryParam("name") String name,
        @Parameter(description = "Merchant UUID") @QueryParam("id") String merchantId);
}
