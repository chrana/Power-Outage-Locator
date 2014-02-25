package com.power.outage.locator.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.power.outage.locator.client.PowerOutageService;
import com.power.outage.locator.client.model.Area;
import com.power.outage.locator.client.model.Coordinates;
import com.power.outage.locator.client.model.Notes;

public class PowerOutageServiceImpl extends RemoteServiceServlet implements
		PowerOutageService {

	private DBManager DBManagerInstance = new DBManager(
			"localhost", "powerplusdb", "root", "root");

	private static final long serialVersionUID = 2720413082357820775L;

	public ArrayList<Coordinates> getCoordinatesByArea(String areaName) {

		ArrayList<Coordinates> result = new ArrayList<Coordinates>();

		if (areaName != null) {
			try {
				DBManagerInstance.connect();
				ResultSet serverResult = DBManagerInstance
						.getCoordinatesByAreaName(areaName);
				while (serverResult.next()) {
					Double lat = serverResult.getDouble("Latitude");
					Double lon = serverResult.getDouble("Longitude");

					result.add(new Coordinates(lat, lon));
				}

				serverResult.close();
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (DBManagerInstance != null)
					try {
						DBManagerInstance.close();
					} catch (SQLException e) {
						Window.alert("Error Disconnect");
					}
			}
		}
		return result;
	}

	@Override
	public ArrayList<String> getAreaNames() {
		ArrayList<String> result = new ArrayList<String>();

		try {
			DBManagerInstance.connect();
			ResultSet serverResult = DBManagerInstance.getAllAreas();
			while (serverResult.next()) {
				result.add(serverResult.getString("AreaName"));
			}
			serverResult.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (DBManagerInstance != null)
				try {
					DBManagerInstance.close();
				} catch (SQLException e) {
					Window.alert("Error Disconnect");
				}
		}
		return result;
	}

	@Override
	public ArrayList<Area> getAllAreasWithCoordinates() {

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
			DBManagerInstance.connect();
			ResultSet serverResult = DBManagerInstance
					.getNotesByAreaName(areaName);
			while (serverResult.next()) {
				result.add(new Notes(serverResult.getInt("notesID"),
						serverResult.getString("notes"), serverResult
								.getInt("outageID")));
			}
			serverResult.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (DBManagerInstance != null)
				try {
					DBManagerInstance.close();
				} catch (SQLException e) {
					Window.alert("Error Disconnect");
				}
		}

		return result;
	}

	@Override
	public ArrayList<String> getAllOutageNames() {
		ArrayList<String> result = new ArrayList<String>();

		try {
			DBManagerInstance.connect();
			ResultSet serverOutageResult = DBManagerInstance.getAllOutages();
			while (serverOutageResult.next()) {
				result.add(serverOutageResult.getString("AreaName"));
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (DBManagerInstance != null)
				try {
					DBManagerInstance.close();
				} catch (SQLException e) {
					Window.alert("Error Disconnect");
				}
		}

		return result;
	}

}
