/**
 * 
 */
package com.intuit.qbo.ecommerce.krc;

/**
 * @author Vijayan Srinivasan
 * @since Oct 15, 2014 9:13:31 PM
 * 
 */
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(Throwable cause) {
		super(cause);
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause);
	}

}
