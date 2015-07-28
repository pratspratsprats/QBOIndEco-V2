package com.intuit.qbo.ecommerce.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author bgopinath
 *
 */
public class Line implements Serializable {

    private static final long serialVersionUID = 1L;
    protected String id;
    protected BigInteger lineNum;
    protected String description;
    protected BigDecimal Amount;
    protected String DetailType;
    protected String globalTaxCalculation;
    protected SalesItemLineDetail SalesItemLineDetail;

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public BigInteger getLineNum() {
	return lineNum;
    }

    public void setLineNum(BigInteger lineNum) {
	this.lineNum = lineNum;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public BigDecimal getAmount() {
	return Amount;
    }

    public void setAmount(BigDecimal amount) {
	Amount = amount;
    }

    public String getDetailType() {
	return DetailType;
    }

    public void setDetailType(String detailType) {
	DetailType = detailType;
    }

    public SalesItemLineDetail getSalesItemLineDetail() {
	return SalesItemLineDetail;
    }

    public void setSalesItemLineDetail(SalesItemLineDetail salesItemLineDetail) {
	SalesItemLineDetail = salesItemLineDetail;
    }

	public String getGlobalTaxCalculation() {
		return globalTaxCalculation;
	}

	public void setGlobalTaxCalculation(String globalTaxCalculation) {
		this.globalTaxCalculation = globalTaxCalculation;
	}


}
