package com.intuit.qbo.ecommerce.krc.entity;

import java.util.Date;

public class SyncDates {
	
	private Date lastOrderSyncDate;
	private Date lastProductSyncDate;
	public Date getLastOrderSyncDate() {
		return lastOrderSyncDate;
	}
	public void setLastOrderSyncDate(Date lastOrderSyncDate) {
		this.lastOrderSyncDate = lastOrderSyncDate;
	}
	public Date getLastProductSyncDate() {
		return lastProductSyncDate;
	}
	public void setLastProductSyncDate(Date lastProductSyncDate) {
		this.lastProductSyncDate = lastProductSyncDate;
	}
	
	

}
