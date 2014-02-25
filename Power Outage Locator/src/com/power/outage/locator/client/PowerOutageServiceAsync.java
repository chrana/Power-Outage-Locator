package com.power.outage.locator.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.power.outage.locator.client.model.Area;
import com.power.outage.locator.client.model.Coordinates;
import com.power.outage.locator.client.model.Notes;

public interface PowerOutageServiceAsync {

	void getCoordinatesByArea(String page,
			AsyncCallback<ArrayList<Coordinates>> callback)
			throws IllegalArgumentException;
	
	void getAreaNames(AsyncCallback<ArrayList<String>> callback);
	
	void getAllAreasWithCoordinates(AsyncCallback<ArrayList<Area>> callback);
	
	void getNotesByAreaName(String areaName, AsyncCallback<ArrayList<Notes>> callback);
	void getAllOutageNames(AsyncCallback<ArrayList<String>> callback);

}
