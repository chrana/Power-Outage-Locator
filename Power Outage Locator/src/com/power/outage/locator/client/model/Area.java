package com.power.outage.locator.client.model;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Area implements IsSerializable {

	public int areaID;
	public String areaName;
	public int totalCustomers;
	public ArrayList<Coordinates> coordinateList = new ArrayList<Coordinates>();

	public Area() {
	}

	public Area(int areaID, String areaName, int totalCustomers, ArrayList<Coordinates> coordinateList) {
		this.areaID = areaID;
		this.areaName = areaName;
		this.totalCustomers = totalCustomers;
		this.coordinateList = coordinateList;
	}

	public Area(String areaName, ArrayList<Coordinates> coordinateList) {
		this.areaName = areaName;
		this.coordinateList = coordinateList;
	}

	public int getAreaID() {
		return areaID;
	}

	public void setAreaID(int areaID) {
		this.areaID = areaID;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public int getTotalCustomers() {
		return totalCustomers;
	}

	public void setTotalCustomers(int totalCustomers) {
		this.totalCustomers = totalCustomers;
	}

	public ArrayList<Coordinates> getCoordinateList() {
		return coordinateList;
	}

	public void setCoordinateList(ArrayList<Coordinates> coordinateList) {
		this.coordinateList = coordinateList;
	}

}
