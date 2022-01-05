package com.helpsystems.common.util;

import static com.helpsystems.common.util.Constants.*;
import static com.helpsystems.common.util.ReportConstants.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;

public class GetFailedTests {
	private static final String SINGLE_THREAD = " -DforkCnt=1";
	private static final String MULTI_THREAD = " -DforkCnt=3";
	private static final String PRE_FIX = "call mvn surefire-report:report -Dtest=";
	private static final WebDriver WebDriver = null;
	static TestUtils testUtils;

	public static void main(String[] args) throws IOException {
//		These local variables should be removed once we have have 100% stable rerun generation.
//		String ONE_THREAD_PACKAGES = "tmp";
//		String REGRESSION_RUN_TYPE = "VA Integrator First Run";
//		String REGRESSION_DB_ADDRESS = "jdbc:postgresql://10.67.72.24:5432/vitylanalytics";
//		String REGRESSION_DB_USERNAME = "postgres";
//		String REGRESSION_DB_PASSWORD = "Qw123456";
		
		testUtils = new TestUtils(WebDriver);
		String oneThreadPackages = ONE_THREAD_PACKAGES;
		String[] oneThreadPackagesList = oneThreadPackages.split(",");

		String getFailedTests = "SELECT testcase.tc_name, subtest.sbt_name, package.package_name FROM lastresults JOIN testcase ON lastresults.tc_id=testcase.tc_id "
				+ "JOIN subtest ON lastresults.sbt_id=subtest.sbt_id "
				+ "JOIN package ON testcase.package_id=package.package_id WHERE lastresults.tc_res IN ('failed', 'iFail', 'cFail') AND "
				+ "(lastresults.run_type_id=(SELECT run_type_id from runtype WHERE run_type_name='"
				+ REGRESSION_RUN_TYPE + "'));";

		Map<String, List<Object>> totalObjectsFailed = testUtils.executeUpdateSqlQuery(REGRESSION_DB_ADDRESS,
				REGRESSION_DB_USERNAME, REGRESSION_DB_PASSWORD, getFailedTests, "tc_name", "sbt_name", "package_name");
		List<Object> testcases = totalObjectsFailed.get("tc_name");
		List<Object> subtests = totalObjectsFailed.get("sbt_name");
		List<Object> packages = totalObjectsFailed.get("package_name");
		List<Object> packagesUnique = new ArrayList<>();

		List<String> combined = new ArrayList<>();

		Set<Object> hs = new HashSet<>();
		hs.addAll(packages);
		packagesUnique.clear();
		packagesUnique.addAll(hs);
		
		// *********************************** Making package + tc combination, the same package name should be combined*************************************

		String tc = "";
		String packTC = "";
		String onlyPackage = "";
		String[] onlyPackageArray;
		for (int i = 0; i < packagesUnique.size(); i++) {
			onlyPackageArray = packagesUnique.get(i).toString().split("\\.");
			onlyPackage = onlyPackageArray[onlyPackageArray.length - 1];

			for (int j = 0; j < packages.size(); j++) {
				if (packages.get(j).equals(packagesUnique.get(i))) {
					packTC = onlyPackage + "." + testcases.get(j) + ".java";
					if ("".equals(tc)) {
						tc = tc + packTC;
					} else {
						tc = tc + "," + packTC;
					}
				}

			}
			// *********************************** SINGLE AND MULTI THREAD DEFINITION *************************************

			for (String currOneThreadPackage : oneThreadPackagesList) {
				if (tc.contains(StringUtils.trim(currOneThreadPackage))) {
					tc = PRE_FIX + tc + SINGLE_THREAD;
					break;
				}
			}
			if (!tc.contains(SINGLE_THREAD)) {
				tc = PRE_FIX + tc + MULTI_THREAD;
			}
			combined.add(tc);
			tc = "";
		}

		// *********************************** WRITE TO FILE *************************************
		BufferedWriter outputWriter = null;
		outputWriter = new BufferedWriter(new FileWriter("./rerun.bat"));

		for (String currStr : combined) {

			outputWriter.write(currStr);
			outputWriter.newLine();
		}

		outputWriter.flush();
		outputWriter.close();

	}

}
