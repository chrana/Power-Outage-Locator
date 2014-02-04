package com.power.outage.locator.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AdminServiceAsync {
	
	void checkCredentials(String userName, String password, AsyncCallback<Boolean> callback);

}
