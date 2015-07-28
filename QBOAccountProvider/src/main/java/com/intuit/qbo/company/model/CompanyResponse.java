package com.intuit.qbo.company.model;

import java.io.Serializable;

/**
 * Class to contain response from ProductService apis
 * @author Amaresh Mourya
 * @date Oct 19, 2014
 **/

public class CompanyResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String success;
	private String errorCode;
	private String errorMessage;
	private String companyId;
	private String userAuthId;
	private String agentId;
	private String parentId;
	private String homeUrl;
	private String ticket;
	private String authId;
	private Qbocompany qbocompany;
	
	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getUserAuthId() {
		return userAuthId;
	}

	public void setUserAuthId(String userAuthId) {
		this.userAuthId = userAuthId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getHomeUrl() {
		return homeUrl;
	}

	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public Qbocompany getQbocompany() {
		return qbocompany;
	}

	public void setQbocompany(Qbocompany qbocompany) {
		this.qbocompany = qbocompany;
	}
	
	

}
