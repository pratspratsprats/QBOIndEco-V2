package com.intuit.qbo.ecommerce.krc.synch;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.IEntity;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.Invoice;
import com.intuit.ipp.data.Item;
import com.intuit.ipp.data.ItemTypeEnum;
import com.intuit.ipp.data.Line;
import com.intuit.ipp.data.LineDetailTypeEnum;
import com.intuit.ipp.data.TelephoneNumber;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Config;
import com.intuit.qbo.ecommerce.krc.constants.Constants;
import com.intuit.qbo.ecommerce.krc.entity.KROrder;
import com.intuit.qbo.ecommerce.krc.entity.KROrderHistory;
import com.intuit.qbo.ecommerce.krc.entity.KROrderProduct;
import com.intuit.qbo.ecommerce.krc.entity.KROrderResponse;
import com.intuit.qbo.ecommerce.krc.entity.KROrderTotal;
import com.intuit.qbo.ecommerce.krc.entity.KRProduct;
import com.intuit.qbo.ecommerce.krc.entity.KRProductResponse;
import com.intuit.qbo.ecommerce.krc.entity.SyncDates;
//import com.intuit.qbo.ecommerce.domain.Line;

/**
 * @author bgopinath
 * 
 */
public class KRCMapper {

	private static final Logger LOG = LoggerFactory.getLogger(KRCMapper.class);
	private static final Charset UTF_8 = Charset.forName("UTF-8");

	public SyncDates syncKRData(String shopDomain, String webApiKey, String appToken, String companyId, Date lastOrderSyncDate, Date lastProductSyncDate, String accessToken, String accessTokenSecret) {

		SyncDates syncDates = new SyncDates();
		// Fetch products from KartRocket
		List<KRProduct> productList = getProductsFromKR(shopDomain, webApiKey, syncDates);
		if (productList != null) {
			System.out.println("Total Product count : " + productList.size() + ".Shop domain : "
					+ shopDomain);
		}
		// Add Products to QB
		if (productList != null) {
			addProducts(appToken, companyId, productList, shopDomain, accessToken, accessTokenSecret);
		}
		// Add Customer and Invoice to QB
		List<KROrder> orders = getOrdersFromKR(shopDomain, webApiKey, lastOrderSyncDate, syncDates);
		if (orders != null) {
			addCustomerAndOrderToQB(shopDomain, webApiKey, appToken, companyId,orders,accessToken, accessTokenSecret);
					
		}

		return syncDates;
	}

	public List<KROrder> getOrdersFromKR(String shopDomain, String webApiKey,
			Date lastOrderSyncDate, SyncDates syncDates) {
		List<KROrder> orders = new ArrayList<KROrder>();
		try {
			Date syncDate = new Date();
			if (lastOrderSyncDate == null) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -14);
				syncDate = cal.getTime();
			} else {
				Calendar cal = Calendar.getInstance();
				cal.setTime(lastOrderSyncDate);
				cal.add(Calendar.HOUR, -1);
				syncDate = cal.getTime();
			}
			System.out.println("Order will be fetched after order date : " + syncDate
					+ ".Shop domain : " + shopDomain);

			RestTemplate restTemplate = new RestTemplate();
			syncDates.setLastOrderSyncDate(new Date());
			for (int pages = 1; pages <= 100; pages++) {
				/*
				 * commenting v1 api
				 * ResponseEntity<String> resEntity = restTemplate.getForEntity(shopDomain
						+ "/index.php?route=feed/web_api/orders&key=" + webApiKey + "&date_from="
						+ (syncDate.getTime() / 1000) + "&page=" + pages, String.class);*/
				//changing v1 end point Url to v2
				//change - adding version=2 as a query parameter
				
				ResponseEntity<String> resEntity = restTemplate.getForEntity(shopDomain
						+ "/index.php?route=feed/web_api/orders&version=2&key=" + webApiKey + "&date_from="
						+ (syncDate.getTime() / 1000) + "&page=" + pages, String.class);

				if (resEntity != null) {
					if (resEntity.getStatusCode() == HttpStatus.OK) {
						String resBody = resEntity.getBody();
						
						Gson gson = new Gson();
						KROrderResponse krOrderResponse = gson.fromJson(resBody,
								KROrderResponse.class);
						
						if (krOrderResponse != null && krOrderResponse.getOrders() != null
								&& krOrderResponse.getOrders().size() != 0) {
							orders=krOrderResponse.getOrders();
						} else {
							break;
						}
					} else {
						LOG.error("Invalid response while hitting Kart Rocket order api : "
								+ resEntity.getStatusCode() + ".Shop domain : " + shopDomain);
					}
				} else {
					LOG.error("No response while hitting Kart Rocket order api" + ".Shop domain : "
							+ shopDomain);
					break;
				}
			}
		} catch (Exception e) {
			LOG.error("Error in fetching orders for shop domain : " + shopDomain, e);
		}
		return orders;
	}

	public void addCustomerAndOrderToQB(String shopDomain, String webApiKey, String appToken, String companyId, List<KROrder> krorders, String accessToken, String accessTokenSecret) {
		
		
		try {
			if (krorders != null) {
				// Aggregating orders based on order number.
				//No aggregation logic required as part of the kartrocket v2 api
				//Map<Long, List<KROrder>> aggregatedOrders = new HashMap<Long, List<KROrder>>();
				System.out.println("Order count :: " + krorders.size() + ".Shop domain : "
						+ shopDomain);
				/*this block of code commented due to the changes in Kartrocket v2  api response
				 * for (KROrder krOrderEntity : krorders) {
					System.out.println("Tax info : " + krOrderEntity.getTaxClass() + "###"
							+ krOrderEntity.getProductTax() + "###" + krOrderEntity.getTax()
							+ "### for shop domain : " + shopDomain);
					List<KROrder> orders = aggregatedOrders.get(krOrderEntity.getId());
					if (orders == null) {
						orders = new ArrayList<KROrder>();
					}
					orders.add(krOrderEntity);
					aggregatedOrders.put(krOrderEntity.getId(), orders);
				}*/

				for (KROrder krOrderEntity : krorders) {
					// commented for the changes in v2 api
					//if (entry.getValue() != null && !entry.getValue().isEmpty()) {
						// Since the customer info will be the same in
						// all
						// aggregating orders, picking the first one
						//KROrder krOrderEntity = entry.getValue().get(0);

						if ("0".equals(krOrderEntity.getStatus())) {
							System.out.println("Skipping order since status is 0 : "
									+ krOrderEntity.getId());
							continue;
						}

						addCustomerToQB(appToken, companyId, krOrderEntity,shopDomain, accessToken, accessTokenSecret);

						// based on payment method either build a
						// invoice or
						// sales receipt
						// if (krOrderEntity.getPaymentMethod() != null
						// &&
						// krOrderEntity.getPaymentMethod().equalsIgnoreCase(
						// Constants.CASH_ON_DELIVERY)) {
						addInvoiceToQB(appToken, companyId,krOrderEntity,
								shopDomain, accessToken, accessTokenSecret);
						// }
					//}
				}
			}

		} catch (Exception e) {
			LOG.error(
					"Some error occured while adding data to QB" + ".Shop domain : " + shopDomain,
					e);
		}

	}
	
	// 
	private Line mapToLineItem(Item itemObject, KROrderProduct productInOrder){
		
		
		
		
				
     /*  Iterator<? extends IEntity> iterator = itemObject.getEntities().iterator();
      
        Item item=null;
        String itemId="";
       
        while(iterator.hasNext()){
        	item = (Item) iterator.next();
            System.out.println( "--------------------Item"+item.getId());
            itemId=item.getId();
           System.out.println("--------------------itemId"+itemId);
          

           
        }*/

		//	ReferenceType referenceType = new ReferenceType();
			com.intuit.ipp.data.ReferenceType referenceType = new com.intuit.ipp.data.ReferenceType();
			
			referenceType.setValue(itemObject.getId());

			//SalesItemLineDetail salesItemLineDetail = new SalesItemLineDetail();
			
			com.intuit.ipp.data.SalesItemLineDetail salesItemLineDetail = new com.intuit.ipp.data.SalesItemLineDetail();

			salesItemLineDetail.setItemRef(referenceType);
			salesItemLineDetail.setQty(new BigDecimal(productInOrder.getQuantity()));
			salesItemLineDetail.setUnitPrice(new BigDecimal(productInOrder.getPrice()
					));

			Line line = new Line();

			line.setDetailType(LineDetailTypeEnum.SALES_ITEM_LINE_DETAIL);

			line.setAmount(new BigDecimal(productInOrder.getQuantity())
					.multiply(new BigDecimal(productInOrder.getPrice())));
			line.setSalesItemLineDetail(salesItemLineDetail);
			// line.setGlobalTaxCalculation("NotApplicable");

			//lineItemsList.add(line);
			return line;
		
		
	}

	public void addInvoiceToQB(String appToken, String companyId,
			KROrder order, String shopDomain, String accessToken, String accessTokenSecret) {
		//KROrder krOrderEntity = orders.get(0);

		 Invoice invc = getInvoiceByOrderIdAndCompanyId(appToken,String.valueOf(order.getId()), companyId, accessToken, accessTokenSecret);

		try {

			if (invc == null) {
				//Invoice invoice = new Invoice();
				
				com.intuit.ipp.data.Invoice invoice = new com.intuit.ipp.data.Invoice();
				List <KROrderProduct> orderProduct=order.getOrderProduct();
				//List<Line> lineItemsList = new ArrayList<Line>();
				List<Line> lineItemsList = new ArrayList<Line>();
				
				for (KROrderProduct productInOrder : orderProduct) {

					// order.setProductName(order.getProductName().replaceAll("'",
					// "\'"));
					
					
					Item itemObject = getItemByNameFromQB( appToken,companyId, productInOrder.getProductName().trim(), productInOrder.getSku().trim(),
							shopDomain, accessToken, accessTokenSecret);
					

					if (itemObject != null) {
						Line line=mapToLineItem(itemObject, productInOrder);
						lineItemsList.add(line);
					
					} else {
						
						List<KRProduct> productList=new ArrayList<KRProduct>();
						KRProduct product=new KRProduct();
						product.setSku(productInOrder.getSku());
						product.setName(productInOrder.getProductName());
						product.setPrice(productInOrder.getPrice().toString());
						product.setQuantity(productInOrder.getQuantity());
						productList.add(product);
					addProducts(appToken, companyId, productList, shopDomain, accessToken, accessTokenSecret);
					Item createdItemObject = getItemByNameFromQB(appToken, companyId, productInOrder.getSku(), productInOrder.getSku(), shopDomain, accessToken, accessTokenSecret);
					/*String itemId = createdItemObject.get("Id").getAsString();

					//	ReferenceType referenceType = new ReferenceType();
						com.intuit.ipp.data.ReferenceType referenceType = new com.intuit.ipp.data.ReferenceType();
						
						referenceType.setValue(itemId);

						//SalesItemLineDetail salesItemLineDetail = new SalesItemLineDetail();
						
						com.intuit.ipp.data.SalesItemLineDetail salesItemLineDetail = new com.intuit.ipp.data.SalesItemLineDetail();

						salesItemLineDetail.setItemRef(referenceType);
						salesItemLineDetail.setQty(new BigDecimal(productInOrder.getQuantity()));
						salesItemLineDetail.setUnitPrice(new BigDecimal(productInOrder.getPrice()
								));

						Line line = new Line();

						line.setDetailType(LineDetailTypeEnum.SALES_ITEM_LINE_DETAIL);

						line.setAmount(new BigDecimal(productInOrder.getQuantity())
								.multiply(new BigDecimal(productInOrder.getPrice())));
						line.setSalesItemLineDetail(salesItemLineDetail);
						// line.setGlobalTaxCalculation("NotApplicable");
*/ 
					Line line=mapToLineItem(createdItemObject, productInOrder);
						lineItemsList.add(line);
						

			
											}

				}
				if (lineItemsList.size() != 0) {
					Line totalLineItem = new Line();
					totalLineItem.setDetailType(LineDetailTypeEnum.SUB_TOTAL_LINE_DETAIL);
					//setDetailType("SubTotalLineDetail");
					totalLineItem.setAmount(new BigDecimal(order.getTotal()));
					// totalLineItem.setGlobalTaxCalculation("NotApplicable");

					lineItemsList.add(totalLineItem);
					// to handle shipping charges adding one more line
					// item
					
					List<KROrderTotal> orderTotal=order.getOrderTotal();
					
					Double shippingAmount=0.00; 
					for(KROrderTotal ordertotal:orderTotal){
						
						if(ordertotal.getText().equalsIgnoreCase("shipping")){
							
							shippingAmount=ordertotal.getValue();
						}
						
					}
					
					
					if (shippingAmount != null && shippingAmount != 0) {

						//ReferenceType shiReferenceType = new ReferenceType();
						com.intuit.ipp.data.ReferenceType shiReferenceType = new com.intuit.ipp.data.ReferenceType();

						shiReferenceType.setValue("SHIPPING_ITEM_ID");
						//com.intuit.qbo.ecommerce.domain.Line shippingLineItem2 = new com.intuit.qbo.ecommerce.domain.Line();
						
						Line shippingLineItem2 = new Line();
						
						//shippingLineItem2.setDetailType(value);

						shippingLineItem2.setDetailType(LineDetailTypeEnum.SALES_ITEM_LINE_DETAIL);
						shippingLineItem2.setAmount(new BigDecimal(shippingAmount));
						
						//SalesItemLineDetail shippingItemLineDetail2 = new SalesItemLineDetail();
						
						com.intuit.ipp.data.SalesItemLineDetail shippingItemLineDetail2 = new com.intuit.ipp.data.SalesItemLineDetail();
						shippingItemLineDetail2.setItemRef(shiReferenceType);
						shippingLineItem2.setSalesItemLineDetail(shippingItemLineDetail2);
						lineItemsList.add(shippingLineItem2);
					}

					invoice.setLine(lineItemsList);
					

					String displayName = (order.getCustomerFirstName().trim() + " " + order
							.getCustomerLastName().trim()).trim();

					Customer customerObject = getCustomerByNameFromQB(appToken,companyId, displayName, accessToken, accessTokenSecret);
					
					
					if (customerObject != null) {
						
						
						String customerId=customerObject.getId();
						
						//ReferenceType customerRef = new ReferenceType();
						
						com.intuit.ipp.data.ReferenceType customerRef = new com.intuit.ipp.data.ReferenceType();
						customerRef.setValue(customerId);
						invoice.setCustomerRef(customerRef);
				
						
					
						//EmailAddress emailAddress2 = new EmailAddress();
						
						com.intuit.ipp.data.EmailAddress emailAddress2 = new com.intuit.ipp.data.EmailAddress();
						emailAddress2.setAddress(order.getCustomerEmail().trim());
						invoice.setBillEmail(emailAddress2);

						//PhysicalAddress physicalAddress = new PhysicalAddress();
						
						com.intuit.ipp.data.PhysicalAddress physicalAddress = new com.intuit.ipp.data.PhysicalAddress();
						physicalAddress.setLine1(normalizeAddress(order
								.getShippingAddress1()));
						physicalAddress.setLine2(normalizeAddress(order
								.getShippingAddress2()));
						physicalAddress
								.setLine3(normalizeAddress(order.getShippingState()));
						physicalAddress.setCity(normalizeAddress(order.getShippingCity()));
						physicalAddress.setCountry(normalizeAddress(order
								.getShippingCountry()));
						physicalAddress.setPostalCode(order.getShippingPostCode());

						invoice.setShipAddr(physicalAddress);
						// setting the same billing address since KR doesn't
						// provide
						// the
						// billing address
						invoice.setBillAddr(physicalAddress);

						//ReferenceType currencyReference = new ReferenceType();
						
						com.intuit.ipp.data.ReferenceType currencyReference = new com.intuit.ipp.data.ReferenceType();
						currencyReference.setValue(order.getCurrency());
						invoice.setCurrencyRef(currencyReference);

						// setting order id as doc number
						invoice.setDocNumber(String.valueOf(order.getId()));

						try {
							/*Date txnDate = Constants.DATE_FORMATTER.parse(order
									.getDateAdded());*/
							
							Date txnDate = Constants.DATE_FORMATTER_QBO.parse(order
									.getDateAdded());
							
							String transactionDate = Constants.DATE_FORMATTER_QBO.format(txnDate);
							invoice.setTxnDate(txnDate);
						} catch (java.text.ParseException pe) {
							LOG.error(
									"Ignoring error while parsing txn date: "
											+ order.getDateAdded(), pe);
						}

						//ReferenceType shipMethodreferenceType = new ReferenceType();
						com.intuit.ipp.data.ReferenceType shipMethodreferenceType = new com.intuit.ipp.data.ReferenceType();
						
						List<KROrderHistory> krorderHostoryList=order.getOrderHistory();
						KROrderHistory krorderHistory=null;
						if(krorderHostoryList!=null&&krorderHostoryList.size()!=0){
							krorderHistory=krorderHostoryList.get(krorderHostoryList.size()-1);
							shipMethodreferenceType.setValue(krorderHistory.getCourier());

							// todo - set shipdate
							invoice.setTrackingNum(krorderHistory.getAwbCode());
							invoice.setShipMethodRef(shipMethodreferenceType);
						}
						
						// https://quickbooks.api.intuit.com/v3/company/1298296485/invoice?requestid=f3cd14b5a6a341bead4ac68542a369b3&minorversion=1&
						// service.add(invoice);

						/*Gson gson2 = new Gson();
						String url = "https://quickbooks.api.intuit.com/v3/company/" + companyId
								+ "/invoice";
						String reqBody = gson2.toJson(invoice);

						MediaType mediaTypeJson1 = new MediaType("application", "json");
						HttpHeaders requestHeaders1 = new HttpHeaders();
						requestHeaders1.setAccept(Collections.singletonList(mediaTypeJson1));
						requestHeaders1.setContentType(mediaTypeJson1);
						requestHeaders1.add("Authorization", "INTUIT_IAM x_intuit_authid=" + authId
								+ ",x_intuit_ticket=" + ticket + ",intuit-app-token=" + appToken
								+ "");
						HttpEntity<String> requestEntity1 = new HttpEntity<String>(reqBody,
								requestHeaders1);

						RestTemplate template = new RestTemplate();

						ResponseEntity<String> resEntity = template.postForEntity(url,
								requestEntity1, String.class);
								
								
*/
						
						DataService service =getService( companyId, accessToken, accessTokenSecret);
						service.add(invoice);
						LOG.info("Added Invoice to QB for customer : " + displayName
								+ ".Shop domain : " + shopDomain);
					} else {
						LOG.info("Customer not found in QB for customer : " + displayName
								+ ".Shop domain : " + shopDomain);
					}
				} else {
					LOG.info("Skipping Invoice creation since no line items found for order : "
							+ order.getId() + ".Shop domain : " + shopDomain);
				}
			} else {
				LOG.info("Invoice already exist !!!" + ".Shop domain : " + shopDomain);
			}

		} catch (Exception e) {
			LOG.error("Some error occured while adding invoice/Sales Reciept to QB for customer : "
					+ order.getCustomerFirstName() + ".Shop domain : " + shopDomain, e);
		}
	}
	
	
	// For getting the Data service from the context
	private DataService getService( String companyID, String accessToken, String accessTokenSecret){
		
		
		OAuthAuthorizer oauth = new OAuthAuthorizer(Constants.consumerKey, Constants.consumerSecret, accessToken, accessTokenSecret);
		Context context;
		DataService service=null;
		try {
			
			String url = "https://quickbooks.api.intuit.com/v3/company"; 
			Config.setProperty(Config.BASE_URL_QBO,url );
			context = new Context(oauth, Constants.appToken, ServiceType.QBO, companyID);
		 service = new DataService(context);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return service;
		
	}

	public void addCustomerToQB(String appToken, String companyId,
			KROrder krOrderEntity, String shopDomain, String accessToken , String accessTokenSecret) {
		try {
			com.intuit.qbo.ecommerce.domain.Customer krCustomer = new com.intuit.qbo.ecommerce.domain.Customer();
			DataService service=getService(companyId, accessToken, accessTokenSecret);
			// setting display name to later query on this
			String displayName = (krOrderEntity.getCustomerFirstName().trim() + " " + krOrderEntity
					.getCustomerLastName().trim()).trim();

			Customer customerObject = getCustomerByNameFromQB(appToken,companyId, displayName, accessToken, accessTokenSecret);
			
			 /*Iterator<? extends IEntity> iterator = customerObject.getEntities().iterator();
		      
		        Customer customerObject1=null;
		      
		        while(iterator.hasNext()){
		        	customerObject1 = (Customer) iterator.next();
		            System.out.println( "--------------------Item"+customerObject1.getId());
		            */
		        
			
			
			Customer customer= new Customer();
			
			if (customerObject== null) {
				
				
				
				customer.setGivenName(krOrderEntity.getCustomerFirstName().trim());
				customer.setFamilyName(krOrderEntity.getCustomerLastName().trim());

				customer.setDisplayName(displayName);

				
				
				
				/*krCustomer.setGivenName(krOrderEntity.getCustomerFirstName().trim());
				krCustomer.setFamilyName(krOrderEntity.getCustomerLastName().trim());

				krCustomer.setDisplayName(displayName);*/
				
				com.intuit.ipp.data.EmailAddress emailAddr= new com.intuit.ipp.data.EmailAddress();
				
				emailAddr.setAddress(krOrderEntity.getCustomerEmail().trim());
				
				customer.setPrimaryEmailAddr(emailAddr);

				/*PrimaryEmailAddr primaryEmailAddr = new PrimaryEmailAddr();
				primaryEmailAddr.setAddress(krOrderEntity.getCustomerEmail().trim());

				
				krCustomer.setPrimaryEmailAddr(primaryEmailAddr);*/
				
				

				/*PrimaryPhone primaryPhone = new PrimaryPhone();

				primaryPhone.setCountryCode("91");
				primaryPhone.setFreeFormNumber(krOrderEntity.getCustomerTelephone().trim());
				krCustomer.setPrimaryPhone(primaryPhone);*/
				
				// For customer primary phone
				TelephoneNumber telNumber = new TelephoneNumber();
				

				telNumber.setCountryCode("91");
				telNumber.setFreeFormNumber(krOrderEntity.getCustomerTelephone().trim());
				customer.setPrimaryPhone(telNumber);
				
				//// For customer mobile phone
				TelephoneNumber mobileNumber = new TelephoneNumber();
				mobileNumber.setCountryCode("91");

				mobileNumber.setFreeFormNumber(krOrderEntity.getCustomerMobile().trim());
				customer.setMobile(mobileNumber);

				/*Mobile mobile = new Mobile();
				mobile.setCountryCode("91");

				mobile.setFreeFormNumber(krOrderEntity.getCustomerMobile().trim());
				krCustomer.setMobile(mobile);*/

				// For customer shipping address
				com.intuit.ipp.data.PhysicalAddress shipAddr = new com.intuit.ipp.data.PhysicalAddress();
				
				
				//ShipAddr shipAddr = new ShipAddr();

				// shipAddr.setLine1(removeSpecialCharFromString(krOrderEntity.getShippingAddress1()));
				// shipAddr.setLine2(removeSpecialCharFromString(krOrderEntity.getShippingAddress2()));
				shipAddr.setLine1(normalizeAddress(krOrderEntity.getShippingAddress1()));
				shipAddr.setLine2(normalizeAddress(krOrderEntity.getShippingAddress2()));
				shipAddr.setLine3(normalizeAddress(krOrderEntity.getShippingState()));
				shipAddr.setCity(normalizeAddress(krOrderEntity.getShippingCity()));
				shipAddr.setCountry(normalizeAddress(krOrderEntity.getShippingCountry()));
				shipAddr.setPostalCode(krOrderEntity.getShippingPostCode());

				customer.setShipAddr(shipAddr);

				// For customer shipping address
				
				com.intuit.ipp.data.PhysicalAddress billAddr = new com.intuit.ipp.data.PhysicalAddress();
				
				
				//BillAddr billAddr = new BillAddr();

				// billAddr.setLine1(removeSpecialCharFromString(krOrderEntity.getShippingAddress1()));
				// billAddr.setLine2(removeSpecialCharFromString(krOrderEntity.getShippingAddress2()));
				billAddr.setLine1(normalizeAddress(krOrderEntity.getShippingAddress1()));
				billAddr.setLine2(normalizeAddress(krOrderEntity.getShippingAddress2()));
				billAddr.setLine3(normalizeAddress(krOrderEntity.getShippingState()));
				billAddr.setCity(normalizeAddress(krOrderEntity.getShippingCity()));
				billAddr.setCountry(normalizeAddress(krOrderEntity.getShippingCountry()));
				billAddr.setPostalCode(krOrderEntity.getShippingPostCode());
				
				customer.setBillAddr(billAddr);

				//krCustomer.setShipAddr(shipAddr);

				//krCustomer.setBillAddr(billAddr);
				
				// For customer currency reference
				com.intuit.ipp.data.ReferenceType currencyRef = new com.intuit.ipp.data.ReferenceType();

				//CurrencyRef currencyRef = new CurrencyRef();
				currencyRef.setValue(krOrderEntity.getCurrency());
				
				customer.setCurrencyRef(currencyRef);
				
				//krCustomer.setCurrencyRef(currencyRef);

				// POST customer to
				// https://quickbooks.api.intuit.com/v3/company/1298296485/customer

				/*Gson gson2 = new Gson();
				String url = "https://quickbooks.api.intuit.com/v3/company/" + companyId
						+ "/customer";
				String reqBody = gson2.toJson(krCustomer);

				MediaType mediaTypeJson = new MediaType("application", "json");
				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.setAccept(Collections.singletonList(mediaTypeJson));
				requestHeaders.setContentType(mediaTypeJson);
				requestHeaders.add("Authorization", "INTUIT_IAM x_intuit_authid=" + authId
						+ ",x_intuit_ticket=" + ticket + ",intuit-app-token=" + appToken);
				HttpEntity<String> requestEntity = new HttpEntity<String>(reqBody, requestHeaders);

				System.out.println(requestHeaders);

				RestTemplate template = new RestTemplate();

				ResponseEntity<String> resEntity = template.postForEntity(url, requestEntity,
						String.class);*/
				
				service.add(customer);
				
				LOG.info("Adding customer in QBO : " + displayName + ".Shop domain : " + shopDomain);

			} else {
				LOG.info("Skipping customer insertion.. " + displayName + ".Shop domain : "
						+ shopDomain);
			}
			
		          

		} catch (Exception e) {
			LOG.error("Error in adding customer to QB" + ".Shop domain : " + shopDomain + e);
		}

	}

	/*private void addProducts(String ticket, String authId, String appTocken, String companyId,
			List<KRProduct> productList, String shopDomain) {
		for (KRProduct krProductEntity : productList) {
			try {

				// krProductEntity.setName(krProductEntity.getName().replaceAll("'",
				// "\'").trim());

				JsonObject itemObject = getItemByNameFromQB(ticket, authId, appTocken, companyId,
						krProductEntity.getName().trim(), krProductEntity.getSku().trim(),
						shopDomain);

				ModifiedProductData modifiedProductData = getSimpleQBProductName(krProductEntity
						.getName().trim(), krProductEntity.getSku().trim());

				String newItemName = modifiedProductData.getName();

				if (itemObject == null) {
					LOG.info("Product not available in QB.. adding to QB.." + newItemName
							+ ".Shop domain : " + shopDomain);

					String price = krProductEntity.getPrice();

					price = price.replace(",", "");

					Product product = new Product();
					product.setName(newItemName);
					if (modifiedProductData.isProductNameChanged()) {
						product.setDescription(removeSpecialCharFromString(krProductEntity
								.getName().trim()));
					}
					product.setQtyOnHand(krProductEntity.getQuantity());
					product.setUnitPrice(new BigDecimal(price.substring(3).trim()));

					product.setType("Inventory");

					IncomeAccountRef incomeAccountRef = new IncomeAccountRef();

					incomeAccountRef.setName("Sales");
					incomeAccountRef.setValue("1");

					product.setIncomeAccountRef(incomeAccountRef);

					if (krProductEntity.getDateAdded() != null) {

						Date productAddedDate = Constants.DATE_FORMATTER.parse(krProductEntity
								.getDateAdded());
						product.setInvStartDate(productAddedDate);
					}

					String url = "https://quickbooks.api.intuit.com/v3/company/" + companyId
							+ "/item";
					Gson gson = new Gson();
					String reqBody = gson.toJson(product);

					MediaType mediaTypeJson = new MediaType("application", "json");
					HttpHeaders requestHeaders = new HttpHeaders();
					requestHeaders.setAccept(Collections.singletonList(mediaTypeJson));
					requestHeaders.setContentType(mediaTypeJson);
					requestHeaders.add("Authorization", "INTUIT_IAM x_intuit_authid=" + authId
							+ ",x_intuit_ticket=" + ticket + ",intuit-app-token=" + appTocken);
					HttpEntity<String> requestEntity = new HttpEntity<String>(reqBody,
							requestHeaders);

					RestTemplate template = new RestTemplate();

					ResponseEntity<String> resEntity = template.postForEntity(url, requestEntity,
							String.class);
				} else {
					LOG.info("Item already exists. Skip inserting Item in QB.. " + newItemName
							+ ".Shop domain : " + shopDomain);
				}

			} catch (Exception e) {
				LOG.error("Error adding Item to QB : : " + krProductEntity.getName()
						+ ".Shop domain : " + shopDomain, e);
			}
		}
	}*/
	
	public void addProducts(String appTocken, String companyId,
			List<KRProduct> productList, String shopDomain, String accessToken, String accessTokenSecret) {
		for (KRProduct krProductEntity : productList) {
			try {

				// krProductEntity.setName(krProductEntity.getName().replaceAll("'",
				// "\'").trim());
				
				String productSku = krProductEntity.getSku().trim().toUpperCase();

				Item itemObject = getItemByNameFromQB(appTocken, companyId,krProductEntity.getName().trim(),productSku,shopDomain, accessToken, accessTokenSecret);

				/*ModifiedProductData modifiedProductData = getSimpleQBProductName(krProductEntity
						.getName().trim(), krProductEntity.getSku().trim());
*/
				
				String newItemName =getModifiedProductName(krProductEntity.getName(),krProductEntity.getSku());//krProductEntity.getSku(); // modifiedProductData.getName();

				

				DataService service=getService(companyId, accessToken, accessTokenSecret);
				if (itemObject == null) {
					LOG.info("Product not available in QB.. adding to QB.." + newItemName
							+ ".Shop domain : " + shopDomain);

					String price = krProductEntity.getPrice();

					price = price.replace(",", "");

					Item product = new Item();
					product.setName(newItemName);
					/*if (modifiedProductData.isProductNameChanged()) {
						product.setDescription(removeSpecialCharFromString(krProductEntity
								.getName().trim()));
					}*/
					product.setQtyOnHand(BigDecimal.valueOf(krProductEntity.getQuantity()));
					product.setUnitPrice(new BigDecimal(price.substring(3).trim()));

					product.setType(ItemTypeEnum.INVENTORY);

					com.intuit.ipp.data.ReferenceType incomeAccountRef = new com.intuit.ipp.data.ReferenceType();

					incomeAccountRef.setName("Sales of Product Income");
					incomeAccountRef.setValue("1");

					product.setIncomeAccountRef(incomeAccountRef);

					if (krProductEntity.getDateAdded() != null) {

						Date productAddedDate = Constants.DATE_FORMATTER.parse(krProductEntity
								.getDateAdded());
						product.setInvStartDate(productAddedDate);
					}
/*
					String url = "https://quickbooks.api.intuit.com/v3/company/" + companyId
							+ "/item";
					Gson gson = new Gson();
					String reqBody = gson.toJson(product);

					MediaType mediaTypeJson = new MediaType("application", "json");
					HttpHeaders requestHeaders = new HttpHeaders();
					requestHeaders.setAccept(Collections.singletonList(mediaTypeJson));
					requestHeaders.setContentType(mediaTypeJson);
					requestHeaders.add("Authorization", "INTUIT_IAM x_intuit_authid=" + authId
							+ ",x_intuit_ticket=" + ticket + ",intuit-app-token=" + appTocken);
					HttpEntity<String> requestEntity = new HttpEntity<String>(reqBody,
							requestHeaders);

					RestTemplate template = new RestTemplate();

					ResponseEntity<String> resEntity = template.postForEntity(url, requestEntity,
							String.class);*/
					service.add(product);
					
				} else {
					LOG.info("Item already exists. Skip inserting Item in QB.. " + newItemName
							+ ".Shop domain : " + shopDomain);
				}

			} catch (Exception e) {
				LOG.error("Error adding Item to QB : : " + krProductEntity.getName()
						+ ".Shop domain : " + shopDomain, e);
			}
			
		}
	}


	private List<KRProduct> getProductsFromKR(String shopDomain, String webApiKey,
			SyncDates syncDates) {
		RestTemplate restTemplate = new RestTemplate();
		List<KRProduct> productsList = new ArrayList<KRProduct>();
		try {
			ResponseEntity<String> response = restTemplate.getForEntity(shopDomain
					+ "/index.php?route=feed/web_api/products&key=" + webApiKey, String.class);

			Integer totalProducts = 0;
			if (response != null) {
				if (response.getStatusCode() == HttpStatus.OK) {
					String json = response.getBody();
					Gson gson = new Gson();
					KRProductResponse businessInfo = gson.fromJson(json, KRProductResponse.class);
					productsList.addAll(businessInfo.getProducts());
					totalProducts = businessInfo.getTotal_products();
				}
			}

			if (totalProducts > 100) {
				int pages = totalProducts / 100;

				for (int i = pages; i >= 0; i--) {
					if ((i + 1) == 1) {
						continue;
					} else {
						response = restTemplate.getForEntity(shopDomain
								+ "/index.php?route=feed/web_api/products&key=" + webApiKey
								+ "&page=" + (i + 1), String.class);
						if (response != null) {
							if (response.getStatusCode() == HttpStatus.OK) {
								String json = response.getBody();
								Gson gson = new Gson();
								KRProductResponse businessInfo = gson.fromJson(json,
										KRProductResponse.class);
								productsList.addAll(businessInfo.getProducts());
								totalProducts = businessInfo.getTotal_products();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.error("Error in fetching products for shop domain : " + shopDomain, e);
		}

		return productsList;

	}

	public Item getItemByNameFromQB(String appToken,String companyId, String productName, String sku, String shopDomain, String accessToken, String accessTokenSecret) {
		
		
		// Query Item based on Unique name for reference in
		// invoice

		productName =getModifiedProductName(productName,sku); 
		System.out.println("==============Product name from QB "+productName);
		//productName = getSimpleQBProductName(productName, sku).getName();
		String productfinalName;
		
		
		
		DataService service=getService(companyId, accessToken, accessTokenSecret);
		 Item item=null;
	        String itemId="";
	        
		
		 QueryResult queryResultForItem=null;
		try {
			
			

				//productfinalName= new String(productName.getBytes(UTF_8), UTF_8);
				productfinalName=StringEscapeUtils.unescapeJava(productName);
				String queryForItem = "Select * from Item where name = '" + productfinalName + "'";
			
			 queryResultForItem = service.executeQuery(queryForItem);
			 Iterator<? extends IEntity> iterator = queryResultForItem.getEntities().iterator();
		      
		       
		       
		        while(iterator.hasNext()){
		        	item = (Item) iterator.next();
		            System.out.println( "--------------------Item"+item.getId());
		            itemId=item.getId();
		           System.out.println("--------------------itemId"+itemId);
		          

		           
		        }
			
		} catch (FMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
/*
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
		if (json.startsWith("{\"Fault\"")) {
			throw new RuntimeException("Error ::::: Wrong params to item api for item name : "
					+ productName + ".Shop domain : " + shopDomain);

		}*/
		
		
		//Gson gson = new Gson();
		//JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
		

		if (item == null) {
			return null;
		}
		

		return item;
	}

	private Customer getCustomerByNameFromQB(String appToken,String companyId, String displayName, String accessToken, String accessTokenSecret) {

		// Query Customer based on Unique display name for
		// reference in invoice
		String queryForCustomer = "Select * from Customer where displayname = '" + displayName+ "'";
				

		DataService service = getService(companyId, accessToken, accessTokenSecret);
		
		 QueryResult queryResultForCustomer=null;
		 Customer customer=null;
			try {
				queryResultForCustomer = service .executeQuery(queryForCustomer);
				
				Iterator<? extends IEntity> iterator = queryResultForCustomer.getEntities().iterator();
			      
		        
		        String customerId="";
		       
		        while(iterator.hasNext()){
		        	customer = (Customer) iterator.next();
		           /* System.out.println( "--------------------Item"+customer.getId());
		            customerId=customer.getId();
		           System.out.println("--------------------itemId"+customer);*/
		         
		        }
				
				
			} catch (FMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		 

		/*MediaType mediaTypeJson = new MediaType("application", "json");
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
		JsonElement customerElement = responseObject.get("Customer");*/

		if (customer == null) {
			return null;
		}
	
		return customer;
	}

	public Invoice getInvoiceByOrderIdAndCompanyId( String appToken, String orderId, String companyId, String accessToken, String accessTokenSecret) {

		// Query Customer based on Unique display name for
		// reference in invoice
		String queryForInvoice = "Select * from Invoice where DocNumber = '" + orderId + "'";
        DataService service= getService(companyId, accessToken, accessTokenSecret);
        
        QueryResult queryResultForInvoice=null;
		try {
			queryResultForInvoice = service .executeQuery(queryForInvoice);
			
		} catch (FMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	  
		
		 Iterator<? extends IEntity> iterator = queryResultForInvoice.getEntities().iterator();
	      
	        Invoice invoiceObject=null;
	      
	        while(iterator.hasNext()){
	        	invoiceObject = (Invoice) iterator.next();
	            System.out.println( "--------------------Invoice"+invoiceObject.getTxnDate());
		 
	        }
/*
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
		JsonElement invoiceElement = responseObject.get("Invoice");*/

		if (invoiceObject == null) {
			return null;
		}
		
		return invoiceObject;

	}

	/*public ModifiedProductData getSimpleQBProductName(String productName, String sku) {
		String productNameWithoutSpecialChars = removeSpecialCharFromString(productName);
		String name = productNameWithoutSpecialChars + "("+ sku +")";
		boolean isChanged = false;
		if (name.length() > 99) {
			// this is to make sure that length of products should not be > 100
			// since QB doesn't support
			name = productNameWithoutSpecialChars.substring(0, (100 - (sku.length() + 4))) + "("
					+ sku +")";
			isChanged = true;
		}
		return new ModifiedProductData(name, isChanged);
	}*/
	
	
	//Froming QB productname with KR sku appended with KR productname with 100 chars
		private String getModifiedProductName(String productName,String sku){
			
			
			String qbItemName = new String(sku.trim()+ "-" + productName.trim());
			if(qbItemName.length()>100){
				qbItemName=qbItemName.substring(0, 99);
			}
			
			return removeSpecialCharFromString(qbItemName);
			
		}


	private String removeSpecialCharFromString(String s) {
		
		String k= new String(s);
		return k.replaceAll("[ \\/$]", "");
	}

	private String normalizeAddress(String s) {
		return Normalizer.normalize(s, Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	private class ModifiedProductData {
		private String name;
		private boolean isProductNameChanged;

		ModifiedProductData(String name, boolean isProductNameChanged) {
			this.name = name;
			this.isProductNameChanged = isProductNameChanged;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public boolean isProductNameChanged() {
			return isProductNameChanged;
		}

		public void setProductNameChanged(boolean isProductNameChanged) {
			this.isProductNameChanged = isProductNameChanged;
		}
	}

	/*public static void main(String[] args) {
		// String s = Normalizer.normalize("ab   cdd",
		// Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		// System.out.println(s);

		KRCMapper krcMapper = new KRCMapper();
		ModifiedProductData tt = krcMapper
				.getSimpleQBProductName(
						"JBeige Dark Red Tree of Life Pattachitra Painted Handloom Sareeeeeeeeeeeerrrrrrawaaaaaaaaas",
						"test");
		System.out.println(tt.getName());
		System.out.println(tt.getName().length());

	}*/
}
