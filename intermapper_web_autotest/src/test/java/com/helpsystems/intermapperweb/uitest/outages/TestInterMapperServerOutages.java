package com.helpsystems.intermapperweb.uitest.outages;

import static com.helpsystems.intermapperweb.uitest.utils.UiDataConstants.INTERMAPPER_SERVER_OUTAGES;
import com.helpsystems.intermapperweb.uitest.base.OutagesBaseTest;
import com.helpsystems.intermapperweb.uitest.utils.InterMapperWebConstants;

/**
 * class for validating Outages Page data.
 * 
 * @author Armen Torunyan
 * 
 */

public class TestInterMapperServerOutages extends OutagesBaseTest {

	@Override
	public void init() throws Exception {

	}

	@Override
	public void testBody() throws Exception {

		interMapperWebBaseValidator.openOutagesPage();

	}

	@Override
	public void validation() throws Exception {
		// Validate the links/headers on the page.
		outagesPageValidator.validateOutagesPageItems();

		// Validate the browser title.
		interMapperWebBaseValidator.checkBrowserTitle(INTERMAPPER_SERVER_OUTAGES);

	}

	@Override
	public void cleanUp() throws Exception {

	}

	@Override
	public String getOwner() {
		return InterMapperWebConstants.ARMEN;
	}

}
