package com.intuit.qbo.ecommerce.domain;

import com.google.gson.annotations.Expose;

/**
 * @author bgopinath
 *
 */
public class PrimaryEmailAddr {

    @Expose
    private String Address;

    /**
     * 
     * @return The Address
     */
    public String getAddress() {
	return Address;
    }

    /**
     * 
     * @param Address
     *            The Address
     */
    public void setAddress(String Address) {
	this.Address = Address;
    }

}