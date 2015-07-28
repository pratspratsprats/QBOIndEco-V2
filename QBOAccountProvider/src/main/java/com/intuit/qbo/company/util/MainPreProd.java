package com.intuit.qbo.company.util;

	import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intuit.qbo.company.model.CompanyResponse;
import com.intuit.qbo.company.model.Qbocompany;
import com.intuit.qbo.company.service.CompanyCreator;
import com.intuit.qbo.company.service.NotificationSender;
	 
/**
 * Main class to create company in PreProduction environment
 * @author Amaresh Mourya
 * @date Oct 18, 2014
 **/

public class MainPreProd {
	private static final Logger LOGGER = LoggerFactory.getLogger(MainPreProd.class);

		public static void main(String[] args) throws Exception {
	
			CompanyCreator creator = new CompanyCreator();
			LOGGER.info("Starting User and Company creation...");
			Random rand =  new Random();
			int next = rand.nextInt(1000);
			CompanyResponse  companyCreated = creator.createUserAndCompany(CompanyCreationUtility.createQboCompanyData("amaresh"+next, "mourya.amar88@gmail.com", "amareshTest"), false);
			
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
