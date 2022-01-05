package com.helpsystems.common.uncompressors;

//import java.io.File;
//import java.io.FileOutputStream;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;



public class UncompressSevenZ implements Uncompressor{
	
	@Override
	public String uncompress(String zipPath) throws Exception {
		String resultFolder = zipPath.substring(0, zipPath.lastIndexOf('/'));
		String result = null;
		try (SevenZFile sevenZFile = new SevenZFile(new File(zipPath))) {
			SevenZArchiveEntry entry = null;
			while ((entry = sevenZFile.getNextEntry()) != null) {
				result = resultFolder + '/' + entry.getName();
				try (FileOutputStream out = new FileOutputStream(result)) {
					final byte[] buffer = new byte[4096];
					int n = 0;
					while (-1 != (n = sevenZFile.read(buffer))) {
						out.write(buffer, 0, n);
					}
				}
			}
		}
		return result;
	}
}