package com.paypulse.merchant.entrypoint.api;

import java.time.Instant;
import java.util.UUID;

import org.slf4j.MDC;

import com.paypulse.merchant.model.error.ErrorCode;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Standard REST error response shape (01-PHASE0-CONVENTIONS.md §4).
 * Build via {@link #of(ErrorCode, String)} — never construct ad hoc.
 */
@Schema(description = "Error response returned when an API call fails.")
public class ErrorResponse {

    @Schema(description = "Stable, machine-readable error code", example = "MERCHANT_NOT_FOUND")
    public final String errorCode;

    @Schema(description = "Human-readable explanation")
    public final String message;

    @Schema(description = "When the error occurred (UTC)", example = "2026-08-24T10:15:30Z")
    public final Instant timestamp;

    @Schema(description = "Correlates this error to logs/traces", example = "5f8b1c2e-9a4d-4e6f-b7c8-d1a2b3c4d5e6")
    public final String traceId;

    private ErrorResponse(String errorCode, String message, Instant timestamp, String traceId) {
        this.errorCode = errorCode;
        this.message = message;
        this.timestamp = timestamp;
        this.traceId = traceId;
    }

    public static ErrorResponse of(ErrorCode code, String message) {
        return new ErrorResponse(code.name(), message, Instant.now(), currentTraceId());
    }

    private static String currentTraceId() {
        String traceId = MDC.get("traceId");
        return traceId != null ? traceId : UUID.randomUUID().toString();
    }
}
