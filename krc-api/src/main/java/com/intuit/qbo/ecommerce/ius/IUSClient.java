package com.intuit.qbo.ecommerce.ius;

import java.util.Collections;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class IUSClient {

	private static final Logger LOG = LoggerFactory.getLogger(IUSClient.class);

	private String tokenTypeHeader = "intuit_token_type=IAM-Ticket";
	private String tokenHeaderKey = "intuit_token=";
	private String userIdHeaderKey = "intuit_userid=";

	// // Prod (accounts)
	private String intuitOfferingId = "Intuit.cto.event_toolkit";
	private String authorizationHeader = "Intuit_IAM_Authentication intuit_appid=Intuit.cto.event_toolkit,intuit_app_secret=prdaDt8QjUGeSkETjo1kQWWNc3JlQxtMZVQXuzTi";
	private String urlBase = "https://accounts.platform.intuit.com";

	private String intuitOriginatingIp = "123.45.67.89";
	private String iusLogin = urlBase + "/v1/iamtickets/";
	private String iusUserBase = urlBase + "/v1/users";

	/**
	 * Create and provide the APIKey to be used for all calls by this instance.
	 * Generally, this will by the "partner" APIKey assigned to the Governance
	 * Engine
	 */
	public IUSClient() {
	}

	public JsonObject login(String user, String pass) {
		String intuitTid = new Date().getTime() + "-event_toolkit-server";
		ResponseEntity<String> resEntity = null;
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("username", user);
		jsonObject.addProperty("password", pass);

		HttpHeaders requestHeaders = new HttpHeaders();

		Gson gson = new Gson();
		String reqBody = gson.toJson(jsonObject);

		MediaType mediaTypeJson = new MediaType("application", "json");
		requestHeaders.setAccept(Collections.singletonList(mediaTypeJson));
		requestHeaders.setContentType(mediaTypeJson);
		requestHeaders.add("Authorization", authorizationHeader);
		requestHeaders.add("intuit_originatingip", intuitOriginatingIp);
		requestHeaders.add("intuit_offeringId", intuitOfferingId);
		requestHeaders.add("intuit_tid", intuitTid);

		HttpEntity<String> requestEntity = new HttpEntity<String>(reqBody, requestHeaders);

		RestTemplate template = new RestTemplate();
		try {
			resEntity = template.postForEntity(iusLogin, requestEntity, String.class);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (resEntity == null) {
			LOG.error("IUSClient signIn not successfull for request body :" + reqBody);
			return null;
		}

		String resBody = resEntity.getBody();
		JsonObject userObject = gson.fromJson(resBody, JsonObject.class);

		return userObject;

	}

	public JsonObject updatePassword(String userId, String ticket, String oldPassword,
			String newPassword) {
		String intuitTid = new Date().getTime() + "-event_toolkit-server";
		String resEntity = null;
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("oldPassword", oldPassword);
		jsonObject.addProperty("newPassword", newPassword);

		HttpHeaders requestHeaders = new HttpHeaders();

		Gson gson = new Gson();
		String reqBody = gson.toJson(jsonObject);

		MediaType mediaTypeJson = new MediaType("application", "json");
		requestHeaders.setAccept(Collections.singletonList(mediaTypeJson));
		requestHeaders.setContentType(mediaTypeJson);
		requestHeaders.add("Authorization", authorizationHeader + "," + tokenTypeHeader + ","
				+ tokenHeaderKey + ticket + "," + userIdHeaderKey + userId);
		requestHeaders.add("intuit_originatingip", intuitOriginatingIp);
		requestHeaders.add("intuit_offeringId", intuitOfferingId);
		requestHeaders.add("intuit_tid", intuitTid);

		HttpEntity<String> requestEntity = new HttpEntity<String>(reqBody, requestHeaders);

		RestTemplate template = new RestTemplate();
		try {

			resEntity = template.exchange(iusUserBase + "/" + userId + "/password", HttpMethod.PUT,
					requestEntity, String.class).getBody();

		} catch (Exception ex) {
		}

		JsonObject userObject = gson.fromJson(resEntity, JsonObject.class);

		return userObject;

	}

	public String getIntuitOfferingId() {
		return intuitOfferingId;
	}

	public void setIntuitOfferingId(String intuitOfferingId) {
		this.intuitOfferingId = intuitOfferingId;
	}

	public String getAuthorizationHeader() {
		return authorizationHeader;
	}

	public void setAuthorizationHeader(String authorizationHeader) {
		this.authorizationHeader = authorizationHeader;
	}

	public String getUrlBase() {
		return urlBase;
	}

	public void setUrlBase(String urlBase) {
		this.urlBase = urlBase;
	}

	public String getIusLogin() {
		return iusLogin;
	}

	public void setIusLogin(String iusLogin) {
		this.iusLogin = iusLogin;
	}

}
