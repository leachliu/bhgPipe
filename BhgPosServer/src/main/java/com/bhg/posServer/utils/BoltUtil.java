package com.bhg.posServer.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang.StringUtils;

/**
 * Created by xiaoai on 2015/7/17.
 */
public class BoltUtil {
	
	private static String projectName = "(advertisement|api)";
	
	public static int getFrame(String key) {
		String frame = "1";
		try {
			Pattern regex = Pattern.compile("\\d+#\\d+#(\\d)", Pattern.MULTILINE);
			Matcher matcher = regex.matcher(key);
			if (matcher.find()) {
				frame = matcher.group(1);
			} 
		} catch (PatternSyntaxException ex) {
			ex.printStackTrace();
		}
		return Integer.parseInt(frame);
	}
	
	public static boolean isView(String nginxLogLine) {
		Pattern pattern = Pattern.compile("\"(GET|POST|HEAD|PUT|DELETE|TRACE|CONNECT|OPTIONS) .*/" + projectName + "/view?.* HTTP/.*\" (2\\d{2}|3\\d{2}) .*");
		Matcher matcher = pattern.matcher(nginxLogLine);
		return matcher.find();
	}
	
	public static boolean isClick(String nginxLogLine) {
		Pattern pattern = Pattern.compile("\"(GET|POST|HEAD|PUT|DELETE|TRACE|CONNECT|OPTIONS) .*/" + projectName + "/click?.* HTTP/.*\" 3\\d{2} .*");
		Matcher matcher = pattern.matcher(nginxLogLine);
		return matcher.find();
	}

	public static String parseAccessUrl(String nginxLogLine) {
		Pattern pattern = Pattern.compile("/" + projectName + "/.* HTTP/");
		Matcher matcher = pattern.matcher(nginxLogLine);
		if (matcher.find()) {
			return matcher.group().replace(" HTTP/", "");
		}
		return "";
	}


    public static Map<String, String> getParams(String request) {
    	Map<String, String> params = new HashMap<String, String>();
    	params.putAll(getParamsOfGet(request));
    	params.putAll(getParamsOfPost(request));
        return params;
    }
    
    private static Map<String, String> getParamsOfGet(String request) {
        Map<String, String> params = new HashMap<String, String>();
        try {
			if(request.indexOf('?') >= 0) {
			    String paramString = StringUtils.substring(request, request.indexOf("?") + 1);
			    paramString = paramString.substring(0,paramString.indexOf(" "));
			    for (String param : StringUtils.split(paramString, "&")) {
			        try {
						String[] parts = StringUtils.split(param,"=");
						if (parts.length > 1) {
						    params.put(new String(parts[0].trim()), new String(parts[1].trim()));
						}
					} catch (Throwable e) {
					}
			    }
			}
		} catch (Throwable e) {
		}
        return params;
    }
    
    private static Map<String, String> getParamsOfPost(String request) {
        Map<String, String> params = new HashMap<String, String>();
    	try {
    		String[] parts = request.split(" ");
    		if(parts != null){
				String paramString = parts[9];
				if(StringUtils.isNotBlank(paramString)) {
				    for (String param : StringUtils.split(paramString, "&")) {
				        try {
							String[] paramParts = StringUtils.split(param,"=");
							if (paramParts.length > 1) {
							    params.put(new String(paramParts[0].trim()), new String(paramParts[1].trim()));
							}
						} catch (Throwable e) {
						}
				    }
				}
    		}
		} catch (Throwable e) {
		}
        return params;
    }

	public static String getNginxAccessTime(String message) {
		int start = message.indexOf("[");
		int end = message.indexOf("]");
		String dateStr = StringUtils.substring(message, start + 1, end);
		return new String(dateStr);
	}

	public static String getStatus(String message) {
		return new String(message.split("\"")[2].trim().split(" ")[0]);
	}

}
