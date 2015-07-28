package com.intuit.oauthsample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.http.HttpParameters;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.intuit.qbo.ecommerce.krc.api.dao.CustomerDAO;
import com.intuit.qbo.ecommerce.krc.api.dto.CustomerDO;
import com.intuit.qbo.ecommerce.krc.util.TokenValidationUtil;
import com.intuit.qbo.quartz.OrderToBooksJob;


public class AccessTokenServlet extends HttpServlet {


	Logger logger = LoggerFactory.getLogger("AccessTokenServlet.class");
	
	
	private static ApplicationContext applicationContext;
	
	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}
	
	
	public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public AccessTokenServlet() {
		super();
	}
	
	public void init(){
		
		ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());

		CustomerDAO customerDAO = context.getBean(CustomerDAO.class);
		List<CustomerDO> listOfCustomers = customerDAO.selectCustomersToSync();

		if (listOfCustomers != null) {
			
			try {
				Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			
	    	    scheduler.start();

			for (CustomerDO customerDO : listOfCustomers) {
				
				if(customerDO.getSyncFrequency()!=null){

				JobKey jobKeyA = new JobKey("jobA"+customerDO.getId(), "group1");
				   JobDataMap newJobDataMap=new JobDataMap();
				   newJobDataMap.put("customerDO",customerDO );
				   newJobDataMap.put("customerDAO", customerDAO);
			    	JobDetail jobA = JobBuilder.newJob(OrderToBooksJob.class).setJobData(newJobDataMap).withIdentity(jobKeyA).build();
			    
			    	//SimpleTrigger trigger1=new SimpleTrigger();
			    	Trigger trigger1 = TriggerBuilder
					.newTrigger()
					.withIdentity("dummyTriggerName1"+customerDO.getId(), "group1").withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(customerDO.getSyncFrequency().intValue())).build();
					 
			    	
			    	System.out.println("Schedular Start::::::");
			    	
			    	//scheduling Batch Job
			    	scheduler.scheduleJob(jobA, trigger1);
				}
			
			}
			
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			
		}
		
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	// The Oauth Callback URL is configured in the properties file and redirects to here
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("AccessTokenServlet");
		
		
		String action=request.getParameter("action");
		String  comapnyId=request.getParameter("companyId");
		if(action!=null&&action.equals("cofigureBatch")){
			RequestDispatcher dispatch = request.getRequestDispatcher("WEB-INF/batchSettings.jsp?companyId="+comapnyId);
			dispatch.forward(request, response);
			
		}
		else if(action!=null&&action.equals("saveBatchSettings")){
			ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());

			CustomerDAO customerDAO = context.getBean(CustomerDAO.class);
			CustomerDO customerDO=customerDAO.selectCustomerByCompanyId((String)request.getParameter("companyId"));
			
			customerDO.setSyncFrequency(Long.valueOf(request.getParameter("repeatinterval")));
			customerDO.setSyncStatrDelay(Long.valueOf(request.getParameter("startDelay")));
			CustomerDO customer=customerDAO.update(customerDO);
			
			if(request.getSession(true).getAttribute("Message")!=null){
				request.getSession(true).removeAttribute("Message");
			}
			RequestDispatcher dispatch = request.getRequestDispatcher("index.jsp");
			dispatch.forward(request, response);
		}
		else if(action!=null&&action.equals("changeSyncStatus")){
			ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());

			CustomerDAO customerDAO = context.getBean(CustomerDAO.class);
			CustomerDO customerDO=customerDAO.selectCustomerByCompanyId((String)request.getParameter("companyId"));
				Boolean syncStatus=Boolean.valueOf(request.getParameter("syncStatus"));
				
				// If sync status is false
				if(syncStatus == false){
					customerDO.setSyncStatus(syncStatus);
					customerDO.setSyncFrequency(null);
					customerDO.setSyncStatrDelay(null);
					
				}else{
					customerDO.setSyncStatus(syncStatus);
				}
			CustomerDO customer=customerDAO.update(customerDO);
			TokenValidationUtil validationUtil=new TokenValidationUtil();
			List customerDOList=validationUtil.validateTokens(customerDAO.selectAll());
			request.getSession(true).setAttribute("customerList", customerDOList);
			
			if(syncStatus && customer.getSyncFrequency()==0){
				
				request.getSession(true).setAttribute("Message", "Please click on Configure Batch button to set the Batch Frequency for the Customer with Shop Domain "+customerDO.getShopDomain());
			}
			else if(request.getSession(true).getAttribute("Message")!=null){
				request.getSession(true).removeAttribute("Message");
			}
			RequestDispatcher dispatch = request.getRequestDispatcher("index.jsp");
			
			
			
			dispatch.forward(request, response);
		}
		else{


		try{
			
		HttpSession session = request.getSession();
		
		// The realm Id is returned in the callback and read into the session
		String realmID = request.getParameter("realmId");
		session.setAttribute("realmId", realmID);

		OAuthConsumer oauthconsumer = (OAuthConsumer) session.getAttribute("oauthConsumer");


		HttpParameters additionalParams = new HttpParameters();
		additionalParams.put("oauth_callback", OAuth.OUT_OF_BAND);
		additionalParams.put("oauth_verifier", request.getParameter("oauth_verifier"));
		oauthconsumer.setAdditionalParameters(additionalParams);
		
		
		// Sign the call to retrieve the access token request
		String signedURL = oauthconsumer.sign(OAuthUtils.ACCESS_TOKEN_URL);
		URL url = new URL(signedURL);

		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setRequestMethod("GET");

		String accesstokenresponse = "";
		String accessToken, accessTokenSecret = "";
		if (urlConnection != null) {
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
				System.out.println(sb.toString());
			}
			rd.close();
			accesstokenresponse = sb.toString();
		}
		if (accesstokenresponse != null) {
			String[] responseElements = accesstokenresponse.split("&");
			if (responseElements.length > 1) {
				accessToken = responseElements[1].split("=")[1];
				accessTokenSecret = responseElements[0].split("=")[1];
				logger.info("OAuth accessToken: " + accessToken);
				logger.info("OAuth accessTokenSecret: " + accessTokenSecret);
				System.out.println("Company Id ::::::"+(String)session.getAttribute("realmId"));
				System.out.println("OAuth accessToken: ::::::"+accessToken);
				System.out.println("OAuth accessToken: Secret ::::::"+accessTokenSecret);
				
				ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());

				CustomerDAO customerDAO = context.getBean(CustomerDAO.class);
				CustomerDO customerDO=customerDAO.selectCustomerByCompanyId((String)session.getAttribute("realmId"));
				customerDO.setAccessTokenSecret(accessTokenSecret);
				customerDO.setAccessToken(accessToken);
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.add(Calendar.DATE, +180);
				Date accessTokenExpirationDate = cal.getTime();

				
				
				customerDO.setAccessTokenExpirationDate(accessTokenExpirationDate);
				
				// Update Access Token and Access Token Secret in the customer table
				customerDAO.update(customerDO);
				
				/*Map<String, String> accesstokenmap = new HashMap<String, String>();
				accesstokenmap.put("accessToken", accessToken);
				accesstokenmap.put("accessTokenSecret", accessTokenSecret);
				session.setAttribute("accessToken", accesstokenmap.get("accessToken"));
				session.setAttribute("accessTokenSecret", accesstokenmap.get("accessTokenSecret"));*/
				session.invalidate();
				
				RequestDispatcher dispatch = request.getRequestDispatcher("WEB-INF/connected.jsp");
			
				dispatch.forward(request, response);

			}
		}

	} catch (Exception e) {
		e.printStackTrace();
		logger.error(e.getLocalizedMessage());
	}

}
		}
}
