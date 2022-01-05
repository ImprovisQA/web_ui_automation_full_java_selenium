package com.helpsystems.common.base;

import static com.helpsystems.common.util.Constants.*;

import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tools.ant.DirectoryScanner;
import org.im4java.core.CompareCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;
import org.im4java.process.StandardStream;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.AssumptionViolatedException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;
import com.helpsystems.common.testrail.APIClient;
import com.helpsystems.common.util.TestUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

/**
 * This is base class for all test classes.
 * 
 * @author giskanda
 * 
 */
@Ignore
public abstract class BaseTest implements SauceOnDemandSessionIdProvider {

	public static final String SAUCE_URL = "http://" + SAUCE_USERNAME + ":" + SAUCE_ACCESS_KEY
			+ "@ondemand.saucelabs.com:80/wd/hub";

	protected static final String HIDE_FIREFOX_EXE = "hideFireFox.exe";
	private static final String HIDE_IE_EXE = "hideInternetExplorer.exe";
	private static final Point BROWSER_POSITION = new Point(0, 0);
	private static final String IE_DRIVER_SERVER = "IEDriverServer.exe";
	private static final String CHROME_DRIVER_SERVER = "chromedriver.exe";
	private static final String GECKO_DRIVER = "geckodriver.exe";
	private static final String BROWSER_IE = "ie";
	private static final String BROWSER_FIREFOX = "ff";
	private static final String BROWSER_GOOGLE_CHROME = "ch";
	private static final String BROWSER_SAUCE_LAB = "saucelab";
	private static final String SELENIUM_GRID = "seleniumgrid";

	protected Logger LOGGER;
	protected TestUtils testUtils;
	private Dimension browserDimension;
	protected Integer testRailid = 0;
	private Integer TEST_CASE_ID = null;
	private Integer SUB_TEST_ID = null;
	private String runDate = null;
	private Integer runTypeId = null;
	protected long startTime;
	private long elapsedTime;
	private Double truncatedSeconds;
	private static FileInputStream fis;
	private static FileInputStream fis_act;
	private static FileInputStream fis_base;
	private static FileInputStream fis_diff;
	private static File actualFile;
	private static File diffFile;
	private static File baseFile;
	
	private static File scrFile;
	
	public static String BASE_FILE_LAST;
	public static String ACTUAL_FILE_LAST;
	public static String DIFF_FILE_LAST;
	

	private String sessionId;
	
    //Test name
    private static String testName;
    public String packageName;
    
    public static String getTestName() {
    	return testName;
    }
	
	public BaseTest() {
		java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = new Double(screenSize.getWidth() * BROWSER_WIDTH_PERCENT).intValue();
		int y = new Double(screenSize.getHeight() * BROWSER_HEIGHT_PERCENT).intValue();
		browserDimension = new Dimension(x, y);
	}

	public BaseTest(double xPercent, double yPercent) {
		java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = new Double(screenSize.getWidth() * xPercent).intValue();
		int y = new Double(screenSize.getHeight() * yPercent).intValue();
		browserDimension = new Dimension(x, y);
	}

	/**
	 * @return driver
	 */
	public WebDriver getDriver() {
		return testUtils.getDriver();
	}

	/**
	 * @return testUtils
	 */
	public TestUtils getTestUtils() {
		return testUtils;
	}

	/**
	 * Create Validator Object by given Class
	 * 
	 * @return the baseValidator
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 */
	@SuppressWarnings("unchecked")
	public <T extends BaseValidator> T getValidator(Class<T> validatorClass) throws InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException {
		return (T) validatorClass.getConstructors()[0].newInstance(getTestUtils());
	}

	/**
	 * Create BaseValidator Object
	 * 
	 * @return
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public BaseValidator getValidator() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, SecurityException {
		return getValidator(BaseValidator.class);
	}

	@Before
	public void setUp() throws Exception {
		setUp(INDEX_PAGE_URL);
		// Make sure Object Grid panel loaded
		getTestUtils().waitForElementWithText(OBJECT_SELECTOR, OBJECT_TEXT, 60);
	}
	
	/**
	 * 
	 */
	protected void toSkip() throws Exception {
	}

	protected void setUp(String pageUrl) throws Exception {
		createLogger(getClass());
		WebDriver driver = null;
		switch (BROWSER_INFO) {
		case BROWSER_IE:
			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
			ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			ieCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			URI uri = copyDriver(IE_DRIVER_SERVER, getClass());

			System.setProperty("webdriver.ie.driver", uri.getPath());
			driver = new InternetExplorerDriver(ieCapabilities);
			// reset IE zoom level to 100%.
			driver.findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.CONTROL,"0"));
//			if (HIDE_BROWSER) {
//				try {
//					url = getClass().getClassLoader().getResource(HIDE_IE_EXE);
//					uri = new URI(url.toString());
//					Runtime.getRuntime().exec(uri.getPath());
//				} catch (Exception e) {
//					LOGGER.info("exe file " + HIDE_IE_EXE + " for hide Internet Explorer don't exist in resource path ",
//							e);
//				}
//			}
			break;
		case BROWSER_FIREFOX:
			
			uri = copyDriver(GECKO_DRIVER, getClass());

			System.setProperty("webdriver.gecko.driver", uri.getPath());
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("marionette", true);
			driver = new FirefoxDriver(capabilities);
			// FirefoxProfile profile = new FirefoxProfile();
			// profile.setEnableNativeEvents(false);
			// profile.setPreference("browser.download.useDownloadDir", false);
			// driver = new FirefoxDriver(profile);
//			if (HIDE_BROWSER) {
//				try {
//					url = getClass().getClassLoader().getResource(HIDE_FIREFOX_EXE);
//					uri = new URI(url.toString());
//					Runtime.getRuntime().exec(uri.getPath());
//				} catch (Exception e) {
//					LOGGER.info("exe file " + HIDE_FIREFOX_EXE + " for hide firefox don't exist in resource path ", e);
//				}
//			}
			break;
		case BROWSER_SAUCE_LAB:
			DesiredCapabilities caps = DesiredCapabilities.firefox();
			caps.setCapability(CapabilityType.BROWSER_NAME, SAUCE_BROWSER);
			caps.setCapability(CapabilityType.PLATFORM, SAUCE_PLATFORM);
			caps.setCapability(CapabilityType.VERSION, SAUCE_BROWSER_VERSION);

			String methodName = name.getMethodName();
			caps.setCapability("name", methodName);

			try {
				driver = new RemoteWebDriver(new URL(SAUCE_URL), caps);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
			break;
		// driver.get(BASE_URL);
		// CASE when selected to run in Selenium Grid
		case SELENIUM_GRID:

			Platform platform = Platform.WINDOWS;

			// Switch Case to define the platform
			switch (GRID_NODE_PLATFORM) {
			case "WINDOWS":
			default:
				platform = Platform.WINDOWS;
				break;
			case "WIN7":
				platform = Platform.VISTA;
				break;
			case "WIN8":
				platform = Platform.WIN8;
				break;
			case "WIN10":
				platform = Platform.WIN10;
				break;
			case "Linux":
				platform = Platform.LINUX;
				break;
			}

			DesiredCapabilities caps2 = new DesiredCapabilities();
			caps2.setBrowserName(GRID_NODE_BROWSER);
			caps2.setVersion(GRID_NODE_BROWSER_VERSION);
			caps2.setPlatform(platform);
			caps2.setAcceptInsecureCerts(true);
			if (!(GRID_NODE_FF_BINARY.isEmpty())) {
				caps2.setCapability("firefox_binary", GRID_NODE_FF_BINARY);
			}

			try {
				driver = new RemoteWebDriver(new URL(GRID_HUB_URL), caps2);
				driver.manage().window().maximize();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case BROWSER_GOOGLE_CHROME:
		default:
			
			uri = copyDriver(CHROME_DRIVER_SERVER, getClass());
			System.setProperty("webdriver.chrome.driver", uri.getPath());
			DesiredCapabilities capabilitiesChrome = DesiredCapabilities.chrome();
			capabilitiesChrome.setAcceptInsecureCerts(true);
			ChromeOptions options = new ChromeOptions();
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			prefs.put("download.default_directory", DEFAULT_DOWNLOAD_PATH);
			options.setExperimentalOption("prefs", prefs);
			options.addArguments("test-type");
			options.addArguments("--disable-backgrounding-occluded-windows");
			options.addArguments("--disable-web-security");
			options.addArguments("--allow-running-insecure-content");
			options.addArguments("disable-infobars");
			
			if (HIDE_BROWSER) {
				options.addArguments("--headless");
			}
			
			if (PROXY_ENABLED) {
				Proxy proxy = new Proxy();
				//Set the http proxy value, host and port.
				proxy.setHttpProxy(PROXY_ADDRESS);
	            //Set the proxy to the Chrome options
	            options.setProxy(proxy);

			}
			
			capabilitiesChrome.setCapability(ChromeOptions.CAPABILITY, options);
			driver = new ChromeDriver(capabilitiesChrome);
			break;
		}

		if (!BROWSER_INFO.equals(SELENIUM_GRID)) {
			driver.manage().window().setPosition(BROWSER_POSITION);
			driver.manage().window().setSize(browserDimension);
		}

		testUtils = new TestUtils(driver);
		testUtils.setLogger(LOGGER);
		toSkip();
		long loadDelay = Long.parseLong(LOAD_DELAY);
		driver.manage().timeouts().pageLoadTimeout(loadDelay, TimeUnit.SECONDS);
		startTime = System.nanoTime();
		testUtils.createFolder(testUtils.parentScreenShotsLocation);
//		testUtils.createFolder(testUtils.parentDifferencesLocation);
		
		//Clean Differences Root Folder
//		File differencesFolder = new File(testUtils.parentDifferencesLocation);
//		FileUtils.cleanDirectory(differencesFolder);
		driver.get(BASE_URL + pageUrl);
		
		
			
		testName = getClass().getSimpleName();
		packageName = getClass().getPackage().getName();
		packageName = packageName.replaceAll("\\.", "\\\\");

		// Create a specific directory for a test
		testUtils.testScreenShotDirectory = testUtils.parentScreenShotsLocation + packageName + "\\" + testName + "\\";
		testUtils.createFolder(testUtils.testScreenShotDirectory);

		File actualFile = new File(testUtils.testScreenShotDirectory + testName + "_Actual.png");
		File diffFile = new File(testUtils.testScreenShotDirectory + testName + "_Diff.png");
		
		// get All screenshots of Actual and DIFF criteria
		DirectoryScanner scanner = new DirectoryScanner();
		scanner.setIncludes(new String[]{"*_Actual*", "*_Diff*"});
		scanner.setBasedir(testUtils.testScreenShotDirectory);
		scanner.setCaseSensitive(false);
		scanner.scan();
		String[] files = scanner.getIncludedFiles();
		
		// remove Actual and DIFF files
		for (String file : files) {
			File currentFile = new File(testUtils.testScreenShotDirectory + file);
			if (currentFile.exists()) {
				currentFile.delete();
			}
		}
		
	}

	private URI copyDriver(String driverType, Class<? extends BaseTest> getClassBase) throws URISyntaxException, IOException, FileNotFoundException {
		URI uri;
		String pathToProjectTargetFolder = getClass().getClassLoader().getResource("").toString();
		System.out.println("resource path = " + pathToProjectTargetFolder);
		String realPath = pathToProjectTargetFolder + driverType;
		uri = new URI(realPath.toString());
		File driverFile = new File(uri);
		if (driverFile.length()==0) {
			driverFile.delete();
		}	
		if(!driverFile.exists()) {
			try(InputStream initialStream = getClassBase.getResourceAsStream("/" + driverType);
					OutputStream o = new FileOutputStream(driverFile)) {
				IOUtils.copy(initialStream, o);
			}
		}

		return uri;
	}

	/**
	 * 
	 * Create Logger by given Class
	 * 
	 * @param clazz
	 */
	public <T extends Object> void createLogger(Class<T> clazz) {
		LOGGER = LoggerFactory.getLogger(clazz);
	}

	abstract public String getOwner();

	@After
	public void tearDown() throws Exception {
		String owner = getOwner();
		String ownerName = null;
		String tcName = null;
		String pckgName = null;
		Integer pckgId = null;
		Integer ownerId = null;
		Integer ownerIdFromTestcase = null;
		Connection connection = null;

		ComboPooledDataSource dataSource = TestUtils.getDataSource(REGRESSION_DB_ADDRESS, REGRESSION_DB_USERNAME,
				REGRESSION_DB_PASSWORD);
		System.out.println(dataSource.getNumBusyConnections());
		connection = dataSource.getConnection();

		String currentRunPackageName = getClass().getPackage().getName();
		String sqlCommandGetPckgName = "SELECT package_name from package where package_name='" + currentRunPackageName
				+ "'";
		String sqlCommandGetPckgId = "SELECT package_id from package where package_name='" + currentRunPackageName
				+ "'";
		String sqlCommandGetOwnerName = "SELECT tc_owner_name from owner where tc_owner_name='" + owner + "'";
		String sqlCommandGetOwnerId = "SELECT tc_owner_id from owner where tc_owner_name='" + owner + "'";
		String sqlCommandGetRunTypeId = "SELECT run_type_id from runtype where run_type_name='" + REGRESSION_RUN_TYPE
				+ "'";
		// check the package table content
		// List<Object> pckgObjects =
		// testUtils.executeUpdateSqlQuery(REGRESSION_DB_ADDRESS,
		// REGRESSION_DB_USERNAME,
		// REGRESSION_DB_PASSWORD, sqlCommandGetPckgName,
		// "package_name").get("package_name");
		List<Object> pckgObjects = testUtils
				.executeSqlQueryReturnSelectedColumns(connection, sqlCommandGetPckgName, "package_name")
				.get("package_name");
		if (pckgObjects.size() != 0) {
			pckgName = pckgObjects.get(0).toString();
		}
		// add package name name if it is missing
		if (pckgName == null) {
			String sqlCommandPack = "INSERT INTO package (package_name) VALUES ('" + currentRunPackageName + "')";
			testUtils.runSqlQuery(connection, sqlCommandPack);
		}

		// check the owner table content
		List<Object> ownerObjects = testUtils
				.executeSqlQueryReturnSelectedColumns(connection, sqlCommandGetOwnerName, "tc_owner_name")
				.get("tc_owner_name");
		if (ownerObjects.size() != 0) {
			ownerName = ownerObjects.get(0).toString();
		}

		// add owner Name if it is missing
		if (ownerName == null) {
			String sqlCommandOwner = "INSERT INTO owner (tc_owner_name) VALUES ('" + owner + "')";
			testUtils.runSqlQuery(connection, sqlCommandOwner);
		}

		// get the package id from package table
		// List<Object> pckgIdObjects =
		// testUtils.executeUpdateSqlQuery(REGRESSION_DB_ADDRESS,
		// REGRESSION_DB_USERNAME,
		// REGRESSION_DB_PASSWORD, sqlCommandGetPckgId, "package_id").get("package_id");
		List<Object> pckgIdObjects = testUtils
				.executeSqlQueryReturnSelectedColumns(connection, sqlCommandGetPckgId, "package_id").get("package_id");

		if (pckgIdObjects.size() != 0) {
			pckgId = Integer.valueOf(pckgIdObjects.get(0).toString());
		}

		// get the owner id from owner table
		List<Object> ownerIdObjects = testUtils
				.executeSqlQueryReturnSelectedColumns(connection, sqlCommandGetOwnerId, "tc_owner_id")
				.get("tc_owner_id");
		if (ownerIdObjects.size() != 0) {
			ownerId = Integer.valueOf(ownerIdObjects.get(0).toString());
		}

		// get the run type id from runtype table
		List<Object> ryntypeIdObjects = testUtils
				.executeSqlQueryReturnSelectedColumns(connection, sqlCommandGetRunTypeId, "run_type_id")
				.get("run_type_id");
		if (ryntypeIdObjects.size() != 0) {
			runTypeId = Integer.valueOf(ryntypeIdObjects.get(0).toString());
		}

		String currentRunTestCaseName = getClass().getSimpleName();

		// get the TC id with current run test case name and current run package id
		String sqlCommandGetTcNameAndPackageID = "SELECT tc_id from testcase where tc_name='" + currentRunTestCaseName
				+ "' AND package_id='" + pckgId + "'";
		List<Object> tcIDobjectsWithPackageID = testUtils
				.executeSqlQueryReturnSelectedColumns(connection, sqlCommandGetTcNameAndPackageID, "tc_id")
				.get("tc_id");

		// if test case is missing then add it into table
		if (tcIDobjectsWithPackageID.size() == 0) {
			String sqlCommandPutTcNameAndPackageId = "INSERT INTO testcase (tc_name, package_id, tc_owner_id) VALUES ('"
					+ currentRunTestCaseName + "', '" + pckgId + "', '" + ownerId + "')";
			testUtils.runSqlQuery(connection, sqlCommandPutTcNameAndPackageId);
		}

		// if test case exists but with another owner

		if (tcIDobjectsWithPackageID.size() != 0) {
			String sqlCommandGetOwnerIDFromTestCases = "SELECT tc_owner_id from testcase where tc_name='"
					+ currentRunTestCaseName + "' AND package_id='" + pckgId + "'";
			List<Object> ownerIDobjectsFromTestCases = testUtils
					.executeSqlQueryReturnSelectedColumns(connection, sqlCommandGetOwnerIDFromTestCases, "tc_owner_id")
					.get("tc_owner_id");
			if (ownerIDobjectsFromTestCases.size() != 0) {
				ownerIdFromTestcase = Integer.valueOf(ownerIDobjectsFromTestCases.get(0).toString());
			}

			if (ownerId != ownerIdFromTestcase) {
				String updateOwnerQuery = "UPDATE testcase SET tc_owner_id='" + ownerId + "' WHERE tc_name='"
						+ currentRunTestCaseName + "' AND package_id='" + pckgId + "'";
				testUtils.runSqlQuery(connection, updateOwnerQuery);
			}

		}

		// get the test case ID
		String sqlCommandGetTcID = "SELECT tc_id from testcase where tc_name='" + currentRunTestCaseName
				+ "' AND package_id='" + pckgId + "'";
		List<Object> tcIDobjects = testUtils
				.executeSqlQueryReturnSelectedColumns(connection, sqlCommandGetTcID, "tc_id").get("tc_id");
		TEST_CASE_ID = Integer.valueOf(tcIDobjects.get(0).toString());

		// ********************************* ADDING SUB TEST
		// *********************************
		String subTestName = name.getMethodName();
		// get the sub test id with current run sub test name and current run test case
		// id
		String sqlCommandGetSubTestNameByTestCaseID = "SELECT sbt_id from subtest where sbt_name='" + subTestName
				+ "' AND tc_id='" + Integer.toString(TEST_CASE_ID) + "'";
		List<Object> sbtIDobjectsWithTestCaseID = testUtils
				.executeSqlQueryReturnSelectedColumns(connection, sqlCommandGetSubTestNameByTestCaseID, "sbt_id")
				.get("sbt_id");
		// if test case is missing then add it into table
		if (sbtIDobjectsWithTestCaseID.size() == 0) {
			String sqlCommandPutSubTestNameAndTestCAseId = "INSERT INTO subtest (sbt_name, tc_id) VALUES ('"
					+ subTestName + "', '" + TEST_CASE_ID + "')";
			testUtils.runSqlQuery(connection, sqlCommandPutSubTestNameAndTestCAseId);
		}

		// Getting Sub test ID
		List<Object> sbtIDobjects = testUtils
				.executeSqlQueryReturnSelectedColumns(connection, sqlCommandGetSubTestNameByTestCaseID, "sbt_id")
				.get("sbt_id");
		SUB_TEST_ID = Integer.valueOf(sbtIDobjects.get(0).toString());

		// get the date for test case run
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.now();
		LocalTime localTime = LocalTime.now();
		String[] timeArr = localTime.toString().split(":");
		String[] dateArr = dtf.format(localDate).split("/");
		runDate = dateArr[1] + "/" + dateArr[2] + "/" + dateArr[0] + " - " + timeArr[0] + ":" + timeArr[1];
		

		if (getDriver() != null) {
			scrFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
//		FileUtils.copyFile(scrFile, new File("c:\\tmp\\screenshot.png"));
			fis = new FileInputStream(scrFile);
			getDriver().quit();
		}
		elapsedTime = System.nanoTime() - startTime;
		double seconds = (double) elapsedTime / 1000000000.0;

		truncatedSeconds = BigDecimal.valueOf(seconds).setScale(3, RoundingMode.HALF_UP).doubleValue();
		if (connection != null)
			connection.close();

	}

	public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(SAUCE_USERNAME,
			SAUCE_ACCESS_KEY);

	/**
	 * JUnit Rule which will mark the Sauce Job as passed/failed when the test
	 * succeeds or fails.
	 */
	@Rule
	public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

	@Rule
	public TestName name = new TestName() {
		public String getMethodName() {
			return String.format("%s", super.getMethodName());
		};
	};

	@Rule
	public TestWatcher watchman = new TestWatcher() {
		
		private void setFailStatus(Throwable e, String status){
			
			Connection connection = null;
			try {
				dataSource = TestUtils.getDataSource(REGRESSION_DB_ADDRESS, REGRESSION_DB_USERNAME,
						REGRESSION_DB_PASSWORD);
				System.out.println(dataSource.getNumBusyConnections());
				connection = dataSource.getConnection();
			} catch (PropertyVetoException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			if (e.getCause()==null) {
				e.printStackTrace(new PrintStream(out));				
			} else {
				e.getCause().printStackTrace(new PrintStream(out));
			}
			String fullStackTrace = new String(out.toByteArray());
			String fullExceptionMessage = null;
			String fullExceptionCauseMessage = null;
			String exceptionMessage = "The exception does not have message";
			String stepsMessage = "";
			String[] splittedMessage = new String[0];
			
			
			if (!(e.getMessage() == null)) {
				fullExceptionMessage = e.getMessage().replace("'", "\"");
				splittedMessage = fullExceptionMessage.split("#\\*\\*\\*\\*\\*\\*\\*#");
				if (splittedMessage.length > 1) {
					exceptionMessage = splittedMessage[0];
					stepsMessage = splittedMessage[1];
				} else {
					exceptionMessage = fullExceptionMessage;
				}
			}
			
			if (!(e.getCause()==null) && !(e.getCause().getMessage() == null)) {
				fullExceptionCauseMessage = e.getCause().getMessage().replace("'", "\"");
				exceptionMessage = fullExceptionCauseMessage;
				
			}
			
			if(!(e.getCause()==null) && (e.getMessage() == null)&&(e.getCause().getMessage() == null)) {
				exceptionMessage = "The exception does not have message";
			}
			if (truncatedSeconds == null) {
				truncatedSeconds = 0.0;
			}
			
			// "\n" + myStackTrace.replace("'", "\"") +
			
			if (!(e.getCause()==null)&&!(e.getCause().getMessage()==null)&&(e.getCause().getMessage().contains("The test case failed on image comparison"))) {
//				actualFile = new File(testUtils.testScreenShotDirectory + testName+"_Actual.png");
//				diffFile = new File(testUtils.testScreenShotDirectory + testName+"_Diff.png");
//				baseFile = new File(testUtils.testScreenShotDirectory + testName+"_Baseline.png");
				actualFile = new File(ACTUAL_FILE_LAST);
				diffFile = new File(DIFF_FILE_LAST);
				baseFile = new File(BASE_FILE_LAST);
				try {
					fis_act = new FileInputStream(actualFile);
					fis_base = new FileInputStream(baseFile);
					fis_diff = new FileInputStream(diffFile);
				} catch (FileNotFoundException ex) {
					
				}
			}

			String sqlQueryAddFailResult = "INSERT INTO lastresults (tc_id, sbt_id, tc_res, tc_fail_tr, tc_steps, tc_cons_log, run_date, run_type_id, tc_run_time, scr_shot, act_shot, base_shot, diff_shot) VALUES ('"
					+ TEST_CASE_ID + "', '" + SUB_TEST_ID + "', '" + status + "', '" + exceptionMessage + "', '" + stepsMessage + "', '"
					+ fullStackTrace.replace("'", "\"") + "', '" + runDate + "', '" + runTypeId + "', '"
					+ truncatedSeconds + "', ?, ?, ?, ?)";
			PreparedStatement pstmt = null;
			try {
				pstmt = connection.prepareStatement(sqlQueryAddFailResult);
				byte[] emptyArray = new byte[0];
				// failure screenshot
				if ((fis!=null)&&(scrFile!=null)) {
					pstmt.setBinaryStream(1, fis, (int)scrFile.length());
				} else {
					pstmt.setBytes(1, emptyArray);
				}
				
				if ((fis_act!=null)&&(actualFile!=null)) {
					pstmt.setBinaryStream(2, fis_act, (int)actualFile.length());
				} else {
					pstmt.setBytes(2, emptyArray);
				}
				
				if ((fis_base!=null)&&(baseFile!=null)) {
					pstmt.setBinaryStream(3, fis_base, (int)baseFile.length());
				} else {
					pstmt.setBytes(3, emptyArray);
				}
				
				if ((fis_diff!=null)&&(diffFile!=null)) {
					pstmt.setBinaryStream(4, fis_diff, (int)diffFile.length());
				} else {
					pstmt.setBytes(4, emptyArray);
				}
				
				
				pstmt.executeUpdate();
				if (fis!=null)
				fis.close();
				if (fis_act!=null)
					fis_act.close();
				if (fis_base!=null)
					fis_base.close();
				if (fis_diff!=null)
					fis_diff.close();
				
			} 
			catch (Exception e2)
		    {
		        try {
		        	System.out.println("Incorrect SQL command: '" + sqlQueryAddFailResult + "'");
					connection.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        e2.printStackTrace();
		    } 
			
			finally {
				try {
					if(pstmt != null) pstmt.close();
				} catch (SQLException ex) {
				}

			}
			
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		
		ComboPooledDataSource dataSource;

		public Statement apply(final Statement base, final Description description) {
			return new Statement() {
				@Override
				public void evaluate() throws Throwable {
					List<Throwable> errors = new ArrayList<Throwable>();

					startingQuietly(description, errors);
					try {
						base.evaluate();
						succeededQuietly(description, errors);
					} catch (InitException e) {
						errors.add(e);
						System.out.println("Init Fail " + e.toString());
						
						setFailStatus(e, "iFail");
						
						//TODO: add method to handle unresolved status
					}  catch (CleanUpException e) {
						errors.add(e);
						System.out.println("Cleanup Fail " + e.toString());
						
						setFailStatus(e, "cFail");
						
						//TODO: add method to handle unresolved status
					} catch (ValidateException e) {
						errors.add(e);
						System.out.println("Validation Fail " + e.toString());
						
						setFailStatus(e, "failed");
						
						//TODO: add method to handle unresolved status
					}
					catch (AssumptionViolatedException e) {
						errors.add(e);
						skippedQuietly(e, description, errors);
					} catch (TestException e) {
						errors.add(e);
						failedQuietly(e, description, errors);
					} catch (Throwable e) {
						errors.add(e);
						failedQuietly(e, description, errors);
					} 
					finally {
						finishedQuietly(description, errors);
					}

					MultipleFailureException.assertEmpty(errors);
				}
			};
		}

		private void startingQuietly(Description description, List<Throwable> errors) {
			try {
				starting(description);
			} catch (Throwable e) {
				errors.add(e);
			}
		}

		private void finishedQuietly(Description description, List<Throwable> errors) {
			try {
				finished(description);
			} catch (Throwable e) {
				errors.add(e);
			}
		}

		private void succeededQuietly(Description description, List<Throwable> errors) {
			try {
				succeeded(description);
			} catch (Throwable e) {
				errors.add(e);
			}
		}

		private void failedQuietly(Throwable e, Description description, List<Throwable> errors) {
			try {
				failed(e, description);
			} catch (Throwable e1) {
				errors.add(e1);
			}
		}

		private void skippedQuietly(AssumptionViolatedException e, Description description, List<Throwable> errors) {
			try {
				skipped(e, description);
			} catch (Throwable e1) {
				errors.add(e1);
			}
		}

		@Override
		protected void failed(Throwable e, Description description) {
			setFailStatus(e, "failed");
//			Connection connection = null;
//			try {
//				dataSource = TestUtils.getDataSource(REGRESSION_DB_ADDRESS, REGRESSION_DB_USERNAME,
//						REGRESSION_DB_PASSWORD);
//				System.out.println(dataSource.getNumBusyConnections());
//				connection = dataSource.getConnection();
//			} catch (PropertyVetoException | SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			// watchedLog+= description + "\n";
//			APIClient client = new APIClient("https://hstestrail.testrail.net");
//			client.setUser(TESTRAIL_USERNAME);
//			client.setPassword(TESTRAIL_PASSWORD);
//
//			try {
//				Map data = new HashMap();
//				data.put("status_id", new Integer(5));
//				data.put("comment", "The test case failed, the following exception is thrown:\n" + e + "");
//				JSONObject r = (JSONObject) client.sendPost("add_result_for_case/" + TESTRAIL_RUN_ID + "/" + testRailid,
//						data);
//			} catch (Exception ex) {
//
//			}
//			ByteArrayOutputStream out = new ByteArrayOutputStream();
//			e.printStackTrace(new PrintStream(out));
//			String fullStackTrace = new String(out.toByteArray());
//			String exceptionMessage = null;
//
//			if (!(e.getMessage() == null)) {
//				exceptionMessage = e.getMessage().replace("'", "\"");
//			} else {
//				exceptionMessage = "The exception does not have message";
//			}
//			if (truncatedSeconds == null) {
//				truncatedSeconds = 0.0;
//			}
//			
//			String sqlQueryAddFailResult = "INSERT INTO lastresults (tc_id, sbt_id, tc_res, tc_fail_tr, tc_cons_log, run_date, run_type_id, tc_run_time, scr_shot) VALUES ('"
//					+ TEST_CASE_ID + "', '" + SUB_TEST_ID + "', 'failed', '" + exceptionMessage + "', '"
//					+ fullStackTrace.replace("'", "\"") + "', '" + runDate + "', '" + runTypeId + "', '"
//					+ truncatedSeconds + "', ?)";
//			
//			PreparedStatement pstmt = null;
//			try {
//				pstmt = connection.prepareStatement(sqlQueryAddFailResult);
//				pstmt.setBinaryStream(1, fis, (int)scrFile.length());
//				pstmt.executeUpdate();
//				fis.close();
//			} 
//			catch (Exception e2)
//		    {
//		        try {
//		        	System.out.println("Incorrect SQL command: '" + sqlQueryAddFailResult + "'");
//					connection.rollback();
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//		        e2.printStackTrace();
//		    } 
//			
//			finally {
//				try {
//					if(pstmt != null) pstmt.close();
//				} catch (SQLException ex) {
//				}
//
//			}
//			
//			if (connection != null)
//				try {
//					connection.close();
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}

			}

		@Override
		protected void succeeded(Description description) {
			Connection connection = null;
			// ComboPooledDataSource dataSource;
			try {
				dataSource = TestUtils.getDataSource(REGRESSION_DB_ADDRESS, REGRESSION_DB_USERNAME,
						REGRESSION_DB_PASSWORD);
				System.out.println(dataSource.getNumBusyConnections());
				connection = dataSource.getConnection();
			} catch (PropertyVetoException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// watchedLog+= description + " " + "success!\n";
//			APIClient client = new APIClient("https://hstestrail.testrail.net");
//			client.setUser(TESTRAIL_USERNAME);
//			client.setPassword(TESTRAIL_PASSWORD);
//
//			try {
//				Map data = new HashMap();
//				data.put("status_id", new Integer(1));
//				data.put("comment", "This test passed");
//				JSONObject r = (JSONObject) client.sendPost("add_result_for_case/" + TESTRAIL_RUN_ID + "/" + testRailid,
//						data);
//			} catch (Exception ex) {
//
//			}
			if (truncatedSeconds == null) {
				truncatedSeconds = 0.0;
			}
			String sqlQueryAddSuccessResult = "INSERT INTO lastresults (tc_id, sbt_id, tc_res, run_date, run_type_id, tc_run_time) VALUES ('"
					+ TEST_CASE_ID + "', '" + SUB_TEST_ID + "', 'pass', '" + runDate + "', '" + runTypeId + "', '"
					+ truncatedSeconds + "')";
			testUtils.runSqlQuery(connection, sqlQueryAddSuccessResult);
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}

		@Override
		protected void skipped(AssumptionViolatedException e, Description description) {
			Connection connection = null;
			// ComboPooledDataSource dataSource;
			try {
				dataSource = TestUtils.getDataSource(REGRESSION_DB_ADDRESS, REGRESSION_DB_USERNAME,
						REGRESSION_DB_PASSWORD);
				System.out.println(dataSource.getNumBusyConnections());
				connection = dataSource.getConnection();
			} catch (PropertyVetoException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String sqlQueryAddSuccessResult = "INSERT INTO lastresults (tc_id, sbt_id, tc_res,  tc_fail_tr, run_date, run_type_id) VALUES ('"
					+ TEST_CASE_ID + "', '" + SUB_TEST_ID + "', 'skipped', '" + e.getMessage() + "', '" + runDate
					+ "', '" + runTypeId + "')";
			testUtils.runSqlQuery(connection, sqlQueryAddSuccessResult);
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
	};

	@Override
	public String getSessionId() {
		// TODO Auto-generated method stub
		return sessionId;

	}

	public void skipThis() {
		skipThis("This test is skipped with no reason");
	}

	public void skipThis(String skipMessage) {
		org.junit.Assume.assumeTrue(skipMessage, 1 == 2);
	}
	
}
