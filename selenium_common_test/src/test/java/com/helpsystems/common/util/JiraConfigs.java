package com.helpsystems.common.util;

public interface JiraConfigs {
	

	
	/***************************** Rest server and client config (system under test) ************/
	//server service url to test
	final String serverUrl = System.getProperty("serverURL","http://sbsa.helpsystems.com");

	//server service port
	final String serverPort = System.getProperty("serverPort","8788");

	//server ATM base service endpoint path
	final String serverPath = System.getProperty("serverPath","/universal-connector/api/v1");

	//username
	final String username = System.getProperty("username","svcJIRAAutotest");

	//password
	final String password = System.getProperty("password","Helpsys2009");
	
	//enable test client debug
	final String enableDebugLogging = System.getProperty("enableDebugLogging", "false");
	
	/********************************************************************************************/
	
	/***************************** Jira server and client config ********************************/
	//jira server service url to test
	final String jiraServerUrl = System.getProperty("jiraServerURL","https://dps.helpsystems.com");

	//jira server service port
	final String jiraServerPort = System.getProperty("jiraServerPort","443");

	//jira server ATM base service endpoint path
	final String jiraAtmServerPath = System.getProperty("jiraAtmServerPath","/rest/api/2");

	//jira username
	final String jiraUsername = System.getProperty("jiraUsername","svcJIRAAutotest");

	//jira password
	final String jiraPassword = System.getProperty("jiraPassword","Helpsys2009");
	
	//enable jira client debug
	final String enableJiraDebugLogging = System.getProperty("enableJiraDebugLogging", "false");
	
	//Jira ATM test run key
	final String jiraTestRunKey = System.getProperty("jiraTestRunKey","PTE-R13");

	/*********************************************************************************************/
}
