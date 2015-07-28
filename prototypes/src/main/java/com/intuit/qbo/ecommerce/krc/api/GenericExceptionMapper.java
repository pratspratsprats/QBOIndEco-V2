package com.intuit.qbo.ecommerce.krc.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.intuit.qbo.ecommerce.krc.ValidationException;

/**
 * 
 * @author Vijayan Srinivasan
 * @since Jan 12, 2014 2:14:35 AM
 *
 */
@Provider
@Component
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
    public Response toResponse(Throwable e) {
    	if(e instanceof ValidationException){
    		return Response.status(Status.BAD_REQUEST)
    	            .entity(e.getMessage())
    	            .build();
    	}
    	
    	return Response.serverError()
            .entity(e.getMessage())
            .build();
    }
}