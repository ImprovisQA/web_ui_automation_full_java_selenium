package com.helpsystems.intermapperweb.uitest.utils;

import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.time.LocalDate;
import java.time.LocalTime;
import com.helpsystems.intermapperweb.uitest.validators.InterMapperWebBaseValidator;

public final class UiDataConstants {
	private UiDataConstants() {
	}

	// ################################ GENERAL ################################
	public static final String INTERMAPPER_SERVER = "InterMapper Server";
	public static final String MAP_LIST = "Map List";
	public static final String INTERMAPPER_MAPS = "InterMapper Maps ";
	public static final String INTERMAPPER_MAPS_SUB_TEXT = "The following maps are available on this server. Hover over the map name to see a preview image.";
	public static final String DEMO_MAIN_MAP = "Demo Main Map";
	public static final String DEMO_CO_OP_WIRELESS = "Demo Co-op Wireless";
	public static final String DEMO_NETWORK_OPERATIONS = "Demo Network Operations";
	public static final String DEMO_OFFICE_LOCATIONS = "Demo Office Location";
	public static final String ERRORS_PAGE_NAME = "Errors";
	public static final String FULL_PAGE_NAME = "Full";
	public static final String OUTAGES_PAGE_NAME = "Outages";
	public static final String SUPPORT_EMAIL_HS = "support.intermapper@helpsystems.com";
	public static final String SUPPORT_EMAIL_IM = "support@intermapper.com";

	// ################################ INTERVAL DROPDOWN ITEMS
	// ################################

	public static final String NEVER = "Never";
	public static final String SECONDS5 = "5 Seconds";
	public static final String SECONDS10 = "10 Seconds";
	public static final String SECONDS30 = "30 Seconds";
	public static final String MINUTE1 = "1 Minute";
	public static final String MINUTES2 = "2 Minutes";
	public static final String MINUTES5 = "5 Minutes";
	public static final String MINUTES10 = "10 Minutes";
	public static final String MINUTES30 = "30 Minutes";
	public static final String HOUR1 = "1 Hour";

	// ################################ FOOTER ITEMS
	// ############################################

	public static final String INTERMAPPER_COM = "\"http://www.intermapper.com\"";
	public static final String HELPSYSTEMS_COM = "\"http://www.helpsystems.com\"";
	public static final String HELPSYSTEMS_LLC = "© HelpSystems LLC.";
	public static final String IS = "is";
	public static final String LAST_REFRESHED_ON = "Last refreshed on ";

	// ################################ DATE TIME
	// ############################################

	public static final DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	public static final String CURRENT_WEEK_DAY = LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.SHORT,
			Locale.ENGLISH);
	public static final String CURRENT_DATE = df.format(LocalDate.now());
	public static final String CURRENT_TIME = DateTimeFormatter.ofPattern("h:mm a").format(LocalTime.now());
	public static final String CURRENT_TZ_OFFSET = InterMapperWebBaseValidator.getTZOffset();

	// ################################ ERRORS ################################
	public static final String INTERMAPPER_SERVER_LABEL_ERRORS = "Errors";
	public static final String INTERMAPPER_DEVICES_IN_ERROR = "InterMapper Devices in Error";
	public static final String DEVICES_AND_LINKS_WITH_ERRORS = "Devices and Links with Errors";
	public static final String ERRORS_LABELS = "Device                           Stat    SysUpTime    Avail  Loss    RTT  Probe  Address";
	public static final String LINKS_LABELS = "Link                             Prt Stat               TPkt    TBytes TErr TDis    RPkt    RBytes RErr RDis  Util  Segment";

	// ################################ FULL ################################
	public static final String INTERMAPPER_SERVER_LABEL_DEVICES_AND_LINKS = "Devices and Links";
	public static final String INTERMAPPER_FULL_DEVICE_LIST = "InterMapper Full Device List";
	public static final String FULL_LIST_OF_DEVICES_AND_LINKS = "Full list of Devices and Links";

	// ################################ OUTAGES ################################
	public static final String INTERMAPPER_SERVER_LABEL_OUTAGES = "InterMapper Server";
	public static final String INTERMAPPER_SERVER_OUTAGES = "Outages";
	public static final String CURRENT_OUTAGES = "Current Outages";
	public static final String OUTAGES_DATE = "Date";
	public static final String OUTAGES_TIME = "Time";
	public static final String OUTAGES_DEVICE = "Device";
	public static final String OUTAGES_DURATION = "Duration";
	public static final String OUTAGES_JUMP_TO = "Jump To";
	public static final String CURRENT_OUTAGES_LINK = "Current Outages";
	public static final String PREVIOUS_OUTAGES_LINK = "Previous Outages";

	// ################################ DEVICE LIST #############################
	public static final String DEVICE_LIST = "Device List";
	public static final String ALL_DEVICES = "All Devices";
	public static final String STATUS = "Status";
	public static final String NAME = "Name";
	public static final String CONDITION = "Condition";
	public static final String DATE = "Date";
	public static final String TIME = "Time";
	public static final String PROBE = "Probe";
	public static final String PORT = "Port";
	public static final String OFFICE_LOCATION = "Office Location  ";
	public static final String CO_OP_WIRELESS = "  Co-op Wireless";
	public static final String DEMO_NOTEBOOK = "Demo Notebook";
	public static final String DEMO_WINDOWS = "Demo Windows";
	public static final String DEMO_PRINTER = "Demo Printer";
	public static final String DEMO_VOIP_PHONES = "Demo VOIP Phones";
	public static final String DEMO_NAS = "Demo NAS";
	public static final String DEMO_192_168_11_109 = "192.168.11.109";
	public static final String DEMO_MAC = "Demo Mac";
	public static final String DEMO_DEVICE = "Demo Device";
	public static final String ROUTER = "Router/";
	public static final String DEMO_192_168_11_222 = "192.168.11.222";
	public static final String DEMO_192_168_11_221 = "192.168.11.221";
	public static final String DEMO_192_168_11_223 = "192.168.11.223";
	public static final String DEMO_WAP_4 = "Demo WAP 4";
	public static final String DEMO_WAP_2 = "Demo WAP 2";
	public static final String DEMO_192_168_11_20 = "192.168.11.20";
	public static final String BACKBONE = "Backbone";
	public static final String DEMO_192_168_11_103 = "192.168.11.103";
	public static final String INTERMAPPER = "InterMapper";
	public static final String DEMO_192_168_11_101 = "192.168.11.101";
	public static final String DEMO_192_168_11_220 = "192.168.11.220";
	public static final String DEMO_192_168_11_102 = "192.168.11.102";
	public static final String HTTPS = "HTTPS";
	public static final String SNMP = "SNMP";
	public static final String HTTP = "HTTP";
	public static final String POP3 = "POP3";
	public static final String HTTP_HTTPS = "HTTP, HTTPS ";
	public static final String CO_OP = "Co-op";
	public static final String SMTP = "SMTP";
	public static final String DEMO_SECURITY_SYSTEM = "Demo Security System";
	public static final String DEMO_WAP_3 = "Demo WAP 3";
	public static final String DEMO_WAP_1 = "Demo WAP 1";
	public static final String DEMO_MACOS = "Demo MacOS";
	public static final String DEMO_LINUX_SERVER = "Demo Linux Server";
	public static final String CORPORATE_PRIMARY_ROUTER = "  Corporate Primary Router";
	public static final String CLIENT_SERVICES_STATUS_UP = "  Client Services Status: UP";
	public static final String NETWORK_OPERATIONS = "Network Operations";
	public static final String CONNECTION_TO = "  Connection to";
	public static final String DEMO_ROUTER = "Demo Router";
	public static final String PORT_8181 = "8181";
	public static final String PORT_23 = "23";
	public static final String PORT_NA = "N/A";

	// ################################ STATISTICS #############################

	public static final String STATISTICS = "Statistics";
	public static final String STATS = "Stats";
	public static final String INTERMAPPER_STATISTICS = "InterMapper Statistics";
	public static final String SYSTEM_NETWORK_CONFIGURATION = "System & Network Configuration";
	public static final String INTERMAPPER_RUNNING_TIME = "InterMapper Running Time:";
	public static final String WINDOWS_RUNNING_TIME = "Windows Running Time:";
	public static final String WINDOWS_VERSION = "Windows Version:";
	public static final String NET_SOFTWARE_VERSION = "Net Software Version:";
	public static final String NETWORK_INTERFACES = "Network Interfaces";
	public static final String INTERFACES = "Interface";

	// ################################ HELP DROPDOWN #############################

	public static final String HELP = "Help ";
	public static final String TELNET = "Telnet";
	public static final String USER_GUIDE = "User Guide";
	public static final String DEVELOPER_GUIDE = "Developer Guide";

	// ################################ ABOUT INTERMAPPER WEB CLIENT
	// #############################

	public static final String USER_GUIDE_LINK = "\"http://www.intermapper.com/go.php?to=intermapper.userguide\"";
	public static final String DEVELOPER_GUIDE_LINK = "\"http://www.intermapper.com/go.php?to=intermapper.devguide\"";

	// ################################ ABOUT INTERMAPPER WEB CLIENT
	// #############################

	public static final String ABOUT_INTERMAPPER_WEB_CLIENT = "About InterMapper Web Client";
	public static final String ABOUT = "About";
	public static final String INTERMAPPER_VERSION = "InterMapper Version " + InterMapperWebConstants.IM_VERSION + " ";
	public static final String SOFTWARE_TYPE = "Network Monitoring and Alerting Software";
	public static final String REGISTRATION_INFO = "Registration Information";
	public static final String BUILD_MACHINE_INFO = ".Version 6.6d1 (i386/Windows/64-bit, Build 111111) ";
	public static final String BUILD_DATE = "Built on Nov 15 2021.";
	public static final String LATEST_NEWS = "For the latest news, visit the ";
	public static final String EMAIL_TO = ".  For feedback and technical support, send e-mail to ";
	public static final String LOGGED_IN = "Logged in as:";
	public static final String IM_WEB_PAGE = "Intermapper Web Page";
	public static final String REGISTERED_TO = "Registered to: HelpSystems License Key";

	// ################################ LOGOUT #############################

	public static final String LOGOUT = "Logout";
	public static final String LOGOUT_TAB_NAME = "InterMapper Server";
	public static final String YOU_HAVE_BEEN_LOGGED_OUT = "You have been logged out.  Close the browser tab to prevent unauthorized viewing.";
	public static final String LOGIN_AS_ADMIN_USER = "Log in as admin user";
	public static final String LOGIN_AS_GUEST_USER = "Log in as guest user";

	// ################################ View Files #############################

	public static final String VIEW_FILES = "View Files";

	// ################################ VIEW FILES #############################
	public static final String FILE_VIEWER = "File Viewer";
	public static final String FILE_VIEWER_HEADER = "File Viewer for InterMapper";
	public static final String VIEW_FILES_PAGE_INFO = "This page lists the files that are available for export from InterMapper.\r\n"
			+ "    To see a file, simply click its link. For more information,\r\n" + "    please contact ";
	public static final String FILE_PATH = "File Path: ";
	public static final String FILES = "~files";
	public static final String ICONS = "icons";
	public static final String SOUNDS = "sounds";
	public static final String MIBS = "mibs";
	public static final String PROBES = "probes";
	public static final String TOOLS = "tools";
	public static final String WEBPAGES = "webpages";
	public static final String FONTS = "fonts";
	public static final String EXTENSIONS = "extensions";
	public static final String MAPS = "maps";
	public static final String TRANSLATIONS = "translations";
	public static final String FOLDER = "Folder: ";

	// ################################ LOGOUT #############################

	public static final String LOGIN = "Login";

	// ################################ TELNET #############################

	public static final String NO_TELNET_ACCESS = "No Telnet Access";
}