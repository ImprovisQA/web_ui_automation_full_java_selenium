package com.helpsystems.common.util;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class ChangeProperty {
	
	public static void main(String[] args) throws IOException, ConfigurationException {
		
		String inputFileName = "src/test/resources/" + args[0];
		String key = args[1];
		String value = args[2];
		
		PropertiesConfiguration conf = new PropertiesConfiguration(inputFileName);
		conf.setProperty(key, value);
		conf.save(); 
		
	}

}
