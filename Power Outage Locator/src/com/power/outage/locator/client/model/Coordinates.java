package com.power.outage.locator.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Coordinates implements IsSerializable {

	public double latitude;
	public double longitude;
	
	public Coordinates(){
		
	}

	public Coordinates(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
}
