package com.helpsystems.common.util;

import static io.restassured.RestAssured.authentication;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.filters;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static io.restassured.RestAssured.preemptive;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import io.restassured.response.Response;
//import com.itextpdf.text.pdf.StringUtils;
import static com.helpsystems.common.util.Constants.*;
public class checkSkipDefect implements JiraConfigs {
	// each test has to update this owner of the test case (user login name)
	private static String jiraTestCaseOwner = "";
	// each test has to update this if a jira environment is to be recorded
	private static String jiraEnvironment = "";
	private static long testTime;
	private static String def_num = "IEPM-136";

	public static void main(String[] args) throws IOException {

		// System.out.println(checkStatus(def_num));
		findSkippedTestCases();
//		System.out.println("reporter = " + checkReporter("IN-3092"));

	}

	private static void findSkippedTestCases() throws IOException {
		String rootPath = "src/test/java";

//		String customPath = "C:\\AutomationTests\\insite-core-autotest\\src\\test\\java";
		// String customPath = "C:\\AutomationTests\\sa-autotest-api\\src\\test\\java";
		// System.out.println(javaFileFinder(customPath).toString());

		List<File> files = listf(rootPath);
		List<File> javaFiles = new ArrayList<File>();

		for (File file : files) {
			String[] fileNameArray = file.getPath().split("\\\\");
			String fileName = fileNameArray[fileNameArray.length - 1];
			if ((file.getPath().contains(".java")) && (fileName.startsWith("Test"))) {
				javaFiles.add(file);
			}
		}

		HashMap<String, String> skipTestsAndReason = getSkippedTestCases(javaFiles);
//		System.out.println("status = " + checkStatus(def_num));

		verifyDefect(skipTestsAndReason);

	}

	public static void verifyDefect(HashMap<String, String> mp) {
		String defectStatus = null;
		String defectReporter = "";
		String[] splittedProduct = REGRESSION_DB_ADDRESS.split("/");
		String product = splittedProduct[splittedProduct.length-1];

		Iterator it = mp.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			// System.out.println(pair.getKey() + " = " + pair.getValue());

			System.out.println(pair.getKey());
			System.out.println(pair.getValue());

			if (!(pair.getValue().toString()==null) && (pair.getValue().toString().contains("incorrect-skip-format"))) {
				// send email to Gevorg I
				GenerateEmail.sendMail(product + ": incorrect skip format",
						"This test case: \"" + pair.getKey() + "\" is skipped with " + pair.getValue().toString() + "\"",
						"9d2755bf.helpsystemsllc.onmicrosoft.com@amer.teams.ms");
			} else {
				defectStatus = checkStatus(pair.getValue().toString());
				defectReporter = checkReporter(pair.getValue().toString());
				if (!(defectStatus == null)) {
					if (defectStatus.matches("Completed|Accepted")) {
						// send email to defect reporter that the defect is completed or Accepted but
						// test case is not skipped
						GenerateEmail.sendMail("A Test Case needs to be unskipped",
								"The defect: \'" + pair.getValue().toString() + "\' has status: " + defectStatus +
										 " but the test case \"" + pair.getKey() + "\" is still skipped",
//										 "gevorg.iskandaryan@helpsystems.com");
								StringUtils.trim(defectReporter) + "@helpsystems.com, gevorg.iskandaryan@helpsystems.com");
					}

				} else {
					// semd email to Gevorg I with the message that not valid defect number
					
					GenerateEmail.sendMail(product + ": No issue exists",
							"This test case \"" + pair.getKey() + "\" is skipped with not valid issue \"" + pair.getValue().toString() + "\"",
							"9d2755bf.helpsystemsllc.onmicrosoft.com@amer.teams.ms");
				}

			}

			it.remove(); // avoids a ConcurrentModificationException
		}
	}

	public static HashMap<String, String> getSkippedTestCases(List<File> inputFiles) throws IOException {
		String line = null;
		String line2 = null;
		String skipLine = null;
		String skipReason = null;
		String owner = null;
		HashMap<String, String> skipMap = new HashMap<String, String>();

		for (File file : inputFiles) {
			List<String> tmpFile = new ArrayList<String>();
			skipLine = null;
			owner = null;
			FileReader fileReader = new FileReader(file.getPath());
			FileReader fileReader2 = new FileReader(file.getPath());
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			// BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
			// while ((line2 = bufferedReader2.readLine()) != null) {
			// tmpFile.add(line2);
			//// System.out.println("ggg" + line2);
			//
			// }
			while ((line = bufferedReader.readLine()) != null) {
				if (line.contains("skipThis")) {
					skipLine = line;
					break;
				}
			}

			// for (int i =0; i < tmpFile.size(); i++) {
			//// System.out.println("ffff " + tmpFile.get(i));
			// if (tmpFile.get(i).contains("getOwner")) {
			// owner = tmpFile.get(i+1);
			// break;
			// }
			//
			// }

			// if (owner!=null) {
			// Matcher matcher = Pattern.compile("\\..*\\;").matcher(owner);
			// if (matcher.find()) {
			// owner = matcher.group();
			// }
			// owner = owner.replaceAll("\\.", "");
			// owner = owner.replaceAll("\\;", "");
			// }

			if (skipLine != null) {
				Matcher matcher = Pattern.compile("\".*\"").matcher(skipLine);
				if (matcher.find()) {
					skipReason = matcher.group();
				}
				skipReason = skipReason.replaceAll("\"", "");
				String[] splitted = skipReason.split("browse/");
				// HashMap<String, Object> value = new HashMap<String, Object>();
				String[] splittedFile = file.getPath().split("src\\\\test\\\\java\\\\");
				String filename = splittedFile[1];
				
				if (splitted.length == 2) {
					//split the file name
					

					// value.put(splitted[1], owner);
					skipMap.put(filename, splitted[1]);

				} else {
					// value.put("incorrect-skip-format", owner);
					skipMap.put(filename, "incorrect-skip-format: \"" + skipReason + "\"");
				}
			}

		}
		return skipMap;
	}

	public static List<File> listf(String directoryName) {
		File directory = new File(directoryName);

		List<File> resultList = new ArrayList<File>();

		// get all the files from a directory
		File[] fList = directory.listFiles();
		resultList.addAll(Arrays.asList(fList));
		for (File file : fList) {
			if (file.isDirectory()) {
				resultList.addAll(listf(file.getAbsolutePath()));
			}
		}
		return resultList;
	}

	/**
	 * 
	 * @param defectNumber
	 * @return
	 */
	private static String checkStatus(String defectNumber) {
		initJiraReportClient();
		String comment = "";
		String statusResponse = null;
		JSONObject body = jiraJsonBody("", comment);
		Object path = given().header("Content-Type", "application/json;charset=UTF-8")
				.header("accept", "application/json;charset=UTF-8").body(body.toJSONString()).when()
				.get("/issue/{issueIdOrKey}?fields=status", defectNumber).then().extract().path("fields.status.name");
		
		if (!(path==null)) {
			statusResponse = path.toString();
		}
		return statusResponse;

	}

	private static String checkReporter(String defectNumber) {
		initJiraReportClient();
		String comment = "";
		JSONObject body = jiraJsonBody("", comment);
		String statusResponse = null;
		Object path = given().header("Content-Type", "application/json;charset=UTF-8")
				.header("accept", "application/json;charset=UTF-8").body(body.toJSONString()).when()
				.get("/issue/{issueIdOrKey}?fields=reporter", defectNumber).then().extract()
				.path("fields.reporter.name");
		
		if (!(path==null)) {
			statusResponse = path.toString();
		}
		return statusResponse;

	}

	// helper method to build the jira json body string
	private static JSONObject jiraJsonBody(String status, String comment) {
		// build the json
		JSONObject postBody = new JSONObject();
		postBody.put("status", status);
		postBody.put("comment", comment);
		postBody.put("userKey", jiraTestCaseOwner);
		postBody.put("executionTime", String.valueOf(testTime));
		if (!jiraEnvironment.isEmpty()) {
			postBody.put("environment", jiraEnvironment);
		}
		return postBody;
	}

	// if RestAssured is used for other testing, then the report server needs to be
	// set up for the reporting
	private static void initJiraReportClient() {
		// server defaults
		baseURI = jiraServerUrl;
		port = Integer.valueOf(jiraServerPort);
		basePath = jiraAtmServerPath;
		authentication = preemptive().basic(jiraUsername, jiraPassword);

		// log all requests and responses if enabled
		if (Boolean.valueOf(enableJiraDebugLogging)) {
			filters(new io.restassured.filter.log.RequestLoggingFilter(),
					new io.restassured.filter.log.ResponseLoggingFilter(),
					new io.restassured.filter.log.ErrorLoggingFilter());
		}

		// enable logging of request and response if validation fails
		enableLoggingOfRequestAndResponseIfValidationFails();
	}

}
