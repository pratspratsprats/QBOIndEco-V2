package com.intuit.incubation.company;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.intuit.incubation.model.QBOCompany;
import com.intuit.incubation.model.QBOCompanyResponse;


@Component
public class QBOCompanyCreator {

	private Logger LOGGER = LoggerFactory.getLogger(QBOCompanyCreator.class);

	public QBOCompanyResponse create(QBOCompany company) {

		Gson gson = new Gson();
		String url = "https://applifecycle-e2e.platform.intuit.com/prototypes/v1/companies";
		String reqBody = gson.toJson(company);

		MediaType mediaTypeJson = new MediaType("application", "json");
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(mediaTypeJson));
		requestHeaders.setContentType(mediaTypeJson);
		HttpEntity<String> requestEntity = new HttpEntity<String>(reqBody,
				requestHeaders);

		// Create a new RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> resEntity = restTemplate.postForEntity(url,
				requestEntity, String.class);

		if (resEntity.getStatusCode() == HttpStatus.OK) {
			String resBody = resEntity.getBody();
			QBOCompanyResponse companyResponse = gson.fromJson(resBody,
					QBOCompanyResponse.class);
			return companyResponse;
		} else {
			LOGGER.error("Error creating company, Http Status Code:"
					+ resEntity.getStatusCode());
		}
		return null;
	}

}
