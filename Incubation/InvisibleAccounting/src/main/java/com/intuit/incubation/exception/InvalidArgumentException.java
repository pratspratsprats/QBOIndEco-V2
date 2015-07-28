package com.intuit.incubation.exception;

import javax.xml.ws.WebFault;
/**
 * Subclass of {@link ServiceException}  to be thrown when argument passed to service apis are invalid
 * @author Amaresh Mourya
 * @date May 17, 2014
 **/


@WebFault(name = "InvalidArgumentEx")
public class InvalidArgumentException extends ServiceException {
	private static final long serialVersionUID = -4769509760501523459L;

	public InvalidArgumentException() {
	}

	public InvalidArgumentException(String str) {
		super(str);
	}

	public InvalidArgumentException(Throwable cause) {
		super(cause);
	}

	public InvalidArgumentException(String message, Throwable cause) {
		super(message, cause);
	}
}
