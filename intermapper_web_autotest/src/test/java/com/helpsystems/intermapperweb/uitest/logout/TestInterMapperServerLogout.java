package com.helpsystems.intermapperweb.uitest.logout;

import static com.helpsystems.intermapperweb.uitest.utils.UiDataConstants.LOGOUT_TAB_NAME;
import com.helpsystems.intermapperweb.uitest.base.LogoutBaseTest;
import com.helpsystems.intermapperweb.uitest.utils.InterMapperWebConstants;

/**
 * class for validating Errors page.
 * 
 * @author Armen Torunyan
 * 
 */

public class TestInterMapperServerLogout extends LogoutBaseTest {

	@Override
	public void init() throws Exception {

	}

	@Override
	public void testBody() throws Exception {
		logoutValidator.clickOnLogoutTextLink();

	}

	@Override
	public void validation() throws Exception {
		// Validate the links/headers on the page.
		logoutValidator.validateLogoutItems();

		// Validate the browser title.
		interMapperWebBaseValidator.checkBrowserTitle(LOGOUT_TAB_NAME);

	}

	@Override
	public void cleanUp() throws Exception {

	}

	@Override
	public String getOwner() {
		return InterMapperWebConstants.ARMEN;
	}

}
