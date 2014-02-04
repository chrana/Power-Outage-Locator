package com.power.outage.locator.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class User implements IsSerializable {

	public int userID;
	public String userName;
	public String password;
	
	public User(){
		
	}

	public User(int userID, String userName, String password) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.password = password;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
