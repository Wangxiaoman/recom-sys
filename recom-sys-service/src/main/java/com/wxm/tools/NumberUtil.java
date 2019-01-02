package com.wxm.tools;

import java.text.DecimalFormat;

public class NumberUtil {
	
	// 保留指定位数小数
	public static String getFormatNumber(double originNumber, int digit) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < digit; i++) {
			sb.append("0");
		}
		DecimalFormat df = new DecimalFormat("0." + sb.toString());
		return df.format(originNumber);
	}
}
