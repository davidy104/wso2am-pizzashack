package org.pizzashack.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class PizzashackUtils {
	public static final Integer DEFAULT_ERROR_LENGTH = 255;

	public static final String DEFAULT_FORMAT = "YYYY-MM-DD'T'HH:MM:SS.mmmmmm";

	public static final String DEFAULT_TIME_ZONE = "UTC";

	public static String dateToStr(Date date) {
		TimeZone tz = TimeZone.getTimeZone(DEFAULT_TIME_ZONE);
		DateFormat dateFormat = new SimpleDateFormat(DEFAULT_FORMAT);
		dateFormat.setTimeZone(tz);
		String result = dateFormat.format(date);
		return result;
	}

	public static Date strToDate(String dateStr) throws Exception {
		TimeZone tz = TimeZone.getTimeZone(DEFAULT_TIME_ZONE);
		DateFormat dateFormat = new SimpleDateFormat(DEFAULT_FORMAT);
		dateFormat.setTimeZone(tz);
		Date date = dateFormat.parse(dateStr);
		return date;
	}

	public static Date strToDateIgnoreException(String dateStr) {
		Date date = null;
		try {
			TimeZone tz = TimeZone.getTimeZone(DEFAULT_TIME_ZONE);
			DateFormat dateFormat = new SimpleDateFormat(DEFAULT_FORMAT);
			dateFormat.setTimeZone(tz);
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {

		}
		return date;
	}

	public static String dateToStr(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public static Date strToDate(String dateStr, String format)
			throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.parse(dateStr);
	}

	public static String getErrorStack(Throwable e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

	public static String getExceptionInfo(Throwable e) {
		String error = getErrorStack(e);
		error = error.substring(0, DEFAULT_ERROR_LENGTH);
		return error;
	}

	public static String getExceptionInfo(Throwable e, Integer length) {
		if (length == null) {
			length = DEFAULT_ERROR_LENGTH;
		}
		String error = getErrorStack(e);
		error = error.substring(0, length);
		return error;
	}
}
