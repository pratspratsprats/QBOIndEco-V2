package com.intuit.qbo.ecommerce.krc.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.intuit.qbo.ecommerce.krc.ValidationException;
import com.intuit.qbo.ecommerce.krc.model.Error;

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
    	Error error = new Error();
    	error.setMessage(e.getLocalizedMessage());
    	
    	if(e instanceof ValidationException){
    		return Response.status(Status.BAD_REQUEST)
    	            .entity(error)
    	            .build();
    	}
    	
    	return Response.serverError()
            .entity(error)
            .build();
    }
}