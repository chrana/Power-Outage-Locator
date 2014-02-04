package com.power.outage.locator.client.model;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Outage implements IsSerializable {

	public int outageID;
	public String areaName;
	public Date outageStartDate;
	public boolean outageStatus;
	public Date outageEndDate;
	public int affectedCustomers;
	
	public Outage(){
		
	}
	
	public Outage(int outageID, String areaName, Date outageStartDate,
			boolean outageStatus, Date outageEndDate, String notes,
			int affectedCustomers) {
		super();
		this.outageID = outageID;
		this.areaName = areaName;
		this.outageStartDate = outageStartDate;
		this.outageStatus = outageStatus;
		this.outageEndDate = outageEndDate;
		this.affectedCustomers = affectedCustomers;
	}
	
	
	public int getOutageID() {
		return outageID;
	}

	public void setOutageID(int outageID) {
		this.outageID = outageID;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Date getOutageStartDate() {
		return outageStartDate;
	}

	public void setOutageStartDate(Date outageStartDate) {
		this.outageStartDate = outageStartDate;
	}

	public boolean isOutageStatus() {
		return outageStatus;
	}

	public void setOutageStatus(boolean outageStatus) {
		this.outageStatus = outageStatus;
	}

	public Date getOutageEndDate() {
		return outageEndDate;
	}

	public void setOutageEndDate(Date outageEndDate) {
		this.outageEndDate = outageEndDate;
	}

	public int getAffectedCustomers() {
		return affectedCustomers;
	}

	public void setAffectedCustomers(int affectedCustomers) {
		this.affectedCustomers = affectedCustomers;
	}
}
