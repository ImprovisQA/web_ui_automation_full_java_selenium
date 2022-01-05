package com.helpsystems.common.util;

import static com.helpsystems.common.util.Constants.DEFAULT_TIMEOUT_FOR_WAIT;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
//import static com.helpsystems.common.base.*;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.mail.BodyPart;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;
import org.im4java.core.CompareCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;
import org.im4java.process.StandardStream;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.helpsystems.common.base.BaseTest;
import com.helpsystems.common.base.TestException;
import com.helpsystems.common.processors.AttachmentProcessor;
import com.helpsystems.common.processors.AttachmentProcessorFactory;
import com.helpsystems.common.uncompressors.UncompressorsFactory;
import com.helpsystems.common.uncompressors.UncompressorsFactory.CompressType;
import com.ibm.as400.access.AS400;
import com.ibm.as400.access.IFSFile;
import com.ibm.as400.access.IFSFileInputStream;
import com.ibm.as400.access.IFSFileOutputStream;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

/**
 * Utility class for all tests
 * 
 * @author Lyduvig Mkrtchyan
 * 
 */
public class TestUtils {

	private static final String LABEL = "label";
	private static final String BUTTON = "button";
	private static final String NAME = "name";
	private static final String INPUT = "input";
	private static final String DIV = "div";
	private static final String CLASS = "class";
	private static final String ID = "id";
	private WebDriver driver;
	private Logger LOGGER;
	private static final Map<String, ComboPooledDataSource> dbMap = new ConcurrentHashMap<>();
	
	//****************** Visual Test variables
    //Test name
//    public String testName;
 
    //Test Screenshot directory
    public String testScreenShotDirectory;
    //Main Directory of the test code
    public String currentDir = System.getProperty("user.dir");
    //Main screenshot directory
    public String parentScreenShotsLocation = currentDir + "\\target\\ScreenShots\\";
 
    //Main differences directory
//    public String parentDifferencesLocation = currentDir + "\\target\\Differences\\";
    
    //Element screenshot paths
    public String baselineScreenShotPath;
    public String actualScreenShotPath;
    public String differenceScreenShotPath;
 
    //Image files
    public File baselineImageFile;
    public File actualImageFile;
    public File differenceImageFile;
//    public File differenceFileForParent;

	public TestUtils(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * set Logger
	 * 
	 * @param logger
	 */
	public void setLogger(Logger logger) {
		this.LOGGER = logger;
	}

	public Logger getLogger() {
		return LOGGER;
	}

	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * Function to find and select an element with id(start and end strings) and
	 * containing (equals) text
	 * 
	 * @param tagName
	 * @param startID
	 * @param endID
	 * @param inputText
	 * @return
	 */
	public String findSelectItemWithTextId(String tagName, String startID,
			String endID, String inputText) {

		WebElement element = findItemWithTextSelectorEndId(tagName + "[" + ID
				+ "^=" + startID + "]", endID, inputText);
		if (element != null) {
			String elementId = element.getAttribute(ID);
			element.click();
			return elementId;
		}
		return "";
	}

	/**
	 * Function to find and return an element with id(start and end strings) and
	 * containing (equals) text
	 * 
	 * @param tagName
	 * @param startID
	 * @param endID
	 * @param inputText
	 * @return
	 */
	public WebElement findItemWithTextSelectorEndId(String selector,
			String endID, String inputText) {
		// Get ID-s with menu
		WebElement element = null;
		List<WebElement> allMenus = getElementsBySelector(selector);
		for (WebElement objectNode : allMenus) {
			String id = objectNode.getAttribute(ID);
			if (StringUtils.endsWith(id, endID)) {

				String trimText = StringUtils.trim(objectNode.getText());
				if (trimText.equals(inputText) && objectNode.isDisplayed()) {
					element = objectNode;
					break;
				}
			}
		}
		return element;
	}
	
	/**
	 * 
	 * @param selector
	 * @param endID
	 * @param inputText
	 * @return
	 */
	public WebElement findItemWithTextSelectorInObjectByDiv(String selector,
			String parentObjID, String inputText) {
		// Get ID-s with menu
		WebElement element = null;
		
		WebElement parentEl = getElementById(parentObjID);
		
		List<WebElement> allMenus = parentEl.findElements(By.cssSelector(selector));
		for (WebElement objectNode : allMenus) {
				String trimText = StringUtils.trim(objectNode.getText());
				if (trimText.equals(inputText) && objectNode.isDisplayed()) {
					element = objectNode;
					break;
				}
		}
		return element;
	}

	/**
	 * Function to find and return an element with id(start and end strings) and
	 * containing (equals) text
	 * 
	 * @param tagName
	 * @param startID
	 * @param endID
	 * @param inputText
	 * @return
	 */
	public List<WebElement> findItemsWithTextSelectorEndId(String selector,
			String endID, String inputText) {
		// Get ID-s with menu
		List<WebElement> elements = new ArrayList<>();
		List<WebElement> allMenus = getElementsBySelector(selector);
		for (WebElement objectNode : allMenus) {
			String id = objectNode.getAttribute(ID);
			if (StringUtils.endsWith(id, endID)) {

				String trimText = StringUtils.trim(objectNode.getText());
				if (trimText.equals(inputText)) {
					elements.add(objectNode);
				}
			}
		}
		return elements;
	}

	/**
	 * Function to find item with class name and Text
	 * 
	 * @param tagName
	 * @param className
	 * @param inputText
	 * @return
	 */
	public String findSelectItemWithClassText(String tagName, String className,
			String inputText) {
		WebElement element = findItemWithTextSelectorEndId(tagName + "["
				+ CLASS + "^=" + className + "]", "", inputText);
		if (element != null) {
			String elementClass = element.getAttribute(CLASS);
			element.click();
			return elementClass;
		}
		return "";
	}

	/**
	 * Function to find item with class name
	 * 
	 * @param tagName
	 * @param className
	 * @param nthEl
	 */
	public void findClickItemWithClassNitem(String tagName, String className,
			int nthEl) {

		// Get ID-s with menu
		List<WebElement> allElements = getElementsBySelector(tagName + "["
				+ CLASS + "=" + className + "]");

		WebElement nthElement = allElements.get(nthEl);

		nthElement.click();

	}

	/**
	 * Function to find an element with id(start and end strings) and containing
	 * text
	 * 
	 * @param tagName
	 * @param startID
	 * @param endID
	 * @param inputText
	 * @return
	 */
	public WebElement findItemWithTextContains(String tagName, String startID,
			String endID, String inputText) {
		WebElement element = null;
		// Get ID-s with menu
		List<WebElement> allMenus = getElementsBySelector(tagName + "[" + ID
				+ "^=" + startID + "]");
		for (WebElement objectNode : allMenus) {
			String id = objectNode.getAttribute(ID);
			if (StringUtils.endsWith(id, endID)) {
				String trimText = StringUtils.trim(objectNode.getText());
				if (trimText.contains(inputText)) {
					element = objectNode;
					break;
				}
			}
		}
		return element;
	}

	/**
	 * Function to find elements with id(start and end strings) and containing
	 * text
	 * 
	 * @param tagName
	 * @param startID
	 * @param endID
	 * @param inputText
	 * @return
	 */
	public List<WebElement> findItemsWithTextContains(String tagName,
			String startID, String endID, String inputText) {
		List<WebElement> elements = new ArrayList<>();
		// Get ID-s with menu
		List<WebElement> allMenus = getElementsBySelector(tagName + "[" + ID
				+ "^=" + startID + "]");
		for (WebElement objectNode : allMenus) {
			String id = objectNode.getAttribute(ID);
			if (StringUtils.endsWith(id, endID)) {
				String trimText = StringUtils.trim(objectNode.getText());
				if (trimText.contains(inputText)) {
					elements.add(objectNode);
				}
			}
		}
		return elements;
	}

	/**
	 * Function to select menu item with selector and item-text
	 * 
	 * @param selector
	 * @param sampleText
	 */
	public void findSelectSubmenuItem(String selector, String sampleText) {
		List<WebElement> element = findItemsWithTextSelectorEndId(selector, "",
				sampleText);
		for (WebElement webElement : element) {
			if (webElement.isDisplayed() && webElement != null) {
				webElement.click();
			}

		}
	}

	/**
	 * find Double Click on Submenu Item
	 * 
	 * @param selector
	 * @param sampleText
	 */
	public void findDblClickSubmenuItem(String selector, String sampleText) {

		WebElement element = findItemWithTextSelectorEndId(selector, "",
				sampleText);
		if (element != null) {
			Actions action = new Actions(driver);
			// double click on the object
			action.doubleClick(element);
			action.perform();
		}
	}

	/**
	 * get Element By given Selector
	 * 
	 * @param selector
	 * @return
	 */
	public WebElement getElementBySelector(String selector) {
		return driver.findElement(By.cssSelector(selector));
	}

	/**
	 * get Element By given Selector and parent node
	 * 
	 * @param parent
	 * @param selector
	 * @return
	 */
	public WebElement getElementBySelector(WebElement parent, String selector) {
		WebElement elements = null;
		if (parent != null) {
			elements = parent.findElement(By.cssSelector(selector));
		}
		return elements;
	}

	/**
	 * get Elements By given Selector
	 * 
	 * @param selector
	 * @return
	 */
	public List<WebElement> getElementsBySelector(String selector) {
		return driver.findElements(By.cssSelector(selector));
	}

	/**
	 * get Elements By given Selector and parent node
	 * 
	 * @param parent
	 * @param selector
	 * @return
	 */
	public List<WebElement> getElementsBySelector(WebElement parent,
			String selector) {
		List<WebElement> elements = null;
		if (parent != null) {
			elements = parent.findElements(By.cssSelector(selector));
		}
		return elements;
	}

	/**
	 * find and Right Click Sub-menu Item
	 * 
	 * @param selector
	 * @param sampleText
	 */
	public void findRightClickSubmenuItem(String selector, String sampleText) {

		WebElement element = findItemWithTextSelectorEndId(selector, "",
				sampleText);
		if (element != null) {
			Actions action = new Actions(driver);
			// double click on the object
			action.contextClick(element);
			action.perform();
		}
	}

	/**
	 * Function to verify if the text exists in the grid
	 * 
	 * @param selector
	 * @param sampleText
	 */
	public void findVerifySubmenuItem(String selector, String sampleText) {
		WebElement element = findItemWithTextSelectorEndId(selector, "",
				sampleText);
		assertTrue(element != null);
	}

	/**
	 * Function to find an element with id(start and end strings) and containing
	 * (equals) text , return ID
	 * 
	 * @param tagName
	 * @param startID
	 * @param endID
	 * @param inputText
	 * @return
	 */
	public String findItemWithTextId(String tagName, String startID,
			String endID, String inputText) {
		WebElement element = findItemWithTextSelectorEndId(tagName + "[" + ID
				+ "^=" + startID + "]", endID, inputText);
		if (element != null) {
			return element.getAttribute(ID);
		}
		return "";
	}

	/**
	 * wait For Button with given id
	 * 
	 * @param id
	 * @return
	 * @throws InterruptedException
	 */
	public WebElement waitForButton(String id) throws InterruptedException {
		// Wait for Create Chart button to make sure the view is opened
		return waitBySelector(BUTTON + "[" + ID + "^=" + id + "]");
	}

	/**
	 * wait For Button with given id and timeout
	 * 
	 * @param id
	 * @param timeout
	 * @return
	 * @throws InterruptedException
	 */
	public WebElement waitForButton(String id, long timeout)
			throws InterruptedException {
		// Wait for Create Chart button to make sure the view is opened
		return waitBySelector(BUTTON + "[" + ID + "^=" + id + "]", timeout);
	}

	/**
	 * wait For Button with given id and text
	 * 
	 * @param id
	 * @param text
	 * @throws InterruptedException
	 */
	public void waitForButton(String id, String text)
			throws InterruptedException {
		// Wait for Create Chart button to make sure the view is opened
		waitForElementWithText(BUTTON + "[" + ID + "^=" + id + "]", text);
	}

	/**
	 * wait For Button with given id and text
	 * 
	 * @param id
	 * @param text
	 * @param timeout
	 * @throws InterruptedException
	 */
	public void waitForButton(String id, String text, long timeout)
			throws InterruptedException {
		// Wait for Create Chart button to make sure the view is opened
		waitForElementWithText(BUTTON + "[" + ID + "^=" + id + "]", text,
				timeout);
	}

	/**
	 * wait For Element with given id
	 * 
	 * @param id
	 * @return
	 * @throws InterruptedException
	 */
	public WebElement waitForElement(String id) throws InterruptedException {
		WebElement webElement = waitByExpectedCondition(ExpectedConditions
				.presenceOfElementLocated(By.id(id)));
		Thread.sleep(500);
		return webElement;
	}

	/**
	 * wait For Div with given id
	 * 
	 * @param id
	 * @return
	 * @throws InterruptedException
	 */
	public WebElement waitForDiv(String id) throws InterruptedException {
		return waitBySelector(DIV + "[" + ID + "=" + id + "]");
	}

	/**
	 * wait For Div hide given with id
	 * 
	 * @param id
	 * @throws InterruptedException
	 */
	public void waitForDivHide(String id) throws InterruptedException {
		waitForHideBySelector(DIV + "[" + ID + "=" + id + "]");
	}

	/**
	 * wait For Div with given start id
	 * 
	 * @param id
	 * @return
	 * @throws InterruptedException
	 */
	public WebElement waitForDivStartId(String id) throws InterruptedException {
		return waitBySelector(DIV + "[" + ID + "^=" + id + "]");
	}

	/**
	 * wait For Div hide with given start id
	 * 
	 * @param id
	 * @throws InterruptedException
	 */
	public void waitForDivHideStartId(String id) throws InterruptedException {
		waitForHideBySelector(DIV + "[" + ID + "^=" + id + "]");
	}

	/**
	 * wait For Div with given id and given time out
	 * 
	 * @param id
	 * @param timeout
	 * @return
	 * @throws InterruptedException
	 */
	public WebElement waitForDiv(String id, long timeout)
			throws InterruptedException {
		return waitBySelector(DIV + "[" + ID + "=" + id + "]", timeout);
	}

	/**
	 * wait For Div hide with given id and given time out
	 * 
	 * @param id
	 * @param timeout
	 * @throws InterruptedException
	 */
	public void waitForDivHide(String id, long timeout)
			throws InterruptedException {
		waitForHideBySelector(DIV + "[" + ID + "=" + id + "]", timeout);
	}

	/**
	 * wait For Div with given start id
	 * 
	 * @param id
	 * @param timeout
	 * @return
	 * @throws InterruptedException
	 */
	public WebElement waitForDivStartId(String id, long timeout)
			throws InterruptedException {
		return waitBySelector(DIV + "[" + ID + "^=" + id + "]", timeout);
	}

	/**
	 * wait For Div hide with given start id
	 * 
	 * @param id
	 * @param timeout
	 * @throws InterruptedException
	 */
	public void waitForDivHideStartId(String id, long timeout)
			throws InterruptedException {
		waitForHideBySelector(DIV + "[" + ID + "^=" + id + "]", timeout);
	}

	/**
	 * wait For Div with given class
	 * 
	 * @param id
	 * @throws InterruptedException
	 */
	public void waitForDivByClass(String clazz) throws InterruptedException {
		waitForDivByClass(clazz, DEFAULT_TIMEOUT_FOR_WAIT);
	}

	/**
	 * wait For Div with given class and timeout
	 * 
	 * @param id
	 * @param timeout
	 * @throws InterruptedException
	 */
	public void waitForDivByClass(String clazz, long timeout)
			throws InterruptedException {
		waitBySelector(DIV + "[" + CLASS + "^=" + clazz + "]", timeout);
	}

	/**
	 * wait For Div hide with given class
	 * 
	 * @param id
	 * @throws InterruptedException
	 */
	public void waitForDivHideByClass(String clazz) throws InterruptedException {
		waitForDivHideByClass(clazz, DEFAULT_TIMEOUT_FOR_WAIT);
	}

	/**
	 * wait For Div hide with given class and timeout
	 * 
	 * @param id
	 * @param timeout
	 * @throws InterruptedException
	 */
	public void waitForDivHideByClass(String clazz, long timeout)
			throws InterruptedException {
		waitForHideBySelector(DIV + "[" + CLASS + "^=" + clazz + "]", timeout);
	}

	/**
	 * wait For Label with given id
	 * 
	 * @param id
	 * @return
	 * @throws InterruptedException
	 */
	public WebElement waitForLabel(String id) throws InterruptedException {
		return waitBySelector(LABEL + "[" + ID + "=" + id + "]");
	}

	/**
	 * 
	 * input given text into element with given id
	 * 
	 * @param id
	 * @param text
	 * @throws InterruptedException
	 */
	public void inputInto(String id, String text) throws InterruptedException {
		getElementById(id).clear();
		getElementById(id).sendKeys(text);
		Thread.sleep(200);
	}

	/**
	 * get Element Value by given id
	 * 
	 * @param id
	 * @return
	 */
	public String getElementValue(String id) {
		return getElementById(id).getAttribute("value");
	}

	/**
	 * get Element By given Id
	 * 
	 * @param id
	 * @return
	 */
	public WebElement getElementById(String id) {
		return driver.findElement(By.id(id));
	}

	/**
	 * get Element By given Id and parent node
	 * 
	 * @param parent
	 * @param id
	 * @return
	 */
	public WebElement getElementById(WebElement parent, String id) {
		WebElement element = null;
		if (parent != null) {
			element = parent.findElement(By.id(id));
		}
		return element;
	}

	/**
	 * get Element By given Name
	 * 
	 * @param name
	 * @return
	 */
	public WebElement getElementByName(String name) {
		return driver.findElement(By.name(name));
	}

	/**
	 * get Element By given Name and parent node
	 * 
	 * @param parent
	 * @param name
	 * @return
	 */
	public WebElement getElementByName(WebElement parent, String name) {
		WebElement element = null;
		if (parent != null) {
			element = parent.findElement(By.name(name));
		}
		return element;
	}

	/**
	 * input given text Into element By given Name
	 * 
	 * @param name
	 * @param text
	 * @throws InterruptedException
	 */
	public void inputIntoByName(String name, String text)
			throws InterruptedException {
		getElementByName(name).clear();
		getElementByName(name).sendKeys(text);
		Thread.sleep(200);
	}
	
	/**
	 * input given text Into element By given Class
	 * 
	 * @param name
	 * @param text
	 * @throws InterruptedException
	 */
	public void inputIntoByClass(String className, String text)
			throws InterruptedException {
		getElementByClass(className).clear();
		getElementByClass(className).sendKeys(text);
	}

	/**
	 * button Click with given id
	 * 
	 * @param buttonId
	 * @throws InterruptedException
	 */
	public void buttonClick(String buttonId) throws InterruptedException {
		WebElement button = getButton(buttonId);
		button.click();
		// TimeUnit.SECONDS.sleep(1);
	}

	/**
	 * button Click with given StartId
	 * 
	 * @param buttonStartId
	 * @throws InterruptedException
	 */
	public void buttonClickStartId(String buttonStartId)
			throws InterruptedException {
		WebElement button = getButtonStartId(buttonStartId);
		button.click();
		// TimeUnit.SECONDS.sleep(1);
	}

	/**
	 * click On Item with given id
	 * 
	 * @param itemId
	 * @throws InterruptedException
	 */
	public void clickOnItem(String itemId) throws InterruptedException {
		getElementById(itemId).click();
		// Thread.sleep(1000);
	}

	/**
	 * click On Item with given Class
	 * 
	 * @param itemClass
	 * @throws InterruptedException
	 */
	public void clickOnItemByClass(String itemClass)
			throws InterruptedException {
		WebElement element = getElementByClass(itemClass);

		element.click();
		// Thread.sleep(1000);
	}

	/**
	 * get Element with given Class
	 * 
	 * @param itemClass
	 * @return
	 */
	public WebElement getElementByClass(String itemClass) {
		WebElement element = driver.findElement(By.className(itemClass));
		return element;
	}

	/**
	 * get Element with given Class and parent node
	 * 
	 * @param parent
	 * @param itemClass
	 * @return
	 */
	public WebElement getElementByClass(WebElement parent, String itemClass) {
		WebElement element = null;
		if (parent != null) {
			element = parent.findElement(By.className(itemClass));
		}
		return element;
	}

	/**
	 * 
	 * get Elements with given Class
	 * 
	 * @param itemClass
	 * @return
	 */
	public List<WebElement> getElementsByClass(String itemClass) {
		List<WebElement> elements = driver
				.findElements(By.className(itemClass));
		return elements;
	}

	/**
	 * get Elements with given Class and parent node
	 * 
	 * @param parent
	 * @param itemClass
	 * @return
	 */
	public List<WebElement> getElementsByClass(WebElement parent,
			String itemClass) {
		List<WebElement> element = null;
		if (parent != null) {
			element = parent.findElements(By.className(itemClass));
		}
		return element;
	}

	/**
	 * 
	 * click On Item with given Name
	 * 
	 * @param itemName
	 * @throws InterruptedException
	 */
	public void clickOnItemByName(String itemName) throws InterruptedException {
		getElementByName(itemName).click();
		Thread.sleep(1000);
	}

	/**
	 * get Button with given id
	 * 
	 * @param buttonId
	 * @return
	 * @throws InterruptedException
	 */
	public WebElement getButton(String buttonId) throws InterruptedException {
		WebElement button = driver.findElement(By.cssSelector(BUTTON + "[" + ID
				+ "=" + buttonId + "]"));
		TimeUnit.MILLISECONDS.sleep(500);
		return button;
	}

	/**
	 * get Button with given Start Id
	 * 
	 * @param buttonStartId
	 * @return
	 * @throws InterruptedException
	 */
	public WebElement getButtonStartId(String buttonStartId)
			throws InterruptedException {
		WebElement button = driver.findElement(By.cssSelector(BUTTON + "[" + ID
				+ "^=" + buttonStartId + "]"));
		TimeUnit.MILLISECONDS.sleep(500);
		return button;
	}

	/**
	 * wait For Input with given id
	 * 
	 * @param inputId
	 * @return
	 * @throws InterruptedException
	 */
	public WebElement waitForInput(String inputId) throws InterruptedException {
		return waitBySelector(INPUT + "[" + ID + "^=" + inputId + "]");
	}

	/**
	 * wait For Input with given name
	 * 
	 * @param inputName
	 * @return
	 * @throws InterruptedException
	 */
	public WebElement waitForInputByName(String inputName)
			throws InterruptedException {
		return waitBySelector(INPUT + "[" + NAME + "^=" + inputName + "]");
	}

	/**
	 * make sure the given text exist in body
	 * 
	 * @param validatetexts
	 * @throws InterruptedException
	 */
	public void validateDataInBody(final String... validatetexts)
			throws InterruptedException {
		validateDataInBody(DEFAULT_TIMEOUT_FOR_WAIT, validatetexts);

	}

	/**
	 * make sure the given text exist in body
	 * 
	 * @param validatetexts
	 * @throws InterruptedException
	 */
	public void validateDataInBody(long timeout, final String... validatetexts)
			throws InterruptedException {
		final WebDriverWait webDriverWait = new WebDriverWait(driver, timeout);
		webDriverWait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				String bodyText = driver.findElement(By.xpath("//body"))
						.getText().trim().replaceAll(" +", " ");
				for (int i = 0; i < validatetexts.length; i++) {
					String text = validatetexts[i];
					// System.out.println(text);
					LOGGER.info(text);
					try {
						assertTrue(bodyText.contains(text));
					} catch (AssertionError e) {
						webDriverWait.withMessage("Validation Error : Text '"
								+ text + "' is not found");
						return false;
					}
				}
				return true;
			}
		});

	}

	/**
	 * return parent node of given Web element
	 * 
	 * @param webElement
	 * @return
	 */
	public WebElement getParent(WebElement webElement) {
		WebElement parent = null;
		if (webElement != null) {
			parent = webElement.findElement(By.xpath(".."));
		}
		return parent;
	}

	/**
	 * make sure the given text is missing in body
	 * 
	 * @param validatetexts
	 */
	public void validateDataIsMissingInBody(String... validatetexts) {
		for (String text : validatetexts) {
			try {
				assertFalse(getDriver().findElement(By.tagName("body")).getText().contains(text));
			} catch (AssertionError e) {
				LOGGER.info("Validate Error : " + "'" + text + "' is not found");
				throw e;
			}
		}
	}

	/**
	 * wait By ExpectedCondition
	 * 
	 * @param <T>
	 * 
	 * @param selector
	 * @return
	 * @throws InterruptedException
	 */
	public <T> T waitByExpectedCondition(
			ExpectedCondition<T> expectedCondition, long timeout) {
		T object = new WebDriverWait(driver, timeout).until(expectedCondition);
		return object;
	}

	/**
	 * wait By ExpectedCondition
	 * 
	 * @param <T>
	 * 
	 * @param selector
	 * @return
	 * @throws InterruptedException
	 */
	public <T> T waitByExpectedCondition(ExpectedCondition<T> expectedCondition) {
		T object = new WebDriverWait(driver, DEFAULT_TIMEOUT_FOR_WAIT)
				.until(expectedCondition);
		return object;
	}

	/**
	 * wait By Selector
	 * 
	 * @param selector
	 * @return
	 * @throws InterruptedException
	 */
	public WebElement waitBySelector(String selector)
			throws InterruptedException {
		return waitBySelector(selector, DEFAULT_TIMEOUT_FOR_WAIT);
	}

	/**
	 * wait For Hide By Selector
	 * 
	 * @param selector
	 * @throws InterruptedException
	 */
	public void waitForHideBySelector(String selector)
			throws InterruptedException {
		waitForHideBySelector(selector, DEFAULT_TIMEOUT_FOR_WAIT);
	}

	/**
	 * wait By Selector with timeout
	 * 
	 * @param selector
	 * @param timeout
	 * @return
	 * @throws InterruptedException
	 */
	public WebElement waitBySelector(String selector, long timeout)
			throws InterruptedException {
		WebElement webElement = waitByExpectedCondition(
				ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(selector)), timeout);
		// TimeUnit.MILLISECONDS.sleep(500);
		return webElement;
	}
	
	public void waitByXpath(String xpath)
			throws InterruptedException {
		waitByXpath(xpath, DEFAULT_TIMEOUT_FOR_WAIT);
	}
	
	/**
	 * 
	 * @param xpath
	 * @param timeout
	 * @return
	 * @throws InterruptedException
	 */
	public WebElement waitByXpath(String xpath, long timeout)
			throws InterruptedException {
		WebElement webElement = waitByExpectedCondition(
				ExpectedConditions.presenceOfElementLocated(By
						.xpath(xpath)), timeout);
		return webElement;
	}

	/**
	 * wait For Hide By Selector with timeout
	 * 
	 * @param selector
	 * @param timeout
	 * @throws InterruptedException
	 */
	public void waitForHideBySelector(String selector, long timeout)
			throws InterruptedException {
		waitByExpectedCondition(
				ExpectedConditions.invisibilityOfElementLocated(By
						.cssSelector(selector)), timeout);
		TimeUnit.MILLISECONDS.sleep(500);
	}

	/**
	 * wait For Element With selector and Text
	 * 
	 * @param selector
	 * @param text
	 * @throws InterruptedException
	 */
	public void waitForElementWithText(String selector, String text)
			throws InterruptedException {
		waitForElementWithText(selector, text, DEFAULT_TIMEOUT_FOR_WAIT);
	}

	/**
	 * wait For Element With selector, Text and timeout
	 * 
	 * @param selector
	 * @param text
	 * @param timeout
	 * @throws InterruptedException
	 */
	public void waitForElementWithText(String selector, String text,
			long timeout) throws InterruptedException {
		waitByExpectedCondition(
				ExpectedConditions.textToBePresentInElementLocated(
						By.cssSelector(selector), text), timeout);
		TimeUnit.MILLISECONDS.sleep(500);
	}

	/**
	 * This method is designed to delete all cookies of the browser
	 * 
	 * @throws InterruptedException
	 */
	public void deleteAllCookies() throws InterruptedException {
		getDriver().manage().deleteAllCookies();
	}

	// public class pdfToText {

	PDFParser parser;
	String parsedText;
	PDFTextStripper pdfStripper;
	PDDocument pdDoc;
	COSDocument cosDoc;
	PDDocumentInformation pdDocInfo;

	/**
	 * @param fileName
	 * @return
	 */
	String pdftoText(String fileName) {

		System.out.println("Parsing text from PDF file " + fileName + "....");
		File f = new File(fileName);

		if (!f.isFile()) {
			System.out.println("File " + fileName + " does not exist.");
			return null;
		}

		try {
			parser = new PDFParser(new FileInputStream(f));
		} catch (Exception e) {
			System.out.println("Unable to open PDF Parser.");
			return null;
		}

		try {
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			parsedText = pdfStripper.getText(pdDoc);
		} catch (Exception e) {
			System.out
					.println("An exception occured in parsing the PDF Document.");
			e.printStackTrace();
			try {
				if (cosDoc != null)
					cosDoc.close();
				if (pdDoc != null)
					pdDoc.close();
			} catch (Exception e1) {
				e.printStackTrace();
			}
			return null;
		}
		System.out.println("Done.");
		return parsedText;
	}

	/**
	 * Write the parsed text from PDF to a file
	 * 
	 * @param pdfText
	 * @param fileName
	 */
	void writeTexttoFile(String pdfText, String fileName) {

		System.out.println("\nWriting PDF text to output text file " + fileName
				+ "....");
		try {
			PrintWriter pw = new PrintWriter(fileName);
			pw.print(pdfText);
			pw.close();
		} catch (Exception e) {
			System.out
					.println("An exception occured in writing the pdf text to file.");
			e.printStackTrace();
		}
		System.out.println("Done.");
	}

	/**
	 * Extracts text from a PDF Document and writes it to a text file
	 * 
	 * @param args
	 */
	public void convertPdfToTxt(String args[]) {
		if (args.length != 2) {
			System.out.println("Usage: java PDFTextParser  ");
			System.exit(1);
		}

		// pdfToText pdfTextParserObj = new pdfToText();
		String textFromPdf = pdftoText(args[0]);

		if (textFromPdf == null) {
			System.out.println("PDF to Text Conversion failed.");
		} else {
			System.out.println("\nThe text parsed from the PDF Document....\n"
					+ textFromPdf);
			writeTexttoFile(textFromPdf, args[1]);
		}
	}

	/**
	 * This method is designed to execute JavaScript command
	 * 
	 * @param command
	 */
	public void executeJScommand(String command) {
		JavascriptExecutor js = null;
		if (getDriver() instanceof JavascriptExecutor) {
			js = (JavascriptExecutor) getDriver();
		}
		js.executeScript(command);

	}
	
	/**
	 * This method is designed to execute JavaScript in the context of the currently selected 
	 * frame or window. The script fragment provided will be executed as the body of an anonymous function. 
	 * @param setAttr
	 * @param testElement
	 * @param arg1
	 * @param arg2
	 */
	public void executeJScommandWithFullArgs(String setAttr, WebElement testElement, String arg1, String arg2) {
		JavascriptExecutor js = null;
		if (getDriver() instanceof JavascriptExecutor) {
			js = (JavascriptExecutor) getDriver();
		}
		js.executeScript(setAttr, testElement, arg1, arg2);

	}
	
	/**
	 * This method is designed to execute JS command and return WEb element, the command parameter should start with 'return'
	 * @param command
	 * @return
	 */
	public WebElement executeJScommandReturnWebElement(String command) {
		JavascriptExecutor js = null;
		if (getDriver() instanceof JavascriptExecutor) {
			js = (JavascriptExecutor) getDriver();
		}
		return (WebElement) js.executeScript(command);
		
	}

	public void checkAttachment(List<Integer> pagesList,
			List<Pair<Integer, String>> content, final String subject,
			final String folderName) throws Exception {
		checkAttachment(pagesList, content, subject, folderName, "pdf", null, 60);
	}
	
	public void checkAttachment(List<Integer> pagesList,
			List<Pair<Integer, String>> content, final String subject,
			final String folderName, int messageWaitTimeout) throws Exception {
		checkAttachment(pagesList, content, subject, folderName, "pdf", null, messageWaitTimeout);
	}

	public void checkAttachment(List<Integer> pagesList, List<?> content,
			final String subject, final String folderName, String type)
			throws Exception {
		checkAttachment(pagesList, content, subject, folderName, type, null, 60);
	}
	
	public void checkAttachment(List<Integer> pagesList, List<?> content,
			final String subject, final String folderName, String type, CompressType ctype)
					throws Exception {
		checkAttachment(pagesList, content, subject, folderName, type, ctype, 60);
	}
	

	/**
	 * The method
	 * 
	 * @param pagesList
	 * @param content
	 * @param subject
	 * @param folderName
	 * @param type
	 * @param ctype
	 * @throws Exception
	 */
	public void checkAttachment(List<Integer> pagesList, List<?> content,
			final String subject, final String folderName, String type,
			CompressType ctype, int messageWaitTimeout) throws Exception {

		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		props.setProperty("mail.imap.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.imap.socketFactory.fallback", "false");
		// set any needed mail.imaps.* properties here
		final String emailUser = Constants.EMAIL_USER;
		final String emailPass = Constants.EMAIL_PASS;
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(emailUser, emailPass);
					}
				});
		Store store = null;
		Folder folder = null;
		File tempDir = null;
		try {
			store = session.getStore("imaps");
			store.connect("smtp.gmail.com", emailUser, emailPass);
			folder = store.getFolder(folderName);

			/* Open the inbox using store. */

			folder.open(Folder.READ_WRITE);
			final Folder f = folder;
			Message message = waitByExpectedCondition(
					new ExpectedCondition<Message>() {

						@Override
						public Message apply(WebDriver arg0) {
							Message message = null;
							try {
								final int messageCount = f.getMessageCount();
								for (int i = messageCount; i > messageCount - 11
										&& i > 0; i--) {
									Message m;
									m = f.getMessage(i);
									if (subject.equals(m.getSubject())) {
										message = m;
										break;
									}
								}
							} catch (MessagingException e) {
								e.printStackTrace();
							}
							return message;
						}
					}, messageWaitTimeout);
			if (message != null) {
				Multipart multipart = (Multipart) message.getContent();
				boolean attachmentFound = false;
				for (int i = 0; i < multipart.getCount(); i++) {
					BodyPart bodyPart = multipart.getBodyPart(i);
					if (!Part.ATTACHMENT.equalsIgnoreCase(bodyPart
							.getDisposition())
							&& !StringUtils.isNotBlank(bodyPart.getFileName())) {
						continue; // dealing with attachments only
					}
					AttachmentProcessor processor = AttachmentProcessorFactory
							.getProcessor(type.toLowerCase());
					attachmentFound = true;
					if (processor == null) {
						throw new Exception(
								String.format(
										"unsupported content type '%s' for attachement",
										type));
					}
					String tempDirPath = Constants.EMAIL_TEMP_DR + '/'
							+ UUIDGenerator.generateUID();
					tempDir = new File(tempDirPath);
					tempDir.mkdir();
					String fileName = bodyPart.getFileName();
					String resultFilePath = tempDirPath + '/' + fileName;
					try (OutputStream output = new FileOutputStream(
							resultFilePath);
							InputStream is = bodyPart.getInputStream()) {
						IOUtils.copy(is, output);
					}
					if (ctype != null) {
						resultFilePath = UncompressorsFactory.getUncompressor(
								ctype).uncompress(resultFilePath);
					}
					if (pagesList != null && !pagesList.isEmpty()) {
						try (InputStream is = new FileInputStream(
								resultFilePath)) {
							processor.validatePages(is, pagesList);
						}
					}
					if (content != null && !content.isEmpty()) {
						try (InputStream is = new FileInputStream(
								resultFilePath)) {
							processor.validateData(is, content);
						}
					}
					break;
				}
				assertTrue("The email does not contain any attachments",
						attachmentFound);
				message.setFlag(Flag.DELETED, true);
				folder.expunge();
			} else {
				throw new Exception(String.format(
						"Message with subject '%s' not found", subject));
			}
		} finally {
			if (folder != null) {
				folder.close(true);
			}
			if (store != null) {
				store.close();
			}
			if (tempDir != null) {
				FileUtils.forceDelete(tempDir);
			}
		}
	}

	/**
	 * This method is designed for validation the list of String in Email
	 * message text body
	 * 
	 * @param content
	 * @param subject
	 * @param folderName
	 * @throws Exception
	 */
	public void validateEmailMessage(List<String> content,
			final String subject, final String folderName) throws Exception {
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		props.setProperty("mail.imap.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.imap.socketFactory.fallback", "false");
		// set any needed mail.imaps.* properties here
		final String emailUser = Constants.EMAIL_USER;
		final String emailPass = Constants.EMAIL_PASS;
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(emailUser, emailPass);
					}
				});
		Store store = null;
		Folder folder = null;
		File tempDir = null;
		try {
			store = session.getStore("imaps");
			store.connect("smtp.gmail.com", emailUser, emailPass);
			folder = store.getFolder(folderName);

			/* Open the inbox using store. */

			folder.open(Folder.READ_WRITE);
			final Folder f = folder;
			Message message = waitByExpectedCondition(
					new ExpectedCondition<Message>() {

						@Override
						public Message apply(WebDriver arg0) {
							Message message = null;
							try {
								final int messageCount = f.getMessageCount();
								for (int i = messageCount; i > messageCount - 11
										&& i > 0; i--) {
									Message m;
									m = f.getMessage(i);
									if (subject.equals(m.getSubject())) {
										message = m;
										break;
									}
								}
							} catch (MessagingException e) {
								e.printStackTrace();
							}
							return message;
						}
					}, 60);
			if (message != null) {

				String result = null;
				Multipart multipart = (Multipart) message.getContent();

				for (int i = 0; i < multipart.getCount(); i++) {

					BodyPart bodyPart = multipart.getBodyPart(i);
					// System.out.println("Body part type = " +
					// bodyPart.getContentType());
					if (bodyPart.isMimeType("multipart/ALTERNATIVE")
							|| bodyPart.isMimeType("multipart/RELATED")) {
						Multipart multipart2 = (Multipart) bodyPart
								.getContent();
						for (int j = 0; j < multipart2.getCount(); j++) {
							BodyPart bodyPart2 = multipart2.getBodyPart(i);
							if (bodyPart2.isMimeType("TEXT/PLAIN")) {
								// get the text of Email message body
								result = result + "\n" + bodyPart2.getContent();
								break;
							}
						}
						break;
					} else if (bodyPart.isMimeType("TEXT/PLAIN")
							|| bodyPart.isMimeType("TEXT/HTML")) {
						result = result + "\n" + bodyPart.getContent();
						break;
					}
				}
				System.out.println(result);
				for (String currentItem : content) {
					assertTrue("The " + currentItem
							+ " is not found in Email message text body",
							result.contains(currentItem));
				}
				assertTrue(result != null);
				message.setFlag(Flag.DELETED, true);
				folder.expunge();
			} else {
				throw new Exception(String.format(
						"Message with subject '%s' not found", subject));
			}
		} finally {
			if (folder != null) {
				folder.close(true);
			}
			if (store != null) {
				store.close();
			}
			if (tempDir != null) {
				FileUtils.forceDelete(tempDir);
			}
		}

	}

	/**
	 * This method is designed to wait for launching new browser window when
	 * current open window count is known
	 * 
	 * @param oldWindowsCount
	 */
	public void waitForNewBrowserWindowOpen(final int oldWindowsCount) {
		ExpectedCondition<Boolean> expectedCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {

				Set<String> allCurrentWinHandles = getDriver()
						.getWindowHandles();

				int newWinCount = allCurrentWinHandles.size();
				return newWinCount > oldWindowsCount;
			}
		};

		waitByExpectedCondition(expectedCondition, 30);
	}

	/**
	 * This method is designed to switch the WebDriver to latest opened browser
	 * window
	 */
	public void switchToNewWindow() {
		Set<String> allCurrentWinHandles = getDriver().getWindowHandles();

		Iterator<String> testIter = allCurrentWinHandles.iterator();

		String winHandle = null;

		while (testIter.hasNext()) {
			winHandle = testIter.next();
		}

		getDriver().switchTo().window(winHandle);
		System.out.println("Wait for page to load");
//		selenium.waitForPageToLoad(Constants.LOAD_DELAY);
		System.out.println("The page is loaded");
	}

	/**
	 * This method is designed to get the current opened Firefox (which browser
	 * is used by WebDriver) windows
	 */
	public Integer getBrowserWindowCount() {
		Set<String> allBeforeWinHandles = getDriver().getWindowHandles();
		return allBeforeWinHandles.size();
	}

	/**
	 * This method is designed to switch to "Displayed" iframe in web page
	 * 
	 */
	public void switchToIframe() {
		List<WebElement> alliFrames = getDriver().findElements(
				By.xpath("//iframe"));
		for (WebElement iframe : alliFrames) {
			if (iframe.isDisplayed()) {
				getDriver().switchTo().frame(iframe);
				break;
			}
		}
	}

	/**
	 * Override the waitForObjectById() method
	 * 
	 * @param objectId
	 * @param custTimeout
	 * @throws InterruptedException
	 */
	public void waitForObjectById(final String objectId)
			throws InterruptedException {
		waitForObjectById(objectId, 30);
	}

	/**
	 * function to wait for Object by Id with custom Timeout
	 * 
	 * @param objectId
	 * @throws InterruptedException
	 */
	public void waitForObjectById(final String objectId, int custTimeout)
			throws InterruptedException {
		ExpectedCondition<Boolean> expectedCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				boolean isFound = false;
				try {
					WebElement objectDiv = waitForDiv(objectId, 30);

					if (objectDiv.isDisplayed()) {
						isFound = true;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return isFound;
			}
		};

		waitByExpectedCondition(expectedCondition, custTimeout);
		TimeUnit.MILLISECONDS.sleep(500);
	}

	/**
	 * Function to check the attachment (PDF content type is default) giving the
	 * page numbers with search text as inputs (overriding)
	 * 
	 * @throws InterruptedException
	 */
	public void validateFileContent(List<Integer> pagesList,
			List<Pair<Integer, String>> content, final String filePath)
			throws Exception {
		validateFileContent(pagesList, content, filePath, "pdf");
	}

	/**
	 * Function to check the attachment (PDF content type is default) giving the
	 * page numbers with search text as inputs
	 * 
	 * @throws InterruptedException
	 */
	public void validateFileContent(List<Integer> pagesList, List<?> content,
			final String filePath, String type) throws Exception {

		AttachmentProcessor processor = AttachmentProcessorFactory
				.getProcessor(type.toLowerCase());
		if (processor == null) {
			throw new Exception(String.format(
					"unsupported content type '%s' for attachement", type));
		}
		String resultFilePath = filePath;

		if (pagesList != null && !pagesList.isEmpty()) {
			try (InputStream is = new FileInputStream(resultFilePath)) {
				processor.validatePages(is, pagesList);
			}
		}
		if (content != null && !content.isEmpty()) {
			try (InputStream is = new FileInputStream(resultFilePath)) {
				processor.validateData(is, content);
			}
		}
		if (filePath != null) {
			FileUtils.forceDelete(new File(filePath));
		}
	}

	/**
	 * The method is designed to download file from iseries machine
	 * 
	 * @param host
	 * @param username
	 * @param password
	 * @param ifilePath
	 * @param oufilePath
	 */
	public void downloadFileFromIseriesMachine(String host, String username,
			String password, String ifilePath, String oufilePath)
			throws Exception {
		AS400 system = new AS400(host, username, password);

		IFSFile file = new IFSFile(system, ifilePath);

		IFSFileInputStream fis = new IFSFileInputStream(file,
				IFSFileInputStream.SHARE_READERS);

		byte[] data = new byte[fis.available()];
		fis.read(data);
		fis.close();

		OutputStream os = new FileOutputStream(oufilePath);
		os.write(data);
		os.close();
	}

	/**
	 * This method is designed to upload file on Iseries machine
	 * 
	 * @param host
	 * @param username
	 * @param password
	 * @param ifilePath
	 * @param oufilePath
	 * @throws Exception
	 */
	public void uploadFileOnIseriesMachine(String host, String username,
			String password, String ifilePath, String oufilePath)
			throws Exception {
		AS400 system = new AS400(host, username, password);

		// preparing input file strem
		InputStream iStream = new FileInputStream(ifilePath);

		// prepare data
		byte[] data = new byte[iStream.available()];
		iStream.read(data);
		iStream.close();

		// create output file
		IFSFile file = new IFSFile(system, oufilePath);
		if (!file.exists()) {
			file.createNewFile();
		}

		// write data into output file
		IFSFileOutputStream fos = new IFSFileOutputStream(file);
		fos.write(data);
		fos.close();
	}

	/**
	 * function to validate Json file content by Key-Value
	 * @param keyValueMap
	 * @param jsonfilePath
	 * @throws Exception
	 */
	public void validateJsonByKeyValue(Map<String, String> keyValueMap, String jsonfilePath)
			throws Exception {
		FileReader fileRead = new FileReader(jsonfilePath);
		JSONObject jsonObject = (JSONObject)  JSONValue.parse(fileRead);
		for (Map.Entry<String, String> entry : keyValueMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			String newValue = null;
			 try {
				newValue = jsonObject.get(key)+"";
			 }
			 catch (Exception e) {
		            e.printStackTrace();
		        }
			 assertTrue("Value of " + key + " should be " + newValue, value.equals(newValue));
		}
		fileRead.close();
		if (jsonfilePath != null) {
			FileUtils.forceDelete(new File(jsonfilePath));
		}
		
	}
	
	/**
	 * Function to get localhost IP Address.
	 * 
	 * @throws InterruptedException
	 * @throws UnknownHostException 
	 */
	public String getLocalHostAddress()
			throws InterruptedException, UnknownHostException {
		 InetAddress addr = InetAddress.getLocalHost();
		 String ipAddress = addr.getHostAddress();
		 return ipAddress;
	}
	
	/**
	 * function to validate Json array content by key-value map and index
	 * @param content
	 * @param filePath
	 * @throws Exception
	 */
	public void validateJsonContent(List<?> content,
			final String filePath) throws Exception {
		List<Pair<Map<String, String>, Integer>> contentList = new ArrayList<>((List<Pair<Map<String, String>, Integer>>) content);
		for (int i = 0; i < contentList.size(); i++) {
			Pair<Map<String, String>, Integer> pair = contentList.get(i);
			// keyValueMap
			Map<String, String> keyValueMap = pair.getKey();
			// number of json object in array started with 0
			Integer objectNumber = pair.getValue();
			
			//read the Json file by Path
			StringBuilder contentBuilder = new StringBuilder();
			try {
				FileReader fileRead = new FileReader(filePath);
			    BufferedReader in = new BufferedReader(fileRead);
			    String str;
			    while ((str = in.readLine()) != null) {
			        contentBuilder.append(str);
			    }
			    in.close();
			    fileRead.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// get content of JsonFile with String type
			String cont = contentBuilder.toString(); 
			
			// convert the json content from String to Json array
			Gson gson = new Gson();
			JSONObject[] a = gson.fromJson(cont, JSONObject[].class);
			
			// loop over Map and check key-value
			for (Map.Entry<String, String> entry : keyValueMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				String newValue = a[objectNumber].get(key) + "";
				assertTrue("Value of \"" + key + "\" should be \"" + newValue + "\" in object at \"" + objectNumber + "\" index", value.equals(newValue));
				System.out.println(key + ":" + newValue);
			}				
			
		}
		
		if (filePath != null) {
			FileUtils.forceDelete(new File(filePath));
		} 
	}
	
	/**
	 * function to delete file from local disk by path
	 * @param filePath
	 * @throws Exception
	 */
	public void deleteLocalDiskFile(final String filePath) throws Exception {
		if (filePath != null) {
			File file = new File(filePath);
			if(file.exists()){
				FileUtils.forceDelete(file);
			}
		}
	}
	
	/**
	 * Function to execute SQL query.
	 * 
	 * @param dbAddress
	 * @param dbUsername
	 * @param dbPassword
	 * @param sqlCommand
	 * @throws InterruptedException
	 */
	public void executeSqlQuery(String dbAddress, String dbUsername, String dbPassword, String sqlCommand) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("org.postgresql.Driver");

			// STEP 3: Open a connection
			conn = DriverManager.getConnection(dbAddress,
					dbUsername, dbPassword);

			// STEP 4: Execute a query
			stmt = conn.createStatement();

			stmt.execute(sqlCommand);

		} catch (ClassNotFoundException e) {
			LOGGER.info("org.postgresql.Driver:DB Driver class is not found", e);
		} catch (SQLException e) {
			LOGGER.info("Either incorrect DB parameters or invalid Sql Query",
					e);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
				LOGGER.info("Problems with DB Statement Close", se2);
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				LOGGER.info("Problems with DB Connection Close", se);
			}
		}

	}
	
	/**
	 * Function to execute update SQL query.
	 * 
	 * @param dbAddress
	 * @param dbUsername
	 * @param dbPassword
	 * @param sqlCommand
	 * @param columnsToReturn
	 * @throws InterruptedException
	 */

	public Map<String, List<Object>> executeUpdateSqlQuery(String dbAddress, String dbUsername, String dbPassword, String sqlCommand,
			String... columnsToReturn) {
		Connection conn = null;
		Statement stmt = null;
		Map<String, List<Object>> resultMap = new HashMap<>();
		try {
			// STEP 2: Register JDBC driver
			Class.forName("org.postgresql.Driver");

			// STEP 3: Open a connection
//			LOGGER.info("Connecting to database...");
			conn = DriverManager.getConnection(dbAddress,
					dbUsername, dbPassword);

			// STEP 4: Execute a query
//			LOGGER.info("Creating statement...");
			stmt = conn.createStatement();
			for (String columnName : columnsToReturn) {
				resultMap.put(columnName, new ArrayList<Object>());
			}

			ResultSet resultSet = stmt.executeQuery(sqlCommand);
			while (resultSet.next()) {
				for (String columnName : columnsToReturn) {
					resultMap.get(columnName).add(
							resultSet.getObject(columnName));
				}
			}

		} catch (ClassNotFoundException e) {
			LOGGER.info("org.postgresql.Driver:DB Driver class is not found", e);
		} catch (SQLException e) {
			LOGGER.info("Either incorrect DB parameters or invalid Sql Query",
					e);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
				LOGGER.info("Problems with DB Statement Close", se2);
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				LOGGER.info("Problems with DB Connection Close", se);
			}
		}
		return resultMap;

	}

	
	/**
	 * Function returning value from the database.
	 * 
	 * @param dbAddress
	 * @param dbUsername
	 * @param dbPassword
	 * @param sqlCommand
	 * @throws InterruptedException
	 */

	public String returnDatabaseValue(String dbAddress, String dbUsername, String dbPassword, String sqlCommand) {
		Connection conn = null;
		Statement stmt = null;
		String valueFromDatabase = null;

		try {
			// STEP 2: Register JDBC driver
			Class.forName("org.postgresql.Driver");

			// STEP 3: Open a connection
			LOGGER.info("Connecting to database...");
			conn = DriverManager.getConnection(dbAddress,
					dbUsername, dbPassword);

			// STEP 4: Execute a query
			LOGGER.info("Creating statement...");
			stmt = conn.createStatement();

			ResultSet result = stmt.executeQuery(sqlCommand);
			String[] tmp = sqlCommand.split(" ");
			String columnName = tmp[1];

			ArrayList<String> arr = new ArrayList<String>();
			while (result.next()) {

				arr.add(result.getString(columnName));
				valueFromDatabase = arr.get(arr.size() - 1);
				System.out.println(arr.get(arr.size() - 1));

			}

		} catch (ClassNotFoundException e) {
			LOGGER.info("org.postgresql.Driver:DB Driver class is not found", e);
		} catch (SQLException e) {
			LOGGER.info("Either incorrect DB parameters or invalid Sql Query",
					e);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
				LOGGER.info("Problems with DB Statement Close", se2);
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				LOGGER.info("Problems with DB Connection Close", se);
			}

		}
		return valueFromDatabase;

	}

	/**
	 * Function returning all values from database.
	 * 
	 * @param dbAddress
	 * @param dbUsername
	 * @param dbPassword
	 * @param sqlCommand
	 */
	public ArrayList<String> returnAllValuesFromDatabase(String dbAddress, String dbUsername, String dbPassword, String sqlCommand) {
		Connection conn = null;
		Statement stmt = null;
		ArrayList<String> arr = new ArrayList<String>();
		try {
			// STEP 2: Register JDBC driver
			Class.forName("org.postgresql.Driver");

			// STEP 3: Open a connection
			LOGGER.info("Connecting to database...");
			conn = DriverManager.getConnection(dbAddress,
					dbUsername, dbPassword);
			// STEP 4: Execute a query
			LOGGER.info("Creating statement...");
			stmt = conn.createStatement();

			ResultSet result = stmt.executeQuery(sqlCommand);
			String[] tmp = sqlCommand.split(" ");
			String columnName = tmp[1];

			while (result.next()) {
				arr.add(result.getString(columnName));
			}

		} catch (ClassNotFoundException e) {
			LOGGER.info("org.postgresql.Driver:DB Driver class is not found", e);
		} catch (SQLException e) {
			LOGGER.info("Either incorrect DB parameters or invalid Sql Query",
					e);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
				LOGGER.info("Problems with DB Statement Close", se2);
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				LOGGER.info("Problems with DB Connection Close", se);
			}

		}
		return arr;

	}
	
	/**
	 * The method is designed to check that the specified text is missing from text file. 
	 * @param fileName
	 * @param textToFind
	 * @throws IOException
	 */
	public void searchNonExistedStringInTextFile(String fileName, String textToFind) throws IOException {

            BufferedReader fileUnderTest = new BufferedReader(new FileReader(fileName));

            String line;
            boolean isFound  = true;

            Pattern pattern = Pattern.compile(".*" + textToFind + ".*");
            while((line = fileUnderTest.readLine()) != null) {
               Matcher m = pattern.matcher(line);
               if (m.matches()) {
                  isFound = false;
               } else {
            	   isFound = true;
               } 
            }
            
            fileUnderTest.close();
            assertTrue("The specified text \"" + textToFind + "\" is found in the file", isFound);
    }
	
	/**
	 * This method is designed for debugging purposes to highlight the webelement in red
	 * @param element
	 * @param duration
	 * @throws InterruptedException
	 */
	public void highlightElement(WebElement element, int duration) throws InterruptedException {
        String original_style = element.getAttribute("style");
        String setAttr = "arguments[0].setAttribute(arguments[1], arguments[2])";
        String strStyle = "style";
        String changeStyle = "border: 2px solid red; border-style: dashed;";
        

        executeJScommandWithFullArgs(setAttr, element, strStyle, changeStyle);

        if (duration > 0) {
            Thread.sleep(duration * 1000);
            executeJScommandWithFullArgs(setAttr, element, strStyle, original_style);
        }
    }
	
	/**
	 * Function to verify if the text not exist in the grid
	 * 
	 * @param selector
	 * @param sampleText
	 */
	public void findVerifySubmenuItemNotExist(String selector, String sampleText) {
		WebElement element = findItemWithTextSelectorEndId(selector, "",sampleText);
		assertFalse(element != null);
	}
	
	/**
	 * The method is designed to check if the text exists in the object by given selector
	 * @param timeout
	 * @param validatetexts
	 * @throws InterruptedException
	 */
	public void validateDataInObjectBySelector(long timeout, final String selector, final String... validatetexts)
			throws InterruptedException {
		final WebDriverWait webDriverWait = new WebDriverWait(driver, timeout);
		webDriverWait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				String bodyText = driver.findElement(By.cssSelector(selector)).getText().trim().replaceAll(" +", " ");
				for (int i = 0; i < validatetexts.length; i++) {
					String text = validatetexts[i];
					// System.out.println(text);
					LOGGER.info(text);
					try {
						assertTrue(bodyText.contains(text));
					} catch (AssertionError e) {
						webDriverWait.withMessage("Validation Error : Text '" + text + "' is not found");
						return false;
					}
				}
				return true;
			}
		});

	}
	
	/**
	 * The method is designed to check if the text is missing in the object by given selector
	 * @param selector
	 * @param validatetexts
	 */
	public void validateDataIsMissingBySelector(final String selector, String... validatetexts) {
		String bodyText = driver.findElement(By.cssSelector(selector)).getText().trim().replaceAll(" +", " ");

		for (String text : validatetexts) {
			try {
				assertFalse(bodyText.contains(text));
			} catch (AssertionError e) {
				LOGGER.info("Validation Error : " + "'" + text + "' text is found");
				throw e;
			}
		}
	}
	
	/**
	 * Method to get element by xpath expression
	 * @param xpathExpression
	 * @return
	 */
	public WebElement getElementByXPath(String xpathExpression) {
		return driver.findElement(By.xpath(xpathExpression));
	}
	
	/**
	 * Method to get elements by xpath expression
	 * @param xpathExpression
	 * @return
	 */
	public List<WebElement> getElementsByXPath(String xpathExpression) {
		return driver.findElements(By.xpath(xpathExpression));
	}
	
	/**
	 * Method to get element by Selector and Text
	 * @param selector
	 * @param itemName
	 * @return
	 */
	public WebElement getElementWithSelectorAndText(String selector, String itemName) {
		List<WebElement> allElements = getElementsBySelector(selector);
		WebElement el = null;

		for (WebElement currEl : allElements) {
			if (itemName.equals(StringUtils.trim(currEl.getText()))) {
				el =  currEl;
				break;
			}
		}
		return el;
	}
	
	/**
	 * The method is designed to click on web element by specified selector 
	 * @param selector
	 * @throws InterruptedException
	 */
	public void clickOnElementBySelector(String selector) throws InterruptedException {
		getElementBySelector(selector).click();
	}
	

/**
 * The method is designed to get the the datasource by db credetials
 * @param dbAddress
 * @param dbUsername
 * @param dbPassword
 * @return
 * @throws PropertyVetoException
 */
public static ComboPooledDataSource getDataSource(String dbAddress, String dbUsername, String dbPassword) throws PropertyVetoException{
		
		return dbMap.computeIfAbsent(dbAddress, (s) -> {
//			String dbAddress = "jdbc:postgresql://" + Constants.HOST + ":" + Constants.DB_PORT + "/" + s;
			ComboPooledDataSource cpds = new ComboPooledDataSource();
			try {
				cpds.setDriverClass( "org.postgresql.Driver" );
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				return null;
			} //loads the jdbc driver
			cpds.setJdbcUrl(dbAddress);
			cpds.setUser(dbUsername);
			cpds.setPassword(dbPassword);
			cpds.setInitialPoolSize(2);
			// the settings below are optional -- c3p0 can work with defaults
			cpds.setMinPoolSize(2);                   
			cpds.setAcquireIncrement(5);
			cpds.setAcquireRetryAttempts(100);
			cpds.setMaxPoolSize(10);
			cpds.setMaxStatements(180);
			return cpds;

		});
}

/**
 * The method is designed to get result set of selected column
 * @param connection
 * @param sqlCommand
 * @param columnsToReturn
 * @return
 */
public Map<String, List<Object>> executeSqlQueryReturnSelectedColumns(Connection connection, String sqlCommand,
		String... columnsToReturn) {
	Map<String, List<Object>> resultMap = new HashMap<>();
	PreparedStatement pstmt = null;
	try {
		pstmt = connection.prepareStatement(sqlCommand);
		
		for (String columnName : columnsToReturn) {
			resultMap.put(columnName, new ArrayList<Object>());
		}

		ResultSet resultSet = pstmt.executeQuery();
		while (resultSet.next()) {
			for (String columnName : columnsToReturn) {
				resultMap.get(columnName).add(
						resultSet.getObject(columnName));
			}
		}

	}
	catch (Exception e)
    {
        try {
			connection.rollback();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        e.printStackTrace();
    }  
	finally {
		try {
			if(pstmt != null) pstmt.close();
		} catch (SQLException ex) {
		}

	}
	return resultMap;

}
/**
 * The method is designedd to run sql query using DB connection pool, providing db connection as a parameter
 * @param connection
 * @param sqlCommand
 */
public void runSqlQuery(Connection connection, String sqlCommand) {
	PreparedStatement pstmt = null;
	try {
		pstmt = connection.prepareStatement(sqlCommand);
		pstmt.executeUpdate();
	} 
	
	catch (Exception e)
    {
        try {
        	System.out.println("Incorrect SQL command: '" + sqlCommand + "'");
			connection.rollback();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        e.printStackTrace();
    } 
	
	finally {
		try {
			if(pstmt != null) pstmt.close();
		} catch (SQLException ex) {
		}

	}
}

/**
 * The method is designed to run sql query using DB connection pool (c3p0 library) providing db credentials
 * @param dbAddress
 * @param dbUsername
 * @param dbPassword
 * @param sqlCommand
 */
public void executeSqlQueryConnPool(String dbAddress, String dbUsername, String dbPassword, String sqlCommand) {
	Connection connection = null;
	ComboPooledDataSource dataSource = null;
	
		try {
			dataSource = TestUtils.getDataSource(dbAddress, dbUsername, dbPassword);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	runSqlQuery(connection, sqlCommand);
	if(connection != null)
		try {
			connection.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

}

/**
 * The method is designed to get Session ID by Name When browser is loaded and the User is authenticated
 * @param sessionIdName
 * @return
 */
public String getSessionIdByName(String sessionIdName) {
	Cookie cookie = getDriver().manage().getCookieNamed(sessionIdName);
	return cookie.getValue();
}

/**
 * The method is designed to find the input by given selector and input a text into it
 * @param locator
 * @param text
 * @throws InterruptedException
 */
public void inputIntoBySelector(String selector, String text)
		throws InterruptedException {
	WebElement elUnderTest = getElementBySelector(selector);
	elUnderTest.clear();
	elUnderTest.sendKeys(text);
	Thread.sleep(200);
}

/**
 * The method is designed to find the input by given xpath expression and input a text into it
 * @param locator
 * @param text
 * @throws InterruptedException
 */
public void inputIntoByXpath(String xpathExpression, String text)
		throws InterruptedException {
	WebElement elUnderTest = getElementByXPath(xpathExpression);
	elUnderTest.clear();
	elUnderTest.sendKeys(text);
	Thread.sleep(200);
}

//Create Folder Method
public void createFolder (String path) {
    File testDirectory = new File(path);
    if (!testDirectory.exists()) {
        if (testDirectory.mkdirs()) {
//            System.out.println("Directory: " + path + " is created!" );
        } else {
//            System.out.println("Failed to create directory: " + path);
        }
    } else {
//        System.out.println("Directory already exists: " + path);
    }
}

   //Take Screenshot with AShot
public Screenshot takeScreenshot (WebElement element) {
    //Take screenshot with Ashot
    Screenshot elementScreenShot = new AShot().takeScreenshot(getDriver(), element);
    /*Screenshot elementScreenShot = new AShot()
            .coordsProvider(new WebDriverCoordsProvider())
            .takeScreenshot(driver,element);*/

    //Print element size
    String size = "Height: " + elementScreenShot.getImage().getHeight() + "\n" +
            "Width: " + elementScreenShot.getImage().getWidth() + "\n";
    System.out.print("Size: " + size);

    return elementScreenShot;
}

//Write
public void writeScreenshotToFolder (Screenshot screenshot, String prefix) throws IOException {
	String testCaseName = BaseTest.getTestName();
	declareScreenShotPaths(prefix + "_" + testCaseName+"_Baseline.png", prefix + "_" + testCaseName+"_Actual.png", prefix + "_" + testCaseName + "_Diff.png");
    ImageIO.write(screenshot.getImage(), "PNG", actualImageFile);
}

//Screenshot paths
public void declareScreenShotPaths (String baseline, String actual, String diff) {
    //BaseLine, Actual, Difference Photo Paths
    baselineScreenShotPath = testScreenShotDirectory + baseline;
    actualScreenShotPath = testScreenShotDirectory + actual;
    differenceScreenShotPath = testScreenShotDirectory + diff;

    //BaseLine, Actual Photo Files
    baselineImageFile = new File(baselineScreenShotPath);
    actualImageFile = new File(actualScreenShotPath);
    differenceImageFile = new File (differenceScreenShotPath);

    //For copying difference to the parent Difference Folder
//    differenceFileForParent = new File (parentDifferencesLocation + diff);
}

//ImageMagick Compare Method
public void compareImagesWithImageMagick (String expected, String actual, String difference) throws Exception {
    // This class implements the processing of os-commands using a ProcessBuilder.
    // This is the core class of the im4java-library where all the magic takes place.
    ProcessStarter.setGlobalSearchPath(Constants.IMAGE_MAGICK_BIN_PATH);

    // This instance wraps the compare command
    CompareCmd compare = new CompareCmd();

    // Set the ErrorConsumer for the stderr of the ProcessStarter.
    compare.setErrorConsumer(StandardStream.STDERR);

    // Create ImageMagick Operation Object
    IMOperation cmpOp = new IMOperation();

    //Add option -fuzz to the ImageMagick commandline
    //With Fuzz we can ignore small changes
    cmpOp.fuzz(80.0);

    //The special "-metric" setting of 'AE' (short for "Absolute Error" count), will report (to standard error),
    //a count of the actual number of pixels that were masked, at the current fuzz factor.
    cmpOp.metric("AE");

    // Add the expected image
    cmpOp.addImage(expected);

    // Add the actual image
    cmpOp.addImage(actual);

    // This stores the difference
    cmpOp.addImage(difference);

    try {
        //Do the compare
        System.out.println ("Comparison Started!");
        compare.run(cmpOp);
        System.out.println ("Comparison Finished!");
    }
    catch (Exception ex) {
    	BaseTest.BASE_FILE_LAST = expected;
    	BaseTest.ACTUAL_FILE_LAST = actual;
    	BaseTest.DIFF_FILE_LAST = difference;
    	
        System.out.println ("Comparison Failed!");
        //Put the difference image to the global differences folder
//        Files.copy(differenceImageFile,differenceFileForParent);
        throw new TestException("The test case failed on image comparison");
    }
}

//Compare Operation
public void doComparison (Screenshot elementScreenShot) throws Exception {
    //Did we capture baseline image before?
    if (baselineImageFile.exists()){
        //Compare screenshot with baseline
        System.out.println("Comparison method will be called!\n");

//        System.out.println("Baseline: " + baselineScreenShotPath + "\n" +
//                "Actual: " + actualScreenShotPath + "\n" +
//                "Diff: " + differenceScreenShotPath);
        
//        File actualFile = new File(actualScreenShotPath);
//        File baseFile = new File(baselineScreenShotPath);
//        File diffFile = new File(differenceScreenShotPath);
        
        //Try to use IM4Java for comparison
        compareImagesWithImageMagick(baselineScreenShotPath, actualScreenShotPath, differenceScreenShotPath);
    } else {
        System.out.println("BaselineScreenshot does not exist! We put it into test screenshot folder.\n");
        //Put the screenshot to the specified folder
        ImageIO.write(elementScreenShot.getImage(), "PNG", baselineImageFile);
    }
}


/**
* wait For Hide By XPath with timeout
* 
 * @param xpath
* @param timeout
* @throws InterruptedException
*/
public void waitForHideByXPath(String xpath, long timeout)
              throws InterruptedException {
       waitByExpectedCondition(
                    ExpectedConditions.invisibilityOfElementLocated(By
                                  .xpath(xpath)), timeout);
       TimeUnit.MILLISECONDS.sleep(300);
}

/**
* wait For Hide By XPath, overloading
* 
 * @param xpath
* @throws InterruptedException
*/
public void waitForHideByXPath(String xpath)
              throws InterruptedException {
       waitForHideByXPath(xpath, DEFAULT_TIMEOUT_FOR_WAIT);
}


}
