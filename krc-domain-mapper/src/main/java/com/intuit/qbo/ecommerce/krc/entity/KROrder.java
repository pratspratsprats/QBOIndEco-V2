package com.intuit.qbo.ecommerce.krc.entity;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KROrder {

	@SerializedName(value = "order_id")
	private long id;

	@SerializedName(value = "invoice_number")
	private String invoiceNumber;

	@SerializedName(value = "store_name")
	private String storeName;
	
	//change for kartrocket v2 api newly added  
	
	@SerializedName(value = "store_url")
	private String storeUrl;
	
	@SerializedName(value = "firstname")
	private String customerFirstName;

	@SerializedName(value = "lastname")
	private String customerLastName;

	@SerializedName(value = "email")
	private String customerEmail;

	@SerializedName(value = "telephone")
	private String customerTelephone;

	@SerializedName(value = "mobile")
	private String customerMobile;

	@SerializedName(value = "shipping_firstname")
	private String shippingFirstName;

	@SerializedName(value = "shipping_lastname")
	private String shippingLastName;

	//Newly added field in Kartrocket v2 api
	@SerializedName(value = "shipping_mobile")
	private String shippingMobile;
	
	
	@SerializedName(value = "shipping_company")
	private String shippingCompany;

	@SerializedName(value = "shipping_address_1")
	private String shippingAddress1;

	@SerializedName(value = "shipping_address_2")
	private String shippingAddress2;

	@SerializedName(value = "shipping_city")
	private String shippingCity;

	//shipping_state chnged to shipping_zone from kartrocket v1 to v2 api
	//@SerializedName(value = "shipping_state")
	@SerializedName(value = "shipping_zone")
	private String shippingState;

	@SerializedName(value = "shipping_postcode")
	private String shippingPostCode;

	@SerializedName(value = "shipping_country")
	private String shippingCountry;

	@SerializedName(value = "shipping_method")
	private String shippingMethod;
	
	//Newly added field in kartrocket v2 api
	@SerializedName(value = "payment_firstname")
	private String paymentFirstName;
	
	//Newly added field in kartrocket v2 api
	@SerializedName(value = "payment_lastname")
	private String paymentLastName;
		
	//Newly added field in kartrocket v2 api
	@SerializedName(value = "payment_mobile")
	private String paymentMobile;
	
	//Newly added field in kartrocket v2 api
	@SerializedName(value = "payment_company")
	private String paymentCompany;
	
	//Newly added field in kartrocket v2 api
	@SerializedName(value = "payment_address1")
	private String paymentAddress1;
		
	//Newly added field in kartrocket v2 api
	@SerializedName(value = "payment_address2")
	private String paymentAddress2;	
	
	//Newly added field in kartrocket v2 api
	@SerializedName(value = "payment_city")
	private String paymentCity;
	
	//Newly added field in kartrocket v2 api
	@SerializedName(value = "payment_zone")
	private String paymentZone;
	
	//Newly added field in kartrocket v2 api
	@SerializedName(value = "payment_postcode")
	private String paymentPostCode;
					
	//Newly added field in kartrocket v2 api
	@SerializedName(value = "payment_country")
	private String paymentCountry;	
	
	@SerializedName(value = "payment_method")
	private String paymentMethod;	
	
	//Newly added field in kartrocket v2 api
	@SerializedName(value = "payment_code")
	private String paymentCode;
	
	//Newly added field in kartrocket v2 api
	@SerializedName(value = "comment")
	private String comment;

	//order_total changed to total in kartrocket v2 api
	//@SerializedName(value = "order_total")
	@SerializedName(value = "total")
	private Double total;
	
	//entered_weight changed to weight in kartrocket v2 api
	//@SerializedName(value = "entered_weight")
	@SerializedName(value = "weight")
	private String enteredWeight;
	
	@SerializedName(value = "order_status_id")
	private int orderStatusId;
	
	@SerializedName(value = "order_status")
	private String orderStatus;

	@SerializedName(value = "routing_code")
	private String routingCode;

	//change currency to currency_code from v1 to v2 kartrocket api 
	//@SerializedName(value = "currency")
	@SerializedName(value = "currency_code")
	private String currency;
	
	

	@SerializedName(value = "date_added")
	private String dateAdded;

	@SerializedName(value = "date_modified")
	private String dateModified;

	//Newly added in kartrocket v2 api
	@SerializedName(value = "import_order_id")
	private String importOrderId;
	
	@SerializedName(value = "status")
	private int status;
	

	/*changes for kartrocket v2 api start
	 * @SerializedName(value = "product_name")
	private String productName;

	@SerializedName(value = "model")
	private String model;

	@SerializedName(value = "sku")
	private String sku;

	@SerializedName(value = "quantity")
	private String quantity;

	@SerializedName(value = "product_price")
	private String productPrice;

	@SerializedName(value = "product_tax")
	private Double productTax;

	@SerializedName(value = "tax")
	private String tax;
	changes for kartrocket v2 api start
	*/
	
	@SerializedName(value="order_product")
	private List<KROrderProduct> orderProduct; 
	
	@SerializedName(value="order_total")
	private List<KROrderTotal> orderTotal;
	
	@SerializedName(value="order_history")
	private List<KROrderHistory> orderHistory;
	@SerializedName(value="order_options")
	private List<KROrderOptions> orderOptions;

	/*@SerializedName(value = "tax_class")
	private String taxClass;*/

	/*@SerializedName(value = "shipping")
	private Double shipping;

	public Double getShipping() {
		return shipping;
	}

	public void setShipping(Double shipping) {
		this.shipping = shipping;
	}
*/
	@SerializedName(value = "sub_total")
	private Double subTotal;

	/*@SerializedName(value = "total")
	private Double total;

	@SerializedName(value = "courier")
	private String courier;

	@SerializedName(value = "awb_number")
	private String awbNumber;*/

	

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

	/*public Double getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(Double orderTotal) {
		this.orderTotal = orderTotal;
	}*/

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
/*
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
*/
	
	
	/*public Double getShipping() {
		return shipping;
	}

	public void setShipping(Double shipping) {
		this.shipping = shipping;
	}*/

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

	@Override
	public String toString() {
		return "KROrder [id=" + id + ", invoiceNumber=" + invoiceNumber
				+ ", storeName=" + storeName + ", storeUrl=" + storeUrl
				+ ", customerFirstName=" + customerFirstName
				+ ", customerLastName=" + customerLastName + ", customerEmail="
				+ customerEmail + ", customerTelephone=" + customerTelephone
				+ ", customerMobile=" + customerMobile + ", shippingFirstName="
				+ shippingFirstName + ", shippingLastName=" + shippingLastName
				+ ", shippingMobile=" + shippingMobile + ", shippingCompany="
				+ shippingCompany + ", shippingAddress1=" + shippingAddress1
				+ ", shippingAddress2=" + shippingAddress2 + ", shippingCity="
				+ shippingCity + ", shippingState=" + shippingState
				+ ", shippingPostCode=" + shippingPostCode
				+ ", shippingCountry=" + shippingCountry + ", shippingMethod="
				+ shippingMethod + ", paymentFirstName=" + paymentFirstName
				+ ", paymentLastName=" + paymentLastName + ", paymentMobile="
				+ paymentMobile + ", paymentCompany=" + paymentCompany
				+ ", paymentAddress1=" + paymentAddress1 + ", paymentAddress2="
				+ paymentAddress2 + ", paymentCity=" + paymentCity
				+ ", paymentZone=" + paymentZone + ", paymentPostCode="
				+ paymentPostCode + ", paymentCountry=" + paymentCountry
				+ ", paymentMethod=" + paymentMethod + ", paymentCode="
				+ paymentCode + ", comment=" + comment + ", total=" + total
				+ ", enteredWeight=" + enteredWeight + ", orderStatusId="
				+ orderStatusId + ", orderStatus=" + orderStatus
				+ ", routingCode=" + routingCode + ", currency=" + currency
				+ ", dateAdded=" + dateAdded + ", dateModified=" + dateModified
				+ ", importOrderId=" + importOrderId + ", status=" + status
				+ ", orderProduct=" + orderProduct + ", orderTotal="
				+ orderTotal + ", orderHistory=" + orderHistory
				+ ", orderOptions=" + orderOptions 
				+ ", subTotal=" + subTotal + "]";
	}

	public String getStoreUrl() {
		return storeUrl;
	}

	public void setStoreUrl(String storeUrl) {
		this.storeUrl = storeUrl;
	}

	public String getShippingMobile() {
		return shippingMobile;
	}

	public void setShippingMobile(String shippingMobile) {
		this.shippingMobile = shippingMobile;
	}

	public String getPaymentFirstName() {
		return paymentFirstName;
	}

	public void setPaymentFirstName(String paymentFirstName) {
		this.paymentFirstName = paymentFirstName;
	}

	public String getPaymentLastName() {
		return paymentLastName;
	}

	public void setPaymentLastName(String paymentLastName) {
		this.paymentLastName = paymentLastName;
	}

	public String getPaymentMobile() {
		return paymentMobile;
	}

	public void setPaymentMobile(String paymentMobile) {
		this.paymentMobile = paymentMobile;
	}

	public String getPaymentCompany() {
		return paymentCompany;
	}

	public void setPaymentCompany(String paymentCompany) {
		this.paymentCompany = paymentCompany;
	}

	public String getPaymentAddress1() {
		return paymentAddress1;
	}

	public void setPaymentAddress1(String paymentAddress1) {
		this.paymentAddress1 = paymentAddress1;
	}

	public String getPaymentAddress2() {
		return paymentAddress2;
	}

	public void setPaymentAddress2(String paymentAddress2) {
		this.paymentAddress2 = paymentAddress2;
	}

	public String getPaymentCity() {
		return paymentCity;
	}

	public void setPaymentCity(String paymentCity) {
		this.paymentCity = paymentCity;
	}

	public String getPaymentZone() {
		return paymentZone;
	}

	public void setPaymentZone(String paymentZone) {
		this.paymentZone = paymentZone;
	}

	public String getPaymentPostCode() {
		return paymentPostCode;
	}

	public void setPaymentPostCode(String paymentPostCode) {
		this.paymentPostCode = paymentPostCode;
	}

	public String getPaymentCountry() {
		return paymentCountry;
	}

	public void setPaymentCountry(String paymentCountry) {
		this.paymentCountry = paymentCountry;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getImportOrderId() {
		return importOrderId;
	}

	public void setImportOrderId(String importOrderId) {
		this.importOrderId = importOrderId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<KROrderProduct> getOrderProduct() {
		return orderProduct;
	}

	public void setOrderProduct(List<KROrderProduct> orderProduct) {
		this.orderProduct = orderProduct;
	}

	public List<KROrderTotal> getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(List<KROrderTotal> orderTotal) {
		this.orderTotal = orderTotal;
	}

	public List<KROrderHistory> getOrderHistory() {
		return orderHistory;
	}

	public void setOrderHistory(List<KROrderHistory> orderHistory) {
		this.orderHistory = orderHistory;
	}

	public List<KROrderOptions> getOrderOptions() {
		return orderOptions;
	}

	public void setOrderOptions(List<KROrderOptions> orderOptions) {
		this.orderOptions = orderOptions;
	}

	/*public String getCourier() {
		return courier;
	}

	public void setCourier(String courier) {
		this.courier = courier;
	}

	public String getAwbNumber() {
		return awbNumber;
	}*/

	/*public void setAwbNumber(String awbNumber) {
		this.awbNumber = awbNumber;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getTaxClass() {
		return taxClass;
	}

	public void setTaxClass(String taxClass) {
		this.taxClass = taxClass;
	}*/

	/*@Override
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
				+ total + ", status=" + status + "]";
	}
*/
	
	/*public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}*/

}
