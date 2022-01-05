package com.helpsystems.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public final class Constants {

	private static final String BROWSER_PARAM = "browser";
	private static final String PROPERTIES_FILE_NAME = "test.properties";
	private static final String HOST_PARAM = "host";
	private static final String PORT_PARAM = "port";
	private static final String OBJECT_SELECTOR_PARAM = "object.selector";
	private static final String OBJECT_TEXT_PARAM = "object.text";
	private static final String LOAD_DELAY_PARAM = "load.delay";
	private static final String INDEX_PAGE_URL_PARAM = "index.page.url";
	private static final String BROWSER_WIDTH_PERCENT_PARAM = "browser.width.percent";
	private static final String BROWSER_HEIGHT_PERCENT_PARAM = "browser.height.percent";
	private static final String DEFAULT_TIMEOUT_FOR_WAIT_PARAM = "default.timeout.for.wait";
	private static final String HIDE_FIREFOX_PARAM = "hide.browser";
	private final static String EMAIL_USER_PARAM = "test.email.user";
	private final static String EMAIL_PASS_PARAM = "test.email.pass";
	private final static String EMAIL_TEMP_DIR_PARAM = "email.temp.dir";
	private static final String PROXY_ENABLED_PARAM = "proxy.enabled";
	private static final String PROXY_ADDRESS_PARAM = "proxy.address";
	
	private final static String TESTRAIL_USERNAME_PARAM = "testrail.username";
	private final static String TESTRAIL_PASSWORD_PARAM = "testrail.password";
	private final static String TESTRAIL_RUN_ID_PARAM = "testrail.run.id";
	
	private final static String SAUCE_PLATFORM_PARAM = "sauce.platform";
	private final static String SAUCE_BROWSER_PARAM = "sauce.browser";
	private final static String SAUCE_BROWSER_VERSION_PARAM = "sauce.browser.version";
	private final static String SAUCE_USERNAME_PARAM = "sauce.username";
	private final static String SAUCE_ACCESS_KEY_PARAM = "sauce.access.key";
	
	private final static String GRID_NODE_BROWSER_PARAM = "grid.node.browser";
	private final static String GRID_NODE_BROWSER_VERSION_PARAM = "grid.node.browser.version";
	private final static String GRID_NODE_PLATFORM_PARAM = "grid.node.platform";
	private final static String GRID_NODE_FF_BINARY_PARAM = "grid.node.ff.binary";
	private final static String GRID_HUB_URL_PARAM = "grid.hub.url";

	private final static String REGRESSION_DB_ADDRESS_PARAM = "regression.db.address";
	private final static String REGRESSION_DB_USERNAME_PARAM = "regression.db.username";
	private final static String REGRESSION_DB_PASSWORD_PARAM = "regression.db.password";
	private final static String REGRESSION_RUN_TYPE_PARAM = "regression.run.type";
	private final static String DEFAULT_DOWNLOAD_PATH_PARAM = "default.download.path";
	private final static String HTTP_SECURE_CHECK_PARAM = "http.secure.check";
	private final static String IMAGE_MAGICK_BIN_PATH_PARAM = "image.magic.path";
	
	
	public  static String EMAIL_USER = null;
	public  static String EMAIL_PASS = null;
	public  static String EMAIL_TEMP_DR=null ;

	public  static String TESTRAIL_USERNAME;
	public  static String TESTRAIL_PASSWORD;
	public  static String TESTRAIL_RUN_ID;
	
	public  static String SAUCE_PLATFORM;
	public  static String SAUCE_BROWSER;
	public  static String SAUCE_BROWSER_VERSION;
	public  static String SAUCE_USERNAME;
	public  static String SAUCE_ACCESS_KEY;
	
	public  static String GRID_NODE_BROWSER;
	public  static String GRID_NODE_BROWSER_VERSION;
	public  static String GRID_NODE_PLATFORM;
	public  static String GRID_HUB_URL;
	public  static String GRID_NODE_FF_BINARY;
	
	public static long DEFAULT_TIMEOUT_FOR_WAIT;
	public static double BROWSER_WIDTH_PERCENT;
	public static double BROWSER_HEIGHT_PERCENT;
	public static String INDEX_PAGE_URL;
	public static String OBJECT_SELECTOR;
	public static String OBJECT_TEXT;
	public static String BROWSER_INFO;
	public static String INPUT_DATE = "01/04/2010";
	public static String HOST;
	public static String BASE_URL;
	public static String PORT;
	public static String PROXY_ADDRESS;
	public static Boolean HIDE_BROWSER;
	public static Boolean PROXY_ENABLED;

	public static String REGRESSION_DB_ADDRESS;
	public static String REGRESSION_DB_USERNAME;
	public static String REGRESSION_DB_PASSWORD;
	public static String REGRESSION_RUN_TYPE;
	public static String DEFAULT_DOWNLOAD_PATH;
	public static String IMAGE_MAGICK_BIN_PATH;
	public static String HTTP_SECURE_CHECK;
	private static boolean HTTP_SECURE_CHECK_BOOL;
	
	public static String LOAD_DELAY;
	public static final int TEST_TIMEOUT = 600_000;
	static {
		try {
			URL url = Constants.class.getClassLoader().getResource(PROPERTIES_FILE_NAME);
			URI uri = new URI(url.toString());
			FileInputStream fileInputStream = new FileInputStream(uri.getPath());
			Properties properties = new Properties();
			properties.load(fileInputStream);
			
			// http secure check value assignment
			String httpType = "http://";
			HTTP_SECURE_CHECK = properties.getProperty(HTTP_SECURE_CHECK_PARAM);
			if (HTTP_SECURE_CHECK!=null) {
				HTTP_SECURE_CHECK_BOOL = Boolean.parseBoolean(HTTP_SECURE_CHECK);
				if (HTTP_SECURE_CHECK_BOOL) {
					httpType = "https://";
				} 
			}  else {
				HTTP_SECURE_CHECK = "";
			}
			
			BROWSER_INFO = properties.getProperty(BROWSER_PARAM).toLowerCase();
			PORT = properties.getProperty(PORT_PARAM,"");
			HOST = properties.getProperty(HOST_PARAM);
			
			
			BASE_URL = httpType + HOST;
			
			if(!PORT.isEmpty()){
				BASE_URL+=(":" + PORT);
			}
//			BASE_URL+= "/";
			LOAD_DELAY = properties.getProperty(LOAD_DELAY_PARAM);
			INDEX_PAGE_URL = properties.getProperty(INDEX_PAGE_URL_PARAM);
			BROWSER_WIDTH_PERCENT = Double.valueOf(properties.getProperty(BROWSER_WIDTH_PERCENT_PARAM,"0.8"));
			BROWSER_HEIGHT_PERCENT = Double.valueOf(properties.getProperty(BROWSER_HEIGHT_PERCENT_PARAM,"0.9"));
			DEFAULT_TIMEOUT_FOR_WAIT = Long.valueOf(properties.getProperty(DEFAULT_TIMEOUT_FOR_WAIT_PARAM, "80"));
			OBJECT_SELECTOR = properties.getProperty(OBJECT_SELECTOR_PARAM);
			OBJECT_TEXT = properties.getProperty(OBJECT_TEXT_PARAM);
			HIDE_BROWSER = Boolean.valueOf(properties.getProperty(HIDE_FIREFOX_PARAM,"false"));
			PROXY_ENABLED = Boolean.valueOf(properties.getProperty(PROXY_ENABLED_PARAM, "false"));
			EMAIL_TEMP_DR = properties.getProperty(EMAIL_TEMP_DIR_PARAM);
			EMAIL_USER = properties.getProperty(EMAIL_USER_PARAM);
			EMAIL_PASS = properties.getProperty(EMAIL_PASS_PARAM);
			TESTRAIL_USERNAME = properties.getProperty(TESTRAIL_USERNAME_PARAM);
			TESTRAIL_PASSWORD = properties.getProperty(TESTRAIL_PASSWORD_PARAM);
			TESTRAIL_RUN_ID = properties.getProperty(TESTRAIL_RUN_ID_PARAM);
			
			SAUCE_BROWSER = properties.getProperty(SAUCE_BROWSER_PARAM);
			SAUCE_BROWSER_VERSION = properties.getProperty(SAUCE_BROWSER_VERSION_PARAM);
			SAUCE_PLATFORM = properties.getProperty(SAUCE_PLATFORM_PARAM);
			SAUCE_USERNAME = properties.getProperty(SAUCE_USERNAME_PARAM);
			SAUCE_ACCESS_KEY = properties.getProperty(SAUCE_ACCESS_KEY_PARAM);
			
			GRID_HUB_URL = properties.getProperty(GRID_HUB_URL_PARAM);
			GRID_NODE_BROWSER = properties.getProperty(GRID_NODE_BROWSER_PARAM);
			GRID_NODE_BROWSER_VERSION = properties.getProperty(GRID_NODE_BROWSER_VERSION_PARAM);
			GRID_NODE_PLATFORM = properties.getProperty(GRID_NODE_PLATFORM_PARAM);
			GRID_NODE_FF_BINARY = properties.getProperty(GRID_NODE_FF_BINARY_PARAM);
			PROXY_ADDRESS = properties.getProperty(PROXY_ADDRESS_PARAM);
			
			REGRESSION_DB_ADDRESS =  properties.getProperty(REGRESSION_DB_ADDRESS_PARAM);
			REGRESSION_DB_USERNAME =  properties.getProperty(REGRESSION_DB_USERNAME_PARAM);
			REGRESSION_DB_PASSWORD =  properties.getProperty(REGRESSION_DB_PASSWORD_PARAM);
			REGRESSION_RUN_TYPE =  properties.getProperty(REGRESSION_RUN_TYPE_PARAM);
			DEFAULT_DOWNLOAD_PATH = properties.getProperty(DEFAULT_DOWNLOAD_PATH_PARAM);
			IMAGE_MAGICK_BIN_PATH = properties.getProperty(IMAGE_MAGICK_BIN_PATH_PARAM);
			
			if (IMAGE_MAGICK_BIN_PATH==null) {
				IMAGE_MAGICK_BIN_PATH = "\\\\autotest.helpsystems.com\\ImageMagick-7.0.8-Q16";
			}

			if (!(DEFAULT_DOWNLOAD_PATH==null)) {
				new File(DEFAULT_DOWNLOAD_PATH).mkdirs();
			}  else {
				DEFAULT_DOWNLOAD_PATH = "";
			}
			new File(EMAIL_TEMP_DR).mkdirs();

		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
