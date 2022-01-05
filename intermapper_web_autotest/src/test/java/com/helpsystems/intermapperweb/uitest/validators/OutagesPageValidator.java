package com.helpsystems.intermapperweb.uitest.validators;

import static com.helpsystems.intermapperweb.uitest.utils.LocatorsConstants.*;
import com.helpsystems.common.util.TestUtils;

/**
 * class for Map List validation and action execution methods
 * 
 * @author Armen Torunyan
 * 
 */

public class OutagesPageValidator extends InterMapperWebBaseValidator {

	/**
	 * 
	 * @param testUtils
	 */
	public OutagesPageValidator(TestUtils testUtils) {
		super(testUtils);

	}

	/**
	 * Method to validate name links/labels on Outages page.
	 * 
	 * @throws InterruptedException
	 */

	public void validateOutagesPageItems() {
		testUtils.getElementByXPath(INTERMAPPER_SERVER_OUTAGES_LABEL_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_SERVER_OUTAGES_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_SERVER_OUTAGES_CURRENT_OUTAGES_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_SERVER_OUTAGES_DATE_COLUMN_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_SERVER_OUTAGES_TIME_COLUMN_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_SERVER_OUTAGES_DEVICE_COLUMN_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_SERVER_OUTAGES_DURATION_COLUMN_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_SERVER_OUTAGES_JUMP_TO_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_SERVER_OUTAGES_CURRENT_OUTAGES_OPTION_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_SERVER_OUTAGES_PREVIOUS_OUTAGES_XPATH);
	}

}
