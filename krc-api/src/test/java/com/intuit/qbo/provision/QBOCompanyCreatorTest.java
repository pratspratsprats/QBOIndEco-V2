/**
 * 
 */
package com.intuit.qbo.provision;

import java.util.Date;

import com.intuit.qbo.service.productservice.v1.rest.QBOCompany;

/**
 * @author Vijayan Srinivasan
 * @since Nov 2, 2014 3:39:09 PM
 * 
 */
public class QBOCompanyCreatorTest {

	/*
	 * { "userName": "vsrinivasan", "password": "intuit$123", "userEmail":
	 * "vijayan_srinivasan@intuit.com", "billingCode": "INP-FRE", "region":
	 * "IN", "partner": "None", "companyName": "FruitWala", "language": "en",
	 * "industryType": "RETAIL_SHOPS_MAIL_ORDER_AND_ONLINE" }
	 */
	
	public static void main(String[] args) {
		QBOCompany qboCompany = new QBOCompany();
		qboCompany.setUserName("vsrinivasan" + new Date().getTime());
		qboCompany.setPassword("intuit$123");
		qboCompany.setUserEmail("VenkataGopinath_Bodagala@intuit.com");
		qboCompany.setBillingCode("INP-FRE");
		qboCompany.setRegion("IN");
		qboCompany.setPartner("None");
		qboCompany.setCompanyName("FruitWala");
		qboCompany.setLanguage("en");
		qboCompany.setIndustryType("RETAIL_SHOPS_MAIL_ORDER_AND_ONLINE");
		new QBOCompanyCreator().create(qboCompany);
	}

}
