package com.bhg.posServer.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

	// 获取时间输入那个时间段,5分钟或者10分钟为时间间隔
	public static Date getTimePointWithMinute(Date date, int minuteInterval) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int min = calendar.get(Calendar.MINUTE);
		if (min % minuteInterval != 0) {
			calendar.set(Calendar.MINUTE, min / minuteInterval * minuteInterval);
		}
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public static Date getTimePointWithHour(Date date, int hourInterval) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if(hour % hourInterval != 0) {
			calendar.set(Calendar.HOUR_OF_DAY, hour / hourInterval * hourInterval);
		}
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public static String getDayString(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return dayFormat.get().format(calendar.getTime());
	}

	public static int getMinuteFromDate() {
		return Calendar.getInstance().get(Calendar.MINUTE);
	}

	public static int getHourFromDate() {
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}

	private static final ThreadLocal<SimpleDateFormat> dayFormat = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	private static final ThreadLocal<SimpleDateFormat> timeFormat = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	private static final ThreadLocal<SimpleDateFormat> nginxTimeFormat = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss z", Locale.US);
		}
	};

	private static final ThreadLocal<SimpleDateFormat> redisTimeFormat = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyyMMddHHmmss");
		}
	};

	public static String getYesterday() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return dayFormat.get().format(calendar.getTime());
	}

	public static String getDayBeforeYesterday() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, -2);
		return dayFormat.get().format(calendar.getTime());
	}

	public static String getTomorrow() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		return dayFormat.get().format(calendar.getTime());
	}

	public static String getToday() {
		return dayFormat.get().format(new Date());
	}

	public static Date getDate(String dateString) {
		Date parseDate = null;
		try {
			parseDate = timeFormat.get().parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parseDate;
	}

	public static Date getNginxDate(String dateString) {
		Date parseDate = null;
		try {
			parseDate = nginxTimeFormat.get().parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parseDate;
	}

	public static Date getRedisDate(String dateString) {
		Date parseDate = null;
		try {
			parseDate = redisTimeFormat.get().parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parseDate;
	}

	public static String dateToRedisString(Date date) {
		return redisTimeFormat.get().format(date);
	}

	public static String toString(Date date) {
		return timeFormat.get().format(date);
	}
	
	public static String toDayString(Date date) {
		return dayFormat.get().format(date);
	}

	public static void main(String[] args) {
		
	}
}
