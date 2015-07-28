package com.intuit.krconnectcore.entity;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KROrderResponse {

	@JsonProperty(value = "success")
	private String success;

	@JsonProperty(value = "orders")
	private List<KROrderEntity> orders;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public List<KROrderEntity> getOrders() {
		return orders;
	}

	public void setOrders(List<KROrderEntity> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "KROrderResponse [success=" + success + ", orders=" + orders + "]";
	}

}
