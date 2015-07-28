package com.intuit.incubation.model;

public enum ErrorType {

	CLIENT, SERVER;

	public String value() {
		return name();
	}

	public static ErrorType fromValue(String v) {
		return valueOf(v);
	}

}
