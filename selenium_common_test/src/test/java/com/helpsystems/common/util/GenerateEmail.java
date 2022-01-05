package com.helpsystems.common.util;

import static com.helpsystems.common.util.Constants.REGRESSION_DB_ADDRESS;
import static com.helpsystems.common.util.Constants.REGRESSION_DB_PASSWORD;
import static com.helpsystems.common.util.Constants.REGRESSION_DB_USERNAME;
import static com.helpsystems.common.util.ReportConstants.EMAIL_RECIPIENTS;
import static com.helpsystems.common.util.ReportConstants.PRIMARY_RUNS;
import static com.helpsystems.common.util.ReportConstants.PRODUCT_NAME;
import static com.helpsystems.common.util.ReportConstants.REPORT_WEB_PATH;
import static com.helpsystems.common.util.ReportConstants.RE_RUNS;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.taskdefs.optional.vss.MSVSS.CurrentModUpdated;
import org.openqa.selenium.WebDriver;


public class GenerateEmail {
	private static final WebDriver WebDriver = null;
	
	private final static String queryCountTotal = "SELECT COUNT(res_id) FROM public.lastresults tres\r\n" + 
			"INNER JOIN (\r\n" + 
					"SELECT max(lastresults.res_id) as MaxResId,  lastresults.tc_id FROM lastresults\r\n" + 
					"where lastresults.run_type_id=(SELECT run_type_id from runtype where run_type_name='%s')\r\n" + 
					"group by lastresults.tc_id \r\n" + 
					") tm\r\n" + 
					"on tres.res_id = tm.MaxResId\r\n";

	private final static String queryCountStatus = "SELECT COUNT(res_id) FROM public.lastresults tres\r\n" + 
			"INNER JOIN (\r\n" + 
					"SELECT max(lastresults.res_id) as MaxResId,  lastresults.tc_id FROM lastresults\r\n" + 
					"where lastresults.run_type_id=(SELECT run_type_id from runtype where run_type_name='%s')\r\n" + 
					"group by lastresults.tc_id \r\n" + 
					") tm\r\n" + 
					"on tres.res_id = tm.MaxResId\r\n" +
					"WHERE tc_res='%s'\r\n";
	
	private final static String getFailedTestCasesLastRun = "SELECT COUNT(res_id) \r\n" + 
			"FROM public.lastresults tres\r\n" + 
			"INNER JOIN (\r\n" + 
			"SELECT max(lastresults.res_id) as MaxResId,  lastresults.tc_id FROM lastresults " +
			"where lastresults.run_type_id=(SELECT run_type_id from runtype where run_type_name='%s')\r\n" +
			"group by lastresults.tc_id)\r\n" + 
			"tm on tres.res_id = tm.MaxResId\r\n" + 
			"WHERE tc_res IN ('%s', '%s', '%s')\r\n";

	static TestUtils testUtils;
	public static void main(String[] args)  {
		testUtils = new TestUtils(WebDriver);

		final String primaryRuns = PRIMARY_RUNS;
		final String reRuns = RE_RUNS;
		int allTotal = 0;
		int allFail = 0;
		int allFailPrimRun = 0;
		int allSkip = 0;

		// getting Primary runs
		ArrayList<String> primaryRunList = new ArrayList<String>();
		String[] primaryRunListPrepare = primaryRuns.split(",");
		for (String currPrimRun : primaryRunListPrepare) {
			primaryRunList.add(StringUtils.trim(currPrimRun));
		}
		
		// getting Rerun-s
		ArrayList<String> reRunList = new ArrayList<String>();
		String[] reRunListPrepare = reRuns.split(",");
		for (String currReRun : reRunListPrepare) {
			if (!StringUtils.trim(currReRun).isEmpty())
			reRunList.add(StringUtils.trim(currReRun));
		}
		java.util.List<Object> totalObjects = new ArrayList<>();
		java.util.List<Object> totalObjectsSkip = new ArrayList<>();
		java.util.List<Object> totalObjectsFail = new ArrayList<>();
		java.util.List<Object> totalObjectsFailPrimary = new ArrayList<>();

		
		//calculating full total
		for (String currRun : primaryRunList) {
			String getTotalQuery = String.format(queryCountTotal, currRun);
			totalObjects = testUtils.executeUpdateSqlQuery(REGRESSION_DB_ADDRESS, REGRESSION_DB_USERNAME, REGRESSION_DB_PASSWORD, getTotalQuery, "count").get("count");
			 if (totalObjects.size() != 0) {
				 allTotal = allTotal + Integer.valueOf(totalObjects.get(0).toString());
			 }
			 // count the skip results
			 String getSkipQuery = String.format(queryCountStatus, currRun, "skipped");
			 totalObjectsSkip = testUtils.executeUpdateSqlQuery(REGRESSION_DB_ADDRESS, REGRESSION_DB_USERNAME, REGRESSION_DB_PASSWORD, getSkipQuery, "count").get("count");
			 if (totalObjectsSkip.size() != 0) {
				 allSkip = allSkip + Integer.valueOf(totalObjectsSkip.get(0).toString());
			 }
			 // count fails of Primary run
			 String getFailQueryPrim  = String.format(getFailedTestCasesLastRun, currRun, "failed", "iFail", "cFail");
	
			 totalObjectsFailPrimary = testUtils.executeUpdateSqlQuery(REGRESSION_DB_ADDRESS, REGRESSION_DB_USERNAME, REGRESSION_DB_PASSWORD, getFailQueryPrim, "count").get("count");
				 if (totalObjectsFailPrimary.size() != 0) {
					 allFailPrimRun = allFailPrimRun + Integer.valueOf(totalObjectsFailPrimary.get(0).toString());
				 }
			
		}
		
		System.out.println("primary runs fail = " + allFailPrimRun);
		//calculating Total fail
		for (String currReRun : reRunList) {
			String getFailQuery = String.format(getFailedTestCasesLastRun, currReRun,  "failed", "iFail", "cFail");
			totalObjectsFail = testUtils.executeUpdateSqlQuery(REGRESSION_DB_ADDRESS, REGRESSION_DB_USERNAME, REGRESSION_DB_PASSWORD, getFailQuery, "count").get("count");
			 if (totalObjectsFail.size() != 0) {
				 allFail = allFail + Integer.valueOf(totalObjectsFail.get(0).toString());
			 }
			
		}
		
		if (reRunList.size()==0) {
			allFail = allFailPrimRun;
		} 
		
		float passRate;
		int allTotalAfterSkip = allTotal - allSkip;
		if (allTotalAfterSkip != 0) {
			passRate = (float) (allTotalAfterSkip - allFail)*100/(allTotalAfterSkip);
			passRate = (float) (Math.round(passRate*100.0)/100.0);
		} else {
			passRate = 0;
		}
		System.out.println("total = " + allTotal);
		System.out.println("fail = " + allFail);

		System.out.println("skip = " + allSkip);
		
		System.out.println("pass rate = " + passRate);
		
		String header = "\"" + PRODUCT_NAME +"\" Regression run is Completed , Total Success Rate is " + passRate + "%";
		String textMessage = "Hi Everyone,\n\n The " + PRODUCT_NAME + " regression has been completed , please see the results by the following URL: \n" + REPORT_WEB_PATH;
		String mailAddresses = EMAIL_RECIPIENTS;
		sendMail(header, textMessage, mailAddresses);
	}
	
	/**
	 * 
	 * @param subject
	 * @param inpuMessage
	 * @param to
	 */
	public static void sendMail(String subject, String inpuMessage, String to) {
		final String username = "hs-autotest-report@sourcio2.com";
		final String password = "QwE123456";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from-email@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(inpuMessage);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
}
