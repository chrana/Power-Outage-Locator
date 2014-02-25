package com.power.outage.locator.server;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import twitter4j.TwitterException;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.power.outage.locator.client.admin.AdminService;
import com.power.outage.locator.client.model.Notes;
import com.power.outage.locator.client.model.Outage;

public class AdminServiceImpl extends RemoteServiceServlet implements
		AdminService {

	private static final long serialVersionUID = 3231950922074455610L;
	private DBManager DBManagerInstance = new DBManager(
			"localhost", "powerplusdb", "root", "root");

	@Override
	public boolean checkCredentials(String userName, String password) {
		Boolean result = false;

		try {
			DBManagerInstance.connect();
			ResultSet response = DBManagerInstance.authenticate(userName);
			if (response.next()) {
				if (Encryption.validatePassword(password,
						response.getString("password"))) {
					result = true;
				}
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException
				| SQLException | ClassNotFoundException e) {
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
	public ArrayList<Outage> getAllOutages() {
		ArrayList<Outage> result = new ArrayList<Outage>();

		try {
			DBManagerInstance.connect();
			ResultSet serverOutageResult = DBManagerInstance.getAllOutages();
			ResultSet serverNotesResult = null;
			ArrayList<Notes> outageNotes = null;
			while (serverOutageResult.next()) {

				serverNotesResult = DBManagerInstance
						.getNotesByAreaName(serverOutageResult
								.getString("AreaName"));
				outageNotes = new ArrayList<Notes>();

				while (serverNotesResult.next()) {
					outageNotes.add(new Notes(serverNotesResult
							.getInt("notesID"), serverNotesResult
							.getString("notes"), serverNotesResult
							.getInt("outageID")));
				}

				result.add(new Outage(serverOutageResult.getInt("OutageID"),
						serverOutageResult.getString("AreaName"),
						serverOutageResult.getDate("OutageStartDate"),
						serverOutageResult.getBoolean("OutageActive"),
						serverOutageResult.getDate("OutageEndDate"),
						serverOutageResult.getInt("CustomersAffected"),
						outageNotes));
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

	@Override
	public ArrayList<String> getAllAreas() {
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
	public int insertNewOutage(Outage outage) {
		try {
			DBManagerInstance.connect();
			DBManagerInstance.inserNewOutage(outage.getAreaName(), outage
					.getOutageStartDate().toString(), outage
					.getAffectedCustomers());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (DBManagerInstance != null)
				try {
					DBManagerInstance.close();
				} catch (SQLException e) {
					Window.alert("Error Disconnect");
				}
		}
		return 0;
	}

	@Override
	public int makeTweet(String status) {

		PowerPlusTwitter powerPlusTwitter = PowerPlusTwitter.getSingelton();
		try {
			powerPlusTwitter.updateStatus(status);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
