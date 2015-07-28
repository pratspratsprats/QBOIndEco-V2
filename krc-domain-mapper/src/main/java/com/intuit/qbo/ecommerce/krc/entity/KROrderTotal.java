package com.intuit.qbo.ecommerce.krc.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * @author nkumar11
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class KROrderTotal {

	@SerializedName(value = "code")
	private String total;
	
	@SerializedName(value = "title")
	private String title;
	
	@SerializedName(value = "text")
	private String text;
	
	@SerializedName(value = "value")
	private Double value;

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
	
	
	
	
}
