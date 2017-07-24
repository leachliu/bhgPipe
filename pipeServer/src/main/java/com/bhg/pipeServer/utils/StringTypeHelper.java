package com.bhg.pipeServer.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StringTypeHelper {

	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isBoolean(String value) {
		try {
			value = value.toLowerCase();
			if (value.equals("true") || value.equals("false")) {
				return true;
			}
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
			if (value.contains("."))
				return true;
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isNumber(String value) {
		return isInteger(value) || isDouble(value);
	}

	public static boolean isDate(String value) {
		boolean convertSuccess = true;
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		try {
			format.setLenient(true);
			format.parse(value);
		} catch (ParseException e) {
			convertSuccess = false;
		}
		return convertSuccess;
	}

	public static boolean isTime(String value) {
		boolean convertSuccess = true;
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		try {
			format.setLenient(true);
			format.parse(value);
		} catch (ParseException e) {
			convertSuccess = false;
		}
		return convertSuccess;
	}

	public static boolean isDateTime(String value) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			format.setLenient(true);
			format.parse(value);
			return true;
		} catch (ParseException e) {
		}
		format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			format.setLenient(true);
			format.parse(value);
			return true;
		} catch (ParseException e) {
		}

		return false;
	}
}