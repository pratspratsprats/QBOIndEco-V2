package com.intuit.qbo.company.util;

	import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intuit.qbo.company.model.CompanyResponse;
import com.intuit.qbo.company.model.Qbocompany;
import com.intuit.qbo.company.service.CompanyCreator;
import com.intuit.qbo.company.service.NotificationSender;
	 
/**
 * Main class to create company in Production environment
 * @author Amaresh Mourya
 * @date Oct 18, 2014
 **/

public class MainProd {
	private static final Logger LOGGER = LoggerFactory.getLogger(MainProd.class);

		public static void main(String[] args) throws Exception {
	
			CompanyCreator creator = new CompanyCreator();
			LOGGER.info("Starting User and Company creation...");
			CompanyResponse  companyCreated = creator.createUserAndCompany(CompanyCreationUtility.createQboCompanyData("amaresh12345", "amaresh.mourya@gmail.com", "IntuitEzone"), true);
			
			if(companyCreated != null)
			{
				Qbocompany qbocompany = companyCreated.getQbocompany();
				if(qbocompany != null)
				{
				  LOGGER.info("Sending notification...");
				  NotificationSender sender =  new NotificationSender();
				  sender.sendNewCompanyCreationMail(qbocompany.getCompanyName(), qbocompany.getUserName(),qbocompany.getUserEmail(), qbocompany.getPassword());
				}	
			}	 
		}
	 
	
}
