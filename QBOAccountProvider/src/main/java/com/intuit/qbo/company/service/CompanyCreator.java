package com.intuit.qbo.company.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.intuit.qbo.company.model.CompanyResponse;
import com.intuit.qbo.company.model.Qbocompany;

/**
 * User and Company creator class
 * @author Amaresh Mourya
 * @date Oct 19, 2014
 **/

public class CompanyCreator {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyCreator.class);

	public CompanyResponse createUserAndCompany(Qbocompany data, boolean forProduction) {
		CompanyResponse companyCreated = null;
		try {

			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(PropertyProvider.getSignUpUrlEndPoint(forProduction));
			Gson gson = new Gson();
			String jsonString = gson.toJson(data);
			StringEntity stringEntity = new StringEntity(jsonString);
			post.setHeader("Content-Type", "application/json");
			post.setHeader("Accept", "application/json");
			post.setEntity(stringEntity);
			LOGGER.info("NewUserSignUp POST URL endpoint is: "+ PropertyProvider.getSignUpUrlEndPoint(forProduction));
			LOGGER.info("Payload: "+ jsonString);
			LOGGER.info("POSTing request to NewUserSignUp URL...");
			HttpResponse response = client.execute(post);
			LOGGER.info("Response Code is: "
					+ response.getStatusLine().getStatusCode());
			if (200 == response.getStatusLine().getStatusCode()) {
				BufferedReader rd = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent()));
				JsonReader reader = new JsonReader(rd);
				LOGGER.info("Converting JSON response to CompanyResponse object");
				CompanyResponse res = gson.fromJson(reader,
						CompanyResponse.class);
				if(res != null)
				{
					Qbocompany qbocompany = res.getQbocompany();
					if(qbocompany == null)
					{
						LOGGER.info("New Company not created, reason is: "+ res.getErrorMessage());
					}
					else
					{
					  LOGGER.info("New company is created");
					  companyCreated = res;
					}
				}
				else{
					LOGGER.info("Converted CompanyResponse object is null");
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception while creating user and company", e);
		}
		return companyCreated;
	}
}
