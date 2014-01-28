package com.power.outage.locator.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.event.PolygonMouseOutHandler;
import com.google.gwt.maps.client.event.PolygonMouseOverHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.PolyStyleOptions;
import com.google.gwt.maps.client.overlay.Polygon;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.power.outage.locator.client.model.Coordinates;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Power_Outage_Locator implements EntryPoint {

	PowerOutageServiceAsync service = (PowerOutageServiceAsync) GWT
			.create(PowerOutageService.class);
	
	MapWidget map = null;
	ArrayList<Coordinates> ontarioCoordinates = new ArrayList<Coordinates>();

	public void onModuleLoad() {

		fetchDataFromServer();

		// Map Key
		Maps.loadMapsApi("AIzaSyC-fM_2v4Q399Yy2paZWahqFZm7HXoOBe4", "2", false,
				new Runnable() {
					public void run() {
					}
				});
	}

	private void buildUi() {

		RootPanel rootPannel = RootPanel.get("bodyContent");
		rootPannel.setStyleName((String) null);

		Label lblLogo = new Label("LOGO");
		Label lblQuickLinks = new Label("QuickLinks");
		Label lblNavMenu = new Label("Navigation Menu");
		Label lblLegend = new Label("Legends and Information");
		Label lblFooter = new Label("Footer (If NEEDED)");

		SimplePanel widg = new SimplePanel();

		widg.setSize("100%", "100%");

		// Google Map

		LatLng[] poly = new LatLng[ontarioCoordinates.size()];

		for (int i = 0; i < ontarioCoordinates.size(); i++) {
			poly[i] = LatLng.newInstance(ontarioCoordinates.get(i)
					.getLatitude(), ontarioCoordinates.get(i).getLongitude());
		}

		final Polygon polygon = new Polygon(poly, null, 0, 1.0, "#000000", 0);
		LatLng apl = LatLng.newInstance(43.77510197731525, -79.13471796520389);
		map = new MapWidget(apl, 11);
		map.setSize("100%", "100%");
		map.addControl(new LargeMapControl());
		map.addOverlay(polygon);

		polygon.addPolygonMouseOverHandler(new PolygonMouseOverHandler() {

			@Override
			public void onMouseOver(PolygonMouseOverEvent event) {
				polygon.setFillStyle(PolyStyleOptions.newInstance(null, 0, 0.4));
			}

		});

		polygon.addPolygonMouseOutHandler(new PolygonMouseOutHandler() {

			@Override
			public void onMouseOut(PolygonMouseOutEvent event) {
				polygon.setFillStyle(PolyStyleOptions.newInstance(null, 0, 0));

			}

		});

		widg.add(map);

		// Adding content

		FlexTable contentFlexTable = new FlexTable();

		contentFlexTable.setWidget(0, 0, lblLogo);
		contentFlexTable.setWidget(0, 1, lblQuickLinks);
		contentFlexTable.setWidget(1, 0, lblNavMenu);
		contentFlexTable.setWidget(2, 0, widg);
		contentFlexTable.setWidget(2, 1, lblLegend);
		contentFlexTable.setWidget(3, 0, lblFooter);

		// Styling
		lblQuickLinks.setStyleName("rightAlign");
		lblLegend.setStyleName("rightAlign");
		contentFlexTable.getFlexCellFormatter().setStyleName(2, 0, "majorRow");
		contentFlexTable.getFlexCellFormatter().setColSpan(1, 0, 2);
		contentFlexTable.getFlexCellFormatter().setColSpan(3, 0, 2);

		rootPannel.add(contentFlexTable);
	}

	private void fetchDataFromServer() {
		
		 AsyncCallback<ArrayList<Coordinates>> callback = new AsyncCallback<ArrayList<Coordinates>>(){

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Error connecting to the server");
				}

				@Override
				public void onSuccess(ArrayList<Coordinates> result) {
					ontarioCoordinates = result;
					buildUi();
				}
				 
			 };
			 service.getCoordinatesByArea("Trinity-Bellwoods", callback);

	}
}
