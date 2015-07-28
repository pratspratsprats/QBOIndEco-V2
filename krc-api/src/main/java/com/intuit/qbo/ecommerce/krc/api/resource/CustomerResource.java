package com.intuit.qbo.ecommerce.krc.api.resource;


import static com.intuit.Constants.SERVER_ERROR;
import static com.intuit.Constants.SUCCESS;
import static com.intuit.Constants.SAMPLE_CUSTOMER_PAYLOAD;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.intuit.qbo.ecommerce.krc.model.Customer;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;


@Path("customers")
@Consumes({APPLICATION_JSON })
@Produces({APPLICATION_JSON })
@Api(value = "/customers", description = "API for accepting customer details from KartRocket")
public interface CustomerResource {
	@POST
	@ApiOperation(
		value = "create a new Customer", 
		response = Customer.class
	)
	@ApiResponses(value = {
	 		@ApiResponse(code = 200, message = SUCCESS),
            @ApiResponse(code = 500, message = SERVER_ERROR)
    })
   public Customer post( @ApiParam(defaultValue = SAMPLE_CUSTOMER_PAYLOAD, required = true) Customer e);
		
}
