package com.helpsystems.common.base;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;

import com.helpsystems.common.util.LogMessageHolder;

public interface CustomBase {
	
	static final Map<String, Object> GLOBAL_MAP = new HashMap<>();
	
	public default void storeValue(String name, Object value) {
		GLOBAL_MAP.put(name,  value);
	}
	
	@SuppressWarnings({ "hiding", "unchecked" })
	public default <T> T  getStoredValue(String name) {
		Object object = GLOBAL_MAP.get(name);
		return (T)object;
	}
	
	
	public void init() throws Exception;
	public void testBody() throws Exception;
	public default void validation() throws Exception {
	}
	public void cleanUp() throws Exception;
	
	@Test
	public default void testRunner() throws InitException, TestException, CleanUpException, ValidateException{
		try {
			init();
		} catch (Throwable e) {
			// TODO: handle exception
			throw new InitException("The test failed on initialization #*******#" + LogMessageHolder.getTestLogs(), e);
		}
		
		try {
			testBody();
		} catch (Throwable e) {
			// TODO: handle exception
			throw new TestException("The test case failed in Test Body  #*******#" + LogMessageHolder.getTestLogs(), e);
		}
		
		try {
			validation();
		} catch (Throwable e) {
			// TODO: handle exception
			throw new ValidateException("The test case failed in Validation  #*******#" + LogMessageHolder.getTestLogs(), e);
		}
		
		try {
			cleanUp();
		} catch (Throwable e) {
			// TODO: handle exception
			throw new CleanUpException("The test case failed on clean up  #*******#" + LogMessageHolder.getTestLogs(), e);
		}
	}

}
