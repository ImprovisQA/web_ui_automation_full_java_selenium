package com.helpsystems.intermapperweb.uitest.validators;

import static com.helpsystems.intermapperweb.uitest.utils.LocatorsConstants.*;
import static com.helpsystems.intermapperweb.uitest.utils.UiDataConstants.LOGOUT;

import com.helpsystems.common.util.TestUtils;

/**
 * class for after Logout validation and action execution methods
 * 
 * @author Armen Torunyan
 * 
 */

public class LogoutValidator extends InterMapperWebBaseValidator {

	/**
	 * 
	 * @param testUtils
	 */

	public LogoutValidator(TestUtils testUtils) {
		super(testUtils);

	}

	public void clickOnLogoutTextLink() {
		testUtils.getElementByXPath("//li/a[text()='" + LOGOUT + "']").click();
	}

	/**
	 * Method to validate name links/labels after Logging Out.
	 * 
	 * @throws InterruptedException
	 */

	public void validateLogoutItems() {
		testUtils.getElementByXPath(INTERMAPPER_SERVER_LOGOUT_LOGGED_OUT_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_SERVER_LOGOUT_LOGIN_AS_ADMIN_USER_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_SERVER_LOGOUT_LOGIN_AS_GUEST_USER_XPATH);

	}

}
