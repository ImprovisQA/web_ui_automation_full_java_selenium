package com.helpsystems.intermapperweb.uitest.validators;

import static com.helpsystems.intermapperweb.uitest.utils.LocatorsConstants.*;
import com.helpsystems.common.util.TestUtils;

/**
 * class for Errors page validation
 * 
 * @author Armen Torunyan
 * 
 */

public class ErrorsPageValidator extends InterMapperWebBaseValidator {

	/**
	 * 
	 * @param testUtils
	 */

	public ErrorsPageValidator(TestUtils testUtils) {
		super(testUtils);

	}

	/**
	 * Method to validate name links/labels on Errors page.
	 * 
	 * @throws InterruptedException
	 */
	public void validateErrorsPageItems() {

		testUtils.getElementByXPath(INTERMAPPER_SERVER_ERRORS_LABEL_ERRORS_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_SERVER_ERRORS_LABEL_PANEL_ERRORS_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_SERVER_ERRORS_DEVICES_AND_LINKS_W_ERRORS_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_SERVER_ERRORS_DEVICE_LIST_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_ERRORS_LABELS_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_LINKS_LABELS_XPATH);
	}

}
