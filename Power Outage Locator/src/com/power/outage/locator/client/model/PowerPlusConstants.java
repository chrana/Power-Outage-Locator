package com.power.outage.locator.client.model;

import com.google.gwt.i18n.client.Constants;

public interface PowerPlusConstants extends Constants {
	@DefaultStringValue("LOGO")
	String logo();

	@DefaultStringValue("Quick Links")
	String quickLinks();

	@DefaultStringValue("Home")
	String home();

	@DefaultStringValue("Administrator")
	String admin();

	@DefaultStringValue("The data on the map is just a prototype. It does not show real data")
	String disclaimer();

	@DefaultStringValue("Error connecting to the server")
	String serverError();

	@DefaultStringValue("Click For More Info")
	String moreInfo();

}
