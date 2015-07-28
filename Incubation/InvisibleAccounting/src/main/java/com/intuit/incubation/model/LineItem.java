package com.intuit.incubation.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

public class LineItem implements Serializable{

	private static final long serialVersionUID = 6871401833470346468L;
	
	@NotBlank
	private String productId;
	@NotBlank
	private String itemName;
	@NotBlank
	private String quantity;
	@NotBlank
	private String sellingPrice;
	@NotBlank
	private String mrp;
	@NotBlank
	private String sellerId;
	@NotBlank
	private String emailId;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(String sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getMrp() {
		return mrp;
	}
	public void setMrp(String mrp) {
		this.mrp = mrp;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}
