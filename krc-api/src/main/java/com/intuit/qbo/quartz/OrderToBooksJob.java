/**
 * 
 */
package com.intuit.qbo.quartz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.intuit.qbo.ecommerce.krc.api.dao.CustomerDAO;
import com.intuit.qbo.ecommerce.krc.api.dto.CustomerDO;
import com.intuit.qbo.ecommerce.krc.constants.Constants;
import com.intuit.qbo.ecommerce.krc.entity.SyncDates;
import com.intuit.qbo.ecommerce.krc.synch.KRCMapper;

/**
 * @author Vijayan Srinivasan
 * @since Nov 2, 2014 12:23:13 PM
 * 
 */
public class OrderToBooksJob extends QuartzJobBean {

	private Logger LOGGER = LoggerFactory.getLogger(OrderToBooksJob.class);

	@Autowired
	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		LOGGER.info("Scheduler triggered on " + new Date());

		//ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		//CustomerDAO customerDAO = applicationContext.getBean(CustomerDAO.class);
		String companyId=(String) context.getMergedJobDataMap().get("companyId");
		CustomerDO customerDO = (CustomerDO) context.getMergedJobDataMap().get("customerDO");
		//ApplicationContext appcontext = new ClassPathXmlApplicationContext("all-app-beans.xml");
		CustomerDAO customerDAO=(CustomerDAO) context.getMergedJobDataMap().get("customerDAO");
		if (customerDO != null) {

			//for (CustomerDO customerDO : listOfCustomers) {
				boolean isToBeSynced = customerDO.getSyncStatus();
				Date accessTokenValid=customerDO.getAccessTokenExpirationDate();
				boolean validTokens=false;
				// For Token Validation
				Date date1=null;
				try {
	    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        	
	    		if(accessTokenValid!=null){
					 date1 = sdf.parse(accessTokenValid.toString());
					System.out.println("accessTokenValid::::::::::::::"+date1);
					System.out.println("currentDate::::::::::::::"+new Date().toString());
					
					
	    		
					SimpleDateFormat sdf1 = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                            Locale.ENGLISH);
					
					Date date2 = sdf1.parse(new Date().toString());
					 
		        	System.out.println(sdf.format(date1));
		        	System.out.println(sdf.format(date2));
		        	
		 
		        	validTokens=(date1.compareTo(date2)>0);
	    		}
				}
		        	catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				// Don't let the scheduler run if QB credentials are not present
				if (isToBeSynced && customerDO.getQbCompanyId() != null
						&& !customerDO.getQbCompanyId().isEmpty()
						&& !StringUtils.isBlank(customerDO.getAccessToken())
						&& !StringUtils.isBlank(customerDO.getAccessTokenSecret())
						&& customerDO.getAccessTokenExpirationDate()!=null
						&& validTokens) {
					
					
					
					
					 
					

					/*IUSClient iusClient = new IUSClient();
					JsonObject qboUser = iusClient.login(customerDO.getQbUserName(),
							customerDO.getQbPassword());
					if (qboUser != null) {
						String ticket = qboUser.get("ticket").getAsString();
						String authId = qboUser.get("userId").getAsString();*/
						KRCMapper krcMapper = new KRCMapper();
						// Sync KR Data From Kart Rocket
						SyncDates syncDates = krcMapper.syncKRData(customerDO.getShopDomain(),
								customerDO.getWebApiKey(), Constants.APP_TOKEN,
								customerDO.getQbCompanyId(), customerDO.getLastOrderSyncDate(),
								customerDO.getLastProductSyncDate(), customerDO.getAccessToken(), customerDO.getAccessTokenSecret());
						
						if (syncDates.getLastOrderSyncDate() != null) {
							customerDAO.updateLastOrderSyncDate(syncDates.getLastOrderSyncDate(),
									customerDO.getId());
						}
						if (syncDates.getLastProductSyncDate() != null) {
							customerDAO.updateLastProductSyncDate(
									syncDates.getLastProductSyncDate(), customerDO.getId());
						}
					

				 }else{
	        	
					LOGGER.info("Either Data sync is disbaled for customer or QB credentials are not present in DB : "
							+ customerDO);
				}
			//}

		}

		LOGGER.info("Scheduler finished execution on " + new Date());
	}
}
