package com.wxm.tools;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RuleTools {
	public static boolean checkSpecialChar(String str) throws PatternSyntaxException {
        // 清除掉所有特殊字符
        String regEx =  ".*[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\]+.*";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }
	
	public static boolean checkOP(String str){
		List<String> ops = Arrays.asList("any","==");
		if(ops.contains(str)){
			return true;
		}
		return false;
	}
	
	public static boolean checkCsaasValue(String str){
		List<String> sorts = Arrays.asList("");
		if(sorts.contains(str)){
			return true;
		}
		return false;
	}
	
	private static final Pattern CONTENT_PATTERN = Pattern.compile("\\s*|\t|\r|\n");
	public static String replaceSpecialStr(String str) {
        String repl = "";  
        if (str!=null) {  
            Matcher m = CONTENT_PATTERN.matcher(str);  
            repl = m.replaceAll("");  
        }  
        return repl;  
    } 
}
