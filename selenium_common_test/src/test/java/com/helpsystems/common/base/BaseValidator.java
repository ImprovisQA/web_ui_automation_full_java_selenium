package com.helpsystems.common.base;

import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import com.helpsystems.common.util.TestUtils;

public class BaseValidator {

	protected TestUtils testUtils;
	protected Logger LOGGER;
	protected WebDriver driver;

	public BaseValidator(TestUtils testUtils) {
		super();
		this.testUtils = testUtils;
		this.driver = testUtils.getDriver();
		setLogger(testUtils.getLogger());
	}
	/**
	 * 
	 * set Logger
	 *
	 * @param logger
	 */
	public void setLogger(Logger logger){
		LOGGER = logger;
	}

	/**
	 * @return the driver
	 */
	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * @param driver
	 *            the driver to set
	 */
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}


	/**
	 * @return the testUtils
	 */
	public TestUtils getTestUtils() {
		return testUtils;
	}

	/**
	 * @param testUtils
	 *            the testUtils to set
	 */
	public void setTestUtils(TestUtils testUtils) {
		this.testUtils = testUtils;
	}
	
	public <T extends BaseValidator> T newInstance(Class<T> validatorClass) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			SecurityException {
		return newInstance(validatorClass, this);
	}

	@SuppressWarnings("unchecked")
	private static <T extends BaseValidator> T newInstance(Class<T> validatorClass, BaseValidator baseValidator) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, SecurityException {
		T validator = null;
		if (baseValidator != null) {
			validator = (T) validatorClass.getConstructors()[0].newInstance(baseValidator.getDriver());
		}
		return validator;
	}
}
