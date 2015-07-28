package com.intuit.qbo.company.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Amaresh Mourya
 * @date Oct 20, 2014
 **/

public class PropertyProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyProvider.class);

	private static Properties prop = new Properties();
	static
	{
		initializeProperties();
	}
	
	public static String getSignUpUrlEndPoint(boolean forProduction)
	{
		if(forProduction)
		return prop.getProperty("prod.usersignup_endpoint");
		else
		return prop.getProperty("preprod.usersignup_endpoint");
	}
	public static String getEmailSender()
	{
		return prop.getProperty("sender_emailid");
	}

	public static String getCreationSuccessSubject() {
		return prop.getProperty("email_subject_signup");
	}
	
	private static void initializeProperties()
	{
		InputStream input = null;
		try {
	 
			String location = System.getProperty("properties.dir");
			input = new FileInputStream(location+"config.properties");
			// load a properties file
			prop.load(input);
	 
		} catch (IOException ex) {
			LOGGER.error(ex.getMessage(), ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
	}
	public static String getAdminEmailId() {
		
		return  prop.getProperty("admin_emailid");
	}
	
	
 
}
