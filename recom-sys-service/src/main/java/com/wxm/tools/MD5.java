package com.wxm.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.wxm.log.CommonLogger;

public class MD5 {
	private static final String SALT = "~!@#$recom%)c8";

	// 全局数组
	private final static String[] STR_DIGITS = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public MD5() {
	}

	// 返回形式为数字跟字符串
	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return STR_DIGITS[iD1] + STR_DIGITS[iD2];
	}

	// 返回形式只为数字
	public static String byteToNum(byte bByte) {
		int iRet = bByte;
		System.out.println("iRet1=" + iRet);
		if (iRet < 0) {
			iRet += 256;
		}
		return String.valueOf(iRet);
	}

	// 转换字节数组为16进制字串
	private static String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}

	public static String getMD5CodeWithSalt(String strObj) {
		strObj = strObj + SALT;
		String resultString = null;
		try {
			// resultString = new String(strObj);
			MessageDigest md = MessageDigest.getInstance("MD5");
			// md.digest() 该函数返回值为存放哈希值结果的byte数组
			resultString = byteToString(md.digest(strObj.getBytes()));
		} catch (NoSuchAlgorithmException ex) {
			CommonLogger.error("get md5 code with default salt error",ex);
		}
		return resultString;
	}
	
	public static String getMD5CodeWithSalt(String strObj,String salt) {
        strObj = strObj + salt;
        String resultString = null;
        try {
            // resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
        	CommonLogger.error("get md5 code with salt error",ex);
        }
        return resultString;
    }

	/**
	 * 编码
	 * 
	 * @param strObj
	 * @return
	 */
	public static String getMD5Code(String strObj) {
		String resultString = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			// md.digest() 该函数返回值为存放哈希值结果的byte数组
			resultString = byteToString(md.digest(strObj.getBytes()));
		} catch (NoSuchAlgorithmException ex) {
			CommonLogger.error("get md5 code error",ex);
		}
		return resultString;
	}
}
