package com.power.outage.locator.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.code.gwt.geolocation.client.Geolocation;
import com.google.code.gwt.geolocation.client.Position;
import com.google.code.gwt.geolocation.client.PositionCallback;
import com.google.code.gwt.geolocation.client.PositionError;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.maps.client.InfoWindow;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.event.PolygonClickHandler;
import com.google.gwt.maps.client.event.PolygonMouseOutHandler;
import com.google.gwt.maps.client.event.PolygonMouseOverHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.PolyStyleOptions;
import com.google.gwt.maps.client.overlay.Polygon;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.power.outage.locator.client.model.Area;
import com.power.outage.locator.client.model.Coordinates;
import com.power.outage.locator.client.model.Notes;
import com.power.outage.locator.client.model.PowerPlusConstants;
import com.power.outage.locator.client.model.PowerPlusManager;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Power_Outage_Locator implements EntryPoint {

	private PowerOutageServiceAsync powerPlusService = GWT
			.create(PowerOutageService.class);

	private MapWidget map;
	private RootPanel rootPannel;
	private HashMap<String, Polygon> polygonMap = new HashMap<String, Polygon>();
	private PowerPlusManager manager = new PowerPlusManager();
	private HTML lblInfo;
	private LatLng defaultLocation;
	private PowerPlusConstants constants = GWT.create(PowerPlusConstants.class);
	private ArrayList<String> outageNames = new ArrayList<String>();

	@Override
	public void onModuleLoad() {
		rootPannel = RootPanel.get("bodyContent");

		try {
			getAllOutageNames();
			Maps.loadMapsApi("AIzaSyC-fM_2v4Q399Yy2paZWahqFZm7HXoOBe4", "2",
					false, new Runnable() {
						public void run() {
							getLocation();
						}
					});
		} catch (Exception e) {
			Window.alert(constants.serverError());
		}
	}

	private void getAllOutageNames() {

		AsyncCallback<ArrayList<String>> callback = new AsyncCallback<ArrayList<String>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error outage names");
			}

			@Override
			public void onSuccess(ArrayList<String> result) {
				outageNames = result;
				getAllAreas();
			}
		};
		powerPlusService.getAllOutageNames(callback);
	}

	private void getLocation() {
		Geolocation geo = Geolocation.getGeolocation();
		geo.getCurrentPosition(new PositionCallback() {

			@Override
			public void onSuccess(Position position) {
				defaultLocation = LatLng.newInstance(position.getCoords()
						.getLatitude(), position.getCoords().getLongitude());
				buildUI();
			}

			@Override
			public void onFailure(PositionError error) {
				defaultLocation = LatLng.newInstance(43.77510197731525,
						-79.13471796520389);
				Window.alert("Couldn't get Location");
				buildUI();

			}
		});
	}

	private void buildUI() {

		Label lblLogo = new Label(constants.logo());
		Label lblQuickLinks = new Label(constants.quickLinks());
		Anchor hypHome = new Anchor(constants.home(), "Home.html");
		Anchor hypAdmin = new Anchor(constants.admin(), "Admin.html");
		lblInfo = new HTML("");
		Label lblFooter = new Label(constants.disclaimer());

		SimplePanel widg = new SimplePanel();

		widg.setSize("100%", "100%");

		Marker myMarker = new Marker(defaultLocation);

		map = new MapWidget(defaultLocation, 11);
		map.setSize("100%", "100%");
		map.addControl(new LargeMapControl());
		map.addOverlay(myMarker);
		widg.add(map);

		FlexTable contentFlexTable = new FlexTable();
		FlowPanel navigationPannel = new FlowPanel();
		navigationPannel.add(hypHome);
		navigationPannel.add(hypAdmin);

		contentFlexTable.setWidget(0, 0, lblLogo);
		contentFlexTable.setWidget(0, 2, lblQuickLinks);
		contentFlexTable.setWidget(1, 0, navigationPannel);
		contentFlexTable.setWidget(1, 2, null);
		contentFlexTable.setWidget(2, 0, null);
		contentFlexTable.setWidget(3, 0, widg);
		contentFlexTable.setWidget(3, 1, lblInfo);
		contentFlexTable.setWidget(6, 0, lblFooter);

		// Adding Twitter
		String s = "<a class=\"twitter-timeline\" href=\"https://twitter.com/PowerPlusGTA/powerpluslist\" data-widget-id=\"430374450886754304\">Tweets from https://twitter.com/PowerPlusGTA/powerpluslist</a>";
		HTML h = new HTML(s);
		contentFlexTable.setWidget(5, 0, h);

		Document doc = Document.get();
		ScriptElement script = doc.createScriptElement();
		script.setSrc("http://platform.twitter.com/widgets.js");
		script.setType("text/javascript");
		script.setLang("javascript");
		doc.getBody().appendChild(script);

		// Styling
		// lblQuickLinks.setStyleName("rightAlign");
		contentFlexTable.getRowFormatter().setStyleName(0, "logoRow");
		contentFlexTable.getFlexCellFormatter().setStyleName(3, 0, "majorRow");
		contentFlexTable.getFlexCellFormatter().setStyleName(3, 1,
				"information");
		contentFlexTable.getFlexCellFormatter().setRowSpan(3, 0, 3);
		contentFlexTable.getFlexCellFormatter().setColSpan(3, 0, 2);
		contentFlexTable.getRowFormatter().setStyleName(1, "topnav");
		contentFlexTable.setCellSpacing(0);
		contentFlexTable.getRowFormatter().setStyleName(2, "borderRow");
		contentFlexTable.getFlexCellFormatter().setColSpan(2, 0, 3);

		rootPannel.add(contentFlexTable);
		lblInfo.setWordWrap(true);
	}

	private void getAllAreas() {

		AsyncCallback<ArrayList<Area>> callback = new AsyncCallback<ArrayList<Area>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error getting region coordinates from the server");
			}

			@Override
			public void onSuccess(ArrayList<Area> result) {
				manager.setAreaList(result);
				ArrayList<Coordinates> coordinates = new ArrayList<Coordinates>();
				for (Area area : result) {
					coordinates = area.getCoordinateList();
					LatLng[] poly = new LatLng[coordinates.size()];

					for (int i = 0; i < coordinates.size(); i++) {
						poly[i] = LatLng.newInstance(coordinates.get(i)
								.getLatitude(), coordinates.get(i)
								.getLongitude());
					}
					if (outageNames.contains(area.getAreaName())) {
						polygonMap.put(area.getAreaName(), new Polygon(poly,
								"#000000", 1, 0.3, "#FF0000", 0.3));
					} else {
						polygonMap.put(area.getAreaName(), new Polygon(poly,
								"#000000", 1, 0.3, "#000000", 0));
					}
				}
				refreshMap();
			}
		};
		powerPlusService.getAllAreasWithCoordinates(callback);
	}

	private void refreshMap() {

		final InfoWindow infoWindow = map.getInfoWindow();

		for (final String key : polygonMap.keySet()) {
			map.addOverlay(polygonMap.get(key));

			polygonMap.get(key).addPolygonMouseOverHandler(
					new PolygonMouseOverHandler() {

						@Override
						public void onMouseOver(PolygonMouseOverEvent event) {
							polygonMap.get(key).setFillStyle(
									PolyStyleOptions.newInstance(null, 0, 0.4));
							infoWindow
									.open(polygonMap.get(key).getVertex(2),
											new InfoWindowContent(constants
													.moreInfo()));
						}

					});

			polygonMap.get(key).addPolygonMouseOutHandler(
					new PolygonMouseOutHandler() {

						@Override
						public void onMouseOut(PolygonMouseOutEvent event) {
							if (!outageNames.contains(key)) {
								polygonMap.get(key).setFillStyle(
										PolyStyleOptions
												.newInstance(null, 0, 0));
							}
							infoWindow.close();

						}
					});

			polygonMap.get(key).addPolygonClickHandler(
					new PolygonClickHandler() {

						@Override
						public void onClick(PolygonClickEvent event) {
							lblInfo.setHTML("<strong>" + key
									+ "</strong><br />");

							AsyncCallback<ArrayList<Notes>> callback = new AsyncCallback<ArrayList<Notes>>() {

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("Error getting nots from the server");
								}

								@Override
								public void onSuccess(ArrayList<Notes> result) {
									for (Notes notes : result) {
										lblInfo.setHTML(lblInfo.getHTML()
												+ notes.getNotes() + "<br />");
									}
								}
							};
							powerPlusService.getNotesByAreaName(key, callback);
						}

					});
		}
	}
}
