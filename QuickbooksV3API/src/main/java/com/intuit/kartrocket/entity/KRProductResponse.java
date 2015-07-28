package com.intuit.kartrocket.entity;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KRProductResponse {

	@JsonProperty(value = "success")
	private String success;
	
	@JsonProperty(value = "products")
	private List<KRProductEntity> products;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public List<KRProductEntity> getProducts() {
		return products;
	}

	public void setProducts(List<KRProductEntity> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "KRProductResponse [success=" + success + ", products=" + products + "]";
	}



}
