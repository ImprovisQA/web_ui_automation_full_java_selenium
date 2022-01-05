package com.helpsystems.common.util;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class StoreTestResultAutomate {
	private static final WebDriver WebDriver = null;
	static TestUtils testUtils;
	private static Integer runTypeId = null;
	private static Integer TEST_CASE_ID = null;
	private static Integer SUB_TEST_ID = 0;
	private static String runDate = null;
	private static String exceptionMessage = "";
	private  static Double truncatedSeconds = 0.0;

	public static void main(String[] args) throws IOException {
//		These local variables should be removed once we have have 100% stable rerun generation.
//		String ONE_THREAD_PACKAGES = "tmp";
//		String REGRESSION_RUN_TYPE = "VA Integrator First Run";
		String REGRESSION_DB_ADDRESS = "jdbc:postgresql://10.67.72.24:5432/automate";
		String REGRESSION_DB_USERNAME = "postgres";
		String REGRESSION_DB_PASSWORD = "Qw123456";
//		String REGRESSION_RUN_TYPE = "Primary";
		String taskName = args[0];
		String tcNameDescr = args[1];
		String status = args[2];
		String failTrace = args[3];
		String owner = args[4];
		String REGRESSION_RUN_TYPE = args[5];
//		String taskName = "task1";
//		String tcNameDescr = "mySampleTC and description";
//		String status = "FAIL";
//		String failTrace = "example of failure trace";
		
		
		switch(status) {
		case "PASS": 
			status = "pass";
			break;
		case "FAIL": 
			status = "failed";
			break;
		case "INCONCLUSIVE": 
			status = "INCONCLUSIVE";
			break;
		case "SKIPPED": 
			status = "skipped";
			break;
		default: break;
		}
		
		String ownerName = null;
		String tcName = null;
		String pckgName = null;
		Integer pckgId = null;
		Integer ownerId = null;
		Integer ownerIdFromTestcase = null;
		Connection connection = null;
		TestUtils testUtils = new TestUtils(WebDriver);


		ComboPooledDataSource dataSource;
		try {
			dataSource = TestUtils.getDataSource(REGRESSION_DB_ADDRESS, REGRESSION_DB_USERNAME,
					REGRESSION_DB_PASSWORD);
			System.out.println(dataSource.getNumBusyConnections());
			connection = dataSource.getConnection();
		} catch (PropertyVetoException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String currentRunPackageName = taskName;
		String sqlCommandGetPckgName = "SELECT package_name from package where package_name='" + currentRunPackageName
				+ "'";
		String sqlCommandGetPckgId = "SELECT package_id from package where package_name='" + currentRunPackageName
				+ "'";
		String sqlCommandGetOwnerName = "SELECT tc_owner_name from owner where tc_owner_name='" + owner + "'";
		String sqlCommandGetOwnerId = "SELECT tc_owner_id from owner where tc_owner_name='" + owner + "'";
		String sqlCommandGetRunTypeId = "SELECT run_type_id from runtype where run_type_name='" + REGRESSION_RUN_TYPE
				+ "'";
		// check the package table content
		// List<Object> pckgObjects =
		// testUtils.executeUpdateSqlQuery(REGRESSION_DB_ADDRESS,
		// REGRESSION_DB_USERNAME,
		// REGRESSION_DB_PASSWORD, sqlCommandGetPckgName,
		// "package_name").get("package_name");
		List<Object> pckgObjects = testUtils
				.executeSqlQueryReturnSelectedColumns(connection, sqlCommandGetPckgName, "package_name")
				.get("package_name");
		if (pckgObjects.size() != 0) {
			pckgName = pckgObjects.get(0).toString();
		}
		// add package name name if it is missing
		if (pckgName == null) {
			String sqlCommandPack = "INSERT INTO package (package_name) VALUES ('" + currentRunPackageName + "')";
			testUtils.runSqlQuery(connection, sqlCommandPack);
		}

		// check the owner table content
		List<Object> ownerObjects = testUtils
				.executeSqlQueryReturnSelectedColumns(connection, sqlCommandGetOwnerName, "tc_owner_name")
				.get("tc_owner_name");
		if (ownerObjects.size() != 0) {
			ownerName = ownerObjects.get(0).toString();
		}

		// add owner Name if it is missing
		if (ownerName == null) {
			String sqlCommandOwner = "INSERT INTO owner (tc_owner_name) VALUES ('" + owner + "')";
			testUtils.runSqlQuery(connection, sqlCommandOwner);
		}

		// get the package id from package table
		// List<Object> pckgIdObjects =
		// testUtils.executeUpdateSqlQuery(REGRESSION_DB_ADDRESS,
		// REGRESSION_DB_USERNAME,
		// REGRESSION_DB_PASSWORD, sqlCommandGetPckgId, "package_id").get("package_id");
		List<Object> pckgIdObjects = testUtils
				.executeSqlQueryReturnSelectedColumns(connection, sqlCommandGetPckgId, "package_id").get("package_id");

		if (pckgIdObjects.size() != 0) {
			pckgId = Integer.valueOf(pckgIdObjects.get(0).toString());
		}

		// get the owner id from owner table
		List<Object> ownerIdObjects = testUtils
				.executeSqlQueryReturnSelectedColumns(connection, sqlCommandGetOwnerId, "tc_owner_id")
				.get("tc_owner_id");
		if (ownerIdObjects.size() != 0) {
			ownerId = Integer.valueOf(ownerIdObjects.get(0).toString());
		}

		// get the run type id from runtype table
		List<Object> ryntypeIdObjects = testUtils
				.executeSqlQueryReturnSelectedColumns(connection, sqlCommandGetRunTypeId, "run_type_id")
				.get("run_type_id");
		if (ryntypeIdObjects.size() != 0) {
			runTypeId = Integer.valueOf(ryntypeIdObjects.get(0).toString());
		}

		String currentRunTestCaseName = tcNameDescr;

		// get the TC id with current run test case name and current run package id
		String sqlCommandGetTcNameAndPackageID = "SELECT tc_id from testcase where tc_name='" + currentRunTestCaseName
				+ "' AND package_id='" + pckgId + "'";
		List<Object> tcIDobjectsWithPackageID = testUtils
				.executeSqlQueryReturnSelectedColumns(connection, sqlCommandGetTcNameAndPackageID, "tc_id")
				.get("tc_id");

		// if test case is missing then add it into table
		if (tcIDobjectsWithPackageID.size() == 0) {
			String sqlCommandPutTcNameAndPackageId = "INSERT INTO testcase (tc_name, package_id, tc_owner_id) VALUES ('"
					+ currentRunTestCaseName + "', '" + pckgId + "', '" + ownerId + "')";
			testUtils.runSqlQuery(connection, sqlCommandPutTcNameAndPackageId);
		}

		// if test case exists but with another owner

		if (tcIDobjectsWithPackageID.size() != 0) {
			String sqlCommandGetOwnerIDFromTestCases = "SELECT tc_owner_id from testcase where tc_name='"
					+ currentRunTestCaseName + "' AND package_id='" + pckgId + "'";
			List<Object> ownerIDobjectsFromTestCases = testUtils
					.executeSqlQueryReturnSelectedColumns(connection, sqlCommandGetOwnerIDFromTestCases, "tc_owner_id")
					.get("tc_owner_id");
			if (ownerIDobjectsFromTestCases.size() != 0) {
				ownerIdFromTestcase = Integer.valueOf(ownerIDobjectsFromTestCases.get(0).toString());
			}

			if (ownerId != ownerIdFromTestcase) {
				String updateOwnerQuery = "UPDATE testcase SET tc_owner_id='" + ownerId + "' WHERE tc_name='"
						+ currentRunTestCaseName + "' AND package_id='" + pckgId + "'";
				testUtils.runSqlQuery(connection, updateOwnerQuery);
			}

		}

		// get the test case ID
		String sqlCommandGetTcID = "SELECT tc_id from testcase where tc_name='" + currentRunTestCaseName
				+ "' AND package_id='" + pckgId + "'";
		List<Object> tcIDobjects = testUtils
				.executeSqlQueryReturnSelectedColumns(connection, sqlCommandGetTcID, "tc_id").get("tc_id");
		TEST_CASE_ID = Integer.valueOf(tcIDobjects.get(0).toString());

		// ********************************* ADDING SUB TEST
		// *********************************
		String subTestName = "";
		// get the sub test id with current run sub test name and current run test case
		// id
		String sqlCommandGetSubTestNameByTestCaseID = "SELECT sbt_id from subtest where sbt_name='" + subTestName
				+ "' AND tc_id='" + Integer.toString(TEST_CASE_ID) + "'";
		List<Object> sbtIDobjectsWithTestCaseID = testUtils
				.executeSqlQueryReturnSelectedColumns(connection, sqlCommandGetSubTestNameByTestCaseID, "sbt_id")
				.get("sbt_id");
		// if test case is missing then add it into table
		if (sbtIDobjectsWithTestCaseID.size() == 0) {
			String sqlCommandPutSubTestNameAndTestCAseId = "INSERT INTO subtest (sbt_name, tc_id) VALUES ('"
					+ subTestName + "', '" + TEST_CASE_ID + "')";
			testUtils.runSqlQuery(connection, sqlCommandPutSubTestNameAndTestCAseId);
		}

		// Getting Sub test ID
		List<Object> sbtIDobjects = testUtils
				.executeSqlQueryReturnSelectedColumns(connection, sqlCommandGetSubTestNameByTestCaseID, "sbt_id")
				.get("sbt_id");
		SUB_TEST_ID = Integer.valueOf(sbtIDobjects.get(0).toString());

		// get the date for test case run
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.now();
		String[] dateArr = dtf.format(localDate).split("/");
		runDate = dateArr[1] + "/" + dateArr[2] + "/" + dateArr[0];

//		elapsedTime = System.nanoTime() - startTime;
//		double seconds = (double) elapsedTime / 1000000000.0;
//
//		truncatedSeconds = BigDecimal.valueOf(seconds).setScale(3, RoundingMode.HALF_UP).doubleValue();

		

		String sqlQueryAddFailResult = "INSERT INTO lastresults (tc_id, sbt_id, tc_res, tc_fail_tr, tc_cons_log, run_date, run_type_id, "
				+ "tc_run_time) VALUES ('" + TEST_CASE_ID + "', '" + SUB_TEST_ID + "', '" + status + "', '" + failTrace.replace("'", "\"") + "', '"
				+ exceptionMessage + "', '" + runDate + "', '" + runTypeId + "', '"
				+ truncatedSeconds + "')";
		testUtils.runSqlQuery(connection, sqlQueryAddFailResult);
		if (connection != null)
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		

	}

}
