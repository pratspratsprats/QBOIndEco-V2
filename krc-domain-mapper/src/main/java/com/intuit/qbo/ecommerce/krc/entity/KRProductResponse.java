package com.intuit.qbo.ecommerce.krc.entity;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KRProductResponse {

	@SerializedName(value = "success")
	private String success;

	@SerializedName(value = "products")
	private List<KRProduct> products;

	@SerializedName(value = "total_products")
	private Integer total_products;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public List<KRProduct> getProducts() {
		return products;
	}

	public void setProducts(List<KRProduct> products) {
		this.products = products;
	}

	public Integer getTotal_products() {
		return total_products;
	}

	public void setTotal_products(Integer total_products) {
		this.total_products = total_products;
	}

	@Override
	public String toString() {
		return "KRProductResponse [success=" + success + ", products=" + products + "]";
	}

}
