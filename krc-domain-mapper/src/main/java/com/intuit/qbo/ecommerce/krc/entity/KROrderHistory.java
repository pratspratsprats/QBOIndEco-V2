package com.intuit.qbo.ecommerce.krc.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


import com.google.gson.annotations.SerializedName;


/**
 * 
 * @author nkumar11
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class KROrderHistory {
	
	@SerializedName(value = "order_status_id")
	private int orderStatusId;
	
	@SerializedName(value = "order_status")
	private String orderStatus;
	
	@SerializedName(value = "notify")
	private int notify;
	
	@SerializedName(value = "comment")
	private String comment;
	
	@SerializedName(value = "date_added")
	private String dateAdded;
	
	@SerializedName(value = "courier")
	private String courier;
	
	@SerializedName(value = "awb_code")
	private String awbCode;

	public int getOrderStatusId() {
		return orderStatusId;
	}

	public void setOrderStatusId(int orderStatusId) {
		this.orderStatusId = orderStatusId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public int getNotify() {
		return notify;
	}

	public void setNotify(int notify) {
		this.notify = notify;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getCourier() {
		return courier;
	}

	public void setCourier(String courier) {
		this.courier = courier;
	}

	public String getAwbCode() {
		return awbCode;
	}

	public void setAwbCode(String awbCode) {
		this.awbCode = awbCode;
	}
	
	

}
