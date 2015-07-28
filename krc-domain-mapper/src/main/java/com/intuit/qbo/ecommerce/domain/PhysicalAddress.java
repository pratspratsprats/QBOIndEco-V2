package com.intuit.qbo.ecommerce.domain;

import java.io.Serializable;

/**
 * @author bgopinath
 *
 */
public class PhysicalAddress implements Serializable {

    private static final long serialVersionUID = 1L;
    protected String id;
    protected String Line1;
    protected String Line2;
    protected String Line3;
    protected String Line4;
    protected String Line5;
    protected String City;
    protected String Country;
    protected String CountryCode;
    protected String CountrySubDivisionCode;
    protected String PostalCode;
    protected String PostalCodeSuffix;
    protected String Lat;
    protected String _long;
    protected String tag;
    protected String note;

    public PhysicalAddress() {
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getLine1() {
	return Line1;
    }

    public void setLine1(String line1) {
	Line1 = line1;
    }

    public String getLine2() {
	return Line2;
    }

    public void setLine2(String line2) {
	Line2 = line2;
    }

    public String getLine3() {
	return Line3;
    }

    public void setLine3(String line3) {
	Line3 = line3;
    }

    public String getLine4() {
	return Line4;
    }

    public void setLine4(String line4) {
	Line4 = line4;
    }

    public String getLine5() {
	return Line5;
    }

    public void setLine5(String line5) {
	Line5 = line5;
    }

    public String getCity() {
	return City;
    }

    public void setCity(String city) {
	City = city;
    }

    public String getCountry() {
	return Country;
    }

    public void setCountry(String country) {
	Country = country;
    }

    public String getCountryCode() {
	return CountryCode;
    }

    public void setCountryCode(String countryCode) {
	CountryCode = countryCode;
    }

    public String getCountrySubDivisionCode() {
	return CountrySubDivisionCode;
    }

    public void setCountrySubDivisionCode(String countrySubDivisionCode) {
	CountrySubDivisionCode = countrySubDivisionCode;
    }

    public String getPostalCode() {
	return PostalCode;
    }

    public void setPostalCode(String postalCode) {
	PostalCode = postalCode;
    }

    public String getPostalCodeSuffix() {
	return PostalCodeSuffix;
    }

    public void setPostalCodeSuffix(String postalCodeSuffix) {
	PostalCodeSuffix = postalCodeSuffix;
    }

    public String getLat() {
	return Lat;
    }

    public void setLat(String lat) {
	Lat = lat;
    }

    public String get_long() {
	return _long;
    }

    public void set_long(String _long) {
	this._long = _long;
    }

    public String getTag() {
	return tag;
    }

    public void setTag(String tag) {
	this.tag = tag;
    }

    public String getNote() {
	return note;
    }

    public void setNote(String note) {
	this.note = note;
    }
}
