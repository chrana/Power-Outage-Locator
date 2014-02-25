package com.power.outage.locator.client;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.power.outage.locator.client.model.Area;
import com.power.outage.locator.client.model.Coordinates;
import com.power.outage.locator.client.model.Notes;

@RemoteServiceRelativePath("PowerOutageAsyncService")
public interface PowerOutageService extends RemoteService {

	ArrayList<Coordinates> getCoordinatesByArea(String areaName);
	ArrayList<String> getAreaNames();
	ArrayList<Area> getAllAreasWithCoordinates();
	ArrayList<Notes> getNotesByAreaName(String areaName);
	ArrayList<String> getAllOutageNames();
}