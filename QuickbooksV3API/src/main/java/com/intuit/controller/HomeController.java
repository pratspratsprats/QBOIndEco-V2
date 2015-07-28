package com.intuit.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.intuit.constant.CommonConstants;
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
import com.intuit.kartrocket.entity.KROrderEntity;
import com.intuit.kartrocket.entity.KROrderResponse;
import com.intuit.kartrocket.entity.KRProductEntity;
import com.intuit.kartrocket.entity.KRProductResponse;
import com.intuit.krconnectcore.manager.KRConnectCoreManager;
import com.intuit.utils.WebUtils;

/*
 * This class is a controller for the application home page related activities.  
 */
@Controller
public class HomeController {

	public static final Logger LOG = Logger.getLogger(HomeController.class);

	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/*
	 * This method is called when the user is redirected from Login Page /
	 * Application Page / Settings Page.
	 */
	@RequestMapping(value = "/home.htm", method = RequestMethod.GET)
	public String showHomePage(final HttpServletRequest request) {
		LOG.info("HomeController -> showHomePage()");

		String redirectTo;
		if (request.getSession().getAttribute("invalidateOAuth") != null) {
			LOG.info("Invalidate ");
			request.getSession().setAttribute("accessToken", null);
			request.getSession().setAttribute("accessTokenSecret", null);
			request.getSession().setAttribute("connectionStatus", "not_authorized");

			request.getSession().removeAttribute("invalidateOAuth");
		}

		final HttpSession session = request.getSession();
		if (session.getAttribute("displayUserName") != null
				|| session.getAttribute("firstName") != null
				|| session.getAttribute("lastName") != null
				|| session.getAttribute("connectionStatus") != null) {
			if (session.getAttribute("isLinkingRequired") != null) {
				redirectTo = "redirect:/linking.htm";
			} else {
				redirectTo = "home";
			}
		} else {
			redirectTo = "redirect:/login.htm?isLoggedIn=false";
		}

		return redirectTo;
	}

	/*
	 * This method is called when the user clicks on 'Get All QuickBooks
	 * Customers' Link on Home Page.
	 */
	@RequestMapping(value = "/customers.htm", method = RequestMethod.GET)
	public String getCustomers(final HttpServletRequest request, final Model model) {
		LOG.info("HomeController -> getCustomers()");

		final HttpSession session = request.getSession();
		final List<Customer> customerList = new ArrayList<Customer>();

		final String accesstoken = (String) session.getAttribute("accessToken");
		final String accessstokensecret = (String) session.getAttribute("accessTokenSecret");
		final String realmID = (String) session.getAttribute("realmId");
		final String dataSource = (String) session.getAttribute("dataSource");

		System.out.println("accesstoken :" + accesstoken);
		System.out.println("accessstokensecret :" + accessstokensecret);
		System.out.println("realmID :" + realmID);
		System.out.println("dataSource :" + dataSource);

		OAuthAuthorizer authorizer = new OAuthAuthorizer(WebUtils.OAUTH_CONSUMER_KEY,
				WebUtils.OAUTH_CONSUMER_SECRET, accesstoken, accessstokensecret);
		Context context = null;

		try {
			context = new Context(authorizer, WebUtils.APP_TOKEN, ServiceType.valueOf(dataSource),
					realmID);
		} catch (FMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (context == null) {
			LOG.error("Error: PlatformSessionContext is null.");
		}

		DataService service = new DataService(context);

		try {
			// Using the service, retrieve all customers and display their
			// names.
			final List<Customer> customers = service.findAll(new Customer());

			for (Customer customer : customers) {
				final String customerName = customer.getDisplayName();
				LOG.info("customerName : " + customerName);
				customerList.add(customer);

			}

		} catch (Exception e) {
			LOG.error("Exception thrown by findAll / OAuth tokens are invalidated");
			session.setAttribute("connectionStatus", "not_authorized");
		}

		model.addAttribute("customerList", customerList);
		return "home";
	}

	@RequestMapping(value = "/syncKRData.htm", method = RequestMethod.GET)
	public String syncKRData(final HttpServletRequest request, final Model model) {
		final HttpSession session = request.getSession();

		final String accesstoken = (String) session.getAttribute("accessToken");
		final String accessstokensecret = (String) session.getAttribute("accessTokenSecret");
		final String realmID = (String) session.getAttribute("realmId");
		final String dataSource = (String) session.getAttribute("dataSource");

		System.out.println("accesstoken :" + accesstoken);
		System.out.println("accessstokensecret :" + accessstokensecret);
		System.out.println("realmID :" + realmID);
		System.out.println("dataSource :" + dataSource);

		KRConnectCoreManager.syncKRData(accesstoken, accessstokensecret, dataSource, realmID,
				WebUtils.OAUTH_CONSUMER_KEY, WebUtils.OAUTH_CONSUMER_SECRET, WebUtils.APP_TOKEN);

//		OAuthAuthorizer authorizer = new OAuthAuthorizer(WebUtils.OAUTH_CONSUMER_KEY,
//				WebUtils.OAUTH_CONSUMER_SECRET, accesstoken, accessstokensecret);
//		Context context = null;
//
//		try {
//			context = new Context(authorizer, WebUtils.APP_TOKEN, ServiceType.valueOf(dataSource),
//					realmID);
//		} catch (FMSException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//
//		if (context == null) {
//			LOG.error("Error: PlatformSessionContext is null.");
//		}
//
//		DataService service = new DataService(context);
//		addProductToQB(service);
//		addCustomerAndOrderToQB(service);
		model.addAttribute("syncStatus", "sucess");
		return "home";
	}

	@SuppressWarnings("unchecked")
	private void addCustomerAndOrderToQB(DataService service) {
		try {
			ClientRequest clientReq = new ClientRequest(CommonConstants.KR_ORDER_FETCH_URL);

			ClientResponse<String> response = clientReq.get(String.class);

			if (response != null) {
				if (response.getStatus() == Response.Status.OK.getStatusCode()) {
					String json = response.getEntity();

					ObjectMapper objectMapper = new ObjectMapper();
					KROrderResponse krOrderResponse = objectMapper.readValue(json,
							KROrderResponse.class);

					for (KROrderEntity krOrderEntity : krOrderResponse.getOrders()) {
						addCustomerToQB(service, krOrderEntity);

						// based on payment method either build a invoice or
						// sales receipt
						if (krOrderEntity.getPaymentMethod() != null
								&& krOrderEntity.getPaymentMethod().equalsIgnoreCase(
										CommonConstants.CASH_ON_DELIVERY)) {
							addInvoiceToQB(service, krOrderEntity);
						} else {
							// assuming that all other payment methods are
							// prepaid orders.
							addSalesRecieptToQB(service, krOrderEntity);
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

	private void addSalesRecieptToQB(DataService service, KROrderEntity krOrderEntity) {
		// TODO Yet to implement

	}

	@SuppressWarnings("unchecked")
	private void addInvoiceToQB(DataService service, KROrderEntity krOrderEntity) {
		try {
			Invoice invoice = new Invoice();

			// Query Item based on Unique name for reference in
			// invoice
			String queryForItem = "Select * from Item where name = '"
					+ krOrderEntity.getProductName() + "'";
			QueryResult queryResultForItem = service.executeQuery(queryForItem);
			List<Item> items = (List<Item>) queryResultForItem.getEntities();

			if (items == null || items.size() != 1) {
				LOG.error("Either item does not exist in QB or item is not unique : "
						+ krOrderEntity.getProductName());
				return;
			}

			ReferenceType itemRef = new ReferenceType();
			itemRef.setValue(items.get(0).getId());

			SalesItemLineDetail salesItemLineDetail = new SalesItemLineDetail();
			salesItemLineDetail.setItemRef(itemRef);
			salesItemLineDetail.setQty(new BigDecimal(krOrderEntity.getQuantity()));
			salesItemLineDetail.setUnitPrice(new BigDecimal(krOrderEntity.getProductPrice()));

			List<Line> lineItems = new ArrayList<Line>();

			Line firstLineItem = new Line();
			firstLineItem.setDetailType(LineDetailTypeEnum.SALES_ITEM_LINE_DETAIL);
			// setting the line amount to quantity * unit price
			firstLineItem.setAmount(new BigDecimal(krOrderEntity.getQuantity())
					.multiply(new BigDecimal(krOrderEntity.getProductPrice())));
			firstLineItem.setSalesItemLineDetail(salesItemLineDetail);

			Line secondLineItem = new Line();
			secondLineItem.setDetailType(LineDetailTypeEnum.SUB_TOTAL_LINE_DETAIL);

			// this will be wrong in case of Kart rocket due to
			// single line item but QB will override this
			secondLineItem.setAmount(new BigDecimal(krOrderEntity.getSubTotal()));

			// to handle shipping charges adding one more line
			// item
			ReferenceType shippingItemRef = new ReferenceType();
			shippingItemRef.setValue("SHIPPING_ITEM_ID");

			Line shippingLineItem = new Line();
			shippingLineItem.setDetailType(LineDetailTypeEnum.SALES_ITEM_LINE_DETAIL);
			shippingLineItem.setAmount(new BigDecimal(krOrderEntity.getShipping()));
			SalesItemLineDetail shippingItemLineDetail = new SalesItemLineDetail();
			shippingItemLineDetail.setItemRef(shippingItemRef);
			shippingLineItem.setSalesItemLineDetail(shippingItemLineDetail);

			// add all the line items
			lineItems.add(firstLineItem);
			lineItems.add(secondLineItem);
			lineItems.add(shippingLineItem);
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

			ReferenceType currencyReference = new ReferenceType();
			currencyReference.setValue(krOrderEntity.getCurrency());
			invoice.setCurrencyRef(currencyReference);

			// setting order id as doc number
			invoice.setDocNumber(String.valueOf(krOrderEntity.getId()));

			try {
				Date txnDate = FORMATTER.parse(krOrderEntity.getDateAdded());
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

	private void addCustomerToQB(DataService service, KROrderEntity krOrderEntity) {
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

			ReferenceType currencyReference = new ReferenceType();
			currencyReference.setValue(krOrderEntity.getCurrency());
			customer.setCurrencyRef(currencyReference);

			customer = service.add(customer);
			LOG.info("Adding Customer to QB : " + displayName);
		} catch (Exception e) {
			LOG.error("Skipping Error while adding new Customer to QB : " + e);
		}

	}

	private void addProductToQB(DataService service) {
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
						Item item = new Item();
						item.setName(krProductEntity.getName());
						item.setDescription(krProductEntity.getDescription());
						item.setQtyOnHand(new BigDecimal(krProductEntity.getQuantity()));
						// item.setTrackQtyOnHand(Boolean.TRUE);
						// DATE NEEDS TO BE SET
						item.setType(ItemTypeEnum.INVENTORY);
						// Remove "Rs." string from the kartrocket price
						item.setUnitPrice(new BigDecimal(krProductEntity.getPrice().substring(3)));

						ReferenceType referenceType = new ReferenceType();
						// Hard coding Sales since all the e-commerce products
						// will be of sales type
						referenceType.setName("Sales");
						referenceType.setValue("1");

						item.setIncomeAccountRef(referenceType);
						service.add(item);
						LOG.info("Adding Item to QB : " + krProductEntity.getName());
					}
				} else {
					LOG.error("Invalid response while hitting Kart Rocket product api : "
							+ response.getStatus());
				}
			} else {
				LOG.error("No response while hitting Kart Rocket product api");
			}
		} catch (Exception e) {
			LOG.error("Some error occured while adding item to QB", e);
		}

	}
}
