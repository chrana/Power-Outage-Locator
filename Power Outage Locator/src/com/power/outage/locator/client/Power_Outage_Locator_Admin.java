package com.power.outage.locator.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class Power_Outage_Locator_Admin implements EntryPoint {

	private RootPanel rootPannel;
	FlexTable flexTable = new FlexTable();
	private AdminServiceAsync powerPlusService = GWT.create(AdminService.class);

	@Override
	public void onModuleLoad() {
		
		rootPannel = RootPanel.get("bodyContent");

		Label lblUseName = new Label("User Name:");
		final TextBox txtUserName = new TextBox();
		Label lblPassword = new Label("Password:");
		final PasswordTextBox txtPassword = new PasswordTextBox();
		final Label lblMsg = new Label();
		Button btnLogIn = new Button("Log In");

		flexTable.setWidget(0, 0, lblUseName);
		flexTable.setWidget(0, 1, txtUserName);
		flexTable.setWidget(1, 0, lblPassword);
		flexTable.setWidget(1, 1, txtPassword);
		flexTable.setWidget(2, 0, btnLogIn);
		flexTable.setWidget(2, 1, lblMsg);

		rootPannel.add(flexTable);

		btnLogIn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
					
					@Override
					public void onSuccess(Boolean result) {
						if(result){
							loadAdminUI();
						}
						else
							lblMsg.setText("Log In Failed");
					}

					@Override
					public void onFailure(Throwable caught) {
							Window.alert(caught.getLocalizedMessage());
					}
				};
				powerPlusService.checkCredentials(txtUserName.getText(), txtPassword.getText(), callback);
			}

		});
	}
	
	private void loadAdminUI() {
		
		flexTable.clear();
		Label lblAdminHeader = new Label("Admin Page");
		flexTable.setWidget(0, 0, lblAdminHeader);
		rootPannel.add(flexTable);
	}
}
