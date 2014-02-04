package com.power.outage.locator.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("AdminAsyncService")
public interface AdminService extends RemoteService {

	boolean checkCredentials(String userName, String password);
}