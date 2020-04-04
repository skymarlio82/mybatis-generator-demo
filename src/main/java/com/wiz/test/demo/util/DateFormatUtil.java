
package com.wiz.test.demo.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {

	public final static String SHORT_SIMPLE_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public final static String SHORT_SIMPLE_DATETIME_FORMAT1 = "yyyyMMddHHmmss";

	private final static DateFormat ssdtf = new SimpleDateFormat(SHORT_SIMPLE_DATETIME_FORMAT);
	private final static DateFormat ssdtf1 = new SimpleDateFormat(SHORT_SIMPLE_DATETIME_FORMAT1);

	public static String getSimpleDateTimeTxt(Date date) {
		return ssdtf.format(date);
	}
	
	public static String getSimpleDateTimeTxt1(Date date) {
		return ssdtf1.format(date);
	}
}