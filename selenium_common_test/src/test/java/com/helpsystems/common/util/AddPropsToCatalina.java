package com.helpsystems.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddPropsToCatalina {

	public static void main(String[] args) throws IOException{
		String tomcatDir = args[0];
		String coverageDir = args[1];
		String catalinaPath =tomcatDir + "\\bin\\catalina.bat";
		final String setCatalinaOPTS ="set CATALINA_OPTS=-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=8086 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl.need.client.auth=false -Xshare:off -Djava.rmi.server.hostname=10.60.33.25 -Djava.net.preferIPv4Stack=true -Dcom.sun.management.jmxremote.rmi.port=8187";
		String line = null;
		Boolean isAlreadyJacocoHas = false;
		
		FileReader fileReader = new FileReader(catalinaPath);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<String> tmp = new ArrayList<String>();
		boolean isCatalianOptsSet = false;
		boolean isJavaOptsSet = false;
		while ((line = bufferedReader.readLine()) != null) {
			
			if (line.contains("jacocoagent.jar")) {
				isAlreadyJacocoHas = true;
				break;
			}
			
			if ((line.contains("set \"JAVA_OPTS"))&&(isJavaOptsSet==false)) {
				tmp.add("set \"JAVA_OPTS=%JAVA_OPTS% -javaagent:" + coverageDir + "\\jacoco\\lib\\jacocoagent.jar=destfile=" + coverageDir + "\\reportfile\\jacoco.exec,append=true,includes=*,jmx=true\"\r\n");
				isJavaOptsSet = true;
			}
			
			if ((line.contains("%_EXECJAVA%"))&&(isCatalianOptsSet==false)) {
				tmp.add(setCatalinaOPTS);
				isCatalianOptsSet=true;
			}
			
			tmp.add(line);
		}
		bufferedReader.close();
		fileReader.close();
		
		if ((isAlreadyJacocoHas == false) && (tmp.size() != 0)) {
		
		FileWriter fileWriter = new FileWriter(catalinaPath);
		BufferedWriter outputWriter = new BufferedWriter(fileWriter);
		for (int i = 0; i < tmp.size(); i++) {
				outputWriter.write(tmp.get(i) + "");
				outputWriter.newLine();
		}
		outputWriter.close();
		fileWriter.close();
		}
	}

}
