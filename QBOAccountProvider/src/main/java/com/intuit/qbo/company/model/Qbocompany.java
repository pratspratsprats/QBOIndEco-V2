package com.intuit.qbo.company.model;

import java.io.Serializable;

/**
 * Class representing a user and company for account creation in QBO
 * @author Amaresh Mourya
 * @date Oct 19, 2014
 **/

public class Qbocompany implements Serializable{

	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;
	private String userEmail;
	private String billingCode;
	private String region;
	private String partner;
	private String companyName;
	private String language; 
	private String industryType;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getBillingCode() {
		return billingCode;
	}
	public void setBillingCode(String billingCode) {
		this.billingCode = billingCode;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getIndustryType() {
		return industryType;
	}
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	
	
}
