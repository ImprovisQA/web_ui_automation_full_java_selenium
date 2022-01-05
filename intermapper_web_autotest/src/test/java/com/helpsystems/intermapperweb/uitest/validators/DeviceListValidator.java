package com.helpsystems.intermapperweb.uitest.validators;

import static com.helpsystems.intermapperweb.uitest.utils.LocatorsConstants.*;
import static com.helpsystems.intermapperweb.uitest.utils.UiDataConstants.*;
import com.helpsystems.common.util.TestUtils;

/**
 * class for Device List validation and action execution methods
 * 
 * @author Lilit Grigoryan
 * 
 */
public class DeviceListValidator extends InterMapperWebBaseValidator {
	/**
	 * 
	 * @param testUtils
	 */
	public DeviceListValidator(TestUtils testUtils) {
		super(testUtils);
	}

	/**
	 * Method to validate Device name, Port, Probe and columns on Device List page.
	 * 
	 * @throws InterruptedException
	 */
	public void validateDeviceListItems() {
		testUtils.getElementByXPath(ALL_DEVICES_XPATH);
		testUtils.getElementByXPath(INTERMAPPER_SERVER_LINK_XPATH);
		testUtils.getElementByXPath(DEVICE_LIST_XPATH);
		testUtils.getElementByXPath(String.format(DEVICE_LIST_COLUMNS_XPATH, STATUS));
		testUtils.getElementByXPath(String.format(DEVICE_LIST_COLUMNS_XPATH, NAME));
		testUtils.getElementByXPath(String.format(DEVICE_LIST_COLUMNS_XPATH, CONDITION));
		testUtils.getElementByXPath(String.format(DEVICE_LIST_COLUMNS_XPATH, DATE));
		testUtils.getElementByXPath(String.format(DEVICE_LIST_COLUMNS_XPATH, TIME));
		testUtils.getElementByXPath(String.format(DEVICE_LIST_COLUMNS_XPATH, PROBE));
		testUtils.getElementByXPath(String.format(DEVICE_LIST_COLUMNS_XPATH, PORT));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, OFFICE_LOCATION));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, CO_OP_WIRELESS));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, DEMO_NOTEBOOK));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, DEMO_WINDOWS));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, DEMO_PRINTER));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, DEMO_VOIP_PHONES));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, DEMO_NAS));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, DEMO_192_168_11_109));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, DEMO_MAC));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, DEMO_DEVICE));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, ROUTER));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, DEMO_192_168_11_222));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, DEMO_192_168_11_221));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, DEMO_192_168_11_223));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, DEMO_WAP_4));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, DEMO_WAP_2));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, DEMO_192_168_11_20));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, BACKBONE));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, DEMO_192_168_11_103));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, INTERMAPPER));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, DEMO_192_168_11_101));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, DEMO_192_168_11_220));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, DEMO_192_168_11_102));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, HTTPS));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, SNMP));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, HTTP));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, POP3));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, HTTP_HTTPS));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, CO_OP));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, SMTP));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, DEMO_SECURITY_SYSTEM));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, DEMO_WAP_3));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, DEMO_WAP_1));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, DEMO_MACOS));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, DEMO_LINUX_SERVER));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, CORPORATE_PRIMARY_ROUTER));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, CLIENT_SERVICES_STATUS_UP));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, NETWORK_OPERATIONS));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, CONNECTION_TO));
		testUtils.getElementByXPath(String.format(DEVICE_NAME_XPATH, DEMO_ROUTER));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, OFFICE_LOCATION, PORT_8181));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, CO_OP_WIRELESS, PORT_8181));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, DEMO_NOTEBOOK, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, DEMO_WINDOWS, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, DEMO_PRINTER, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, DEMO_VOIP_PHONES, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, DEMO_NAS, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, DEMO_192_168_11_109, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, DEMO_MAC, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, DEMO_DEVICE, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, ROUTER, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, DEMO_192_168_11_222, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, DEMO_192_168_11_221, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, DEMO_192_168_11_223, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, DEMO_WAP_4, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, DEMO_WAP_2, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, DEMO_192_168_11_20, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, BACKBONE, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, DEMO_192_168_11_103, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, INTERMAPPER, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, DEMO_192_168_11_101, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, DEMO_192_168_11_220, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, DEMO_192_168_11_102, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, HTTPS, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, SNMP, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, HTTP, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, POP3, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, CO_OP, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, SMTP, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, DEMO_SECURITY_SYSTEM, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, DEMO_WAP_3, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, DEMO_WAP_1, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, DEMO_MACOS, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, DEMO_LINUX_SERVER, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, CORPORATE_PRIMARY_ROUTER, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, CLIENT_SERVICES_STATUS_UP, PORT_NA));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, NETWORK_OPERATIONS, PORT_8181));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, CONNECTION_TO, PORT_23));
		testUtils.getElementByXPath(String.format(DEVICE_PORT_XPATH, DEMO_ROUTER, PORT_23));
	}

}
