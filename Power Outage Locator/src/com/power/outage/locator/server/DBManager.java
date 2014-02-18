package com.power.outage.locator.server;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {

	private String serverName;
	private String dbContext;
	private String userName;
	private String password;
	private Connection connection;

	private final String GET_ALL_AREAS = "SELECT * FROM `powerplusdb`.`areas`";
	private final String GET_COORDINATES_BY_AREA = "SELECT * FROM `powerplusdb`.`coordinates` WHERE `AreaName` = ?";
	private final String GET_NOTES_BY_AREA = "SELECT * FROM `powerplusdb`.`notes` INNER JOIN `powerplusdb`.`outage` ON `powerplusdb`.`notes`.`outageID` = `powerplusdb`.`outage`.`OutageID` WHERE `powerplusdb`.`outage`.`AreaName` = ? AND `powerplusdb`.`outage`.`OutageActive` = 1";
	private final String AUTHENTICATE_USER = "CALL authenticateUser(?)";
	private final String GET_ALL_OUTAGES = "CALL getAllOutages()";
	private final String INSERT_NEW_OUTAGE = "CALL `powerplusdb`.`insertNewOutage`(?, ?, ?)";

	public DBManager(String serverName, String dbContext, String userName,
			String password) {
		super();
		this.serverName = serverName;
		this.dbContext = dbContext;
		this.userName = userName;
		this.password = password;

	}

	public void connect() throws SQLException, ClassNotFoundException {

		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://" + serverName
				+ "/" + dbContext, userName, password);
	}

	public ResultSet getCoordinatesByAreaName(String areaName)
			throws ClassNotFoundException, SQLException {

		PreparedStatement selectStatement = connection
				.prepareStatement(GET_COORDINATES_BY_AREA);
		selectStatement.setString(1, areaName);
		ResultSet result = selectStatement.executeQuery();
		return result;
	}

	public ResultSet getAllAreas() throws SQLException, ClassNotFoundException {
		PreparedStatement selectStatement = connection
				.prepareStatement(GET_ALL_AREAS);
		ResultSet result = selectStatement.executeQuery();
		return result;
	}

	public ResultSet getNotesByAreaName(String areaName)
			throws ClassNotFoundException, SQLException {
		PreparedStatement selectStatement = connection
				.prepareStatement(GET_NOTES_BY_AREA);
		selectStatement.setString(1, areaName);
		ResultSet result = selectStatement.executeQuery();
		return result;
	}

	public ResultSet authenticate(String userName)
			throws ClassNotFoundException, SQLException {
		CallableStatement storedProcedureCall = connection
				.prepareCall(AUTHENTICATE_USER);
		storedProcedureCall.setString(1, userName);
		ResultSet result = storedProcedureCall.executeQuery();
		return result;
	}

	public ResultSet getAllOutages() throws ClassNotFoundException,
			SQLException {
		CallableStatement storedProcedureCall = connection
				.prepareCall(GET_ALL_OUTAGES);
		ResultSet result = storedProcedureCall.executeQuery();
		return result;
	}

	public int inserNewOutage(String areaName, String d, int customers) throws ClassNotFoundException, SQLException {
		CallableStatement storedProcedureCall = connection
				.prepareCall(INSERT_NEW_OUTAGE);
		storedProcedureCall.setString(1, areaName);
		storedProcedureCall.setString(2, d);
		storedProcedureCall.setInt(3, customers);
		return storedProcedureCall.executeUpdate();
	}

	public void close() throws SQLException {
		connection.close();
	}

}
