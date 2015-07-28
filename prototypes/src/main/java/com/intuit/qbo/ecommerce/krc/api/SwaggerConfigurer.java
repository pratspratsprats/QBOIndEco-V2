package com.intuit.qbo.ecommerce.krc.api;

import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.config.SwaggerConfig;
import com.wordnik.swagger.jaxrs.config.ReflectiveJaxrsScanner;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.reader.ClassReaders;

/**
 * 
 * @author Vijayan Srinivasan
 * @since Dec 24, 2013 6:41:10 PM
 *
 */

public class SwaggerConfigurer {

	private String resourcePackage;
	private String apiVersion;
	private String basePath;

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public void init() {
		ReflectiveJaxrsScanner scanner = new ReflectiveJaxrsScanner();
		scanner.setResourcePackage(resourcePackage);
		ScannerFactory.setScanner(scanner);
		ClassReaders.setReader(new DefaultJaxrsApiReader());
		SwaggerConfig config = ConfigFactory.config();
		config.setApiVersion(apiVersion);
		config.setBasePath(basePath);
	}

	public String getResourcePackage() {
		return resourcePackage;
	}

	public void setResourcePackage(String resourcePackage) {
		this.resourcePackage = resourcePackage;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	
}