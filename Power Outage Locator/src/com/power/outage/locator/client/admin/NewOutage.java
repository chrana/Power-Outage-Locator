package com.power.outage.locator.client.admin;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NewOutage extends Composite {

	ListBox lstBoxAreas;
	IntegerBox txtCustomersAffected;
	Button btnSubmit;
	CheckBox chkMakeTweet;

	public NewOutage() {

		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);

		Label lblAddNewOutage = new Label("Add a New Outage");
		lblAddNewOutage.setStyleName("gwt-Label-Login");
		verticalPanel.add(lblAddNewOutage);

		FlexTable flexTable = new FlexTable();
		verticalPanel.add(flexTable);
		flexTable.setWidth("80%");

		Label lblAreaName = new Label("Area Name:");
		Label lblCustomersAffected = new Label("No of Customers Affected");
		txtCustomersAffected = new IntegerBox();
		chkMakeTweet = new CheckBox("Make A Tweet");
		btnSubmit = new Button("Submit New Outage");
		lstBoxAreas = new ListBox(false);

		flexTable.setWidget(0, 0, lblAreaName);
		flexTable.setWidget(0, 1, lstBoxAreas);
		flexTable.setWidget(1, 0, lblCustomersAffected);
		flexTable.setWidget(1, 1, txtCustomersAffected);
		flexTable.setWidget(2, 1, chkMakeTweet);
		flexTable.setWidget(3, 1, btnSubmit);
	}

	public ListBox getLstBoxAreas() {
		return lstBoxAreas;
	}

	public IntegerBox getTxtCustomersAffected() {
		return txtCustomersAffected;
	}

	public Button getBtnSubmit() {
		return btnSubmit;
	}

	public CheckBox getChkMakeTweet() {
		return chkMakeTweet;
	}
}
