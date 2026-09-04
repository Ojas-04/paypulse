package com.paypulse.merchant.application.exception;

import com.paypulse.merchant.model.error.ErrorCode;

/** Request payload failed validation. */
public class InvalidRequestException extends ApiException {

    public InvalidRequestException(String message) {
        super(ErrorCode.VALIDATION_FAILED, message);
    }
}
