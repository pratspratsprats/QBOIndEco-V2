/**
 * @author generated by bgopinath
 */

package com.intuit.qbo.ecommerce.krc.model;

import java.io.Serializable;
import java.util.Date;

import com.wordnik.swagger.annotations.ApiModelProperty;

public class Customer implements Serializable {
	private static final long serialVersionUID = 3949920585672943857L;

	@ApiModelProperty(required = true)
	protected String shopDomain;

	@ApiModelProperty(required = true)
	protected String webApiKey;

	@ApiModelProperty(required = true)
	protected Boolean syncStatus;

	@ApiModelProperty(required = false)
	protected String email;

	@ApiModelProperty(required = false)
	protected String companyName;

	@ApiModelProperty(required = false)
	protected String firstName;

	@ApiModelProperty(required = false)
	protected String lastName;

	@ApiModelProperty(required = false)
	protected String address1;

	@ApiModelProperty(required = false)
	protected String address2;

	@ApiModelProperty(required = false)
	protected String city;

	@ApiModelProperty(required = false)
	protected String state;

	@ApiModelProperty(required = false)
	protected String postCode;

	@ApiModelProperty(required = false)
	protected String country;

	@ApiModelProperty(required = false)
	protected String tinNo;

	@ApiModelProperty(required = false)
	protected String mobile;

	@ApiModelProperty(required = false)
	protected String qbUserName;

	@ApiModelProperty(required = false)
	protected String qbPassword;

	@ApiModelProperty(required = false)
	protected String qbCompanyId;

	@ApiModelProperty(required = false)
	protected Integer id;

	@ApiModelProperty(required = false)
	protected Date createdDt;

	@ApiModelProperty(required = false)
	protected Date updatedDt;

	/**
	 * Gets the value of the id property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setId(Integer value) {
		this.id = value;
	}

	/**
	 * Gets the value of the email property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the value of the email property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setEmail(String value) {
		this.email = value;
	}

	/**
	 * Gets the value of the webApiKey property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getWebApiKey() {
		return webApiKey;
	}

	/**
	 * Sets the value of the webApiKey property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setWebApiKey(String value) {
		this.webApiKey = value;
	}

	/**
	 * Gets the value of the companyName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * Sets the value of the companyName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCompanyName(String value) {
		this.companyName = value;
	}

	/**
	 * Gets the value of the firstName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the value of the firstName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setFirstName(String value) {
		this.firstName = value;
	}

	/**
	 * Gets the value of the lastName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the value of the lastName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setLastName(String value) {
		this.lastName = value;
	}

	/**
	 * Gets the value of the address1 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * Sets the value of the address1 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setAddress1(String value) {
		this.address1 = value;
	}

	/**
	 * Gets the value of the address2 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * Sets the value of the address2 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setAddress2(String value) {
		this.address2 = value;
	}

	/**
	 * Gets the value of the city property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the value of the city property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCity(String value) {
		this.city = value;
	}

	/**
	 * Gets the value of the postCode property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * Sets the value of the postCode property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPostCode(String value) {
		this.postCode = value;
	}

	/**
	 * Gets the value of the country property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the value of the country property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCountry(String value) {
		this.country = value;
	}

	/**
	 * Gets the value of the tinNo property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTinNo() {
		return tinNo;
	}

	/**
	 * Sets the value of the tinNo property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTinNo(String value) {
		this.tinNo = value;
	}

	/**
	 * Gets the value of the mobile property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * Sets the value of the mobile property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMobile(String value) {
		this.mobile = value;
	}

	/**
	 * Gets the value of the createdDt property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public Date getCreatedDt() {
		return createdDt;
	}

	/**
	 * Sets the value of the createdDt property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCreatedDt(Date value) {
		this.createdDt = value;
	}

	/**
	 * Gets the value of the updatedDt property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public Date getUpdatedDt() {
		return updatedDt;
	}

	/**
	 * Sets the value of the updatedDt property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUpdatedDt(Date value) {
		this.updatedDt = value;
	}

	public String getShopDomain() {
		return shopDomain;
	}

	public void setShopDomain(String shopDomain) {
		this.shopDomain = shopDomain;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Boolean getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(Boolean syncStatus) {
		this.syncStatus = syncStatus;
	}

	public String getQbUserName() {
		return qbUserName;
	}

	public void setQbUserName(String qbUserName) {
		this.qbUserName = qbUserName;
	}

	public String getQbPassword() {
		return qbPassword;
	}

	public void setQbPassword(String qbPassword) {
		this.qbPassword = qbPassword;
	}

	public String getQbCompanyId() {
		return qbCompanyId;
	}

	public void setQbCompanyId(String qbCompanyId) {
		this.qbCompanyId = qbCompanyId;
	}

	@Override
	public String toString() {
		return "Customer [shopDomain=" + shopDomain + ", webApiKey=" + webApiKey + ", syncStatus="
				+ syncStatus + ", email=" + email + ", companyName=" + companyName + ", firstName="
				+ firstName + ", lastName=" + lastName + ", address1=" + address1 + ", address2="
				+ address2 + ", city=" + city + ", state=" + state + ", postCode=" + postCode
				+ ", country=" + country + ", tinNo=" + tinNo + ", mobile=" + mobile
				+ ", qbUserName=" + qbUserName + ", qbPassword=" + qbPassword + ", qbCompanyId="
				+ qbCompanyId + ", id=" + id + ", createdDt=" + createdDt + ", updatedDt="
				+ updatedDt + "]";
	}

}
