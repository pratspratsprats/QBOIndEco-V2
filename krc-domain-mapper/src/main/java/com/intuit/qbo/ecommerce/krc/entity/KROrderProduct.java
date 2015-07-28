package com.intuit.qbo.ecommerce.krc.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * @author nkumar11
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class KROrderProduct {
	
	@SerializedName(value = "product_id")
	private Long productId;

	@SerializedName(value = "name")
	private String productName;
	
	@SerializedName(value = "sku")
	private String  sku;
	
	@SerializedName(value = "model")
	private String model;
	
	@SerializedName(value = "quantity")
	private int quantity;
	
	@SerializedName(value = "price")
	private Double price;
	
	@SerializedName(value = "total")
	private Double total;
	
	@SerializedName(value = "tax")
	private Double tax;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}
	
	
}
