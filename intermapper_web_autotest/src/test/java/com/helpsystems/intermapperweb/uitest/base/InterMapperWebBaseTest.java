package com.helpsystems.intermapperweb.uitest.base;

import org.junit.Test;

import com.helpsystems.common.base.BaseTest;
import com.helpsystems.common.base.CleanUpException;
import com.helpsystems.common.base.CustomBase;
import com.helpsystems.common.base.InitException;
import com.helpsystems.common.base.TestException;
import com.helpsystems.common.base.ValidateException;
import com.helpsystems.intermapperweb.uitest.validators.*;

public abstract class InterMapperWebBaseTest extends BaseTest implements CustomBase {

	protected MapListValidator mapListValidator;
	protected ErrorsPageValidator errorPageValidator;
	protected FullPageValidator fullPageValidator;
	protected OutagesPageValidator outagesPageValidator;
	protected DeviceListValidator deviceListValidator;
	protected InterMapperWebBaseValidator interMapperWebBaseValidator;
	protected StatisticsValidator statisticsValidator;
	protected HelpValidator helpValidator;
	protected LogoutValidator logoutValidator;
	protected LoginValidator loginValidator;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		interMapperWebBaseValidator = getValidator(InterMapperWebBaseValidator.class);
		mapListValidator = getValidator(MapListValidator.class);
		errorPageValidator = getValidator(ErrorsPageValidator.class);
		fullPageValidator = getValidator(FullPageValidator.class);
		outagesPageValidator = getValidator(OutagesPageValidator.class);
		deviceListValidator = getValidator(DeviceListValidator.class);
		statisticsValidator = getValidator(StatisticsValidator.class);
		helpValidator = getValidator(HelpValidator.class);
		logoutValidator = getValidator(LogoutValidator.class);
		loginValidator = getValidator(LoginValidator.class);
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void runner() throws InitException, TestException, CleanUpException, ValidateException {
		testRunner();
	}

}