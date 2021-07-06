package com.bdf.common;

import java.io.File;

public class FileUtils {

	public static File getPdfFilePath(long nId) {
		int n10_10 = (int) (((long) (nId / 10000000000L)) % 100);
		int n10_8 = (int) (((long) (nId / 100000000L)) % 100);
		int n10_6 = (int) (((long) (nId / 1000000L)) % 100);
		int n10_4 = (int) (((long) (nId / 10000L)) % 100);
		int n10_2 = (int) (((long) (nId / 100L)) % 100);
		
		String folderPath = Global.PATH_PDF_ROOT
				+ File.separator + String.valueOf(n10_10)
				+ File.separator + String.valueOf(n10_8)
				+ File.separator + String.valueOf(n10_6)
				+ File.separator + String.valueOf(n10_4)
				+ File.separator + String.valueOf(n10_2);
		
		File folder = new File(folderPath);
		folder.mkdirs();
		File file = new File(folder, String.valueOf(nId) + ".pdf");
		return file;
	}
}
