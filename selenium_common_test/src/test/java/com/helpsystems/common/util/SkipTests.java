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

public class SkipTests {

	public static void main(String[] args) throws IOException, ConfigurationException {
		String file = "skiplist.txt";
		String line = null;
		FileReader fileReader = new FileReader(file);

		BufferedReader bufferedReader = new BufferedReader(fileReader);
		while ((line = bufferedReader.readLine()) != null) {
			if (line != "") {
				String[] inputData = line.split(" ", 2);
				if (inputData.length > 1) {
					if (!inputData[0].contains("Test*")) {
						skipSingle(inputData[0], inputData[1]);
					} else { // the case with Test*
						
						String inputDataWithPoints = inputData[0].replace(".Test*", "");
						
						String testCasesLocation = inputDataWithPoints.replace(".", "/");
						
						final String fullPathLocation = getCurrentPath() + testCasesLocation;
						
						try {
							File folder = new File(fullPathLocation);
							// get the files of the content
							File[] listOfFiles = folder.listFiles();
							
							for (int i = 0; i < listOfFiles.length; i++) {
								if ((listOfFiles[i].isFile()) && (listOfFiles[i].getName().contains("java"))) {
									skipSingle(inputDataWithPoints + "." + listOfFiles[i].getName().replaceAll(".java", ""), inputData[1]);
								} else if (listOfFiles[i].isDirectory()) {
									System.out.println("This content is directory " + listOfFiles[i].getName());
								}
							}
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println("************ Folder '" + testCasesLocation  + "'is not found");
							
						}
						
						
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

	public static void skipSingle(String filePath, String skipReason) throws IOException {

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
		List<String> tmp = new ArrayList<String>();

		try {
			FileReader fileReader = new FileReader(fullPath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				tmp.add(line);

				if (line.contains("toSkip")) {
					isAlreadySkipped = true;
					System.out.println(
							"/-----/-----/This test case is already skipped: '" + inputFilePath + "' /-----/-----/");
					break;
				}
			}

			// Always close files.
			bufferedReader.close();
			fileReader.close();
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			System.out.println("*********** THIS TEST CASE IS NOT FOUND: '" + inputFilePath + "' ***********");
		}

		if ((isAlreadySkipped == false) && (tmp.size() != 0)) {
			int firstBraceIntex = 0;
			for (int i = tmp.size() - 1; i >= 0; i--) {
				if ("}".equals(StringUtils.trim(tmp.get(i)))) {
					firstBraceIntex = i;
					break;
				}
			}

			FileWriter fileWriter = new FileWriter(currentPath + inputFilePath);
			BufferedWriter outputWriter = new BufferedWriter(fileWriter);

			for (int i = 0; i < tmp.size(); i++) {
				if (i != firstBraceIntex) {
					outputWriter.write(tmp.get(i) + "");
					outputWriter.newLine();
				}
			}
			outputWriter.write("	@Override");
			outputWriter.newLine();
			outputWriter.write("	public void toSkip() {");
			outputWriter.newLine();
			outputWriter.write("      	skipThis(\"" + skipReason.replace("\"","") + "\");");
			outputWriter.newLine();
			outputWriter.write("	}");
			outputWriter.newLine();
			outputWriter.write("}");
			outputWriter.newLine();

			// outputWriter.flush();
			outputWriter.close();
			fileWriter.close();

		}

	}

}
