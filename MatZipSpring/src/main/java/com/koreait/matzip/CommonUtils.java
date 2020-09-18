package com.koreait.matzip;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

public class CommonUtils {
	public static int getIntParameter(MultipartRequest request, String key) {
		return parseStrToInt(request.getParameter(key));
	}
	
	public static int getIntParameter(HttpServletRequest request, String key) {
		return parseStrToInt(request.getParameter(key));
	}
	
	public static int parseStrToInt(String str) {
		return parseStrToInt(str, 0);
	}
	
	public static int parseStrToInt(String str, int defNo) {
		try {
			return Integer.parseInt(str);
		} catch(Exception e) {
			return defNo;
		}
	}
	

	
}
