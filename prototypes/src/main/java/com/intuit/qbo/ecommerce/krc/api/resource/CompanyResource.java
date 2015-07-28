package com.intuit.qbo.ecommerce.krc.api.resource;

import static com.intuit.Constants.SERVER_ERROR;
import static com.intuit.Constants.SUCCESS;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
@Path("companies")
@Consumes({ APPLICATION_JSON })
@Produces({ APPLICATION_JSON })
@Api(value = "/companies", description = "Create QBO Company, User, India Free Subscription")
public interface CompanyResource {
	
	@POST
	@ApiOperation(value = "create a new company")
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS),
			@ApiResponse(code = 500, message = SERVER_ERROR) })
	public Response post(String company)
			throws Exception;

}
