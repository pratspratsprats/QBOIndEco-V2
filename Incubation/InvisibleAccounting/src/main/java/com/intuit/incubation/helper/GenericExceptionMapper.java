package com.intuit.incubation.helper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.intuit.incubation.exception.InvalidArgumentException;
import com.intuit.incubation.model.Error;

@Provider
@Component
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
    public Response toResponse(Throwable e) {
    	Error error = new Error();
    	error.setMessage(e.getLocalizedMessage());
    	
    	if(e instanceof InvalidArgumentException){
    		return Response.status(Status.BAD_REQUEST)
    	            .entity(error)
    	            .build();
    	}
    	
    	return Response.serverError()
            .entity(error)
            .build();
    }
}