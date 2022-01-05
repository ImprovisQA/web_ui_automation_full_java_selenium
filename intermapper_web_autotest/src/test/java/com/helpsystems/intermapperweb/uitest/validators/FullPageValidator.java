package com.helpsystems.intermapperweb.uitest.validators;

import static com.helpsystems.intermapperweb.uitest.utils.LocatorsConstants.*;
import com.helpsystems.common.util.TestUtils;

/**
 * class for Full page validation
 * 
 * @author Armen Torunyan
 * 
 */

public class FullPageValidator extends InterMapperWebBaseValidator {

	/**
	 * 
	 * @param testUtils
	 */

	public FullPageValidator(TestUtils testUtils) {
		super(testUtils);

	}

	/**
	 * Method to validate name links/labels on Full page.
	 * 
	 * @throws InterruptedException
	 */

	public void validateFullPageItems() {
		testUtils.getElementByXPath(INTERMAPPER_SERVER_FULL_LABEL_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_SERVER_FULL_DEVICES_AND_LINKS_FULL_LABEL_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_SERVER_FULL_LIST_OF_DEVICES_AND_LINKS_LABEL_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_ERRORS_LABELS_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_FULL_LINKS_LABELS_XPATH);
	}

}
