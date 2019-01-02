package com.wxm.tools;

import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.CaseFormat;
import com.google.common.base.Objects;

public class ContentUtil {
    
	public static String filterContent(String content){
		String replaceHtml = delHtml(content);
		String replaceEmoji = filterEmojiAndOtherChars(replaceHtml);
		String replaceSql = escapeSql(replaceEmoji);
		
		return replaceSql;
	}
	
    private static final String REGEX_SCRIPT = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式  
    private static final String REGEX_STYLE = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式  
    private static final String REGEX_HTML = "<[^>]+>"; // 定义HTML标签的正则表达式  
    private static final String REGEX_ITEMID = "^[A-Za-z0-9\\-\\/\\:]+$";
    
    public static boolean isItemId(String itemId){
        if(itemId.contains(" ")){
            return false;
        }
        
        Pattern pScript = Pattern.compile(REGEX_ITEMID, Pattern.CASE_INSENSITIVE);
        Matcher mScript = pScript.matcher(itemId);
        
        return mScript.find();
    }
    
	public static String delHtml(String htmlStr) {
		Pattern pScript = Pattern.compile(REGEX_SCRIPT, Pattern.CASE_INSENSITIVE);
		Matcher mScript = pScript.matcher(htmlStr);
		htmlStr = mScript.replaceAll(""); // 过滤script标签

		Pattern pStyle = Pattern.compile(REGEX_STYLE, Pattern.CASE_INSENSITIVE);
		Matcher mStyle = pStyle.matcher(htmlStr);
		htmlStr = mStyle.replaceAll(""); // 过滤style标签

		Pattern pHtml = Pattern.compile(REGEX_HTML, Pattern.CASE_INSENSITIVE);
		Matcher mHtml = pHtml.matcher(htmlStr);
		htmlStr = mHtml.replaceAll(""); // 过滤html标签

		return htmlStr.trim(); // 返回文本字符串
	}

	public static String filterEmojiAndOtherChars(String str) {

		if (str.trim().isEmpty()) {
			return str;
		}
		String pattern = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
		String reStr = "";
		Pattern emoji = Pattern.compile(pattern);
		Matcher emojiMatcher = emoji.matcher(str);
		str = emojiMatcher.replaceAll(reStr);
		return str;
	}

	public static String escapeSql(String str) {
		if (str == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char src = str.charAt(i);
			switch (src) {
			case '\'':
				sb.append("");
				break;
			case '\"':
			case '\\':
				sb.append('\\');
			default:
				sb.append(src);
				break;
			}
		}
		return sb.toString();
	}

	private static final int CAMEL_TO_UNDER_LINE = 1;
	private static final int UNDER_LINE_TO_CAMEL = 2;

	// 驼峰->下划线
	public static JSONObject camelToUnderline(JSONObject jo) {
		return underlinerCamelExchange(jo, CAMEL_TO_UNDER_LINE);
	}

	// 下划线->驼峰
	public static JSONObject underlineToCamel(JSONObject jo) {
		return underlinerCamelExchange(jo, UNDER_LINE_TO_CAMEL);
	}

	public static String camelToUnderline(String key) {
		return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, key);
	}

	public static String underlineToCamel(String key) {
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key);
	}

	private static JSONObject underlinerCamelExchange(JSONObject jo, int type) {
		if (jo.isEmpty()) {
			return jo;
		}

		JSONObject result = new JSONObject();
		for (Entry<String, Object> entry : jo.entrySet()) {
			String newKey = entry.getKey();
			if (Objects.equal(type, CAMEL_TO_UNDER_LINE)) {
				newKey = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entry.getKey());
			} else if (Objects.equal(type, UNDER_LINE_TO_CAMEL)) {
				newKey = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, entry.getKey());
			}
			result.put(newKey, entry.getValue());
		}
		return result;
	}
}
