package com.paypulse.merchant.model.error;

import lombok.Getter;

/**
 * Stable, machine-readable error codes shared across the API surface
 * (01-PHASE0-CONVENTIONS.md §4). Each code maps to the HTTP status the
 * service responds with.
 */
@Getter
public enum ErrorCode {

    VALIDATION_FAILED(400),
    MERCHANT_NOT_FOUND(404),
    MERCHANT_ALREADY_EXISTS(409),
    INTERNAL_ERROR(500);

    private final int httpStatus;

    ErrorCode(int httpStatus) {
        this.httpStatus = httpStatus;
    }
}
