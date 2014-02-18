package com.power.outage.locator.client.model;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Outage implements IsSerializable {

	private int outageID;
	private String areaName;
	private Date outageStartDate;
	private boolean outageStatus;
	private Date outageEndDate;
	private int affectedCustomers;
	private ArrayList<Notes> notes;
	

	public Outage() {

	}

	public Outage(int outageID, String areaName, Date outageStartDate,
			boolean outageStatus, Date outageEndDate, int affectedCustomers, ArrayList<Notes> notes) {
		super();
		this.outageID = outageID;
		this.areaName = areaName;
		this.outageStartDate = outageStartDate;
		this.outageStatus = outageStatus;
		this.outageEndDate = outageEndDate;
		this.affectedCustomers = affectedCustomers;
		this.notes = notes;
	}

	public Outage(String areasName, Date date, int affectedCustomers) {
		this.areaName = areasName;
		this.outageStartDate = date;
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

	public ArrayList<Notes> getNotes() {
		return notes;
	}

	public void addNote(Notes note) {
		getNotes().add(note);
	}
}
