package com.helpsystems.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public final class ReportConstants {

	private static final String PROPERTIES_FILE_NAME = "report.properties";
	private static final String REPORT_WEB_PATH_PARAM = "report.web.path";
	private static final String PRODUCT_NAME_PARAM = "product.name";
	private static final String EMAIL_RECIPIENTS_PARAM = "email.recipients";
	private static final String PRIMARY_RUNS_PARAM = "primary.runs";
	private static final String RE_RUNS_PARAM = "re.runs";
	private static final String ONE_THREAD_PACKAGES_PARAM = "one.thread.packages";
	public static String REPORT_WEB_PATH;
	public static String PRODUCT_NAME;
	public static String EMAIL_RECIPIENTS;
	public static String PRIMARY_RUNS;
	public static String RE_RUNS;
	public static String ONE_THREAD_PACKAGES;
	
	public static final int TEST_TIMEOUT = 600_000;
	static {
		try {
			URL url = ReportConstants.class.getClassLoader().getResource(PROPERTIES_FILE_NAME);
			URI uri = new URI(url.toString());
			FileInputStream fileInputStream = new FileInputStream(uri.getPath());
			Properties properties = new Properties();
			properties.load(fileInputStream);
			PRODUCT_NAME = properties.getProperty(PRODUCT_NAME_PARAM,"");
			REPORT_WEB_PATH = properties.getProperty(REPORT_WEB_PATH_PARAM);
			EMAIL_RECIPIENTS = properties.getProperty(EMAIL_RECIPIENTS_PARAM);
			RE_RUNS = properties.getProperty(RE_RUNS_PARAM);
			PRIMARY_RUNS = properties.getProperty(PRIMARY_RUNS_PARAM);
			ONE_THREAD_PACKAGES = properties.getProperty(ONE_THREAD_PACKAGES_PARAM);

		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
