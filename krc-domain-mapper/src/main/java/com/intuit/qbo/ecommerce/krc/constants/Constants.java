package com.intuit.qbo.ecommerce.krc.constants;

import java.text.SimpleDateFormat;

public abstract class Constants {

    public static final String CASH_ON_DELIVERY = "Cash On Delivery";
   
//    private static final String KR_STORE_DOMAIN = "intuit.kartrocket.co";
//    private static final String KR_WEB_API_KEY = "1ff1de774005f8da13f42943881c655f";
//
//    public static final String KR_PRODUCT_FETCH_URL = "http://"
//	    + KR_STORE_DOMAIN + "/index.php?route=feed/web_api/products&key="
//	    + KR_WEB_API_KEY;
//    public static final String KR_ORDER_FETCH_URL = "http://" + KR_STORE_DOMAIN
//	    + "/index.php?route=feed/web_api/orders&key=" + KR_WEB_API_KEY + "&date_from=";;

    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(
	    "yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMATTER_QBO = new SimpleDateFormat(
    	    "yyyy-MM-dd");
    public static final String APP_TOKEN = "fc42dce2bdf9cb4614baa3ebfeef1811bfc8";
    
    
    // For sandbox companies
    /*public static final String appToken="3c5f5e06ba82eb45a3bb830bf7ab1810dc86";
    public static final String consumerKey="qyprdFmx5IFJw6TptUq1bTXqfkXGsU";
    public static final String consumerSecret="TJ9fqfo6of6Mup4QrLUJFFskvAe08Y7ggRrdeM0H";
    public static final String acessTokenSecret="9PdV3xjqBY4meHtGysEuCFCNRCjqp6HAtigu6lpp";
    public static final String acessToken="qyprduEtNdZ5FYVksJV2c4nIQL4FrZzwqIQLsKyNkf0DN6Bc";
    public static final String acessToken="lvprdNPxPhHO8qARNvQg1phckbiJH6jJTyCM8oV2vZOFX0AH";
    public static final String acessTokenSecret="Zt1Z3mSo95sVW6hZIsskCdneGhIq7dAw2ves1n0Q";
    public static final String companyID="1379665470";*/
    
    // For production companies
    public static final String appToken="d458895ab8ccbb40cbb9c3ebf859a0c9f5fe";
    public static final String consumerKey="lvprdufkXe3TbcnncNMlQnYYiqnv7b";
    public static final String consumerSecret="0zZZmb5FHhqAc7Zbdj4dVIR0b0Iisvt2adOz5qcW";
    /*public static final String acessTokenSecret="9PdV3xjqBY4meHtGysEuCFCNRCjqp6HAtigu6lpp";
    public static final String acessToken="qyprduEtNdZ5FYVksJV2c4nIQL4FrZzwqIQLsKyNkf0DN6Bc";*/
    public static final String acessToken="lvprdpdqG04gmpnDZqPWJuvpbWx5g17xIagK80iiDaIMIohm";
    public static final String acessTokenSecret="UsaOCpZVI3sPxmXSu9kIxLWnC1zpc5i8cWCXC0n5";
    public static final String companyID="1391161205";
    
   
    
   


}
