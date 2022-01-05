package com.helpsystems.intermapperweb.uitest.help;

import static com.helpsystems.intermapperweb.uitest.utils.UiDataConstants.VIEW_FILES;

import com.helpsystems.intermapperweb.uitest.base.HelpBaseTest;
import com.helpsystems.intermapperweb.uitest.utils.InterMapperWebConstants;

/**
 * class for validating View Files page data.
 * 
 * @author Lilit Grigoryan
 * 
 */
public class TestViewFiles extends HelpBaseTest {

	@Override
	public void init() throws Exception {
	}

	@Override
	public void testBody() throws Exception {

		// Open Help menu.
		interMapperWebBaseValidator.openHelp();

		// Open View Files page
		interMapperWebBaseValidator.openViewFiles();

	}

	@Override
	public void validation() throws Exception {

		// Validate the links/headers on the page.
		helpValidator.validateViewFilesItems();

		// Validate the browser title.
		interMapperWebBaseValidator.checkBrowserTitle(VIEW_FILES);

	}

	@Override
	public void cleanUp() throws Exception {
	}

	@Override
	public String getOwner() {
		return InterMapperWebConstants.LILIT;
	}
}
