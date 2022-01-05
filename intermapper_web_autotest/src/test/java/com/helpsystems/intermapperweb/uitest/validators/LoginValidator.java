package com.helpsystems.intermapperweb.uitest.validators;

import static com.helpsystems.intermapperweb.uitest.utils.LocatorsConstants.*;
import static com.helpsystems.common.util.Constants.HOST;
import com.helpsystems.common.util.TestUtils;

/**
 * class for after Login validation and action execution methods
 * 
 * @author Armen Torunyan
 * 
 */

public class LoginValidator extends InterMapperWebBaseValidator {

	/**
	 * 
	 * @param testUtils
	 */

	public LoginValidator(TestUtils testUtils) {
		super(testUtils);

	}

	/**
	 * Method to click on the Log in as admin user text link.
	 * 
	 * @throws InterruptedException
	 */

	public void clickOnLoginAsAdminUserTextLink() throws InterruptedException {
		testUtils.getElementByXPath(INTERMAPPER_SERVER_LOGOUT_LOGIN_AS_ADMIN_USER_XPATH).click();
	}

	public void clickOnLoginAsGuestUserTextLink() throws InterruptedException {
		testUtils.getElementByXPath(INTERMAPPER_SERVER_LOGOUT_LOGIN_AS_GUEST_USER_XPATH).click();
	}

	/**
	 * Method to validate name links/labels after Logging Out.
	 * 
	 * @throws InterruptedException
	 */
	
	public void login() throws InterruptedException {
		getDriver().get("http://" + HOST + "");
	}
}
