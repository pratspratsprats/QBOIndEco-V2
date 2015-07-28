package com.intuit.incubation.model;


import java.io.Serializable;

public class Error implements Serializable {

	private final static long serialVersionUID = 1L;
	protected String code;
	protected ErrorType type;
	protected String message;
	protected String detail;
	protected String moreInfo;

	/**
	 * Gets the value of the code property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the value of the code property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCode(String value) {
		this.code = value;
	}

	/**
	 * Gets the value of the type property.
	 * 
	 * @return possible object is {@link ErrorType }
	 * 
	 */
	public ErrorType getType() {
		return type;
	}

	/**
	 * Sets the value of the type property.
	 * 
	 * @param value
	 *            allowed object is {@link ErrorType }
	 * 
	 */
	public void setType(ErrorType value) {
		this.type = value;
	}

	/**
	 * Gets the value of the message property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the value of the message property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMessage(String value) {
		this.message = value;
	}

	/**
	 * Gets the value of the detail property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * Sets the value of the detail property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDetail(String value) {
		this.detail = value;
	}

	/**
	 * Gets the value of the moreInfo property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMoreInfo() {
		return moreInfo;
	}

	/**
	 * Sets the value of the moreInfo property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMoreInfo(String value) {
		this.moreInfo = value;
	}

}
