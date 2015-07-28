package com.intuit.krconnectcore.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.EmailAddress;
import com.intuit.ipp.data.Invoice;
import com.intuit.ipp.data.Item;
import com.intuit.ipp.data.ItemTypeEnum;
import com.intuit.ipp.data.Line;
import com.intuit.ipp.data.LineDetailTypeEnum;
import com.intuit.ipp.data.PhysicalAddress;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.data.SalesItemLineDetail;
import com.intuit.ipp.data.TelephoneNumber;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.krconnectcore.constant.CommonConstants;
import com.intuit.krconnectcore.entity.KROrderEntity;
import com.intuit.krconnectcore.entity.KROrderResponse;
import com.intuit.krconnectcore.entity.KRProductEntity;
import com.intuit.krconnectcore.entity.KRProductResponse;

public class KRConnectCoreManager {
	private static final Logger LOG = LoggerFactory.getLogger(KRConnectCoreManager.class);

	public static void syncKRData(String accesstoken, String accessstokensecret, String dataSource,
			String realmID, String oauthConsumerKey, String oauthConsumerSecret, String appToken) {

		// todo - derive all the keys from username and password
		OAuthAuthorizer authorizer = new OAuthAuthorizer(oauthConsumerKey, oauthConsumerSecret,
				accesstoken, accessstokensecret);
		Context context = null;

		try {
			context = new Context(authorizer, appToken, ServiceType.valueOf(dataSource), realmID);
		} catch (FMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (context == null) {
			LOG.error("Error: PlatformSessionContext is null.");
		}

		DataService service = new DataService(context);
		addProductToQB(service);
		addCustomerAndOrderToQB(service);
	}

	@SuppressWarnings("unchecked")
	private static void addCustomerAndOrderToQB(DataService service) {
		try {
			ClientRequest clientReq = new ClientRequest(CommonConstants.KR_ORDER_FETCH_URL);

			ClientResponse<String> response = clientReq.get(String.class);

			if (response != null) {
				if (response.getStatus() == Response.Status.OK.getStatusCode()) {
					String json = response.getEntity();

					ObjectMapper objectMapper = new ObjectMapper();
					KROrderResponse krOrderResponse = objectMapper.readValue(json,
							KROrderResponse.class);

					// Aggregating orders based on order number.
					Map<Long, List<KROrderEntity>> aggregatedOrders = new HashMap<Long, List<KROrderEntity>>();
					for (KROrderEntity krOrderEntity : krOrderResponse.getOrders()) {
						List<KROrderEntity> orders = aggregatedOrders.get(krOrderEntity.getId());
						if (orders == null) {
							orders = new ArrayList<KROrderEntity>();
						}
						orders.add(krOrderEntity);
						aggregatedOrders.put(krOrderEntity.getId(), orders);
					}

					for (Entry<Long, List<KROrderEntity>> entry : aggregatedOrders.entrySet()) {
						if (entry.getValue() != null && !entry.getValue().isEmpty()) {
							// Since the customer info will be the same in all
							// aggregating orders, picking the first one
							KROrderEntity krOrderEntity = entry.getValue().get(0);
							addCustomerToQB(service, krOrderEntity);

							// based on payment method either build a invoice or
							// sales receipt
							if (krOrderEntity.getPaymentMethod() != null
									&& krOrderEntity.getPaymentMethod().equalsIgnoreCase(
											CommonConstants.CASH_ON_DELIVERY)) {
								addInvoiceToQB(service, entry.getValue());
							} else {
								// assuming that all other payment methods are
								// prepaid orders.
								addSalesRecieptToQB(service, entry.getValue());
							}
						}
					}
				} else {
					LOG.error("Invalid response while hitting Kart Rocket order api : "
							+ response.getStatus());
				}
			} else {
				LOG.error("No response while hitting Kart Rocket order api");
			}
		} catch (Exception e) {
			LOG.error("Some error occured while adding data to QB", e);
		}

	}

	private static void addSalesRecieptToQB(DataService service, List<KROrderEntity> list) {
		// TODO Yet to implement

	}

	@SuppressWarnings("unchecked")
	private static void addInvoiceToQB(DataService service, List<KROrderEntity> orders) {
		KROrderEntity krOrderEntity = orders.get(0);
		try {
			Invoice invoice = new Invoice();

			List<Line> lineItems = new ArrayList<Line>();
			for (KROrderEntity order : orders) {
				// Query Item based on Unique name for reference in
				// invoice
				String queryForItem = "Select * from Item where name = '" + order.getProductName()
						+ "'";
				QueryResult queryResultForItem = service.executeQuery(queryForItem);
				List<Item> items = (List<Item>) queryResultForItem.getEntities();

				if (items == null || items.size() != 1) {
					LOG.error("Either item does not exist in QB or item is not unique : "
							+ order.getProductName());
					return;
				}

				ReferenceType itemRef = new ReferenceType();
				itemRef.setValue(items.get(0).getId());

				SalesItemLineDetail salesItemLineDetail = new SalesItemLineDetail();
				salesItemLineDetail.setItemRef(itemRef);
				salesItemLineDetail.setQty(new BigDecimal(order.getQuantity()));
				salesItemLineDetail.setUnitPrice(new BigDecimal(order.getProductPrice()));

				Line lineItem = new Line();
				lineItem.setDetailType(LineDetailTypeEnum.SALES_ITEM_LINE_DETAIL);
				// setting the line amount to quantity * unit price
				lineItem.setAmount(new BigDecimal(order.getQuantity()).multiply(new BigDecimal(
						order.getProductPrice())));
				lineItem.setSalesItemLineDetail(salesItemLineDetail);

				// add all the line items
				lineItems.add(lineItem);
			}

			Line subTotalLineItem = new Line();
			subTotalLineItem.setDetailType(LineDetailTypeEnum.SUB_TOTAL_LINE_DETAIL);

			// subtotal = order total - shipping
			subTotalLineItem.setAmount(new BigDecimal(krOrderEntity.getSubTotal()));
			lineItems.add(subTotalLineItem);

			// to handle shipping charges adding one more line
			// item
			if (krOrderEntity.getShipping() != null && krOrderEntity.getShipping() != 0) {
				ReferenceType shippingItemRef = new ReferenceType();
				shippingItemRef.setValue("SHIPPING_ITEM_ID");
				Line shippingLineItem = new Line();
				shippingLineItem.setDetailType(LineDetailTypeEnum.SALES_ITEM_LINE_DETAIL);
				shippingLineItem.setAmount(new BigDecimal(krOrderEntity.getShipping()));
				SalesItemLineDetail shippingItemLineDetail = new SalesItemLineDetail();
				shippingItemLineDetail.setItemRef(shippingItemRef);
				shippingLineItem.setSalesItemLineDetail(shippingItemLineDetail);
				lineItems.add(shippingLineItem);
			}

			invoice.setLine(lineItems);

			String displayName = (krOrderEntity.getCustomerFirstName() + " " + krOrderEntity
					.getCustomerLastName()).trim();

			// Query Customer based on Unique display name for
			// reference in invoice
			String queryForCustomer = "Select * from Customer where displayname = '" + displayName
					+ "'";

			QueryResult queryResultForCustomer = service.executeQuery(queryForCustomer);
			List<Customer> customers = (List<Customer>) queryResultForCustomer.getEntities();

			if (customers == null || customers.size() != 1) {
				LOG.error("Either customer does not exist in QB or Customer is not unique : "
						+ displayName);
				return;
			}

			ReferenceType customerRef = new ReferenceType();
			customerRef.setValue(customers.get(0).getId());
			invoice.setCustomerRef(customerRef);

			EmailAddress emailAddress = new EmailAddress();
			emailAddress.setAddress(krOrderEntity.getCustomerEmail());
			invoice.setBillEmail(emailAddress);

			PhysicalAddress physicalAddress = new PhysicalAddress();
			physicalAddress.setLine1(krOrderEntity.getShippingAddress1());
			physicalAddress.setLine2(krOrderEntity.getShippingAddress2());
			physicalAddress.setLine3(krOrderEntity.getShippingState());
			physicalAddress.setCity(krOrderEntity.getShippingCity());
			physicalAddress.setCountry(krOrderEntity.getShippingCountry());
			physicalAddress.setPostalCode(krOrderEntity.getShippingPostCode());

			invoice.setShipAddr(physicalAddress);
			// setting the same billing address since KR doesn't provide the
			// billing address
			invoice.setBillAddr(physicalAddress);

			ReferenceType currencyReference = new ReferenceType();
			currencyReference.setValue(krOrderEntity.getCurrency());
			invoice.setCurrencyRef(currencyReference);

			// setting order id as doc number
			invoice.setDocNumber(String.valueOf(krOrderEntity.getId()));

			try {
				Date txnDate = CommonConstants.DATE_FORMATTER.parse(krOrderEntity.getDateAdded());
				invoice.setTxnDate(txnDate);
			} catch (java.text.ParseException pe) {
				LOG.error("Ignoring error while parsing txn date: " + krOrderEntity.getDateAdded(),
						pe);
			}

			ReferenceType shipMethodreferenceType = new ReferenceType();
			shipMethodreferenceType.setValue(krOrderEntity.getCourier());

			// todo - set shipdate
			invoice.setTrackingNum(krOrderEntity.getAwbNumber());
			invoice.setShipMethodRef(shipMethodreferenceType);

			service.add(invoice);
			LOG.info("Adding Invoice to QB for customer : " + displayName);
		} catch (Exception e) {
			LOG.error("Some error occured while adding invoice/Sales Reciept to QB for customer : "
					+ krOrderEntity.getCustomerFirstName(), e);
		}
	}

	private static void addCustomerToQB(DataService service, KROrderEntity krOrderEntity) {
		try {
			Customer customer = new Customer();

			customer.setGivenName(krOrderEntity.getCustomerFirstName());
			customer.setFamilyName(krOrderEntity.getCustomerLastName());
			// setting display name to later query on this
			String displayName = (krOrderEntity.getCustomerFirstName() + " " + krOrderEntity
					.getCustomerLastName()).trim();
			customer.setDisplayName(displayName);

			EmailAddress emailAddress = new EmailAddress();
			emailAddress.setAddress(krOrderEntity.getCustomerEmail());
			customer.setPrimaryEmailAddr(emailAddress);

			TelephoneNumber telephoneNumber = new TelephoneNumber();
			telephoneNumber.setCountryCode("91");
			telephoneNumber.setFreeFormNumber(krOrderEntity.getCustomerTelephone());
			customer.setPrimaryPhone(telephoneNumber);

			TelephoneNumber mobile = new TelephoneNumber();
			mobile.setCountryCode("91");
			mobile.setFreeFormNumber(krOrderEntity.getCustomerMobile());
			customer.setMobile(mobile);

			PhysicalAddress physicalAddress = new PhysicalAddress();
			physicalAddress.setLine1(krOrderEntity.getShippingAddress1());
			physicalAddress.setLine2(krOrderEntity.getShippingAddress2());
			physicalAddress.setLine3(krOrderEntity.getShippingState());
			physicalAddress.setCity(krOrderEntity.getShippingCity());
			physicalAddress.setCountry(krOrderEntity.getShippingCountry());
			physicalAddress.setPostalCode(krOrderEntity.getShippingPostCode());

			customer.setShipAddr(physicalAddress);
			// setting the same billing address since KR doesn't provide the
			// billing address
			customer.setBillAddr(physicalAddress);

			ReferenceType currencyReference = new ReferenceType();
			currencyReference.setValue(krOrderEntity.getCurrency());
			customer.setCurrencyRef(currencyReference);

			customer = service.add(customer);
			LOG.info("Adding Customer to QB : " + displayName);
		} catch (Exception e) {
			LOG.error("Skipping Error while adding new Customer to QB : " + e);
		}

	}

	private static void addProductToQB(DataService service) {
		try {
			ClientRequest clientReq = new ClientRequest(CommonConstants.KR_PRODUCT_FETCH_URL);

			ClientResponse<String> response = clientReq.get(String.class);

			if (response != null) {
				if (response.getStatus() == Response.Status.OK.getStatusCode()) {
					String json = response.getEntity();
					ObjectMapper objectMapper = new ObjectMapper();
					KRProductResponse businessInfo = objectMapper.readValue(json,
							KRProductResponse.class);

					for (KRProductEntity krProductEntity : businessInfo.getProducts()) {
						try {
							Item item = new Item();
							item.setName(krProductEntity.getName());
							item.setDescription(krProductEntity.getDescription());
							item.setQtyOnHand(new BigDecimal(krProductEntity.getQuantity()));
							// item.setTrackQtyOnHand(Boolean.TRUE);
							// DATE NEEDS TO BE SET
							item.setType(ItemTypeEnum.INVENTORY);

							String price = krProductEntity.getPrice();

							// remove all ',' from price string
							price = price.replace(",", "");
							// Remove "Rs." string from the kartrocket price
							item.setUnitPrice(new BigDecimal(price.substring(3).trim()));

							ReferenceType referenceType = new ReferenceType();
							// Hard coding Sales since all the e-commerce
							// products
							// will be of sales type
							referenceType.setName("Sales");
							referenceType.setValue("1");

							item.setIncomeAccountRef(referenceType);

							try {
								Date productAddedDate = CommonConstants.DATE_FORMATTER
										.parse(krProductEntity.getDateAdded());
								item.setInvStartDate(productAddedDate);
							} catch (java.text.ParseException pe) {
								LOG.error("Ignoring error while parsing inventory start date: "
										+ krProductEntity.getDateAdded(), pe);
							}

							service.add(item);
							LOG.info("Adding Item to QB : " + krProductEntity.getName());
						} catch (Exception e) {
							LOG.error(e.getMessage()
									+ krProductEntity.getName());
						}
					}
				} else {
					LOG.error("Invalid response while hitting Kart Rocket product api : "
							+ response.getStatus());
				}
			} else {
				LOG.error("No response while hitting Kart Rocket product api");
			}
		} catch (Exception e) {
			LOG.error("Some unknown error occured while adding items to QB", e);
		}

	}

}
