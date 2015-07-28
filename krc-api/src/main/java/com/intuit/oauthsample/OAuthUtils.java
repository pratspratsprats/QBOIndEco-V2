package com.intuit.oauthsample;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class OAuthUtils {
	
	Properties prop = new Properties();
	public static final String PROP_FILE = "/app1.properties";
	
	public Properties readProperties()
	{
		try {
			//InputStream input = new FileInputStream("/WEB-INF/app.properties");
			//getServletContext().getResourceAsStream("/WEB-INF/app.properties")
			
			Thread currentThread = Thread.currentThread();
			ClassLoader contextClassLoader = currentThread.getContextClassLoader();
			InputStream propertiesStream = contextClassLoader.getResourceAsStream(PROP_FILE);
			if (propertiesStream != null) {
				prop.load(propertiesStream);
				 System.out.println("Properties Loaded successfully");
				} else {
				  System.out.println("Properties File Not Found!");
				}
			return prop;
			} catch (IOException ex) {
				ex.printStackTrace(); 
				return prop; 
			}
	}
	
	public static final String REQUEST_TOKEN_URL = "https://oauth.intuit.com/oauth/v1/get_request_token";
	public static final String ACCESS_TOKEN_URL = "https://oauth.intuit.com/oauth/v1/get_access_token";
	public static final String AUTHORIZE_URL = "https://appcenter.intuit.com/connect/begin";
	
	

}
