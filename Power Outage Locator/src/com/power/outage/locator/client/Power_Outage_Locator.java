package com.power.outage.locator.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.GoogleMap;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Power_Outage_Locator implements EntryPoint {

	
	public void onModuleLoad() {
		
		RootPanel rootPannel = RootPanel.get("bodyContent");
		rootPannel.setStyleName((String) null);
		
		Label lblLogo = new Label("LOGO");
		Label lblQuickLinks = new Label("QuickLinks");
		Label lblNavMenu = new Label("Navigation Menu");
		Label lblLegend = new Label("Legends and Information");
		Label lblFooter = new Label("Footer (If NEEDED)");
		
		//Google Map
		MapOptions options  = MapOptions.create() ;

	    options.setCenter(LatLng.create( 43.7816, -79.2346 ));   
	    options.setZoom( 10 ) ;
	    options.setMapTypeId( MapTypeId.ROADMAP );
	    options.setDraggable(true);
	    options.setMapTypeControl(true);
	    options.setScaleControl(true) ;
	    options.setScrollwheel(true) ;

	    SimplePanel widg = new SimplePanel() ;

	    widg.setSize("100%","100%");

	    GoogleMap theMap = GoogleMap.create( widg.getElement(), options ) ;
		
		FlexTable contentFlexTable = new FlexTable();
		
		contentFlexTable.setWidget(0, 0, lblLogo);
		contentFlexTable.setWidget(0, 1, lblQuickLinks);
		contentFlexTable.setWidget(1, 0, lblNavMenu);
		contentFlexTable.setWidget(2, 0, widg);
		contentFlexTable.setWidget(2, 1, lblLegend);
		contentFlexTable.setWidget(3, 0, lblFooter);
		
		
		//Styling
		lblQuickLinks.setStyleName("rightAlign");
		lblLegend.setStyleName("rightAlign");
		contentFlexTable.getFlexCellFormatter().setStyleName(2, 0, "majorRow");
		contentFlexTable.getFlexCellFormatter().setColSpan(1, 0, 2);
		contentFlexTable.getFlexCellFormatter().setColSpan(3, 0, 2);
		
		
		rootPannel.add(contentFlexTable);
	}
}
