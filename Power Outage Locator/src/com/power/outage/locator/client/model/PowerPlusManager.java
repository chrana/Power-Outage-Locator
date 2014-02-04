package com.power.outage.locator.client.model;

import java.util.ArrayList;

public class PowerPlusManager {
	
	private ArrayList<Area> areaList = new ArrayList<Area>();
	private ArrayList<Outage> outageList = new ArrayList<Outage>();
	
	public ArrayList<Outage> getOutageList() {
		return outageList;
	}
	public void setOutageList(ArrayList<Outage> outageList) {
		this.outageList = outageList;
	}
	public ArrayList<Area> getAreaList() {
		return areaList;
	}
	public void setAreaList(ArrayList<Area> areaList) {
		this.areaList = areaList;
	}

}
