package com.helpsystems.intermapperweb.uitest.validators;

import static com.helpsystems.intermapperweb.uitest.utils.LocatorsConstants.*;
import static com.helpsystems.intermapperweb.uitest.utils.UiDataConstants.*;
import com.helpsystems.common.util.TestUtils;

/**
 * class for validation and action execution methods of Help dropdown list and
 * also for the pages of that list's items
 * 
 * @author Lilit Grigoryan
 * 
 */
public class HelpValidator extends InterMapperWebBaseValidator {
	/**
	 * 
	 * @param testUtils
	 */
	public HelpValidator(TestUtils testUtils) {
		super(testUtils);
	}

	/**
	 * Method to validate the items of Help dropdown list
	 * 
	 * @throws InterruptedException
	 */
	public void validateHelpDropdownItems() {
		testUtils.getElementByXPath(String.format(HELP_DROPDOWN_ITEMS_XPATH, ABOUT_INTERMAPPER_WEB_CLIENT));
		testUtils.getElementByXPath(String.format(HELP_DROPDOWN_ITEMS_XPATH, VIEW_FILES));
		testUtils.getElementByXPath(String.format(HELP_DROPDOWN_ITEMS_XPATH, TELNET));
		testUtils.getElementByXPath(String.format(HELP_DROPDOWN_ITEMS_LINKS_XPATH, USER_GUIDE_LINK, USER_GUIDE));
		testUtils.getElementByXPath(
				String.format(HELP_DROPDOWN_ITEMS_LINKS_XPATH, DEVELOPER_GUIDE_LINK, DEVELOPER_GUIDE));

	}

	/**
	 * Method to validate links/labels and other data on About InterMapper Web
	 * Client page
	 * 
	 * @throws InterruptedException
	 */
	public void validateAboutInterMapperWebClientItems() {
		testUtils.getElementByXPath(INTERMAPPER_SERVER_LINK_XPATH);
		testUtils.getElementByXPath(ABOUT_XPATH);
		testUtils.getElementByXPath(String.format(PAGE_HEADER_XPATH, INTERMAPPER_VERSION));
		testUtils.getElementByXPath(String.format(DETAILS_IN_BOLD_XPATH, SOFTWARE_TYPE));
		testUtils.getElementByXPath(String.format(DETAILS_IN_BOLD_XPATH, REGISTRATION_INFO));
		testUtils.getElementByXPath(String.format(INFO_ABOUT_INTEREMAPPER_XPATH, LATEST_NEWS));
		testUtils.getElementByXPath(String.format(INFO_ABOUT_INTEREMAPPER_XPATH, EMAIL_TO));
		testUtils.getElementByXPath(String.format(INFO_ABOUT_LOGIN_XPATH, LOGGED_IN));
		testUtils.getElementByXPath(IM_WEB_PAGE_XPATH);
		testUtils.getElementByXPath(String.format(SUPPORT_EMAIL_XPATH, SUPPORT_EMAIL_HS));
		testUtils.getElementByXPath(String.format(COLORED_INFO_XPATH, REGISTERED_TO));

	}

	/**
	 * Method to validate links/labels and other data on View Files page
	 * 
	 * @throws InterruptedException
	 */
	public void validateViewFilesItems() {

		testUtils.getElementByXPath(INTERMAPPER_SERVER_LINK_XPATH);
		testUtils.getElementByXPath(FILE_VIEWER_XPATH);
		testUtils.getElementByXPath(String.format(SUPPORT_EMAIL_XPATH, SUPPORT_EMAIL_IM));
		testUtils.getElementByXPath(String.format(DETAILS_IN_BOLD_XPATH, FILE_PATH));
		testUtils.getElementByXPath(String.format(FOLDER_NAMES_XPATH, FILES));
		testUtils.getElementByXPath(String.format(FOLDER_NAMES_XPATH, ICONS));
		testUtils.getElementByXPath(String.format(FOLDER_NAMES_XPATH, SOUNDS));
		testUtils.getElementByXPath(String.format(FOLDER_NAMES_XPATH, MIBS));
		testUtils.getElementByXPath(String.format(FOLDER_NAMES_XPATH, PROBES));
		testUtils.getElementByXPath(String.format(FOLDER_NAMES_XPATH, TOOLS));
		testUtils.getElementByXPath(String.format(FOLDER_NAMES_XPATH, WEBPAGES));
		testUtils.getElementByXPath(String.format(FOLDER_NAMES_XPATH, FONTS));
		testUtils.getElementByXPath(String.format(FOLDER_NAMES_XPATH, EXTENSIONS));
		testUtils.getElementByXPath(String.format(FOLDER_NAMES_XPATH, MAPS));
		testUtils.getElementByXPath(String.format(FOLDER_NAMES_XPATH, TRANSLATIONS));
		testUtils.getElementByXPath(String.format(FOLDER_XPATH, FOLDER));
	}

}
