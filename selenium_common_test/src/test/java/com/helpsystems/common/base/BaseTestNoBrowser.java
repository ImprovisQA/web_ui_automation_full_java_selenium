package com.helpsystems.common.base;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.helpsystems.common.util.Constants.*;

import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.helpsystems.common.util.TestUtils;

/**
 * This is base class to run the test cases w/o browser, the Selenium and
 * Webdriver
 * 
 * @author giskanda
 * 
 */
public abstract class BaseTestNoBrowser extends com.helpsystems.common.base.BaseTest {
	private static final String CHROME_DRIVER_SERVER = "chromedriver.exe";

	private WebDriver driver;

	protected Boolean runDriver = false;

	public BaseTestNoBrowser() {
		super();
	}

	@Override
	@Before
	public void setUp() throws Exception {
		startTime = System.nanoTime();
		if (runDriver) {
			URL url = getClass().getClassLoader().getResource(CHROME_DRIVER_SERVER);
			URI uri = new URI(url.toString());
			System.setProperty("webdriver.chrome.driver", uri.getPath());
			DesiredCapabilities capabilitiesChrome = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			prefs.put("download.default_directory", DEFAULT_DOWNLOAD_PATH);
			options.setExperimentalOption("prefs", prefs);
			options.addArguments("test-type");
			options.addArguments("--disable-web-security");
			options.addArguments("--allow-running-insecure-content");
			capabilitiesChrome.setCapability(ChromeOptions.CAPABILITY, options);

			driver = new ChromeDriver(capabilitiesChrome);

			createLogger(getClass());
			testUtils = new TestUtils(driver);
			testUtils.setLogger(LOGGER);

		} else {
			createLogger(getClass());
			testUtils = new TestUtils(null);
			testUtils.setLogger(LOGGER);
		}
		toSkip();
	}

}
