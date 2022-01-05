package com.helpsystems.intermapperweb.uitest.statistics;

import com.helpsystems.intermapperweb.uitest.base.StatisticstBaseTest;
import com.helpsystems.intermapperweb.uitest.utils.InterMapperWebConstants;
import static com.helpsystems.intermapperweb.uitest.utils.UiDataConstants.*;

/**
 * class for validating Statistics page data.
 * 
 * @author Lilit Grigoryan
 * 
 */
public class TestStatistics extends StatisticstBaseTest {

	@Override
	public void init() throws Exception {
	}

	@Override
	public void testBody() throws Exception {
		// Go to Statistics page
		interMapperWebBaseValidator.openStatistics();
	}

	@Override
	public void validation() throws Exception {
		// Validate the links/headers and other data on the page.
		statisticsValidator.validateStatisticsPageItems();

		// Validate the browser title.
		interMapperWebBaseValidator.checkBrowserTitle(STATS);
	}

	@Override
	public void cleanUp() throws Exception {
	}

	@Override
	public String getOwner() {
		return InterMapperWebConstants.LILIT;
	}
}
