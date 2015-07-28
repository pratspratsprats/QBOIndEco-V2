package com.intuit.incubation.exception;

import javax.xml.ws.WebFault;

/**
 * BaseClass for all exceptions thrown from ASCServices, it has several overloaded methods 
 * which takes <code>List</code> of <code>ErrorCode</code> to create <code>Exception</code>
 * @author Amaresh Mourya
 * @date May 17, 2014
 **/

@WebFault(name = "ServiceEx")
public class ServiceException extends Exception{
	private static final long serialVersionUID = 5920912585655496023L;

	public ServiceException() {
	}

	public ServiceException(String str) {
		super(str);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}

