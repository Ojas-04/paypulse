package com.paypulse.merchant.application.exception;

import com.paypulse.merchant.model.error.ErrorCode;

import lombok.Getter;

/**
 * Base for business exceptions carrying a stable {@link ErrorCode}
 * (01-PHASE0-CONVENTIONS.md §4). The entrypoint maps these onto the standard
 * REST error response shape.
 */
@Getter
public abstract class ApiException extends RuntimeException {

    private final transient ErrorCode errorCode;

    protected ApiException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
