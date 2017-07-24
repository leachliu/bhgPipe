package com.bhg.posServer.storm.bolt;

import org.apache.commons.lang.StringUtils;

public class CommonUtil {

	public static String getMobileSystem(String userAgent) {
		String systemType = "Unknown";

		if (userAgent.contains("Android") && userAgent.contains("Linux")) {
			systemType = CommonUtil.getAndroidSystem(userAgent);
		} else if (userAgent.contains("like Mac OS X")) {
			systemType = "ios " + CommonUtil.getIosMobileName(userAgent);
		} else if (userAgent.contains("Windows Phone")) {
			systemType = CommonUtil.getWindowsPhoneSystem(userAgent);
		}
		return systemType;
	}

	public static String getMobileType(String userAgent) {
		String mobileType = "Unknown";
		if (userAgent.contains("Android") && userAgent.contains("Linux")) {
			mobileType = CommonUtil.getAndroidMobileName(userAgent);
		} else if (userAgent.contains("like Mac OS X")) {
			mobileType = CommonUtil.getIosSystem(userAgent);
		} else if (userAgent.contains("Windows Phone")) {
			mobileType = CommonUtil.getWindowsPhoneMobileName(userAgent);
		}
		return mobileType;
	}

	public static String getAndroidSystem(String userAgent) {
		String typeInfo = getTypeInfo(userAgent);
		String[] parts = typeInfo.split(";");
		String systemType = "";
		for (String part : parts) {
			if (part.contains("Android")) {
				systemType = part.trim();
			}
		}
		return systemType;
	}

	public static String getAndroidMobileName(String userAgent) {
		String typeInfo = getTypeInfo(userAgent);
		String[] parts = typeInfo.split(";");
		String mobileName = parts[parts.length - 1].trim();
		if (mobileName.contains("Build")) {
			int index = mobileName.indexOf("Build");
			mobileName = StringUtils.substring(mobileName,0,index).trim();
		}
		return mobileName;
	}

	public static String getIosSystem(String userAgent) {
		String typeInfo = getTypeInfo(userAgent);
		String[] parts = typeInfo.split(";");
		String systemType = "";
		for (String part : parts) {
			if (part.contains("like Mac OS X")) {
				int start = part.indexOf(" OS");
				int end = part.indexOf("like Mac OS X");
				systemType = StringUtils.substring(part,start + 3, end);
			}
		}
		return systemType.trim();
	}

	public static String getIosMobileName(String userAgent) {
		String typeInfo = getTypeInfo(userAgent);
		String[] parts = typeInfo.split(";");

		return parts[0].trim();
	}

	public static String getWindowsPhoneSystem(String userAgent) {
		String typeInfo = getTypeInfo(userAgent);
		String[] parts = typeInfo.split(";");
		String systemType = "";
		for (String part : parts) {
			if (part.contains("Windows Phone")) {
				systemType = part.trim();
			}
		}
		return systemType;
	}

	public static String getWindowsPhoneMobileName(String userAgent) {
		String typeInfo = getTypeInfo(userAgent);
		String[] parts = typeInfo.split(";");
		String mobileName = parts[parts.length - 2].trim() + " " + parts[parts.length - 1].trim();
		return mobileName;
	}

	private static String getTypeInfo(String userAgent) {
		int start = userAgent.indexOf("(");
		int end = userAgent.indexOf(")");
		String typeInfo = StringUtils.substring(userAgent,start + 1, end);
		return typeInfo;
	}

	public static void main(String[] args) {
		String andorid = "Mozilla/5.0 (Linux; U; Android 4.2.2; en-us; HTC6500LVW 4G Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";
		System.out.println(CommonUtil.getAndroidSystem(andorid));
		System.out.println(CommonUtil.getAndroidMobileName(andorid));
		String ipad = "Mozilla/5.0(iPad; U; CPU iPhone OS 3_2 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Version/4.0.4 Mobile/7B314 Safari/531.21.10gin_lib.cc";
		System.out.println(CommonUtil.getIosSystem(ipad));
		System.out.println(CommonUtil.getIosMobileName(ipad));
		String iphone = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_1 like Mac OS X; en-us) AppleWebKit/532.9 (KHTML, like Gecko) Version/4.0.5 Mobile/8B5097d Safari/6531.22.7";
		System.out.println(CommonUtil.getIosSystem(iphone));
		System.out.println(CommonUtil.getIosMobileName(iphone));
		String windowsPhone = "Mozilla/5.0 (compatible; MSIE 10.0; Windows Phone 8.0; Trident/6.0; IEMobile/10.0; ARM; Touch; NOKIA; Lumia 820)";
		System.out.println(CommonUtil.getWindowsPhoneMobileName(windowsPhone));
		System.out.println(CommonUtil.getWindowsPhoneSystem(windowsPhone));

	}

}