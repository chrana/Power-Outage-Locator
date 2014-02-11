package com.power.outage.locator.server;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.power.outage.locator.client.admin.AdminService;

public class AdminServiceImpl extends RemoteServiceServlet implements
		AdminService {

	private static final long serialVersionUID = 3231950922074455610L;
	private DBManager DBManagerInstance = new DBManager("localhost",
			"powerplusdb", "root", "root");

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
					e.printStackTrace();
				}
		}
		return result;
	}
}
