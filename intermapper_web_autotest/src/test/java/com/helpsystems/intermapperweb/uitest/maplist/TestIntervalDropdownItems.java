package com.helpsystems.intermapperweb.uitest.maplist;

import com.helpsystems.intermapperweb.uitest.base.MapListBaseTest;
import com.helpsystems.intermapperweb.uitest.utils.InterMapperWebConstants;

/**
 * class for validating Interval dropdown data.
 * 
 * @author Lilit Grigoryan
 * 
 */
public class TestIntervalDropdownItems extends MapListBaseTest {

	@Override
	public void init() throws Exception {
	}

	@Override
	public void testBody() throws Exception {
	}

	@Override
	public void validation() throws Exception {

		// Validate Interval dropdown list items.
		mapListValidator.validateIntervalDropdownItems();

	}

	@Override
	public void cleanUp() throws Exception {
	}

	@Override
	public String getOwner() {
		return InterMapperWebConstants.LILIT;
	}
}
