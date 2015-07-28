package com.intuit.qbo.ecommerce.krc.synch;


import java.util.Iterator;
import java.util.StringTokenizer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.IEntity;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.Invoice;
import com.intuit.ipp.data.Item;
import com.intuit.ipp.data.ReferenceType;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Config;

public class TestIpp {

	public static void main(String[] args) {
		
		// For sandbox
		/*String consumerKey="qyprdFmx5IFJw6TptUq1bTXqfkXGsU";
		String consumerSecret="TJ9fqfo6of6Mup4QrLUJFFskvAe08Y7ggRrdeM0H";
		String acessTokenSecret="Zt1Z3mSo95sVW6hZIsskCdneGhIq7dAw2ves1n0Q";
		String acessToken="lvprdNPxPhHO8qARNvQg1phckbiJH6jJTyCM8oV2vZOFX0AH";
		String appToken="3c5f5e06ba82eb45a3bb830bf7ab1810dc86";
		String companyID="1379665470";*/
		
		 // For production companies
	 /*    String appToken="d458895ab8ccbb40cbb9c3ebf859a0c9f5fe";
	     final String consumerKey="lvprdufkXe3TbcnncNMlQnYYiqnv7b";
	     final String consumerSecret="0zZZmb5FHhqAc7Zbdj4dVIR0b0Iisvt2adOz5qcW";
	    public static final String acessTokenSecret="9PdV3xjqBY4meHtGysEuCFCNRCjqp6HAtigu6lpp";
	    public static final String acessToken="qyprduEtNdZ5FYVksJV2c4nIQL4FrZzwqIQLsKyNkf0DN6Bc";
	     String acessToken="lvprdpdqG04gmpnDZqPWJuvpbWx5g17xIagK80iiDaIMIohm";
	     final String acessTokenSecret="UsaOCpZVI3sPxmXSu9kIxLWnC1zpc5i8cWCXC0n5";
	     final String companyID="1391161205";

		
		IAPlatformClient client=new IAPlatformClient();
		 * final Map<String, String> requestTokenAndSecret =
	            client.getRequestTokenAndSecret(consumerKey,consumerSecret);
		OAuthAuthorizer oauth = new OAuthAuthorizer(consumerKey, consumerSecret, acessToken, acessTokenSecret);
		Context context;
		try {
			String url = "https://quickbooks.api.intuit.com/v3/company"; 
			Config.setProperty(Config.BASE_URL_QBO,url );
			context = new Context(oauth, appToken, ServiceType.QBO, companyID);
		DataService service = new DataService(context);
		
		Customer customer=new Customer();
		customer.setDisplayName("Mr. Dhruba Jyoti Saha");
		
		QueryResult executeQuery = service.executeQuery("Select * from Customer where displayName='Mr Suresh Kumar'");
        Iterator<? extends IEntity> iterator = executeQuery.getEntities().iterator();
		
		Item item = new Item();
		item.setName("HOM-WALL-PAT_008-Patachitra \u2013 Wall Hanging – Tribal Small – Framed with Wood and Glass");
		com.intuit.ipp.data.ReferenceType incomeAccountRef = new com.intuit.ipp.data.ReferenceType();

		incomeAccountRef.setName("Sales of Product Income");
		incomeAccountRef.setValue("1");
		item.setIncomeAccountRef(incomeAccountRef);*/
		
		
		
		
		/*String sku="HOM-WALL-PAT_008";
		
		String queryForItem = "Select * from Item where name = '" + sku + "'";
        
        
       // QueryResult executeQuery = service.executeQuery("Select * from Customer where displayName='Mr Suresh Kumar'");
        QueryResult executeQuery = service.executeQuery(queryForItem);
        Iterator<? extends IEntity> iterator = executeQuery.getEntities().iterator();
        Customer sr = null;
        Invoice sr1 = null;
        String custId="";
        Item item=null;
        String itemId="";
        JsonObject jsonObject=null;
		 JsonElement itemElement=null;
		 
        while(iterator.hasNext()){
        	item = (Item) iterator.next();
            System.out.println( "--------------------Item"+item.getId());
            itemId=item.getId();
           System.out.println("--------------------itemId"+itemId);
          

           
        }*/
       /* while(iterator.hasNext()){
            sr = (Customer) iterator.next();
            System.out.println( "--------------------Customer"+sr.getId());
            custId=sr.getId();
            String query="Select * from Invoice where CustomerRef='"+custId+"'";
            QueryResult executeQuery1 = service.executeQuery(query);
            Iterator<? extends IEntity> iterator1 = executeQuery1.getEntities().iterator();
            while(iterator1.hasNext()){
                sr1 = (Invoice) iterator1.next();
                System.out.println( "--------------------Invoice"+sr1.getDomain());
                
            }
           
        }*/
       
		
		//service.add(item);

		/*} catch (FMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		String a="\\//\u2013()";
		String b = new String (a);
		System.out.println("++++++++++"+b);
		
		 String str = b.replaceAll("[ \\/$]", "");
		
		
		System.out.println("++++++++++"+str);
	}
	
	

}
