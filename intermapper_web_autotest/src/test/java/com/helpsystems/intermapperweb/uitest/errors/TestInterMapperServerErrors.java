package com.helpsystems.intermapperweb.uitest.errors;

import static com.helpsystems.intermapperweb.uitest.utils.UiDataConstants.INTERMAPPER_DEVICES_IN_ERROR;
import com.helpsystems.intermapperweb.uitest.base.ErrorsBaseTest;
import com.helpsystems.intermapperweb.uitest.utils.InterMapperWebConstants;

/**
 * class for validating Errors page.
 * 
 * @author Armen Torunyan
 * 
 */

public class TestInterMapperServerErrors extends ErrorsBaseTest {

	@Override
	public void init() throws Exception {

	}

	@Override
	public void testBody() throws Exception {
		interMapperWebBaseValidator.openErrorsPage();

	}

	@Override
	public void validation() throws Exception {
		// Validate the links/headers on the page.
		errorPageValidator.validateErrorsPageItems();

		// Validate the browser title.
		interMapperWebBaseValidator.checkBrowserTitle(INTERMAPPER_DEVICES_IN_ERROR);

	}

	@Override
	public void cleanUp() throws Exception {

	}

	@Override
	public String getOwner() {
		return InterMapperWebConstants.ARMEN;
	}

}
