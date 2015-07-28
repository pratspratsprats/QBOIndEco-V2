package com.intuit.qbo.ecommerce.domain;

import com.google.gson.annotations.Expose;

/**
 * @author bgopinath
 *
 */
public class Customer {
    @Expose
    private String id;
    @Expose
    private BillAddr BillAddr;
    @Expose
    private ShipAddr ShipAddr;
    @Expose
    private CurrencyRef CurrencyRef;
    @Expose
    private String GivenName;
    @Expose
    private String FamilyName;
    @Expose
    private String DisplayName;
    @Expose
    private PrimaryPhone PrimaryPhone;
    @Expose
    private Mobile Mobile;
    @Expose
    private PrimaryEmailAddr PrimaryEmailAddr;

    /**
     * 
     * @return The BillAddr
     */
    public BillAddr getBillAddr() {
	return BillAddr;
    }

    /**
     * 
     * @param BillAddr
     *            The BillAddr
     */
    public void setBillAddr(BillAddr BillAddr) {
	this.BillAddr = BillAddr;
    }

    /**
     * 
     * @return The ShipAddr
     */
    public ShipAddr getShipAddr() {
	return ShipAddr;
    }

    /**
     * 
     * @param ShipAddr
     *            The ShipAddr
     */
    public void setShipAddr(ShipAddr ShipAddr) {
	this.ShipAddr = ShipAddr;
    }

    /**
     * 
     * @return The CurrencyRef
     */
    public CurrencyRef getCurrencyRef() {
	return CurrencyRef;
    }

    /**
     * 
     * @param CurrencyRef
     *            The CurrencyRef
     */
    public void setCurrencyRef(CurrencyRef CurrencyRef) {
	this.CurrencyRef = CurrencyRef;
    }

    /**
     * 
     * @return The GivenName
     */
    public String getGivenName() {
	return GivenName;
    }

    /**
     * 
     * @param GivenName
     *            The GivenName
     */
    public void setGivenName(String GivenName) {
	this.GivenName = GivenName;
    }

    /**
     * 
     * @return The FamilyName
     */
    public String getFamilyName() {
	return FamilyName;
    }

    /**
     * 
     * @param FamilyName
     *            The FamilyName
     */
    public void setFamilyName(String FamilyName) {
	this.FamilyName = FamilyName;
    }

    /**
     * 
     * @return The DisplayName
     */
    public String getDisplayName() {
	return DisplayName;
    }

    /**
     * 
     * @param DisplayName
     *            The DisplayName
     */
    public void setDisplayName(String DisplayName) {
	this.DisplayName = DisplayName;
    }

    /**
     * 
     * @return The PrimaryPhone
     */
    public PrimaryPhone getPrimaryPhone() {
	return PrimaryPhone;
    }

    /**
     * 
     * @param PrimaryPhone
     *            The PrimaryPhone
     */
    public void setPrimaryPhone(PrimaryPhone PrimaryPhone) {
	this.PrimaryPhone = PrimaryPhone;
    }

    /**
     * 
     * @return The Mobile
     */
    public Mobile getMobile() {
	return Mobile;
    }

    /**
     * 
     * @param Mobile
     *            The Mobile
     */
    public void setMobile(Mobile Mobile) {
	this.Mobile = Mobile;
    }

    /**
     * 
     * @return The PrimaryEmailAddr
     */
    public PrimaryEmailAddr getPrimaryEmailAddr() {
	return PrimaryEmailAddr;
    }

    /**
     * 
     * @param PrimaryEmailAddr
     *            The PrimaryEmailAddr
     */
    public void setPrimaryEmailAddr(PrimaryEmailAddr PrimaryEmailAddr) {
	this.PrimaryEmailAddr = PrimaryEmailAddr;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

}