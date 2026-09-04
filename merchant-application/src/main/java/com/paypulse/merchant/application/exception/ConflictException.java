package com.paypulse.merchant.application.exception;

import com.paypulse.merchant.model.error.ErrorCode;

/** The request conflicts with existing state (e.g. duplicate merchant). */
public class ConflictException extends ApiException {

    public ConflictException(String message) {
        super(ErrorCode.MERCHANT_ALREADY_EXISTS, message);
    }
}
