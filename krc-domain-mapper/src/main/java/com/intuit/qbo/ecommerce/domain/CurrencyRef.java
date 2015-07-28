package com.intuit.qbo.ecommerce.domain;

import com.google.gson.annotations.Expose;

/**
 * @author bgopinath
 *
 */
public class CurrencyRef {

    @Expose
    private String value;

    /**
     * 
     * @return The value
     */
    public String getValue() {
	return value;
    }

    /**
     * 
     * @param value
     *            The value
     */
    public void setValue(String value) {
	this.value = value;
    }

}