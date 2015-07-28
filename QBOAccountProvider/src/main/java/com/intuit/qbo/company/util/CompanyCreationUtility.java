package com.intuit.qbo.company.util;

import com.intuit.qbo.company.model.Qbocompany;

/**
 * Utility class for user and company creation
 * @author Amaresh Mourya
 * @date Oct 19, 2014
 **/

public class CompanyCreationUtility {
	
	public static Qbocompany createQboCompanyData(String userName, String userEmailId, String companyName)
	{
		Qbocompany data = new Qbocompany();
		data.setUserName(userName);
		data.setUserEmail(userEmailId );
		data.setCompanyName(companyName );
		data.setPassword("intuit01");
		data.setBillingCode("INP-FRE");
		data.setIndustryType("RETAIL_SHOPS_MAIL_ORDER_AND_ONLINE");
		data.setLanguage("en");
		data.setPartner("None");
		data.setRegion("IN");
		return data; 
	}
	
	public static Qbocompany getTestData(int next)
	{
		return createQboCompanyData("amourya"+next, "amaresh"+next+"@gmail.com", "AmareshTestCompany"+next);
	}
}
