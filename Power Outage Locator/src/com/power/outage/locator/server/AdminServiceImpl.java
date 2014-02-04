package com.power.outage.locator.server;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.power.outage.locator.client.AdminService;

public class AdminServiceImpl extends RemoteServiceServlet implements
		AdminService {

	private static final long serialVersionUID = 3231950922074455610L;
	private Functions FUNCTIONS = new Functions("localhost", "powerplusdb",
			"root", "root");

	@Override
	public boolean checkCredentials(String userName, String password) {
		Boolean result = false;
		
		try {
			ResultSet response = FUNCTIONS.authenticate(userName);
			if(response.next()){
				if(Encryption.validatePassword(password, response.getString("password"))){
					result = true;
				}
			}
		} catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
