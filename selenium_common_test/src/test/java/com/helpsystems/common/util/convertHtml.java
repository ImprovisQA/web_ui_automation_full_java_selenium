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

public class convertHtml {

	public static void main(String[] args) throws IOException {
		String file = "C:\\Users\\gevorg.iskandaryan\\Documents\\books\\Exploratory Testing\\html-files.txt";
		String line = null;
		FileReader fileReader = new FileReader(file);

		BufferedReader bufferedReader = new BufferedReader(fileReader);
		while ((line = bufferedReader.readLine()) != null) {
			if (line != "") {
				changeHtml(line);
			}

		}
		bufferedReader.close();
		fileReader.close();

	}

	public static void changeHtml(String filePath) throws IOException {
		System.out.println(filePath);
		FileReader fileReader = new FileReader(filePath);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<String> tmpFile = new ArrayList<String>();
		List<String> tmpFilePreFinal = new ArrayList<String>();
		List<String> tmpFileFinal = new ArrayList<String>();
		String line = null;
		boolean isCopyStarted = false;

		while ((line = bufferedReader.readLine()) != null) {
			tmpFile.add(line);
		}
		bufferedReader.close();
		fileReader.close();

		for (int i = 0; i < tmpFile.size(); i++) {

			if (tmpFile.get(i).contains("div class=\"annotator-outer annotator-viewer viewer annotator-hide")
					|| (tmpFile.get(i).contains("<body class=\"reading sidenav"))) {
				isCopyStarted = false;
			}

			if (tmpFile.get(i).contains("div id=\"sbo-rt-content\"") || (tmpFile.get(i).contains("<!DOCTYPE html>"))) {
				isCopyStarted = true;
			}
			// if (tmpFile.get(i).contains("div class=\"annotator-outer annotator-viewer
			// viewer annotator-hide")) {
			// isCopyStarted = false;
			// }
			//
			// if (tmpFile.get(i).contains("div id=\"sbo-rt-content\"")) {
			// isCopyStarted = true;
			// }

			if (isCopyStarted) {
				tmpFilePreFinal.add(tmpFile.get(i));
			}

		}
		String updatedLine = null;
		for (int i = 0; i < tmpFilePreFinal.size(); i++) {
			updatedLine = tmpFilePreFinal.get(i).replace(".js", "");
			updatedLine = updatedLine.replace("https://www.safaribooksonline.com", "");
			updatedLine = updatedLine.replace("www.google", "");
			updatedLine = updatedLine.replace("www.safaribooks", "");
			updatedLine = updatedLine.replace("(function", " (functionl");
			tmpFileFinal.add(updatedLine);
		}


		// Writing to output

		FileWriter fileWriter = new FileWriter(filePath);
		BufferedWriter outputWriter = new BufferedWriter(fileWriter);



		for (int i = 0; i < tmpFilePreFinal.size(); i++) {
			outputWriter.write(tmpFileFinal.get(i) + "");
			outputWriter.newLine();
		}
		outputWriter.write("</html>");
		outputWriter.newLine();
		outputWriter.close();
		fileWriter.close();
	}

}
