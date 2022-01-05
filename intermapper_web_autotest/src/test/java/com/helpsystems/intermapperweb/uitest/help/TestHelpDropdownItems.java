package com.helpsystems.intermapperweb.uitest.help;

import com.helpsystems.intermapperweb.uitest.base.HelpBaseTest;
import com.helpsystems.intermapperweb.uitest.utils.InterMapperWebConstants;

/**
 * class for validating Help dropdown list items.
 * 
 * @author Lilit Grigoryan
 * 
 */
public class TestHelpDropdownItems extends HelpBaseTest {

	@Override
	public void init() throws Exception {
	}

	@Override
	public void testBody() throws Exception {
		// Open Help menu.
		interMapperWebBaseValidator.openHelp();

	}

	@Override
	public void validation() throws Exception {
		// Validate the links/headers on the page.
		helpValidator.validateHelpDropdownItems();

	}

	@Override
	public void cleanUp() throws Exception {
	}

	@Override
	public String getOwner() {
		return InterMapperWebConstants.LILIT;
	}
}
