package com.paypulse.merchant.application.exception;

import com.paypulse.merchant.model.error.ErrorCode;

import lombok.Getter;

/** Requested resource does not exist. */
public class NotFoundException extends ApiException {

    public NotFoundException(String message) {
        super(ErrorCode.MERCHANT_NOT_FOUND, message);
    }
}
