package com.intuit.qbo.ecommerce.domain;

import com.google.gson.annotations.Expose;

/**
 * @author bgopinath
 *
 */
public class PrimaryPhone {

    @Expose
    private String CountryCode;
    @Expose
    private String FreeFormNumber;

    /**
     * 
     * @return The CountryCode
     */
    public String getCountryCode() {
	return CountryCode;
    }

    /**
     * 
     * @param CountryCode
     *            The CountryCode
     */
    public void setCountryCode(String CountryCode) {
	this.CountryCode = CountryCode;
    }

    /**
     * 
     * @return The FreeFormNumber
     */
    public String getFreeFormNumber() {
	return FreeFormNumber;
    }

    /**
     * 
     * @param FreeFormNumber
     *            The FreeFormNumber
     */
    public void setFreeFormNumber(String FreeFormNumber) {
	this.FreeFormNumber = FreeFormNumber;
    }

}