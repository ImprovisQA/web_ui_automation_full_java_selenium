package com.helpsystems.intermapperweb.uitest.validators;

import static com.helpsystems.intermapperweb.uitest.utils.LocatorsConstants.*;
import static com.helpsystems.intermapperweb.uitest.utils.UiDataConstants.*;
import com.helpsystems.common.util.TestUtils;

/**
 * class for Map List validation and action execution methods
 * 
 * @author Anzhela Sarukhanyan
 * 
 */
public class MapListValidator extends InterMapperWebBaseValidator {
	/**
	 * 
	 * @param testUtils
	 */
	public MapListValidator(TestUtils testUtils) {
		super(testUtils);
	}

	/**
	 * Method to validate name links/labels on Map List page.
	 * 
	 * @throws InterruptedException
	 */
	public void validateMapListItems() {
		testUtils.getElementByXPath(INTERMAPPER_SERVER_LABEL_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_MAPS_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_MAPS_SUB_TEXT_XPATH);
		testUtils.getElementByXPath(DEMO_MAIN_MAP_XPATH);
		testUtils.getElementByXPath(DEMO_CO_OP_WIRELESS_XPATH);
		testUtils.getElementByXPath(DEMO_NETWORK_OPERATIONS_XPATH);
		testUtils.getElementByXPath(DEMO_OFFICE_LOCATIONS_XPATH);
	}

	/**
	 * Method to validate items of Interval dropdown.
	 * 
	 * @throws InterruptedException
	 */
	public void validateIntervalDropdownItems() {

		testUtils.getElementByXPath(String.format(INTERVAL_DROPDOWN_ITEMS_XPATH, NEVER));
		testUtils.getElementByXPath(String.format(INTERVAL_DROPDOWN_ITEMS_XPATH, SECONDS5));
		testUtils.getElementByXPath(String.format(INTERVAL_DROPDOWN_ITEMS_XPATH, SECONDS10));
		testUtils.getElementByXPath(String.format(INTERVAL_DROPDOWN_ITEMS_XPATH, SECONDS30));
		testUtils.getElementByXPath(String.format(INTERVAL_DROPDOWN_ITEMS_XPATH, MINUTE1));
		testUtils.getElementByXPath(String.format(INTERVAL_DROPDOWN_ITEMS_XPATH, MINUTES2));
		testUtils.getElementByXPath(String.format(INTERVAL_DROPDOWN_ITEMS_XPATH, MINUTES5));
		testUtils.getElementByXPath(String.format(INTERVAL_DROPDOWN_ITEMS_XPATH, MINUTES10));
		testUtils.getElementByXPath(String.format(INTERVAL_DROPDOWN_ITEMS_XPATH, MINUTES30));
		testUtils.getElementByXPath(String.format(INTERVAL_DROPDOWN_ITEMS_XPATH, HOUR1));

	}

	/**
	 * Method to validate Footer data.
	 * 
	 * @throws InterruptedException
	 */
	public void validateFooterItems() {

		testUtils.getElementByXPath(String.format(FOOTER_LINK_ITEMS_XPATH, INTERMAPPER_COM, INTERMAPPER));
		testUtils.getElementByXPath(String.format(FOOTER_LINK_ITEMS_XPATH, HELPSYSTEMS_COM, HELPSYSTEMS_LLC));
		testUtils.getElementByXPath(FOOTER_TEXT_XPATH);
		testUtils.getElementByXPath(LAST_REFRESHED_XPATH);

	}
}
