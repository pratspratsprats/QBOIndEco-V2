package com.intuit.qbo.company.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Sends notifications after new user signup with user credentials
 * @author Amaresh Mourya
 * @date Oct 19, 2014
 **/

public class NotificationSender {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(NotificationSender.class);

	private static JavaMailSenderImpl javaMailSender;
	private static VelocityEngine velocityEngine;
	static {
		try {
			initializeVelocityEngine();
			initializeMailSender();
		} catch (Exception e) {
			LOGGER.error("Exception while initializing Notification Sender", e);
		}
	}

	private static void initializeMailSender() {
		javaMailSender = new JavaMailSenderImpl();
		Properties mailProperties = new Properties();
 		mailProperties.put("mail.smtp.host", "mailout.data.ie.intuit.net");
 		mailProperties.put("mail.smtp.timeout", 18000);
		javaMailSender.setJavaMailProperties(mailProperties);
		LOGGER.info("Mail sender initialized");
	}

	private static void initializeVelocityEngine() throws VelocityException,
			IOException {

		Properties velocityProperties = new Properties();
		velocityProperties.put("resource.loader", "class");
		velocityProperties
				.put("class.resource.loader.class",
						"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		VelocityEngineFactoryBean factory = new VelocityEngineFactoryBean();
		factory.setVelocityProperties(velocityProperties);
		factory.afterPropertiesSet();
		velocityEngine = factory.getObject();
		LOGGER.info("Velocity engine initialized");
		
	}

	@SuppressWarnings("rawtypes")
	public void sendNewCompanyCreationMail(String companyName, String userName, String emailId, String password) {

		try {
			Map model = constructMap(emailId, userName, companyName, password);
			String body = VelocityEngineUtils.mergeTemplateIntoString(
					velocityEngine,
					"notification/newCompanyTemplate.vm", "UTF-8",
					model);
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(PropertyProvider.getEmailSender());
			helper.setSubject(PropertyProvider.getCreationSuccessSubject());
			String[] emailArray = { PropertyProvider.getAdminEmailId(), emailId};
			helper.setTo(emailArray);
     		helper.setText(body, true);
     		ClassPathResource qbLogoRes = new ClassPathResource("logo/qb_intuitlogo.png");
     		helper.addInline("qblogo", qbLogoRes);
			javaMailSender.send(mimeMessage);
			LOGGER.info("Company creation mail sent successfully to { Admin Email Id: "+PropertyProvider.getAdminEmailId()+", User Email Id: "+emailId+"}");

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map constructMap(String emailId, String userName, String companyName, String password) {
		
		Map modelMap =  new HashMap();
		Map dataMap = new HashMap();
		dataMap.put("username", userName);
		dataMap.put("emailid", emailId);
		dataMap.put("companyname", companyName);
		dataMap.put("password", password);
		modelMap.put("item", dataMap);
		return modelMap; 
		
	}

}
