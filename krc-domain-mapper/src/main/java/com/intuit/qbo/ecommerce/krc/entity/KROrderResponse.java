package com.intuit.qbo.ecommerce.krc.entity;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KROrderResponse {

	@SerializedName(value = "success")
	private String success;

	@SerializedName(value = "orders")
	private List<KROrder> orders;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public List<KROrder> getOrders() {
		return orders;
	}

	public void setOrders(List<KROrder> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "KROrderResponse [success=" + success + ", orders=" + orders + "]";
	}

}
