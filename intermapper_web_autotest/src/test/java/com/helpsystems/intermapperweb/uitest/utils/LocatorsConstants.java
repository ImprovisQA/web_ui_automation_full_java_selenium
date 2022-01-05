package com.helpsystems.intermapperweb.uitest.utils;

import static com.helpsystems.intermapperweb.uitest.utils.UiDataConstants.*;

public final class LocatorsConstants {

	private LocatorsConstants() {
	}

	// ################################ GENERAL #################################
	public static final String INTERMAPPER_SERVER_LABEL_XPATH = "//li[text()='" + INTERMAPPER_SERVER + "']";
	public static final String INTERMAPPER_MAPS_XPATH = "//h1[text()='" + INTERMAPPER_MAPS + "']";
	public static final String INTERMAPPER_MAPS_SUB_TEXT_XPATH = "//small[text()='" + INTERMAPPER_MAPS_SUB_TEXT + "']";
	public static final String DEMO_MAIN_MAP_XPATH = "//a[text()='" + DEMO_MAIN_MAP + "']";
	public static final String DEMO_CO_OP_WIRELESS_XPATH = "//a[text()='" + DEMO_CO_OP_WIRELESS + "']";
	public static final String DEMO_NETWORK_OPERATIONS_XPATH = "//a[text()='" + DEMO_NETWORK_OPERATIONS + "']";
	public static final String DEMO_OFFICE_LOCATIONS_XPATH = "//a[text()='" + DEMO_OFFICE_LOCATIONS + "']";
	public static final String PAGE_NAME_XPATH = "//a[text()='%s']";
	public static final String PAGE_HEADER_XPATH = "//h1[text()='%s']";
	public static final String ALL_DEVICES_XPATH = "//li[text()='" + ALL_DEVICES + "']";
	public static final String INTERMAPPER_SERVER_LINK_XPATH = "//li/a[text()='" + INTERMAPPER_SERVER + "']";
	public static final String INTERMAPPER_ERRORS_PAGE_TASKBAR_XPATH = "//div/pre[text()='" + ERRORS_PAGE_NAME + "']";

	// ################################ Device List
	// #################################
	public static final String DEVICE_LIST_XPATH = "//div/h1[text()='" + DEVICE_LIST + "']";
	public static final String INTERVAL_DROPDOWN_ITEMS_XPATH = "//select/option[text()='%s']";
	public static final String FOOTER_TEXT_XPATH = "//span[contains(text()[2],'" + IS + "')]";
	public static final String FOOTER_LINK_ITEMS_XPATH = "//span/a[@href=%s and text()='%s']";
	public static final String LAST_REFRESHED_XPATH = "//span/form[contains(text()[1],'" + LAST_REFRESHED_ON + ""
			+ CURRENT_WEEK_DAY + ", " + CURRENT_DATE + " at " + CURRENT_TIME + " " + CURRENT_TZ_OFFSET
			+ " \u00A0\u00A0\u00A0 Interval:')]";

	// ################################ Device List
	// #################################
	public static final String DEVICE_LIST_COLUMNS_XPATH = "//tr/th[text()='%s']";
	public static final String DEVICE_NAME_XPATH = "//td/a[text()='%s']";
	public static final String DEVICE_PORT_XPATH = "//td/a[text()='%s']/../..//td[text()='%s']";

	// ################################ ERRORS #################################
	public static final String INTERMAPPER_SERVER_ERRORS_LABEL_ERRORS_XPATH = "//li/a[text()='" + INTERMAPPER_SERVER
			+ "']";
	public static final String INTERMAPPER_SERVER_ERRORS_LABEL_PANEL_ERRORS_XPATH = "//ol/li[text()='"
			+ INTERMAPPER_SERVER_LABEL_ERRORS + "']";
	public static final String INTERMAPPER_SERVER_ERRORS_DEVICES_AND_LINKS_W_ERRORS_XPATH = "//div/h1[text()='"
			+ DEVICES_AND_LINKS_WITH_ERRORS + "']";
	public static final String INTERMAPPER_SERVER_ERRORS_DEVICE_LIST_XPATH = "//h1[(text()='"
			+ DEVICES_AND_LINKS_WITH_ERRORS + "')]";
	public static final String INTERMAPPER_ERRORS_LABELS_XPATH = "//pre[contains(text(),'" + ERRORS_LABELS + "')]";
	public static final String INTERMAPPER_LINKS_LABELS_XPATH = "//pre[contains(text()[5],'" + LINKS_LABELS + "')]";

	// ################################ FULL #################################
	public static final String INTERMAPPER_SERVER_FULL_LABEL_XPATH = "//li/a[text()='" + INTERMAPPER_SERVER + "']";
	public static final String INTERMAPPER_SERVER_FULL_DEVICES_AND_LINKS_FULL_LABEL_XPATH = "//ol/li[text()='"
			+ INTERMAPPER_SERVER_LABEL_DEVICES_AND_LINKS + "']";
	public static final String INTERMAPPER_SERVER_FULL_LIST_OF_DEVICES_AND_LINKS_LABEL_XPATH = "//div/h1[text()='"
			+ FULL_LIST_OF_DEVICES_AND_LINKS + "']";
	public static final String INTERMAPPER_FULL_LINKS_LABELS_XPATH = "//pre[contains(text()[49],'" + LINKS_LABELS
			+ "')]";

	// ################################ OUTAGES #################################
	public static final String INTERMAPPER_SERVER_OUTAGES_LABEL_XPATH = "//li/a[text()='" + INTERMAPPER_SERVER + "']";
	public static final String INTERMAPPER_SERVER_OUTAGES_XPATH = "//ol/li[text()='" + INTERMAPPER_SERVER_OUTAGES
			+ "']";
	public static final String INTERMAPPER_SERVER_OUTAGES_CURRENT_OUTAGES_XPATH = "//div/h1[text()='" + CURRENT_OUTAGES
			+ "']";
	public static final String INTERMAPPER_SERVER_OUTAGES_DATE_COLUMN_XPATH = "//tr/th[text()='" + OUTAGES_DATE + "']";
	public static final String INTERMAPPER_SERVER_OUTAGES_TIME_COLUMN_XPATH = "//tr/th[text()='" + OUTAGES_TIME + "']";
	public static final String INTERMAPPER_SERVER_OUTAGES_DEVICE_COLUMN_XPATH = "//tr/th[text()='" + OUTAGES_DEVICE
			+ "']";
	public static final String INTERMAPPER_SERVER_OUTAGES_DURATION_COLUMN_XPATH = "//tr/th[text()='" + OUTAGES_DURATION
			+ "']";
	public static final String INTERMAPPER_SERVER_OUTAGES_JUMP_TO_XPATH = "//div/div[text()='" + OUTAGES_JUMP_TO + "']";
	public static final String INTERMAPPER_SERVER_OUTAGES_CURRENT_OUTAGES_OPTION_XPATH = "//li/a[text()='"
			+ CURRENT_OUTAGES_LINK + "']";
	public static final String INTERMAPPER_SERVER_OUTAGES_PREVIOUS_OUTAGES_XPATH = "//li/a[text()='"
			+ PREVIOUS_OUTAGES_LINK + "']";

	// ################################ STATISTICS #################################

	public static final String STATISTICS_XPATH = "//ol/li[text()='" + STATISTICS + "']";
	public static final String INTERMAPPER_STATISTICS_XPATH = "//div/h1[text()='" + INTERMAPPER_STATISTICS + "']";
	public static final String CONFIGURATION_XPATH = "//pre/b[text()='" + SYSTEM_NETWORK_CONFIGURATION + "']";
	public static final String INTERMAPPER_RUNNING_TIME_XPATH = "//pre/font[text()='" + INTERMAPPER_RUNNING_TIME + "']";
	public static final String WINDOWS_RUNNING_TIME_XPATH = "//pre/font[text()='" + WINDOWS_RUNNING_TIME + "']";
	public static final String WINDOWS_VERSION_XPATH = "//pre/font[text()='" + WINDOWS_VERSION + "']";
	public static final String NET_SOFTWARE_VERSION_XPATH = "//pre/font[text()='" + NET_SOFTWARE_VERSION + "']";
	public static final String NETWORK_INTERFACES_XPATH = "//pre/font[text()='" + NETWORK_INTERFACES + "']";
	public static final String INTERFACES_XPATH = "//pre/font[text()='" + INTERFACES + "']";

	// ################################ HELP DROPDOWN
	// #################################

	public static final String HELP_DROPDOWN_ITEMS_XPATH = "//li/a[text()='%s']";
	public static final String HELP_DROPDOWN_ITEMS_LINKS_XPATH = "//li/a[@href=%s and text()='%s']";

	// ################################ LOGOUT #################################

	public static final String INTERMAPPER_SERVER_LOGOUT_TEXT_XPATH = "//li/a[text()='" + LOGOUT + "']";
	public static final String INTERMAPPER_SERVER_LOGOUT_LOGGED_OUT_XPATH = "//div/h1[text()='"
			+ YOU_HAVE_BEEN_LOGGED_OUT + "']";
	public static final String INTERMAPPER_SERVER_LOGOUT_LOGIN_AS_ADMIN_USER_XPATH = "//h1/a[@href='/~admin/!index.html' and text()='Log in as admin user']";
	public static final String INTERMAPPER_SERVER_LOGOUT_LOGIN_AS_GUEST_USER_XPATH = "//h1/a[@href='/' and text()='Log in as guest user']";

	// ################################ ABOUT #################################

	public static final String ABOUT_XPATH = "//li[text()='" + ABOUT + "']";
	public static final String DETAILS_IN_BOLD_XPATH = "//b[text()='%s']";
	public static final String INFO_ABOUT_INTEREMAPPER_XPATH = "//p[text()='%s']";
	public static final String INFO_ABOUT_LOGIN_XPATH = "//p[contains(text(),'%s')]";
	public static final String IM_WEB_PAGE_XPATH = "//a[text()= '" + IM_WEB_PAGE + "']";
	public static final String SUPPORT_EMAIL_XPATH = "//a[text()= '%s']";
	public static final String COLORED_INFO_XPATH = "//font[text()='%s']";

	// ################################ View Files #############################
	public static final String FILE_VIEWER_XPATH = "//li[text()='" + FILE_VIEWER + "']";
	public static final String FILE_VIEWER_HEADER_XPATH = "//h1[text()='" + FILE_VIEWER_HEADER + "']";
	public static final String VIEW_FILES_PAGE_INFO_XPATH = "//p[text()='" + VIEW_FILES_PAGE_INFO + "']";
	public static final String FOLDER_NAMES_XPATH = "//a[text()='%s']";
	public static final String FOLDER_XPATH = "//pre[text()='" + FOLDER + "']";

}