package com.helpsystems.intermapperweb.uitest.devicelist;

import com.helpsystems.intermapperweb.uitest.base.DeviceListBaseTest;
import com.helpsystems.intermapperweb.uitest.utils.InterMapperWebConstants;
import static com.helpsystems.intermapperweb.uitest.utils.UiDataConstants.*;

/**
 * class for validating Device List page data.
 * 
 * @author Lilit Grigoryan
 * 
 */
public class TestDeviceList extends DeviceListBaseTest {

	@Override
	public void init() throws Exception {
	}

	@Override
	public void testBody() throws Exception {
		// Go to Device List page
		interMapperWebBaseValidator.openDeviceList();
	}

	@Override
	public void validation() throws Exception {
		// Validate the links/headers and other data on the page.
		deviceListValidator.validateDeviceListItems();

		// Validate the browser title.
		interMapperWebBaseValidator.checkBrowserTitle(DEVICE_LIST);
	}

	@Override
	public void cleanUp() throws Exception {
	}

	@Override
	public String getOwner() {
		return InterMapperWebConstants.LILIT;
	}
}
