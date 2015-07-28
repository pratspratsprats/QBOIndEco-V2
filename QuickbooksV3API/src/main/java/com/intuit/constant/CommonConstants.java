package com.intuit.constant;

public abstract class CommonConstants {

	public static final String CASH_ON_DELIVERY = "Cash On Delivery";
	private static final String KR_STORE_DOMAIN = "intuit.kartrocket.co";
	private static final String KR_WEB_API_KEY = "1ff1de774005f8da13f42943881c655f";
	
	public static final String KR_PRODUCT_FETCH_URL = "http://" + KR_STORE_DOMAIN
			+ "/index.php?route=feed/web_api/products&key=" + KR_WEB_API_KEY;
	public static final String KR_ORDER_FETCH_URL = "http://" + KR_STORE_DOMAIN
			+ "/index.php?route=feed/web_api/orders&key=" + KR_WEB_API_KEY;

}
