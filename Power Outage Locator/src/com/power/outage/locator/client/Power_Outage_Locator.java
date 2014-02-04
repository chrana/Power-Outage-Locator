package com.power.outage.locator.client;

import java.util.ArrayList;
import java.util.HashMap;

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
import com.google.gwt.maps.client.overlay.PolyStyleOptions;
import com.google.gwt.maps.client.overlay.Polygon;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.power.outage.locator.client.model.Area;
import com.power.outage.locator.client.model.Coordinates;
import com.power.outage.locator.client.model.Notes;
import com.power.outage.locator.client.model.PowerPlusManager;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Power_Outage_Locator implements EntryPoint {

	PowerOutageServiceAsync powerPlusService = GWT
			.create(PowerOutageService.class);

	MapWidget map = null;
	HashMap<String, Polygon> polygonMap = new HashMap<String, Polygon>();
	PowerPlusManager manager = new PowerPlusManager();
	HTML lblLegend;

	/**
	 * @wbp.parser.entryPoint
	 */
	public void onModuleLoad() {

		getAllAreas();
		// Map Key
		Maps.loadMapsApi("AIzaSyC-fM_2v4Q399Yy2paZWahqFZm7HXoOBe4", "2", false,
				new Runnable() {
					public void run() {
						buildUi();
					}
				});
	}

	private void buildUi() {

		RootPanel rootPannel = RootPanel.get("bodyContent");
		rootPannel.clear();
		rootPannel.setStyleName((String) null);

		Label lblLogo = new Label("LOGO");
		Label lblQuickLinks = new Label("QuickLinks");
		Label lblNavMenu = new Label("Navigation Menu");
		lblLegend = new HTML("Legends and Information");
		Label lblFooter = new Label("Footer (If NEEDED)");

		SimplePanel widg = new SimplePanel();

		widg.setSize("100%", "100%");

		LatLng apl = LatLng.newInstance(43.77510197731525, -79.13471796520389);
		map = new MapWidget(apl, 11);
		map.setSize("100%", "100%");
		map.addControl(new LargeMapControl());
		widg.add(map);

		FlexTable contentFlexTable = new FlexTable();

		contentFlexTable.setWidget(0, 0, lblLogo);
		contentFlexTable.setWidget(0, 1, lblQuickLinks);
		contentFlexTable.setWidget(1, 0, lblNavMenu);
		contentFlexTable.setWidget(2, 0, widg);
		contentFlexTable.setWidget(2, 1, lblLegend);
		contentFlexTable.setWidget(4, 0, lblFooter);

		// Adding Twitter
		String s = "<a class=\"twitter-timeline\" href=\"https://twitter.com/PowerPlusGTA/powerpluslist\" data-widget-id=\"430374450886754304\">Tweets from https://twitter.com/PowerPlusGTA/powerpluslist</a>";
		HTML h = new HTML(s);
		contentFlexTable.setWidget(3, 0, h);

		Document doc = Document.get();
		ScriptElement script = doc.createScriptElement();
		script.setSrc("http://platform.twitter.com/widgets.js");
		script.setType("text/javascript");
		script.setLang("javascript");
		doc.getBody().appendChild(script);

		// Styling
		lblQuickLinks.setStyleName("rightAlign");
		contentFlexTable.getFlexCellFormatter().setStyleName(2, 0, "majorRow");
		contentFlexTable.getFlexCellFormatter().setStyleName(2, 1,
				"information");
		contentFlexTable.getFlexCellFormatter().setColSpan(1, 0, 2);
		contentFlexTable.getFlexCellFormatter().setRowSpan(2, 0, 2);

		rootPannel.add(contentFlexTable);
		lblLegend.setWordWrap(true);
	}

	private void getAllAreas() {

		AsyncCallback<ArrayList<Area>> callback = new AsyncCallback<ArrayList<Area>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error connecting to the server\n"
						+ caught.toString());
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
					polygonMap.put(area.getAreaName(), new Polygon(poly,
							"#000000", 1, 1.0, "#000000", 0));
				}
				refreshMap();
			}
		};
		powerPlusService.getAllAreas(callback);
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
											new InfoWindowContent(
													"Click For More Info"));
						}

					});

			polygonMap.get(key).addPolygonMouseOutHandler(
					new PolygonMouseOutHandler() {

						@Override
						public void onMouseOut(PolygonMouseOutEvent event) {
							polygonMap.get(key).setFillStyle(
									PolyStyleOptions.newInstance(null, 0, 0));
							infoWindow.close();

						}

					});

			polygonMap.get(key).addPolygonClickHandler(
					new PolygonClickHandler() {

						@Override
						public void onClick(PolygonClickEvent event) {
							lblLegend.setHTML("<strong>" + key
									+ "</strong><br />");

							AsyncCallback<ArrayList<Notes>> callback = new AsyncCallback<ArrayList<Notes>>() {

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("Error connecting to the server"
											+ caught.toString());
								}

								@Override
								public void onSuccess(ArrayList<Notes> result) {
									for (Notes notes : result) {
										lblLegend.setHTML(lblLegend.getHTML()
												+ notes.getNotes() + "<br />");
									}

								}
							};
							powerPlusService.getNotesByAreaName(key, callback);
							;

						}

					});
		}
	}
}
