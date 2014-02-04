package com.power.outage.locator.server;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Functions {

	private String serverName;
	private String dbContext;
	private String userName;
	private String password;
	private Connection connection;

	private final String GET_ALL_AREA_NAMES = "SELECT * FROM `powerplusdb`.`areas`";
	private final String GET_COORDINATES_BY_AREA = "SELECT * FROM `powerplusdb`.`coordinates` WHERE `AreaName` = ?";
	private final String GET_OUTAGE_BY_AREA = "SELECT * FROM `powerplusdb`.`outage` WHERE `AreaName` = ?";
	private final String GET_NOTES_BY_AREA = "SELECT * FROM `powerplusdb`.`notes` INNER JOIN `powerplusdb`.`outage` ON `powerplusdb`.`notes`.`outageID` = `powerplusdb`.`outage`.`OutageID` WHERE `powerplusdb`.`outage`.`AreaName` = ? AND `powerplusdb`.`outage`.`OutageActive` = 1";
	private final String AUTHENTICATE_USER = "CALL authenticateUser(?,?)";

	public Functions(String serverName, String dbContext, String userName,
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

		connect();
		PreparedStatement selectStatement = connection
				.prepareStatement(GET_COORDINATES_BY_AREA);
		selectStatement.setString(1, areaName);
		ResultSet result = selectStatement.executeQuery();
		return result;
	}

	public ResultSet getAllTheAreaNames() throws SQLException,
			ClassNotFoundException {
		connect();
		PreparedStatement selectStatement = connection
				.prepareStatement(GET_ALL_AREA_NAMES);
		ResultSet result = selectStatement.executeQuery();
		return result;
	}

	public ResultSet getOutageByAreaName(String areaName)
			throws ClassNotFoundException, SQLException {

		connect();
		PreparedStatement selectStatement = connection
				.prepareStatement(GET_OUTAGE_BY_AREA);
		selectStatement.setString(1, areaName);
		ResultSet result = selectStatement.executeQuery();
		return result;
	}
	
	public ResultSet getNotesByAreaName(String areaName)
			throws ClassNotFoundException, SQLException {

		connect();
		PreparedStatement selectStatement = connection
				.prepareStatement(GET_NOTES_BY_AREA);
		selectStatement.setString(1, areaName);
		ResultSet result = selectStatement.executeQuery();
		return result;
	}
	
	public ResultSet authenticate(String userName, String password) throws ClassNotFoundException, SQLException{
		connect();
		
		CallableStatement storedProcedureCall = connection.prepareCall(AUTHENTICATE_USER);
		storedProcedureCall.setString(1, userName);
		storedProcedureCall.setString(2, password);
		ResultSet result = storedProcedureCall.executeQuery();
		
		return result;
	}
	
	public void close() throws SQLException {
		connection.close();
	}

}
