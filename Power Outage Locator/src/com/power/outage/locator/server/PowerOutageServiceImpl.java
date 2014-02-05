package com.power.outage.locator.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.power.outage.locator.client.PowerOutageService;
import com.power.outage.locator.client.model.Area;
import com.power.outage.locator.client.model.Coordinates;
import com.power.outage.locator.client.model.Notes;

public class PowerOutageServiceImpl extends RemoteServiceServlet implements
		PowerOutageService {

	private Functions FUNCTIONS = new Functions("PowerPlus.cloudapp.net", "powerplusdb",
			"chrana", "root");

	private static final long serialVersionUID = 2720413082357820775L;

	public ArrayList<Coordinates> getCoordinatesByArea(String areaName) {

		ArrayList<Coordinates> result = new ArrayList<Coordinates>();

		try {
			ResultSet serverResult = FUNCTIONS
					.getCoordinatesByAreaName(areaName);
			while (serverResult.next()) {
				Double lat = serverResult.getDouble("Latitude");
				Double lon = serverResult.getDouble("Longitude");

				result.add(new Coordinates(lat, lon));
			}

			serverResult.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public ArrayList<String> getAreaNames() {
		ArrayList<String> result = new ArrayList<String>();

		try {
			ResultSet serverResult = FUNCTIONS.getAllTheAreaNames();
			while (serverResult.next()) {
				result.add(serverResult.getString("AreaName"));
			}
			serverResult.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public ArrayList<Area> getAllAreas() {

		ArrayList<Area> result = new ArrayList<Area>();
		ArrayList<String> areaNames = getAreaNames();
		ArrayList<Coordinates> coordinates = null;

		for (String areaName : areaNames) {
			coordinates = getCoordinatesByArea(areaName);
			result.add(new Area(areaName, coordinates));
		}

		return result;
	}

	@Override
	public ArrayList<Notes> getNotesByAreaName(String areaName) {
		ArrayList<Notes> result = new ArrayList<Notes>();

		try {
			ResultSet serverResult = FUNCTIONS.getNotesByAreaName(areaName);
			while (serverResult.next()) {
				result.add(new Notes(serverResult.getInt("notesID"),
						serverResult.getString("notes"), serverResult
								.getInt("outageID")));
			}
			serverResult.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

}
