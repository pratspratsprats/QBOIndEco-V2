package com.intuit.incubation.entity;

import java.util.Date;

public class Seller {
	private Integer id ;           
	private String emailId;
	private String sellerId ;
	private String companyName;
	private String qbCompanyId;
	private String qbUsername;   
	private String qbPassword;   
	private Date createdDate ;  
	private Date updatedDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getQbCompanyId() {
		return qbCompanyId;
	}
	public void setQbCompanyId(String qbCompanyId) {
		this.qbCompanyId = qbCompanyId;
	}
	public String getQbUsername() {
		return qbUsername;
	}
	public void setQbUsername(String qbUsername) {
		this.qbUsername = qbUsername;
	}
	public String getQbPassword() {
		return qbPassword;
	}
	public void setQbPassword(String qbPassword) {
		this.qbPassword = qbPassword;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}
