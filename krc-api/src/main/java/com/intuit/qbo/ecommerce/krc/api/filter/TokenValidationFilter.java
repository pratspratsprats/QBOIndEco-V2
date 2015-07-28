package com.intuit.qbo.ecommerce.krc.api.filter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.intuit.qbo.ecommerce.krc.api.dao.CustomerDAO;
import com.intuit.qbo.ecommerce.krc.api.dto.CustomerDO;
import com.intuit.qbo.ecommerce.krc.util.TokenValidationUtil;

public class TokenValidationFilter implements Filter{
	
	
	
	
	FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		HttpSession session = ((HttpServletRequest)servletRequest).getSession();
		
		ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());
		CustomerDAO customerDAO = context.getBean(CustomerDAO.class);
		System.out.println("customerDAO==========>"+customerDAO);
		List<CustomerDO> customerList=customerDAO.selectAll();
		TokenValidationUtil validationUtil=new TokenValidationUtil();
		List<CustomerDO> customerDOList=validationUtil.validateTokens(customerList);
			
		session.setAttribute("customerList", customerDOList);
		System.out.println("Customer List Size :::::::::"+customerDOList.size());
		filterChain.doFilter(servletRequest, servletResponse);
		

	}

}
