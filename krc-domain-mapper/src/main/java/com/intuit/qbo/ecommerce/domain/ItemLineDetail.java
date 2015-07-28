package com.intuit.qbo.ecommerce.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author bgopinath
 *
 */
public abstract class ItemLineDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    protected ReferenceType ItemRef;
    protected ReferenceType ClassRef;
    protected BigDecimal UnitPrice;
    protected BigDecimal RatePercent;
    protected ReferenceType PriceLevelRef;
    protected BigDecimal Qty;
    protected ReferenceType ItemAccountRef;
    protected ReferenceType InventorySiteRef;
    protected ReferenceType TaxCodeRef;

    public ReferenceType getItemRef() {
	return ItemRef;
    }

    public void setItemRef(ReferenceType itemRef) {
	ItemRef = itemRef;
    }

    public ReferenceType getClassRef() {
	return ClassRef;
    }

    public void setClassRef(ReferenceType classRef) {
	ClassRef = classRef;
    }

    public BigDecimal getUnitPrice() {
	return UnitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
	UnitPrice = unitPrice;
    }

    public BigDecimal getRatePercent() {
	return RatePercent;
    }

    public void setRatePercent(BigDecimal ratePercent) {
	RatePercent = ratePercent;
    }

    public ReferenceType getPriceLevelRef() {
	return PriceLevelRef;
    }

    public void setPriceLevelRef(ReferenceType priceLevelRef) {
	PriceLevelRef = priceLevelRef;
    }

    public BigDecimal getQty() {
	return Qty;
    }

    public void setQty(BigDecimal qty) {
	Qty = qty;
    }

    public ReferenceType getItemAccountRef() {
	return ItemAccountRef;
    }

    public void setItemAccountRef(ReferenceType itemAccountRef) {
	ItemAccountRef = itemAccountRef;
    }

    public ReferenceType getInventorySiteRef() {
	return InventorySiteRef;
    }

    public void setInventorySiteRef(ReferenceType inventorySiteRef) {
	InventorySiteRef = inventorySiteRef;
    }

    public ReferenceType getTaxCodeRef() {
	return TaxCodeRef;
    }

    public void setTaxCodeRef(ReferenceType taxCodeRef) {
	TaxCodeRef = taxCodeRef;
    }

}