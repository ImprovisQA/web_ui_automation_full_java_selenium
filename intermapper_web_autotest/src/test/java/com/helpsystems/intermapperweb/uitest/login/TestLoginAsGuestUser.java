package com.helpsystems.intermapperweb.uitest.login;

import static com.helpsystems.intermapperweb.uitest.utils.UiDataConstants.MAP_LIST;
import com.helpsystems.intermapperweb.uitest.base.LoginBaseTest;
import com.helpsystems.intermapperweb.uitest.utils.InterMapperWebConstants;

/**
 * class for logging in as guest user and for validating Map List items.
 * 
 * @author Armen Torunyan
 * 
 */

public class TestLoginAsGuestUser extends LoginBaseTest {

	@Override
	public void init() throws Exception {

	}

	@Override
	public void testBody() throws Exception {
		// click on logout button to log out from web page
		logoutValidator.clickOnLogoutTextLink();

		// click on login as admin user textlink
		loginValidator.clickOnLoginAsGuestUserTextLink();

		// send credentials through URL and login
		loginValidator.login();

	}

	@Override
	public void validation() throws Exception {

		// Validate the links/headers on the page.
		mapListValidator.validateMapListItems();

		// Validate the browser title.
		interMapperWebBaseValidator.checkBrowserTitle(MAP_LIST);

	}

	@Override
	public void cleanUp() throws Exception {

	}

	@Override
	public String getOwner() {
		return InterMapperWebConstants.ARMEN;
	}

}
