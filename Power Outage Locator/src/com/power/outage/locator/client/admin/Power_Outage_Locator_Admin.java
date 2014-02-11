package com.power.outage.locator.client.admin;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Power_Outage_Locator_Admin implements EntryPoint {

	private RootPanel rootPanel;
	FlexTable flexTable = new FlexTable();
	private AdminServiceAsync powerPlusService = GWT.create(AdminService.class);

	@Override
	public void onModuleLoad() {

		rootPanel = RootPanel.get();

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		rootPanel.add(horizontalPanel, 10, 10);
		horizontalPanel.setSize("470px", "212px");

		VerticalPanel verticalPanel = new VerticalPanel();
		horizontalPanel.add(verticalPanel);

		Label lblWelcomeToThe = new Label("Administrator Page");
		lblWelcomeToThe.setStyleName("gwt-Label-Login");
		verticalPanel.add(lblWelcomeToThe);
		lblWelcomeToThe.setSize("226px", "124px");

		final Login login = new Login();
		horizontalPanel.add(login);

		login.getBtnSignIn().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

					@Override
					public void onSuccess(Boolean result) {
						if (result) {
							loadAdminUI();
						} else {
							login.getTextBoxPassword().setText("");
							login.getTextBoxUsername().setText("");
							login.getLblErrorMsg().setText("Log In Failed");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Could not connect to the server");
					}
				};
				powerPlusService.checkCredentials(login.getTextBoxUsername()
						.getText(), login.getTextBoxPassword().getText(),
						callback);
			}

		});
	}

	private void loadAdminUI() {

		flexTable.clear();
		rootPanel.clear();
		Label lblAdminHeader = new Label("Administrator Page");
		flexTable.setWidget(0, 0, lblAdminHeader);
		TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(1.5, Unit.EM);
		
		StackLayoutPanel p = new StackLayoutPanel(Unit.EM);
		p.add(new HTML("Comments<br />More Comments"), new HTML("Region One"), 3);
		p.add(new HTML("Info<br />Infor for Outage in Region Two"), new HTML("Region Two"), 3);
		p.add(new HTML("Notes<br />Notes Pulled From Database"), new HTML("Region Three"), 3);
		
		tabLayoutPanel.add(p, new HTML("Current Outage"));
		tabLayoutPanel.add(new HTML("Flex Table will go here to deatils of new Outage"), new HTML("Add New Outage"));
		tabLayoutPanel.selectTab(0);
		tabLayoutPanel.setSize("70%", "500px");
		
		flexTable.setWidget(1, 0, tabLayoutPanel);
		rootPanel.add(flexTable);
	}
}
