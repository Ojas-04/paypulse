package com.paypulse.merchant.entrypoint.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paypulse.merchant.application.exception.ApiException;
import com.paypulse.merchant.entrypoint.api.ErrorResponse;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Maps business exceptions onto the standard error shape (§4). HTTP status is
 * derived from the exception's {@link com.paypulse.merchant.model.error.ErrorCode}.
 */
@Provider
public class ApiExceptionMapper implements ExceptionMapper<ApiException> {

    private static final Logger LOG = LoggerFactory.getLogger(ApiExceptionMapper.class);

    @Override
    public Response toResponse(ApiException ex) {
        LOG.debug("Business failure [{}]: {}", ex.getErrorCode(), ex.getMessage());
        return Response.status(ex.getErrorCode().getHttpStatus())
                .entity(ErrorResponse.of(ex.getErrorCode(), ex.getMessage()))
                .build();
    }
}
