package com.helpsystems.common.util;

public class LogMessageHolder {

	private static StringBuilder TEST_SCOPE_LOGS = new StringBuilder();
	
	public static StringBuilder getTestLogs() {
		return TEST_SCOPE_LOGS;
	}
}
