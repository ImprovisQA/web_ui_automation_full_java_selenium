package com.helpsystems.intermapperweb.uitest.help;

import static com.helpsystems.intermapperweb.uitest.utils.UiDataConstants.ABOUT;

import com.helpsystems.intermapperweb.uitest.base.HelpBaseTest;
import com.helpsystems.intermapperweb.uitest.utils.InterMapperWebConstants;

/**
 * class for validating About InterMapperWebClient page data.
 * 
 * @author Lilit Grigoryan
 * 
 */
public class TestAboutInterMapperWebClient extends HelpBaseTest {

	@Override
	public void init() throws Exception {
	}

	@Override
	public void testBody() throws Exception {

		// Open Help menu.
		interMapperWebBaseValidator.openHelp();

		// Open About InterMapper Web Client page
		interMapperWebBaseValidator.openAboutInterMapperWebClient();

	}

	@Override
	public void validation() throws Exception {

		// Validate the links/headers on the page.
		helpValidator.validateAboutInterMapperWebClientItems();

		// Validate the browser title.
		interMapperWebBaseValidator.checkBrowserTitle(ABOUT);

	}

	@Override
	public void cleanUp() throws Exception {
	}

	@Override
	public String getOwner() {
		return InterMapperWebConstants.LILIT;
	}
}
