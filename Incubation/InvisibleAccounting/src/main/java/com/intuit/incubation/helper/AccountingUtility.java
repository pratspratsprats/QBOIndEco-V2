package com.intuit.incubation.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.intuit.incubation.exception.InvalidArgumentException;
import com.intuit.incubation.logging.Log;
import com.intuit.incubation.model.LineItem;
import com.intuit.incubation.model.Order;
import com.intuit.incubation.model.QBOCompany;
import com.intuit.qbo.ecommerce.domain.BillAddr;
import com.intuit.qbo.ecommerce.domain.CurrencyRef;
import com.intuit.qbo.ecommerce.domain.EmailAddress;
import com.intuit.qbo.ecommerce.domain.IncomeAccountRef;
import com.intuit.qbo.ecommerce.domain.Invoice;
import com.intuit.qbo.ecommerce.domain.Line;
import com.intuit.qbo.ecommerce.domain.Mobile;
import com.intuit.qbo.ecommerce.domain.PhysicalAddress;
import com.intuit.qbo.ecommerce.domain.PrimaryEmailAddr;
import com.intuit.qbo.ecommerce.domain.Product;
import com.intuit.qbo.ecommerce.domain.ReferenceType;
import com.intuit.qbo.ecommerce.domain.SalesItemLineDetail;
import com.intuit.qbo.ecommerce.domain.ShipAddr;
import com.intuit.qbo.ecommerce.krc.constants.Constants;

@Component
public class AccountingUtility {

	@Autowired
	private Validator jsrValidator;
	@Log
	private Logger LOG;

	public void pushDataToQBO(String ticket, String authId, String appToken, String companyId,
			Order order, LineItem lineItem) {
		addProducts(ticket, authId, appToken, companyId, order, lineItem);
		addCustomerAndOrderToQB(ticket, authId, appToken, companyId, order, lineItem);
	}

	private void addCustomerAndOrderToQB(String ticket, String authId, String appToken,
			String companyId, Order order, LineItem lineItem) {
		try {
			addCustomerToQB(ticket, authId, appToken, companyId, order, lineItem);

			// based on payment method either build a
			// invoice or
			// sales receipt
			if (order.getPaymentMode() != null
					&& order.getPaymentMode().equalsIgnoreCase(Constants.CASH_ON_DELIVERY)) {
				addInvoiceToQB(ticket, authId, appToken, companyId, order, lineItem);
			}

		} catch (Exception e) {
			LOG.error("Some error occured while adding data to QB", e);
		}

	}

	private JsonObject getInvoiceByOrderIdAndCompanyId(String ticket, String authId,
			String appToken, String orderId, String companyId, LineItem lineItem) {

		// Query Customer based on Unique display name for
		// reference in invoice
		String search = orderId;
		String queryForCustomer = "Select * from Invoice where DocNumber = '" + search + "'";

		MediaType mediaTypeJson = new MediaType("application", "json");
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(mediaTypeJson));
		requestHeaders.add("Authorization", "INTUIT_IAM x_intuit_authid=" + authId
				+ ",x_intuit_ticket=" + ticket + ",intuit-app-token=" + appToken + "");
		HttpEntity<String> requestEntity = new HttpEntity<String>(requestHeaders);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(
				"https://quickbooks.api.intuit.com/v3/company/" + companyId + "/query?query="
						+ queryForCustomer, HttpMethod.GET, requestEntity, String.class);

		String json = response.getBody();
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
		JsonObject responseObject = jsonObject.get("QueryResponse").getAsJsonObject();
		JsonElement invoiceElement = responseObject.get("Invoice");

		if (invoiceElement == null) {
			return null;
		}
		JsonArray invoiceArray = invoiceElement.getAsJsonArray();
		JsonObject invoiceObject = invoiceArray.get(0).getAsJsonObject();
		return invoiceObject;

	}

	private void addInvoiceToQB(String ticket, String authId, String appToken, String companyId,
			Order order, LineItem lineItem) {
		JsonObject invoiceJsonObject = getInvoiceByOrderIdAndCompanyId(ticket, authId, appToken,
				String.valueOf(order.getOrderId()), companyId, lineItem);

		try {

			if (invoiceJsonObject == null) {
				Invoice invoice = new Invoice();
				String shippingItemRef = null;

				List<Line> lineItemsList = new ArrayList<Line>();

				JsonObject itemObject = getItemByNameFromQB(ticket, authId, appToken, companyId,
						lineItem.getItemName());

				String itemId = itemObject.get("Id").getAsString();

				ReferenceType referenceType = new ReferenceType();
				referenceType.setValue(itemId);

				SalesItemLineDetail salesItemLineDetail = new SalesItemLineDetail();

				salesItemLineDetail.setItemRef(referenceType);
				salesItemLineDetail.setQty(new BigDecimal(lineItem.getQuantity()));
				salesItemLineDetail.setUnitPrice(new BigDecimal(lineItem.getMrp()));

				Line line = new Line();

				line.setDetailType("SalesItemLineDetail");

				BigDecimal amount = new BigDecimal(lineItem.getQuantity()).multiply(new BigDecimal(
						lineItem.getMrp()));

				line.setAmount(amount);
				line.setSalesItemLineDetail(salesItemLineDetail);
				// line.setGlobalTaxCalculation("NotApplicable");

				lineItemsList.add(line);
				shippingItemRef = itemId;

				Line totalLineItem = new Line();
				totalLineItem.setDetailType("SubTotalLineDetail");

				totalLineItem.setAmount(amount);
				// totalLineItem.setGlobalTaxCalculation("NotApplicable");

				lineItemsList.add(totalLineItem);
				// to handle shipping charges adding one more line
				// item
				// if (krOrderEntity.getShipping() != null &&
				// krOrderEntity.getShipping() != 0) {
				//
				// ReferenceType shiReferenceType = new ReferenceType();
				//
				// shiReferenceType.setValue(shippingItemRef);
				// com.intuit.qbo.ecommerce.domain.Line shippingLineItem2 = new
				// com.intuit.qbo.ecommerce.domain.Line();
				//
				// shippingLineItem2.setDetailType("SalesItemLineDetail");
				// shippingLineItem2.setAmount(new
				// BigDecimal(krOrderEntity.getShipping()));
				// SalesItemLineDetail shippingItemLineDetail2 = new
				// SalesItemLineDetail();
				// shippingItemLineDetail2.setItemRef(shiReferenceType);
				// shippingLineItem2.setSalesItemLineDetail(shippingItemLineDetail2);
				// lineItemsList.add(shippingLineItem2);
				// }

				invoice.setLine(lineItemsList);

				String displayName = (order.getCustomerFirstName() + " " + order
						.getCustomerLastName()).trim();

				JsonObject customerObject = getCustomerByNameFromQB(ticket, authId, appToken,
						companyId, displayName);
				String customerId = customerObject.get("Id").getAsString();
				ReferenceType customerRef = new ReferenceType();
				customerRef.setValue(customerId);
				invoice.setCustomerRef(customerRef);

				EmailAddress emailAddress2 = new EmailAddress();
				emailAddress2.setAddress(order.getCustomerEmailId());
				invoice.setEmailAddress(emailAddress2);

				PhysicalAddress physicalAddress = new PhysicalAddress();
				physicalAddress.setLine1(order.getShippingAddress());
				physicalAddress.setLine3(order.getState());
				physicalAddress.setCity(order.getCity());
				physicalAddress.setCountry(order.getCountry());
				physicalAddress.setPostalCode(order.getPostalCode());

				invoice.setShipAddr(physicalAddress);
				// setting the same billing address since KR doesn't provide the
				// billing address
				invoice.setBillAddr(physicalAddress);

				ReferenceType currencyReference = new ReferenceType();
				currencyReference.setValue("INR");
				invoice.setCurrencyRef(currencyReference);

				// setting order id as doc number
				invoice.setDocNumber(order.getOrderId());
				invoice.setTxnDate(new Date());

				ReferenceType shipMethodreferenceType = new ReferenceType();
				shipMethodreferenceType.setValue(order.getCourier());

				// todo - set shipdate
				invoice.setTrackingNum(order.getAwbNumber());
				invoice.setShipMethodRef(shipMethodreferenceType);
				// https://quickbooks.api.intuit.com/v3/company/1298296485/invoice?requestid=f3cd14b5a6a341bead4ac68542a369b3&minorversion=1&
				// service.add(invoice);

				Gson gson2 = new Gson();
				String url = "https://quickbooks.api.intuit.com/v3/company/" + companyId
						+ "/invoice";
				String reqBody = gson2.toJson(invoice);

				MediaType mediaTypeJson1 = new MediaType("application", "json");
				HttpHeaders requestHeaders1 = new HttpHeaders();
				requestHeaders1.setAccept(Collections.singletonList(mediaTypeJson1));
				requestHeaders1.setContentType(mediaTypeJson1);
				requestHeaders1.add("Authorization", "INTUIT_IAM x_intuit_authid=" + authId
						+ ",x_intuit_ticket=" + ticket + ",intuit-app-token=" + appToken + "");
				HttpEntity<String> requestEntity1 = new HttpEntity<String>(reqBody, requestHeaders1);

				RestTemplate template = new RestTemplate();

				ResponseEntity<String> resEntity = template.postForEntity(url, requestEntity1,
						String.class);

				LOG.info("Adding Invoice to QB for customer : " + displayName);
			} else {
				LOG.info("Invoice already exist !!!");
			}

		} catch (Exception e) {
			LOG.error("Some error occured while adding invoice/Sales Reciept to QB for customer : "
					+ order.getCustomerFirstName(), e);
		}
	}

	private void addCustomerToQB(String ticket, String authId, String appToken, String companyId,
			Order order, LineItem lineItem) {
		try {
			com.intuit.qbo.ecommerce.domain.Customer customer = new com.intuit.qbo.ecommerce.domain.Customer();

			// setting display name to later query on this
			String displayName = (order.getCustomerFirstName() + " " + order.getCustomerLastName())
					.trim();

			JsonObject customerObject = getCustomerByNameFromQB(ticket, authId, appToken,
					companyId, displayName);
			if (customerObject == null) {
				customer.setGivenName(order.getCustomerFirstName());
				customer.setFamilyName(order.getCustomerLastName());

				customer.setDisplayName(displayName);

				PrimaryEmailAddr primaryEmailAddr = new PrimaryEmailAddr();
				primaryEmailAddr.setAddress(order.getCustomerEmailId());

				customer.setPrimaryEmailAddr(primaryEmailAddr);

				// PrimaryPhone primaryPhone = new PrimaryPhone();
				//
				// primaryPhone.setCountryCode("91");
				// primaryPhone.setFreeFormNumber(krOrderEntity.getCustomerTelephone());
				// customer.setPrimaryPhone(primaryPhone);

				Mobile mobile = new Mobile();
				mobile.setCountryCode("91");

				mobile.setFreeFormNumber(order.getCustomerMobile());
				customer.setMobile(mobile);

				ShipAddr shipAddr = new ShipAddr();

				shipAddr.setLine1(order.getShippingAddress());
				shipAddr.setLine3(order.getState());
				shipAddr.setCity(order.getCity());
				shipAddr.setCountry(order.getCountry());
				shipAddr.setPostalCode(order.getPostalCode());

				BillAddr billAddr = new BillAddr();

				billAddr.setLine1(order.getShippingAddress());
				billAddr.setLine3(order.getState());
				billAddr.setCity(order.getCity());
				billAddr.setCountry(order.getCountry());
				billAddr.setPostalCode(order.getPostalCode());
				customer.setShipAddr(shipAddr);

				customer.setBillAddr(billAddr);

				CurrencyRef currencyRef = new CurrencyRef();
				currencyRef.setValue("INR");
				customer.setCurrencyRef(currencyRef);

				// POST customer to
				// https://quickbooks.api.intuit.com/v3/company/1298296485/customer

				Gson gson2 = new Gson();
				String url = "https://quickbooks.api.intuit.com/v3/company/" + companyId
						+ "/customer";
				String reqBody = gson2.toJson(customer);

				MediaType mediaTypeJson = new MediaType("application", "json");
				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.setAccept(Collections.singletonList(mediaTypeJson));
				requestHeaders.setContentType(mediaTypeJson);
				requestHeaders.add("Authorization", "INTUIT_IAM x_intuit_authid=" + authId
						+ ",x_intuit_ticket=" + ticket + ",intuit-app-token=" + appToken);
				HttpEntity<String> requestEntity = new HttpEntity<String>(reqBody, requestHeaders);

				System.out.println(requestHeaders);

				RestTemplate template = new RestTemplate();

				LOG.info("Adding customer in QBO : " + displayName);

				ResponseEntity<String> resEntity = template.postForEntity(url, requestEntity,
						String.class);
			} else {
				LOG.info("Skipping customer insertion.. " + displayName);
			}

		} catch (Exception e) {
			LOG.error("Error in adding customer to QB" + e);
		}

	}

	private void addProducts(String ticket, String authId, String appToken, String companyId,
			Order order, LineItem lineItem) {
		try {

			JsonObject itemObject = getItemByNameFromQB(ticket, authId, appToken, companyId,
					lineItem.getItemName());

			if (itemObject == null) {
				LOG.info("Product not available in QB.. adding to QB.." + lineItem.getItemName());

				String price = lineItem.getMrp();

				Product product = new Product();
				product.setName(lineItem.getItemName());
				// product.setDescription(krProductEntity.getDescription());
				product.setQtyOnHand(Integer.valueOf(lineItem.getQuantity()));
				product.setUnitPrice(new BigDecimal(price.trim()));

				product.setType("Inventory");

				IncomeAccountRef incomeAccountRef = new IncomeAccountRef();

				incomeAccountRef.setName("Sales");
				incomeAccountRef.setValue("1");

				product.setIncomeAccountRef(incomeAccountRef);
				product.setInvStartDate(new Date());

				String url = "https://quickbooks.api.intuit.com/v3/company/" + companyId + "/item";
				Gson gson = new Gson();
				String reqBody = gson.toJson(product);

				MediaType mediaTypeJson = new MediaType("application", "json");
				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.setAccept(Collections.singletonList(mediaTypeJson));
				requestHeaders.setContentType(mediaTypeJson);
				requestHeaders.add("Authorization", "INTUIT_IAM x_intuit_authid=" + authId
						+ ",x_intuit_ticket=" + ticket + ",intuit-app-token=" + appToken);
				HttpEntity<String> requestEntity = new HttpEntity<String>(reqBody, requestHeaders);

				RestTemplate template = new RestTemplate();

				ResponseEntity<String> resEntity = template.postForEntity(url, requestEntity,
						String.class);
			} else {
				LOG.info("Skip inserting Item in QB.. " + lineItem.getItemName());
			}

		} catch (Exception e) {
			LOG.error("Error adding Item to QB" + lineItem.getItemName(), e);
		}

	}

	private JsonObject getItemByNameFromQB(String ticket, String authId, String appToken,
			String companyId, String productName) {
		// Query Item based on Unique name for reference in
		// invoice
		String queryForItem = "Select * from Item where name = '" + productName + "'";
		/*
		 * QueryResult queryResultForItem = service .executeQuery(queryForItem);
		 * List<Item> items = (List<Item>) queryResultForItem .getEntities();
		 */

		MediaType mediaTypeJson = new MediaType("application", "json");
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(mediaTypeJson));
		requestHeaders.add("Authorization", "INTUIT_IAM x_intuit_authid=" + authId
				+ ",x_intuit_ticket=" + ticket + ",intuit-app-token=" + appToken + "");
		HttpEntity<String> requestEntity = new HttpEntity<String>(requestHeaders);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(
				"https://quickbooks.api.intuit.com/v3/company/" + companyId + "/query?query="
						+ queryForItem, HttpMethod.GET, requestEntity, String.class);

		String json = response.getBody();
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
		JsonObject responseObject = jsonObject.get("QueryResponse").getAsJsonObject();
		JsonElement itemElement = responseObject.get("Item");

		if (itemElement == null) {
			return null;
		}
		JsonArray itemsArray = itemElement.getAsJsonArray();

		JsonObject itemObject = itemsArray.get(0).getAsJsonObject();

		return itemObject;
	}

	private JsonObject getCustomerByNameFromQB(String ticket, String authId, String appToken,
			String companyId, String displayName) {

		// Query Customer based on Unique display name for
		// reference in invoice
		String queryForCustomer = "Select * from Customer where displayname = '" + displayName
				+ "'";

		MediaType mediaTypeJson = new MediaType("application", "json");
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Collections.singletonList(mediaTypeJson));
		requestHeaders.add("Authorization", "INTUIT_IAM x_intuit_authid=" + authId
				+ ",x_intuit_ticket=" + ticket + ",intuit-app-token=" + appToken + "");
		HttpEntity<String> requestEntity = new HttpEntity<String>(requestHeaders);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(
				"https://quickbooks.api.intuit.com/v3/company/" + companyId + "/query?query="
						+ queryForCustomer, HttpMethod.GET, requestEntity, String.class);

		String json = response.getBody();
		Gson gson = new Gson();
		System.out.println(json);
		JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
		JsonObject responseObject = jsonObject.get("QueryResponse").getAsJsonObject();
		JsonElement customerElement = responseObject.get("Customer");

		if (customerElement == null) {
			return null;
		}
		JsonArray customerArray = customerElement.getAsJsonArray();
		JsonObject customerObject = customerArray.get(0).getAsJsonObject();

		return customerObject;
	}

	public QBOCompany createQBOCompanyDataFromSellerId(String sellerId, String companyName,
			String sellerEmail) {
		QBOCompany company = new QBOCompany();
		if (sellerId != null) {
			company.setUserName(sellerId);
			company.setPassword("intuit");
			company.setCompanyName(companyName);
			company.setUserEmail(sellerEmail);
		}
		return company;
	}
	

	public <T> void validateComponent(final T data)
			throws InvalidArgumentException {
		final Set<ConstraintViolation<T>> violations = jsrValidator
				.validate(data);
		final StringBuffer violationString = processViolations(violations);
		if (null != violationString) {
			throw new InvalidArgumentException(violationString.toString());
		}
	}
	
	private <T> StringBuffer processViolations ( final Set<ConstraintViolation<T>> violations )
	{
		final StringBuffer buffer = new StringBuffer();
		if ( violations == null || violations.isEmpty() )
		{
			return null;
		}
		for ( ConstraintViolation<T> violation: violations )
		{
			final String className = violation.getLeafBean().getClass().getName();
			buffer.append("\n" + violation.getPropertyPath() + " in " + className.substring(className.lastIndexOf('.') + 1) + " is \"" + violation
					.getInvalidValue() + "\". Expected:  " + violation.getMessage());

		}
		return buffer;
	}


}
