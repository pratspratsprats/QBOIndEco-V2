package com.intuit.qbo.ecommerce.krc.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KRProduct {

	// "id":"147","name":"Orange","description":"","model":"California","sku":"O0001","price":"Rs.30","quantity":"96",
	// "href":"https://intuit.kartrocket.co/orange",
	// "thumb":"http://res.cloudinary.com/kartrocket/image/fetch/w_200%2Ch_236%2Cc_pad/http%3A%2F%2Fkartrocket-mtp.s3.amazonaws."
	// +
	// "com%2Fall-stores%2Fimage_intuit%2Fdata%2Forange.jpg","image":"http://res.cloudinary.com/kartrocket/image/fetch/"
	// +
	// "w_200%2Ch_236%2Cc_pad/http%3A%2F%2Fkartrocket-mtp.s3.amazonaws.com%2Fall-stores%2Fimage_intuit%2Fdata%2Forange"
	// +
	// ".jpg","images":["http://res.cloudinary.com/kartrocket/image/fetch/w_200%2Ch_236%2Cc_pad/http%3A%2F%2Fkar"
	// +
	// "trocket-mtp.s3.amazonaws.com%2Fall-stores%2Fimage_intuit%2Fdata%2Forange.jpg"],"categories":["79"],
	// "special":false,"rating":null,"discounts":[],"options":[],"minimum":"1","attribute_groups":[],
	// "date_added":"2014-09-25 13:21:40","date_modified":"0000-00-00 00:00:00","currency":"INR"},

	@SerializedName(value = "id")
	private long id;

	@SerializedName(value = "name")
	private String name;

	@SerializedName(value = "description")
	private String description;

	@SerializedName(value = "model")
	private String model;

	@SerializedName(value = "sku")
	private String sku;

	@SerializedName(value = "price")
	private String price;

	@SerializedName(value = "quantity")
	private int quantity;

	@SerializedName(value = "href")
	private String linkUrl;

	@SerializedName(value = "date_added")
	private String dateAdded;

	@SerializedName(value = "date_modified")
	private String dateModified;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getDateModified() {
		return dateModified;
	}

	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}

	@Override
	public String toString() {
		return "KRProductEntity [id=" + id + ", name=" + name + ", description=" + description
				+ ", model=" + model + ", sku=" + sku + ", price=" + price + ", quantity="
				+ quantity + ", linkUrl=" + linkUrl + "]";
	}

}
