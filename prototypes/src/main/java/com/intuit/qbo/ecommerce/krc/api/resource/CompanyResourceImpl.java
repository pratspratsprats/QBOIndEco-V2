/**
 * 
 */
package com.intuit.qbo.ecommerce.krc.api.resource;

import java.util.Collections;

import javax.ws.rs.core.Response;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Vijayan Srinivasan
 * @since Nov 4, 2014 6:40:06 PM
 * 
 */
@Component
public class CompanyResourceImpl implements CompanyResource {

	public Response post(String company) throws Exception {
		String url = "https://internal.qbo.intuit.com/qbo37/productservice/v1/signupnewuser?minorVersion=.0.1";

		MediaType mediaTypeJson = new MediaType("application", "json");
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(mediaTypeJson));
		requestHeaders.setContentType(mediaTypeJson);
		HttpEntity<String> requestEntity = new HttpEntity<String>(company,
				requestHeaders);

		// Create a new RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> resEntity = restTemplate.postForEntity(url,
				requestEntity, String.class);

		if(resEntity.hasBody()){
			String resBody = resEntity.getBody();
			return Response.status(resEntity.getStatusCode().value()).entity(resBody).build();
		}else{
			return Response.status(resEntity.getStatusCode().value()).build();
		}
	}

}
