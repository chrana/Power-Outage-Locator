package com.power.outage.locator.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Notes implements IsSerializable {

	public int notesID;
	public String notes;
	public int outageID;
	
	public Notes(){
		
	}
	
	public Notes(int notesID, String notes, int outageID) {
		super();
		this.notesID = notesID;
		this.notes = notes;
		this.outageID = outageID;
	}

	public int getNotesID() {
		return notesID;
	}

	public void setNotesID(int notesID) {
		this.notesID = notesID;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getOutageID() {
		return outageID;
	}

	public void setOutageID(int outageID) {
		this.outageID = outageID;
	}
}
