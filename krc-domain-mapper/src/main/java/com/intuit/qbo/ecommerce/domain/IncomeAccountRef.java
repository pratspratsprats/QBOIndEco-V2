package com.intuit.qbo.ecommerce.domain;

import com.google.gson.annotations.Expose;

/**
 * @author bgopinath
 *
 */
public class IncomeAccountRef {

    @Expose
    private String value;
    @Expose
    private String name;

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

    /**
     * 
     * @return The name
     */
    public String getName() {
	return name;
    }

    /**
     * 
     * @param name
     *            The name
     */
    public void setName(String name) {
	this.name = name;
    }

}