package com.helpsystems.intermapperweb.uitest.validators;

import static com.helpsystems.intermapperweb.uitest.utils.LocatorsConstants.*;
import com.helpsystems.common.util.TestUtils;

/**
 * class for Statistics Page validation and action execution methods
 * 
 * @author Lilit Grigoryan
 * 
 */

public class StatisticsValidator extends InterMapperWebBaseValidator {
	/**
	 * 
	 * @param testUtils
	 */

	public StatisticsValidator(TestUtils testUtils) {
		super(testUtils);

	}

	/**
	 * Method to validate Statistics page headers/links
	 * 
	 * @throws InterruptedException
	 */
	public void validateStatisticsPageItems() {
		testUtils.getElementByXPath(STATISTICS_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_SERVER_LINK_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_STATISTICS_XPATH);
		testUtils.getElementByXPath(CONFIGURATION_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_RUNNING_TIME_XPATH);
		testUtils.getElementByXPath(WINDOWS_RUNNING_TIME_XPATH);
		testUtils.getElementByXPath(WINDOWS_VERSION_XPATH);
		testUtils.getElementByXPath(NET_SOFTWARE_VERSION_XPATH);
		testUtils.getElementByXPath(NETWORK_INTERFACES_XPATH);
		testUtils.getElementsByXPath(INTERFACES_XPATH);
	}
}
