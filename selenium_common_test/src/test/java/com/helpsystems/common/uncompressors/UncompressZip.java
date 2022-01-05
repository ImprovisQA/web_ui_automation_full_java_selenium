package com.helpsystems.common.uncompressors;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;

//import org.apache.commons.compress.utils.IOUtils;

import com.helpsystems.common.uncompressors.Uncompressor;

public class UncompressZip implements Uncompressor {

	public String uncompress(String zipPath) throws Exception {
		int BUFFER = 2048;
		File file = new File(zipPath);
		String result = null;
		try (ZipFile zip = new ZipFile(file)) {
			String newPath = file.getParent();

			Enumeration<? extends ZipEntry> zipFileEntries = zip.entries();

			// Process each entry
			while (zipFileEntries.hasMoreElements()) {
				// grab a zip file entry
				ZipEntry entry = zipFileEntries.nextElement();
				String currentEntry = entry.getName();
				File destFile = new File(newPath, currentEntry);
				result = destFile.getAbsolutePath();
				if (!entry.isDirectory()) {
					try (BufferedInputStream is = new BufferedInputStream(zip.getInputStream(entry));
							FileOutputStream fos = new FileOutputStream(destFile);
							BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER)) {
						IOUtils.copy(is, dest);
					}
				}
			}
		}
		return result;
	}
}
