package com.power.outage.locator.client.admin;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.power.outage.locator.client.model.Outage;

@RemoteServiceRelativePath("AdminAsyncService")
public interface AdminService extends RemoteService {

	boolean checkCredentials(String userName, String password);
	ArrayList<Outage> getAllOutages();
	ArrayList<String> getAllAreas();
	int insertNewOutage(Outage outage);
	int makeTweet(String status);
}