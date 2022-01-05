package com.helpsystems.common.util;

import org.openqa.selenium.WebDriver;

import static com.helpsystems.common.util.Constants.REGRESSION_DB_ADDRESS;
import static com.helpsystems.common.util.Constants.REGRESSION_DB_PASSWORD;
import static com.helpsystems.common.util.Constants.REGRESSION_DB_USERNAME;

public class ClearLastRunResults {

	private final static String mergeTablesQuery = "INSERT INTO results (tc_id, sbt_id, run_type_id, tc_res, tc_cons_log, tc_fail_tr, tc_run_time, run_date, scr_shot) "
			+ "(SELECT tc_id, sbt_id, run_type_id, tc_res, tc_cons_log, tc_fail_tr, tc_run_time, run_date, scr_shot FROM lastresults)";
	private final static String deleteAllFromLastResults = "DELETE FROM public.lastresults";
	private static final WebDriver WebDriver = null;
	public static void main(String[] args) {
		TestUtils testUtils = new TestUtils(WebDriver);
		
		testUtils.executeSqlQuery(REGRESSION_DB_ADDRESS, REGRESSION_DB_USERNAME,
				REGRESSION_DB_PASSWORD, mergeTablesQuery);
		testUtils.executeSqlQuery(REGRESSION_DB_ADDRESS, REGRESSION_DB_USERNAME,
				REGRESSION_DB_PASSWORD, deleteAllFromLastResults);
		
	}
	
	
}
