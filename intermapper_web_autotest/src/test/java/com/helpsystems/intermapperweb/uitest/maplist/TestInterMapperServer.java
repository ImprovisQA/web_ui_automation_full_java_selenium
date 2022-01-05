package com.helpsystems.intermapperweb.uitest.maplist;

import com.helpsystems.intermapperweb.uitest.base.MapListBaseTest;
import com.helpsystems.intermapperweb.uitest.utils.InterMapperWebConstants;
import static com.helpsystems.intermapperweb.uitest.utils.UiDataConstants.*;

/**
 * class for validating Map List data.
 * 
 * @author Anzhela Sarukhanyan
 * 
 */
public class TestInterMapperServer extends MapListBaseTest {

	@Override
	public void init() throws Exception {
	}

	@Override
	public void testBody() throws Exception {
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
		return InterMapperWebConstants.ANZHELA;
	}
}
