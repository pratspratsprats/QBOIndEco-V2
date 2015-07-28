package com.intuit.incubation.service;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.intuit.incubation.company.QBOCompanyCreator;
import com.intuit.incubation.dao.SellerDAO;
import com.intuit.incubation.entity.Seller;
import com.intuit.incubation.exception.ServiceException;
import com.intuit.incubation.helper.AccountingUtility;
import com.intuit.incubation.logging.Log;
import com.intuit.incubation.model.LineItem;
import com.intuit.incubation.model.Order;
import com.intuit.incubation.model.QBOCompany;
import com.intuit.incubation.model.QBOCompanyResponse;

@Component
public class AccountingService implements IAccountingService {

	@Log
	private Logger logger;
	@Autowired
	private SellerDAO sellerDao;

	@Autowired
	private AccountingUtility utility;

	@Autowired
	private QBOCompanyCreator companyCreator;

	public static final String APP_TOKEN = "fc42dce2bdf9cb4614baa3ebfeef1811bfc8";

	public void newOrderPlaced(Order order) throws ServiceException {
		try {
			logger.info("Validating input Order object for OrderId: " + order.getOrderId());
			utility.validateComponent(order);
			logger.info("Order object validated successfully for OrderId: " + order.getOrderId());
			List<LineItem> items = order.getLineItems();
			if (items != null && !items.isEmpty()) {
				for (LineItem item : items) {
					try {
						String sellerId = item.getSellerId();
						logger.info("Processing started for Order:Seller= " + order.getOrderId()
								+ ":" + sellerId);
						String companyName = item.getSellerId() + "_" + order.getChannel();
						;
						Seller sellerEntity = sellerDao.selectBySellerId(sellerId);
						if (sellerEntity == null) // New Seller's order is
													// received
						{
							logger.info("Order:Seller= " + order.getOrderId() + ":" + sellerId
									+ " doesn't have QBO company, creating one for it.");

							QBOCompany qbCompany = utility.createQBOCompanyDataFromSellerId(
									sellerId, companyName, item.getEmailId());
							logger.info("Creating company for Order:Seller= " + order.getOrderId()
									+ ":" + sellerId);
							QBOCompanyResponse response = companyCreator.create(qbCompany);
							if (response != null) {
								if (response.getSuccess()) {
									sellerEntity = new Seller();
									sellerEntity.setCompanyName(companyName);
									sellerEntity.setSellerId(sellerId);
									sellerEntity.setQbUsername(response.getQbocompany()
											.getUserName());
									sellerEntity.setQbPassword(response.getQbocompany().getPassword());
									sellerEntity.setQbCompanyId(response.getCompanyId());
									sellerDao.insert(sellerEntity);
									logger.info("Company credentials inserted in Seller table for Seller: "
											+ sellerId);

									utility.pushDataToQBO(response.getTicket(),
											response.getAuthId(), APP_TOKEN,
											response.getCompanyId(), order, item);
								} else {
									logger.error("Company creation failed for Order:Seller= "
											+ order.getOrderId() + ":" + sellerId + ". Reason: "
											+ response.getErrorMessage());
								}
							} else {
								logger.error("CompanyCreationResponse is null for Order:Seller= " + order.getOrderId() +":"+ sellerId);							}

						} else // Existing seller's order is received
						{
							logger.info("Order:Seller= "
									+ order.getOrderId()
									+ ":"
									+ sellerId
									+ " have QBO company,  will be using it for pushing order data.");
							sellerDao.updatePassword(sellerId);
							sellerEntity = sellerDao.selectBySellerId(sellerId);
							com.intuit.incubation.company.IUSClient iusClient = new com.intuit.incubation.company.IUSClient();

							logger.info("Qbo user ::::: " + sellerEntity.getQbUsername());
							logger.info("Qbo password ::::: " + sellerEntity.getQbPassword());
							logger.info("Qbo company id ::::: " + sellerEntity.getQbCompanyId());

							JsonObject qboUser = iusClient.login(sellerEntity.getQbUsername(),
									sellerEntity.getQbPassword());

							if (qboUser != null) {

								String ticket = qboUser.get("ticket").getAsString();
								String authId = qboUser.get("userId").getAsString();

								utility.pushDataToQBO(ticket, authId, APP_TOKEN,
										sellerEntity.getQbCompanyId(), order, item);

							}
						}

					} catch (Exception e) {
						logger.error("Error while processing Order:Seller= " + order.getOrderId()
								+ ":" + item.getSellerId() + "Exception: " + e.getMessage(), e);
					}
				}
			}
			logger.info("Processing completed for OrderId: " + order.getOrderId());
		} catch (Exception e) {
			logger.error("Order object is not valid for OrderId: " + order.getOrderId(), e);
			throw new ServiceException(e);
		}
	}

}
