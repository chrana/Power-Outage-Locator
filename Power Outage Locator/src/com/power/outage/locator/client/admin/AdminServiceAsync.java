package com.power.outage.locator.client.admin;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.power.outage.locator.client.model.Outage;

public interface AdminServiceAsync {
	
	void checkCredentials(String userName, String password, AsyncCallback<Boolean> callback);
	void getAllOutages(AsyncCallback<ArrayList<Outage>> callback);
	void getAllAreas(AsyncCallback<ArrayList<String>> callback);
	void insertNewOutage(Outage outage, AsyncCallback<Integer> callback);
	void makeTweet(String status, AsyncCallback<Integer> callback);

}
