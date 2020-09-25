package com.koreait.matzip;

import java.io.File;

import javax.servlet.http.Part;

public class FileUtils {
	public static void makeFolder(String path) {
		File dir = new File(path);
		if(!dir.exists()) {
			// 굳이 mkdir 을  쓸 이유가 없다 mkdirs 써도 하나생성 가능
			dir.mkdirs();
		}
	}
	
	public static String getExt(String fileNm) {
		return fileNm.substring(fileNm.lastIndexOf("."));
	}
	
	public static boolean delFile(String path) {
		File file = new File(path);
		if(file.exists()) {
			return file.delete();
		}
		
		return false;
	}
	
}
