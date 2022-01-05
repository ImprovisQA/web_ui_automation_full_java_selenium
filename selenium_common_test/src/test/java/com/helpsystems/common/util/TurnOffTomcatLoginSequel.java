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

public class TurnOffTomcatLoginSequel {
	
	public static void main(String[] args) throws IOException {
//		String filePath = "C:\\Program Files (x86)\\SEQUEL\\SEQUELWebServer\\webapps\\sequel\\WEB-INF\\web.xml";
		String filePath = args[0];
		String line = null;
		String urlPatternSettings = "<url-pattern>/settings/*</url-pattern>";
		String urlPatternSecureSettings = "<url-pattern>/secure/settings/*</url-pattern>";
		String urlPatternHost = "<url-pattern>/host/*</url-pattern>";
		String urlPatternSecureHost = "<url-pattern>/secure/host/*</url-pattern>";
		List<String> tmp = new ArrayList<String>();


//		File file = new File(filePath);
		FileReader fileReader = new FileReader(filePath);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		while ((line = bufferedReader.readLine()) != null) {
			if (line.contains(urlPatternSettings)) {
				tmp.add("<!--" + urlPatternSettings + "-->");
			} else if (line.contains(urlPatternSecureSettings)) {
				tmp.add("<!--" + urlPatternSecureSettings + "-->");
			} else if (line.contains(urlPatternHost)) {
				tmp.add("<!--" + urlPatternHost + "-->");
			} else if (line.contains(urlPatternSecureHost)) {
				tmp.add("<!--" + urlPatternSecureHost + "-->");
			} else {
				tmp.add(line);
			}
			
		}
		bufferedReader.close();
		fileReader.close();
		
		FileWriter fileWriter = new FileWriter(filePath);
		BufferedWriter outputWriter = new BufferedWriter(fileWriter);

		for (int i = 0; i < tmp.size(); i++) {
				outputWriter.write(tmp.get(i));
				outputWriter.newLine();
		}

		outputWriter.close();
		fileWriter.close();
		
		
		
	}

}
