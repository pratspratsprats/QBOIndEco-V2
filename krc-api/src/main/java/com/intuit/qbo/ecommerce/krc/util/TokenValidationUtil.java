package com.intuit.qbo.ecommerce.krc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.intuit.qbo.ecommerce.krc.api.dto.CustomerDO;

public class TokenValidationUtil {
	
	public List<CustomerDO> validateTokens(List<CustomerDO> customerList){

		String accessToken;
		String accesTokenSecret;
		CustomerDO custDO=null;
		Date accessTokenValid=null;
		 int count=0;
		 int uncount=0;
		 int imcount=0;
		 String tokenStatusMessage="Connected";
		 List<CustomerDO> customerDOList=new ArrayList<CustomerDO>();
		for(CustomerDO customerDO : customerList){
			
			System.out.println("QB Company id in Beginning :::::::::"+customerDO.getQbCompanyId());
    		System.out.println("QB User name in Beginning :::::::::"+customerDO.getQbUserName());
    		System.out.println("QB Company id in Beginning :::::::::"+customerDO.getQbPassword());
			
			
			// For Token Validation 
			try {
			accessTokenValid=customerDO.getAccessTokenExpirationDate();
			accessToken=customerDO.getAccessToken();
			accesTokenSecret=customerDO.getAccessTokenSecret();
			
			   if(accessToken !=null && accesTokenSecret!=null){
	    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        	
					Date date1 = sdf.parse(accessTokenValid.toString());
					System.out.println("accessTokenValid::::::::::::::"+date1);
					System.out.println("currentDate::::::::::::::"+new Date().toString());
					count++;
					
					
					
					SimpleDateFormat sdf1 = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                            Locale.ENGLISH);
					
					Date date2 = sdf1.parse(new Date().toString());
					 
		        	System.out.println(sdf.format(date1));
		        	System.out.println(sdf.format(date2));
		        	
		 
		        	if(date1.compareTo(date2)>0){
		        		
		        		System.out.println("Happy Flow ::::::"+count);
		        	
		        		customerDO.setTokenValidationMessage(tokenStatusMessage);
		        		
		        		
		        	}else {
		        		
		        		imcount++;
		        		System.out.println("Partial Happy Flow ::::::"+imcount);
		        		
		        		customerDO.setTokenValidationMessage("NotConnected");
		
		        	}
		 	
			   }else{
				  
				   uncount++;
				   System.out.println("In the Else "+uncount);
					
				   customerDO.setTokenValidationMessage("NotConnected");
		   
			   }
			   
						// Adding CustomerDO object to customerDOList
			   System.out.println("Adding To List");
			       		customerDOList.add(customerDO);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
		}
		return customerDOList;
	}

}
