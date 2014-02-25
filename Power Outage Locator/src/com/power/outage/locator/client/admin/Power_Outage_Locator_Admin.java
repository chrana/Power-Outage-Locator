package com.power.outage.locator.client.admin;

import java.util.ArrayList;
import java.sql.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.power.outage.locator.client.model.Notes;
import com.power.outage.locator.client.model.Outage;
import com.power.outage.locator.client.model.PowerPlusConstants;

public class Power_Outage_Locator_Admin implements EntryPoint {

	private RootPanel rootPanel;
	private FlexTable flexTable = new FlexTable();
	private FlexTable contentFlexTable = new FlexTable();
	private TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(1.5, Unit.EM);
	private AdminServiceAsync powerPlusService = GWT.create(AdminService.class);
	private PowerPlusConstants constants = GWT.create(PowerPlusConstants.class);

	@Override
	public void onModuleLoad() {

		rootPanel = RootPanel.get("bodyContent");
		buildUI();

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		VerticalPanel verticalPanel = new VerticalPanel();
		horizontalPanel.add(verticalPanel);
		final Login login = new Login();
		
		horizontalPanel.add(login);
		Label lblWelcomeToThe = new Label(constants.admin());

		verticalPanel.add(lblWelcomeToThe);
		contentFlexTable.setWidget(3, 0, horizontalPanel);

		lblWelcomeToThe.setStyleName("gwt-Label-Login");
		horizontalPanel.setStyleName("logInTable");
		lblWelcomeToThe.setSize("226px", "124px");
		
		rootPanel.add(contentFlexTable);


		login.getBtnSignIn().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

					@Override
					public void onSuccess(Boolean result) {
						if (result) {
							loadCurrentOutage();
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
		
		horizontalPanel.getElement().getStyle().setPosition(Position.RELATIVE);
	}
	
	private void buildUI(){
		
		Label lblLogo = new Label(constants.logo());
		Label lblQuickLinks = new Label(constants.quickLinks());
		FlowPanel navigationPannel = new FlowPanel();
		Anchor hypHome = new Anchor(constants.home(), "Home.html");
		Anchor hypAdmin = new Anchor(constants.admin(), "Admin.html");
		navigationPannel.add(hypHome);
		navigationPannel.add(hypAdmin);
		
		contentFlexTable.setWidget(0, 0, lblLogo);
		contentFlexTable.setWidget(0, 2, lblQuickLinks);
		contentFlexTable.setWidget(1, 0, navigationPannel);
		contentFlexTable.setWidget(1, 2, null);
		
		contentFlexTable.getRowFormatter().setStyleName(0, "logoRow");
		contentFlexTable.getRowFormatter().setStyleName(1, "topnav");
		contentFlexTable.setCellSpacing(0);
		contentFlexTable.getRowFormatter().setStyleName(2, "borderRow");
		contentFlexTable.getFlexCellFormatter().setColSpan(2, 0, 3);
		
		rootPanel.add(contentFlexTable);

	}

	private void loadCurrentOutage() {

		final StackLayoutPanel stackLayoutCurrentOutages = new StackLayoutPanel(
				Unit.EM);
		AsyncCallback<ArrayList<Outage>> currentOutageCallBack = new AsyncCallback<ArrayList<Outage>>() {
			@Override
			public void onSuccess(ArrayList<Outage> result) {
				for (Outage outage : result) {
					HTML notes = new HTML();

					for (Notes n : outage.getNotes()) {
						notes.setHTML(notes.getHTML() + "<br /> "
								+ n.getNotes());
					}

					stackLayoutCurrentOutages.add(notes, outage.getAreaName(),
							3);
					tabLayoutPanel.add(stackLayoutCurrentOutages, new HTML(
							"Current Outages"));
				}
				loadNewOutage();

			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error Connecting to the server");
			}
		};
		powerPlusService.getAllOutages(currentOutageCallBack);
	}

	private void loadNewOutage() {

		final NewOutage newOutageLayout = new NewOutage();

		// New Outage
		tabLayoutPanel.add(newOutageLayout, new HTML("Add New Outage"));
		tabLayoutPanel.selectTab(0);
		tabLayoutPanel.setSize("70%", "500px");

		flexTable.setWidget(1, 0, tabLayoutPanel);
		contentFlexTable.setWidget(3, 0, flexTable);

		AsyncCallback<ArrayList<String>> newOutageAreaCallBack = new AsyncCallback<ArrayList<String>>() {
			@Override
			public void onSuccess(ArrayList<String> result) {
				for (String s : result) {
					newOutageLayout.getLstBoxAreas().addItem(s);
				}
				newOutageLayout.getBtnSubmit().addClickHandler(
						new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								String areaName = newOutageLayout
										.getLstBoxAreas().getItemText(
												newOutageLayout
														.getLstBoxAreas()
														.getSelectedIndex());

								Date d = new Date(new java.util.Date()
										.getTime());

								int customersAffected = Integer
										.parseInt(newOutageLayout
												.getTxtCustomersAffected()
												.getText());
								Outage newOutage = new Outage(areaName, d,
										customersAffected);

								insertNewOutage(newOutage);

								if (newOutageLayout.getChkMakeTweet()
										.getValue()) {
									makeTweet(newOutage);
								}
							}
						});
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error Connecting to the server");
			}
		};
		powerPlusService.getAllAreas(newOutageAreaCallBack);

	}

	private void insertNewOutage(Outage o) {

		AsyncCallback<Integer> callback = new AsyncCallback<Integer>() {

			@Override
			public void onSuccess(Integer result) {
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error Inserting new Outage");
			}
		};
		powerPlusService.insertNewOutage(o, callback);
	}

	private void makeTweet(Outage o) {
		AsyncCallback<Integer> callback = new AsyncCallback<Integer>() {

			@Override
			public void onSuccess(Integer result) {
				Window.alert("Tweet Sucessfull");

			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error Making a tweet");
			}
		};
		powerPlusService
				.makeTweet(
						"Outage in "
								+ o.getAreaName()
								+ ". Detedcted on "
								+ o.getOutageStartDate().toString()
								+ ". This tweet is just for test puposes and doesnt not reflect real outages",
						callback);
	}
}
