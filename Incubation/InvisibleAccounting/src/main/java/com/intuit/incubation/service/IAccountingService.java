package com.intuit.incubation.service;

import static com.intuit.incubation.helper.Constants.SAMPLE_CUSTOMER_PAYLOAD;
import static com.intuit.incubation.helper.Constants.SERVER_ERROR;
import static com.intuit.incubation.helper.Constants.SUCCESS;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.intuit.incubation.exception.InvalidArgumentException;
import com.intuit.incubation.exception.ServiceException;
import com.intuit.incubation.model.Order;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
@Path("accounting")
@Consumes({APPLICATION_JSON})
@Produces({APPLICATION_JSON,TEXT_PLAIN })
@Api(value = "/accounting", description = "API for accepting orders from marketplaces")
public interface IAccountingService {
	@POST
	@ApiOperation(
		value = "A new order is placed"
	)
	@ApiResponses(value = {
	 		@ApiResponse(code = 200, message = SUCCESS),
            @ApiResponse(code = 500, message = SERVER_ERROR)
    })
   public void newOrderPlaced( @ApiParam(defaultValue = SAMPLE_CUSTOMER_PAYLOAD, required = true) Order order) throws ServiceException;
}
