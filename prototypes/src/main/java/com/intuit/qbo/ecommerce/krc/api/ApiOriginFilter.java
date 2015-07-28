package com.intuit.qbo.ecommerce.krc.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;


/**
 * 
 * @author Vijayan Srinivasan
 * @since Dec 24, 2013 6:41:10 PM
 *
 */

public class ApiOriginFilter implements ContainerResponseFilter {
    
	@Override
	public ContainerResponse filter(ContainerRequest req,
			ContainerResponse contResp) {

		ResponseBuilder resp = Response.fromResponse(contResp.getResponse());
		resp.header("Access-Control-Allow-Origin", "*");
		resp.header("Access-Control-Allow-Methods",
				"GET, POST, PUT, DELETE, HEAD, OPTIONS");

		String reqHead = req.getHeaderValue("Access-Control-Request-Headers");
		if (null != reqHead && !reqHead.equals(null)) {
			resp.header("Access-Control-Allow-Headers", reqHead);
		}
		contResp.setResponse(resp.build());

		return contResp;
	}
}