package api;

import com.paypulse.model.dto.RegisterMerchantRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Path("/merchants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Merchant", description = "Operations related to merchants")
public interface MerchantApi {

    @Path("/register")
    @POST
    @Operation(summary = "Register a new merchant", description = "Creates a new merchant in the system.")
    @RequestBody(description = "Merchant registration request", required = true)
    @APIResponse(
        responseCode = "201",
        description = "Merchant registered successfully",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = com.paypulse.model.dto.RegisterMerchantRequest.class),
            examples = @ExampleObject(
                name = "Success",
                value = "{\"name\": \"John Doe\", \"email\": \"john@example.com\", \"phone\": \"1234567890\"}"
            )
        )
    )
    @APIResponse(
        responseCode = "400",
        description = "Invalid request data",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ErrorResponse.class),
            examples = @ExampleObject(
                name = "BadRequest",
                value = "{\"error\": \"Invalid merchant data\"}"
            )
        )
    )
    @APIResponse(
        responseCode = "409",
        description = "Merchant already exists",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ErrorResponse.class),
            examples = @ExampleObject(
                name = "Conflict",
                value = "{\"error\": \"Merchant already exists\"}"
            )
        )
    )
    @APIResponse(
        responseCode = "500",
        description = "Internal server error",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ErrorResponse.class),
            examples = @ExampleObject(
                name = "ServerError",
                value = "{\"error\": \"Unexpected error occurred\"}"
            )
        )
    )
    Response registerMerchant(RegisterMerchantRequest registerMerchantRequest);
}
