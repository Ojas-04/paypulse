package com.paypulse.merchant.entrypoint.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paypulse.merchant.entrypoint.api.ErrorResponse;
import com.paypulse.merchant.model.error.ErrorCode;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Last-resort mapper so unexpected failures still surface in the standard
 * error shape (§4) instead of a framework default page.
 */
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionMapper.class);

    @Override
    public Response toResponse(Exception ex) {
        if (ex instanceof WebApplicationException wae) {
            return wae.getResponse();
        }
        LOG.error("Unhandled failure", ex);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ErrorResponse.of(ErrorCode.INTERNAL_ERROR, "An unexpected error occurred"))
                .build();
    }
}
