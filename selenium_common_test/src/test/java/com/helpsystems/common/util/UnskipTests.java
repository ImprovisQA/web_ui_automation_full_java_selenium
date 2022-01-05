package com.helpsystems.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;

public class UnskipTests {

	public static void main(String[] args) throws IOException, ConfigurationException {
		String file = "unskiplist.txt";
		String line = null;
		FileReader fileReader = new FileReader(file);

		BufferedReader bufferedReader = new BufferedReader(fileReader);
		while ((line = bufferedReader.readLine()) != null) {
			if (line != "") {
//				String[] inputData = line.split(" ", 2);
					if (!line.contains("Test*")) {
						unskipSingle(line);
					} else { // the case with Test*

						String inputDataWithPoints = line.replace(".Test*", "");

						String testCasesLocation = inputDataWithPoints.replace(".", "/");

						final String fullPathLocation = getCurrentPath() + testCasesLocation;

						try {
							File folder = new File(fullPathLocation);
							// get the files of the content
							File[] listOfFiles = folder.listFiles();

							for (int i = 0; i < listOfFiles.length; i++) {
								if ((listOfFiles[i].isFile()) && (listOfFiles[i].getName().contains("java"))) {
									unskipSingle(inputDataWithPoints + "."
											+ listOfFiles[i].getName().replaceAll(".java", ""));
								} else if (listOfFiles[i].isDirectory()) {
									System.out.println("This content is directory " + listOfFiles[i].getName());
								}
							}
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println("************ Folder '" + testCasesLocation + "'is not found");

						}

					}
			}
		}
		bufferedReader.close();
		fileReader.close();

	}

	public static String getCurrentPath() {
		return System.getProperty("user.dir") + "/src/test/java/";
	}

	public static void unskipSingle(String filePath) throws IOException {

		// String inputFilePath =
		// "com.helpsystems.insitecore.uitest.account.TestSavePreferences";
		// String inputFilePath =
		// "com.helpsystems.insitecore.uitest.settings.TestAddRole";
		// String skipReason = "my sample reason";
		// String inputFilePath = args[0];
		// String skipReason = args[1];

		String inputFilePath = filePath.replace(".", "/");
		inputFilePath = inputFilePath + ".java";

		Boolean isAlreadySkipped = false;

		// final String currentPath = System.getProperty("user.dir") +
		// "/../insite-core-autotest/src/test/java/";
		final String currentPath = System.getProperty("user.dir") + "/src/test/java/";
		final String fullPath = currentPath + inputFilePath;
		String line = null;
		List<String> fileInArray = new ArrayList<String>();
		List<String> fileInArrayAfterUnskip = new ArrayList<String>();

		try {
			FileReader fileReader = new FileReader(fullPath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				fileInArray.add(line);

				if (line.contains("toSkip")) {
					isAlreadySkipped = true;
				}
			}
			if (isAlreadySkipped == false) {
				System.out.println("/-----/-----/This test case is not skipped: '" + inputFilePath + "' /-----/-----/");
			}

			// Always close files.
			bufferedReader.close();
			fileReader.close();
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			System.out.println("*********** THIS TEST CASE IS NOT FOUND: '" + inputFilePath + "' ***********");
		}

		if ((isAlreadySkipped == true) && (fileInArray.size() != 0)) {
			Boolean foundFlag = false;

			for (int i = 0; i < fileInArray.size(); i++) {
				if ((foundFlag == true) && (fileInArray.get(i-1).contains("}"))) {
					foundFlag = false;

				}

				if ((fileInArray.get(i).contains("@Override")) && (fileInArray.get(i + 1).contains("toSkip()"))) {
					foundFlag = true;
				}

				if (foundFlag == true) {
					continue;
				}
				if (foundFlag == false) {
					fileInArrayAfterUnskip.add(fileInArray.get(i));
				}

			}

			FileWriter fileWriter = new FileWriter(currentPath + inputFilePath);
			BufferedWriter outputWriter = new BufferedWriter(fileWriter);

			for (int i = 0; i < fileInArrayAfterUnskip.size(); i++) {
				outputWriter.write(fileInArrayAfterUnskip.get(i) + "");
				outputWriter.newLine();
			}

			outputWriter.close();
			fileWriter.close();

		}

	}

}
