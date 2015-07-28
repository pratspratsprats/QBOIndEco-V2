package com.intuit.krconnectcore.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KROrderEntity {

	@JsonProperty(value = "order_id")
	private long id;

	@JsonProperty(value = "invoice_number")
	private String invoiceNumber;

	@JsonProperty(value = "store_name")
	private String storeName;

	@JsonProperty(value = "firstname")
	private String customerFirstName;

	@JsonProperty(value = "lastname")
	private String customerLastName;

	@JsonProperty(value = "email")
	private String customerEmail;

	@JsonProperty(value = "telephone")
	private String customerTelephone;

	@JsonProperty(value = "mobile")
	private String customerMobile;

	@JsonProperty(value = "shipping_firstname")
	private String shippingFirstName;

	@JsonProperty(value = "shipping_lastname")
	private String shippingLastName;

	@JsonProperty(value = "shipping_company")
	private String shippingCompany;

	@JsonProperty(value = "shipping_address_1")
	private String shippingAddress1;

	@JsonProperty(value = "shipping_address_2")
	private String shippingAddress2;

	@JsonProperty(value = "shipping_city")
	private String shippingCity;

	@JsonProperty(value = "shipping_state")
	private String shippingState;

	@JsonProperty(value = "shipping_postcode")
	private String shippingPostCode;

	@JsonProperty(value = "shipping_country")
	private String shippingCountry;

	@JsonProperty(value = "shipping_method")
	private String shippingMethod;

	@JsonProperty(value = "order_total")
	private Double orderTotal;

	@JsonProperty(value = "entered_weight")
	private String enteredWeight;

	@JsonProperty(value = "routing_code")
	private String routingCode;

	@JsonProperty(value = "currency")
	private String currency;

	@JsonProperty(value = "date_added")
	private String dateAdded;

	@JsonProperty(value = "date_modified")
	private String dateModified;

	@JsonProperty(value = "order_status")
	private String orderStatus;

	@JsonProperty(value = "order_status_id")
	private int orderStatusId;

	@JsonProperty(value = "payment_method")
	private String paymentMethod;

	@JsonProperty(value = "product_name")
	private String productName;

	@JsonProperty(value = "model")
	private String model;

	@JsonProperty(value = "sku")
	private String sku;

	@JsonProperty(value = "quantity")
	private String quantity;

	@JsonProperty(value = "product_price")
	private String productPrice;

	@JsonProperty(value = "product_tax")
	private Double productTax;

	@JsonProperty(value = "shipping")
	private Double shipping;

	@JsonProperty(value = "sub_total")
	private Double subTotal;

	@JsonProperty(value = "total")
	private Double total;

	@JsonProperty(value = "courier")
	private String courier;

	@JsonProperty(value = "awb_number")
	private String awbNumber;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerTelephone() {
		return customerTelephone;
	}

	public void setCustomerTelephone(String customerTelephone) {
		this.customerTelephone = customerTelephone;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getShippingFirstName() {
		return shippingFirstName;
	}

	public void setShippingFirstName(String shippingFirstName) {
		this.shippingFirstName = shippingFirstName;
	}

	public String getShippingLastName() {
		return shippingLastName;
	}

	public void setShippingLastName(String shippingLastName) {
		this.shippingLastName = shippingLastName;
	}

	public String getShippingCompany() {
		return shippingCompany;
	}

	public void setShippingCompany(String shippingCompany) {
		this.shippingCompany = shippingCompany;
	}

	public String getShippingAddress1() {
		return shippingAddress1;
	}

	public void setShippingAddress1(String shippingAddress1) {
		this.shippingAddress1 = shippingAddress1;
	}

	public String getShippingAddress2() {
		return shippingAddress2;
	}

	public void setShippingAddress2(String shippingAddress2) {
		this.shippingAddress2 = shippingAddress2;
	}

	public String getShippingCity() {
		return shippingCity;
	}

	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}

	public String getShippingState() {
		return shippingState;
	}

	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}

	public String getShippingPostCode() {
		return shippingPostCode;
	}

	public void setShippingPostCode(String shippingPostCode) {
		this.shippingPostCode = shippingPostCode;
	}

	public String getShippingCountry() {
		return shippingCountry;
	}

	public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public Double getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(Double orderTotal) {
		this.orderTotal = orderTotal;
	}

	public String getEnteredWeight() {
		return enteredWeight;
	}

	public void setEnteredWeight(String enteredWeight) {
		this.enteredWeight = enteredWeight;
	}

	public String getRoutingCode() {
		return routingCode;
	}

	public void setRoutingCode(String routingCode) {
		this.routingCode = routingCode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public int getOrderStatusId() {
		return orderStatusId;
	}

	public void setOrderStatusId(int orderStatusId) {
		this.orderStatusId = orderStatusId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public Double getProductTax() {
		return productTax;
	}

	public void setProductTax(Double productTax) {
		this.productTax = productTax;
	}

	public Double getShipping() {
		return shipping;
	}

	public void setShipping(Double shipping) {
		this.shipping = shipping;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getCourier() {
		return courier;
	}

	public void setCourier(String courier) {
		this.courier = courier;
	}

	public String getAwbNumber() {
		return awbNumber;
	}

	public void setAwbNumber(String awbNumber) {
		this.awbNumber = awbNumber;
	}

	@Override
	public String toString() {
		return "KROrderEntity [id=" + id + ", invoiceNumber=" + invoiceNumber + ", storeName="
				+ storeName + ", customerFirstName=" + customerFirstName + ", customerLastName="
				+ customerLastName + ", customerEmail=" + customerEmail + ", customerTelephone="
				+ customerTelephone + ", customerMobile=" + customerMobile + ", shippingFirstName="
				+ shippingFirstName + ", shippingLastName=" + shippingLastName
				+ ", shippingCompany=" + shippingCompany + ", shippingAddress1=" + shippingAddress1
				+ ", shippingAddress2=" + shippingAddress2 + ", shippingCity=" + shippingCity
				+ ", shippingState=" + shippingState + ", shippingPostCode=" + shippingPostCode
				+ ", shippingCountry=" + shippingCountry + ", shippingMethod=" + shippingMethod
				+ ", orderTotal=" + orderTotal + ", enteredWeight=" + enteredWeight
				+ ", routingCode=" + routingCode + ", currency=" + currency + ", dateAdded="
				+ dateAdded + ", dateModified=" + dateModified + ", orderStatus=" + orderStatus
				+ ", orderStatusId=" + orderStatusId + ", paymentMethod=" + paymentMethod
				+ ", productName=" + productName + ", model=" + model + ", sku=" + sku
				+ ", quantity=" + quantity + ", productPrice=" + productPrice + ", productTax="
				+ productTax + ", shipping=" + shipping + ", subTotal=" + subTotal + ", total="
				+ total + "]";
	}

}
