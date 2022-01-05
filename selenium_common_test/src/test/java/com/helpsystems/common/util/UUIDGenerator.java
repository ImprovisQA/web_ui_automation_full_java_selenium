package com.helpsystems.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Yeva
 * Class for generating unique identifiers
 */

public class UUIDGenerator {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(UUIDGenerator.class);

	/**
	 * Generates unique identifiers
	 * @return identifier
	 */
	public static String generateUID() {
		String resultStr = null;
		try {
	      //Initialize SecureRandom
	      //This is a lengthy operation, to be done only upon
	      //initialization of the application
	      final SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");

	      //generate a random number
	      final String randomNum = String.valueOf(prng.nextInt());

	      //get its digest
	      final MessageDigest sha = MessageDigest.getInstance("SHA-1");
	      final byte[] result =  sha.digest( randomNum.getBytes() );
	      resultStr = hexEncode(result);
	    }
	    catch ( final NoSuchAlgorithmException ex ) {
	      LOGGER.error("",ex);
	    }
	    
	    return resultStr;
	    
	  }

	  /**
	  * The byte[] returned by MessageDigest does not have a nice
	  * textual representation, so some form of encoding is usually performed.
	  */
	  static private String hexEncode( final byte[] aInput){
	    final StringBuilder result = new StringBuilder();
	    final char[] digits = {'0', '1', '2', '3', '4','5','6','7','8','9','a','b','c','d','e','f'};
	    
	    for ( int idx = 0; idx < aInput.length; ++idx) {
	      final byte b = aInput[idx];
	      result.append( digits[ (b&0xf0) >> 4 ] );
	      result.append( digits[ b&0x0f] );
	    }
	    return result.toString();
	  }
	 
}
