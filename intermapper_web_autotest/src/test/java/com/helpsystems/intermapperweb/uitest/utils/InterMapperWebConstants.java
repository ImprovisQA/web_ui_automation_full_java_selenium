package com.helpsystems.intermapperweb.uitest.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import com.helpsystems.common.util.Constants;

public final class InterMapperWebConstants {

	private static final String INTERMAPPERWEB_PROPERTIES_FILE_NAME = "intermapperweb.test.properties";

	private final static String INTERMAPPERWEB_TEST_INPUTS_ROOT_PARAM = "intermapperweb.test.inputs.root";

	private final static String ANZHELA_PARAM = "user.anzhela";
	private final static String LILIT_PARAM = "user.lilit";
	private final static String ARMEN_PARAM = "user.armen";
	private final static String IM_VERSION_PARAM = "im.version";

	public final static String INTERMAPPERWEB_TEST_INPUTS_ROOT;
	public static String ANZHELA;
	public static String LILIT;
	public static String ARMEN;
	public static String IM_VERSION;

	static {
		Properties properties = new Properties();
		try {
			URL url = Constants.class.getClassLoader().getResource(INTERMAPPERWEB_PROPERTIES_FILE_NAME);
			URI uri = new URI(url.toString());
			FileInputStream fileInputStream = new FileInputStream(uri.getPath());
			properties.load(fileInputStream);
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}

		ANZHELA = properties.getProperty(ANZHELA_PARAM);
		LILIT = properties.getProperty(LILIT_PARAM);
		ARMEN = properties.getProperty(ARMEN_PARAM);
		IM_VERSION = properties.getProperty(IM_VERSION_PARAM);

		INTERMAPPERWEB_TEST_INPUTS_ROOT = properties.getProperty(INTERMAPPERWEB_TEST_INPUTS_ROOT_PARAM);
		new File(INTERMAPPERWEB_TEST_INPUTS_ROOT).mkdirs();

	}
}
