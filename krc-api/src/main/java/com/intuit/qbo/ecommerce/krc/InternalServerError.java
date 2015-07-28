package com.intuit.qbo.ecommerce.krc;

public class InternalServerError extends RuntimeException {

	private static final long serialVersionUID = 4052815816001248190L;

	public InternalServerError(String message) {
		super(message);
	}

	public InternalServerError(Throwable cause) {
		super(cause);
	}

	public InternalServerError(String message, Throwable cause) {
		super(message, cause);
	}

	public InternalServerError(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause);
	}

}
