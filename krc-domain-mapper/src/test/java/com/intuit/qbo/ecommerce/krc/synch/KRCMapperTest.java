package com.intuit.qbo.ecommerce.krc.synch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.IEntity;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.Invoice;
import com.intuit.ipp.data.Item;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Config;
import com.intuit.qbo.ecommerce.krc.constants.Constants;
import com.intuit.qbo.ecommerce.krc.entity.KROrder;
import com.intuit.qbo.ecommerce.krc.entity.KRProduct;
import com.intuit.qbo.ecommerce.krc.entity.SyncDates;

public class KRCMapperTest {
	
	private DataService qboDataService = null;
	private KRCMapper krcMapper = null;

	// For production companies
    String appToken="d458895ab8ccbb40cbb9c3ebf859a0c9f5fe";
    final String consumerKey="lvprdufkXe3TbcnncNMlQnYYiqnv7b";
    final String consumerSecret="0zZZmb5FHhqAc7Zbdj4dVIR0b0Iisvt2adOz5qcW";
   /*public static final String acessTokenSecret="9PdV3xjqBY4meHtGysEuCFCNRCjqp6HAtigu6lpp";
   public static final String acessToken="qyprduEtNdZ5FYVksJV2c4nIQL4FrZzwqIQLsKyNkf0DN6Bc";*/
    String acessToken="lvprdpdqG04gmpnDZqPWJuvpbWx5g17xIagK80iiDaIMIohm";
    final String acessTokenSecret="UsaOCpZVI3sPxmXSu9kIxLWnC1zpc5i8cWCXC0n5";
    final String companyID="1391161205";
	
    @Before
	public void setup(){
		this.qboDataService = getService( );
		this.krcMapper = new KRCMapper();
		
	}
	
	
	// For getting the Data service from the context
		private DataService getService( ){
			
			 

			
			
			OAuthAuthorizer oauth = new OAuthAuthorizer(Constants.consumerKey, Constants.consumerSecret, Constants.acessToken, Constants.acessTokenSecret);
			Context context= null;
			DataService service=null;
			try {
				
				String url = "https://quickbooks.api.intuit.com/v3/company"; 
				Config.setProperty(Config.BASE_URL_QBO,url );
				context = new Context(oauth, Constants.appToken, ServiceType.QBO, companyID);
			 service = new DataService(context);
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return service;
			
		}
	

	
	// Pulling Product from KR and adding Item to QB
		@Test
		public void testaddKRProductToQB(){
			
			String productSku= "HOM-WAL-DHRUBA_015";
			String productName="  This \u2013 is Retail product ";
			String shopDomain="www.balaji.com";
			String finalProductName=productSku+"-"+productName;
			List<KRProduct> productList=new ArrayList<KRProduct>();
			KRProduct product=new KRProduct();
			product.setSku(productSku);
			product.setName(new String(productName));
			product.setDescription(productName);
			product.setPrice("1000");
			product.setQuantity(1);
			String accessToken="";
			String accessTokenSecret="";
			
			productList.add(product);
			krcMapper.addProducts(appToken, companyID, productList, shopDomain,accessToken, accessTokenSecret );
			Item item=krcMapper.getItemByNameFromQB(appToken, companyID, product.getName(), product.getSku(), shopDomain, accessToken, accessTokenSecret);

		
					assertNotNull(item.getId());
					assertEquals(finalProductName, item.getName());
					
			
			
	           
			
		}
		
		
		// Pulling order from KR and adding Customer  to QB
			/*	@Test
				public void testaddCustomerToQB(){
					
					
					String shopDomain="http://ethnicshack.kartrocket.co";
					String webApiKey="89da0bc73fab5be44691585cb81eed8c";
					
					SyncDates syncDates = new SyncDates();
					Date lastOrderSyncDate = null;
				
					List<KROrder> orders = krcMapper.getOrdersFromKR(shopDomain, webApiKey, lastOrderSyncDate, syncDates);
					if (orders != null) {
						
						KROrder krOrderentity=orders.get(0);

						krcMapper.addCustomerToQB(appToken, companyID, orders.get(0),shopDomain);
						String displayName = (krOrderentity.getCustomerFirstName().trim() + " " + krOrderentity
								.getCustomerLastName().trim()).trim();
						String query="select * from Customer where displayName='"+displayName+"'";
						try {
							QueryResult executeQuery =this.qboDataService.executeQuery(query);
						    Iterator<? extends IEntity> iterator = executeQuery.getEntities().iterator();
						    Customer customer=null;
						    while(iterator.hasNext()){
					            customer = (Customer) iterator.next();
					            
					            assertNotNull(customer);
					            assertNotNull(customer.getId());
					            
					            }
					           
					        
						} catch (FMSException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
								
					}
			}
				
				// Pulling order from KR and adding Invoice  to QB
				@Test
				public void testaddInvoiceToQB(){
					
					
					String shopDomain="http://ethnicshack.kartrocket.co";
					String webApiKey="89da0bc73fab5be44691585cb81eed8c";
					
					SyncDates syncDates = new SyncDates();
					Date lastOrderSyncDate = null;
				
					List<KROrder> orders = krcMapper.getOrdersFromKR(shopDomain, webApiKey, lastOrderSyncDate, syncDates);
					if (orders != null) {
						
						KROrder krOrderentity=orders.get(0);

						krcMapper.addInvoiceToQB(appToken, companyID, orders.get(0),shopDomain);
						Invoice invoice=krcMapper.getInvoiceByOrderIdAndCompanyId(webApiKey, String.valueOf(krOrderentity.getId()),companyID);
						
					
					
					            assertNotNull(invoice);
					            assertNotNull(invoice.getId());
					            
					      
								
					}
			}*/
	
}
