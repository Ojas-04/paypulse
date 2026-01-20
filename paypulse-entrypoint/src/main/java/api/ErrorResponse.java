package api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Error response returned when an API call fails.")
public class ErrorResponse {
    @Schema(description = "Error code identifying the type of error", example = "INVALID_DATA")
    public String errorCode;

    @Schema(description = "Detailed error message", example = "Invalid merchant data")
    public String message;
}
