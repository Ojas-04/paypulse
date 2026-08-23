package com.paypulse.merchant.entrypoint.rest;

import com.paypulse.merchant.entrypoint.api.ErrorResponse;
import com.paypulse.merchant.application.exception.IllegalStateException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class IllegalStateExceptionMapper implements ExceptionMapper<IllegalStateException> {
    @Override
    public Response toResponse(IllegalStateException ex) {
        ErrorResponse error = new ErrorResponse();
        error.errorCode = ex.getErrorCode() != null ? ex.getErrorCode() : "409";
        error.message = ex.getMessage() != null ? ex.getMessage() : "Merchant already exists";
        return Response.status(Response.Status.CONFLICT)
                .entity(error)
                .build();
    }
}
