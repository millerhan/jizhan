package com.han.util;

import java.util.Random;

public class StringUtil {

	public static String UpperFirstLetter(String srcStr) {

		StringBuilder destStr = new StringBuilder();

		destStr.append(srcStr.substring(0, 1).toUpperCase());
		destStr.append(srcStr.substring(1));

		return destStr.toString();
	}
	
	private static String baseString = "0123456789ABCDEFGHJKLMNOPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";// 随机产生的字符串

	public static String randomString(int length){
		StringBuilder sb = new StringBuilder();
		Random ran = new Random();
		for (int i = 0; i < length; i++) {
			int ranIndex = ran.nextInt(baseString.length());
			sb.append(baseString.charAt(ranIndex));
		}
		return sb.toString();
	}

}
