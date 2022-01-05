package com.helpsystems.intermapperweb.uitest.full;

import static com.helpsystems.intermapperweb.uitest.utils.UiDataConstants.INTERMAPPER_FULL_DEVICE_LIST;
import com.helpsystems.intermapperweb.uitest.base.FullBaseTest;
import com.helpsystems.intermapperweb.uitest.utils.InterMapperWebConstants;

/**
 * class for validating Full page data.
 * 
 * @author Armen Torunyan
 * 
 */

public class TestInterMapperServerFull extends FullBaseTest {

	@Override
	public void init() throws Exception {

	}

	@Override
	public void testBody() throws Exception {
		// Opens the Full page
		interMapperWebBaseValidator.openFullPage();

	}

	@Override
	public void validation() throws Exception {
		// Validate the links/headers on the page.
		fullPageValidator.validateFullPageItems();

		// Validate the browser title.
		interMapperWebBaseValidator.checkBrowserTitle(INTERMAPPER_FULL_DEVICE_LIST);

	}

	@Override
	public void cleanUp() throws Exception {

	}

	@Override
	public String getOwner() {
		return InterMapperWebConstants.ARMEN;
	}

}
