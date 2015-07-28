package com.intuit.qbo.ecommerce.domain;

import com.google.gson.annotations.Expose;

/**
 * @author bgopinath
 *
 */
public class BillAddr {

    @Expose
    private String Line1;
    @Expose
    private String Line2;
    @Expose
    private String Line3;
    @Expose
    private String City;
    @Expose
    private String Country;
    @Expose
    private String PostalCode;

    /**
     * 
     * @return The Line1
     */
    public String getLine1() {
	return Line1;
    }

    /**
     * 
     * @param Line1
     *            The Line1
     */
    public void setLine1(String Line1) {
	this.Line1 = Line1;
    }

    /**
     * 
     * @return The Line2
     */
    public String getLine2() {
	return Line2;
    }

    /**
     * 
     * @param Line2
     *            The Line2
     */
    public void setLine2(String Line2) {
	this.Line2 = Line2;
    }

    /**
     * 
     * @return The Line3
     */
    public String getLine3() {
	return Line3;
    }

    /**
     * 
     * @param Line3
     *            The Line3
     */
    public void setLine3(String Line3) {
	this.Line3 = Line3;
    }

    /**
     * 
     * @return The City
     */
    public String getCity() {
	return City;
    }

    /**
     * 
     * @param City
     *            The City
     */
    public void setCity(String City) {
	this.City = City;
    }

    /**
     * 
     * @return The Country
     */
    public String getCountry() {
	return Country;
    }

    /**
     * 
     * @param Country
     *            The Country
     */
    public void setCountry(String Country) {
	this.Country = Country;
    }

    /**
     * 
     * @return The PostalCode
     */
    public String getPostalCode() {
	return PostalCode;
    }

    /**
     * 
     * @param PostalCode
     *            The PostalCode
     */
    public void setPostalCode(String PostalCode) {
	this.PostalCode = PostalCode;
    }

}