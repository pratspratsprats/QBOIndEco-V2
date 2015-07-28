package com.intuit.qbo.ecommerce.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.google.gson.annotations.Expose;

/**
 * @author bgopinath
 *
 */
public class Product {

    @Expose
    private String Name;
    @Expose
    private String Description;
    @Expose
    private BigDecimal UnitPrice;
    @Expose
    private String Type;
    @Expose
    private IncomeAccountRef IncomeAccountRef;
    @Expose
    private Integer QtyOnHand;
    @Expose
    private Date InvStartDate;

    /**
     * 
     * @return The Name
     */
    public String getName() {
	return Name;
    }

    /**
     * 
     * @param Name
     *            The Name
     */
    public void setName(String Name) {
	this.Name = Name;
    }

    /**
     * 
     * @return The Description
     */
    public String getDescription() {
	return Description;
    }

    /**
     * 
     * @param Description
     *            The Description
     */
    public void setDescription(String Description) {
	this.Description = Description;
    }

    /**
     * 
     * @return The UnitPrice
     */
    public BigDecimal getUnitPrice() {
	return UnitPrice;
    }

    /**
     * 
     * @param UnitPrice
     *            The UnitPrice
     */
    public void setUnitPrice(BigDecimal UnitPrice) {
	this.UnitPrice = UnitPrice;
    }

    /**
     * 
     * @return The Type
     */
    public String getType() {
	return Type;
    }

    /**
     * 
     * @param Type
     *            The Type
     */
    public void setType(String Type) {
	this.Type = Type;
    }

    /**
     * 
     * @return The IncomeAccountRef
     */
    public IncomeAccountRef getIncomeAccountRef() {
	return IncomeAccountRef;
    }

    /**
     * 
     * @param IncomeAccountRef
     *            The IncomeAccountRef
     */
    public void setIncomeAccountRef(IncomeAccountRef IncomeAccountRef) {
	this.IncomeAccountRef = IncomeAccountRef;
    }

    /**
     * 
     * @return The QtyOnHand
     */
    public Integer getQtyOnHand() {
	return QtyOnHand;
    }

    /**
     * 
     * @param QtyOnHand
     *            The QtyOnHand
     */
    public void setQtyOnHand(Integer QtyOnHand) {
	this.QtyOnHand = QtyOnHand;
    }

    /**
     * 
     * @return The InvStartDate
     */
    public Date getInvStartDate() {
	return InvStartDate;
    }

    /**
     * 
     * @param InvStartDate
     *            The InvStartDate
     */
    public void setInvStartDate(Date InvStartDate) {
	this.InvStartDate = InvStartDate;
    }

}