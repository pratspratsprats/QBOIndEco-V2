package com.intuit.qbo.ecommerce.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author bgopinath
 *
 */
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;
    protected List Line;
    protected ReferenceType CustomerRef;
    protected ReferenceType CurrencyRef;
    protected EmailAddress BillEmail;
    protected PhysicalAddress ShipAddr;
    protected PhysicalAddress BillAddr;
    protected String DocNumber;
    protected String TxnDate;
    protected String TrackingNum;
    protected ReferenceType ShipMethodRef;

    protected String GlobalTaxCalculation;

    public String getGlobalTaxCalculation() {
	return GlobalTaxCalculation;
    }

    public void setGlobalTaxCalculation(String globalTaxCalculation) {
	GlobalTaxCalculation = globalTaxCalculation;
    }

    public List getLine() {
	return Line;
    }

    public void setLine(List line) {
	Line = line;
    }

    public ReferenceType getCustomerRef() {
	return CustomerRef;
    }

    public void setCustomerRef(ReferenceType customerRef) {
	CustomerRef = customerRef;
    }

    public ReferenceType getCurrencyRef() {
	return CurrencyRef;
    }

    public void setCurrencyRef(ReferenceType currencyRef) {
	CurrencyRef = currencyRef;
    }

    public EmailAddress getEmailAddress() {
	return BillEmail;
    }

    public void setEmailAddress(EmailAddress emailAddress) {
	BillEmail = emailAddress;
    }

    public PhysicalAddress getShipAddr() {
	return ShipAddr;
    }

    public void setShipAddr(PhysicalAddress shipAddr) {
	ShipAddr = shipAddr;
    }

    public PhysicalAddress getBillAddr() {
	return BillAddr;
    }

    public void setBillAddr(PhysicalAddress billAddr) {
	BillAddr = billAddr;
    }

    public String getDocNumber() {
	return DocNumber;
    }

    public void setDocNumber(String docNumber) {
	DocNumber = docNumber;
    }

    public String getTxnDate() {
	return TxnDate;
    }

    public void setTxnDate(String txnDate) {
	TxnDate = txnDate;
    }

    public String getTrackingNum() {
	return TrackingNum;
    }

    public void setTrackingNum(String trackingNum) {
	TrackingNum = trackingNum;
    }

    public ReferenceType getShipMethodRef() {
	return ShipMethodRef;
    }

    public void setShipMethodRef(ReferenceType shipMethodRef) {
	ShipMethodRef = shipMethodRef;
    }
}
