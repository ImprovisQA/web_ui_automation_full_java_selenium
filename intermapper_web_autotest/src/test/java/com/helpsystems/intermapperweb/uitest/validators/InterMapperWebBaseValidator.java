package com.helpsystems.intermapperweb.uitest.validators;

import static com.helpsystems.intermapperweb.uitest.utils.LocatorsConstants.*;
import static com.helpsystems.intermapperweb.uitest.utils.UiDataConstants.*;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;

import com.helpsystems.common.base.BaseValidator;
import com.helpsystems.common.util.TestUtils;

/**
 * Base class for all Validators
 * 
 * @author
 * 
 */
public class InterMapperWebBaseValidator extends BaseValidator {

	public InterMapperWebBaseValidator(TestUtils testUtils) {
		super(testUtils);
	}

	/**
	 * Method to open Pages.
	 * 
	 * @throws InterruptedException
	 */
	public void openPage(String pageName) {
		testUtils.getElementByXPath(String.format(PAGE_NAME_XPATH, pageName)).click();
	}

	/**
	 * Method to check browser title.
	 * 
	 * @throws InterruptedException
	 */
	public void checkBrowserTitle(String title) {
		String actualTitle = getDriver().getTitle();
		assertTrue(title + " does not match with " + actualTitle, actualTitle.equals(title));
	}

	/**
	 * Method to validate the page header.
	 * 
	 * @throws InterruptedException
	 */
	public void validatePageHeader(String header) {
		testUtils.getElementByXPath(String.format(PAGE_HEADER_XPATH, header));
	}

	/**
	 * Method to open Errors page.
	 * 
	 * @throws InterruptedException
	 */
	public void openErrorsPage() {
		openPage(ERRORS_PAGE_NAME);
	}

	/**
	 * Method to open Full page.
	 * 
	 * @throws InterruptedException
	 */
	public void openFullPage() {
		openPage(FULL_PAGE_NAME);

	}

	/**
	 * Method to open Outages page.
	 * 
	 * @throws InterruptedException
	 */
	public void openOutagesPage() {
		openPage(OUTAGES_PAGE_NAME);
	}

	/**
	 * Method to open Device List page.
	 * 
	 * @throws InterruptedException
	 */
	public void openDeviceList() {
		openPage(DEVICE_LIST);
	}

	/**
	 * Method to open Statistics page.
	 * 
	 * @throws InterruptedException
	 */
	public void openStatistics() {
		openPage(STATISTICS);
	}

	/**
	 * Method to open Help page.
	 * 
	 * @throws InterruptedException
	 */
	public void openHelp() {
		openPage(HELP);
	}

	/**
	 * Method to validate count of element in the page.
	 * 
	 * @throws InterruptedException
	 */

	public void validateElementsByCount(String name, int count) {
		int cnt = (testUtils.getElementsByXPath(String.format(DEVICE_NAME_XPATH, name))).size();
		assert (cnt == count);
	}

	/**
	 * Method to open About InterMapper Web Client page.
	 * 
	 * @throws InterruptedException
	 */

	public void openAboutInterMapperWebClient() {
		openPage(ABOUT_INTERMAPPER_WEB_CLIENT);
	}

	/**
	 * Method to open a View Files page.
	 * 
	 * @throws InterruptedException
	 */

	public void openViewFiles() {
		openPage(VIEW_FILES);
	}

	/**
	 * Method to open a Telnet page.
	 * 
	 * @throws InterruptedException
	 */

	public void openTelnet() {
		openPage(TELNET);
	}

	/**
	 * Method to get Current TZ offset.
	 * 
	 * @throws InterruptedException
	 */
	public static final String getTZOffset() {

		Calendar now = Calendar.getInstance();
		ZonedDateTime CURRENT_TZ = Instant.now().atZone(ZoneId.of(now.getTimeZone().toZoneId().getId()));

		return String.format("%tz", CURRENT_TZ);

	}
}