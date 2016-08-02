package cn.xuexi.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {
	/**
	 * 获取系统当前日期和时间并格式化为yyyyMMddHHmmss即类似20110810155638格式
	 * 
	 * @param 无
	 * @return 系统当前日期和时间并格式化为yyyyMMddHHmmss即类似20110810155638格式
	 */
	public static String getCurrentDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}

	/**
	 * 获取系统当前日期和时间并格式化为yyyyMMddHHmmssSSS即类似20130526002728796格式
	 * 
	 * @param 无
	 * @return 系统当前日期和时间并格式化为yyyyMMddHHmmssSSS即类似20130526002728796格式
	 */
	public static String getDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(new Date());
	}

	/**
	 * 获取系统当前日期并格式化为yyyyMMdd即类似20110810格式
	 * 
	 * @param 无
	 * @return 系统当前日期并格式化为yyyyMMdd即类似20110810格式
	 */
	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}

	/**
	 * 获取系统当前时间并格式化为HHmmss即类似155638格式
	 * 
	 * @param 无
	 * @return 系统当前时间并格式化为HHmmss即类似155638格式
	 */
	public static String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		return sdf.format(new Date());
	}

	/**
	 * 获取系统当前时间并格式化为HHmmssSSS即类似155039527格式
	 * 
	 * @param 无
	 * @return 系统当前时间并格式化为HHmmssSSS即类似155039527格式
	 */
	public static String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmssSSS");
		return sdf.format(new Date());
	}

	/**
	 * 根据自定义格式化获取系统当前时间
	 * 
	 * @param format
	 *            时间格式化如yyyy-MM-dd HH:mm:ss:SSS
	 * @return 根据自定义格式化返回系统当前时间
	 */
	public static String formatedTime(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}

	/**
	 * get specified time string in specified date format.
	 * 
	 * @param days
	 *            days after or before current date, use + and - to add.
	 * @param dateFormat
	 *            the formatter of date, such as:yyyy-MM-dd HH:mm:ss:SSS.
	 */
	public static String addDaysByFormatter(int days, String dateFormat) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, days);
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(cal.getTime());
	}

	/**
	 * get specified time string in specified date format.
	 * 
	 * @param months
	 *            months after or before current date, use + and - to add.
	 * @param dateFormat
	 *            the formatter of date, such as:yyyy-MM-dd HH:mm:ss:SSS.
	 */
	public static String addMonthsByFormatter(int months, String dateFormat) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, months);
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(cal.getTime());
	}

	/**
	 * get specified time string in specified date format.
	 * 
	 * @param years
	 *            years after or before current date, use + and - to add.
	 * @param dateFormat
	 *            the formatter of date, such as:yyyy-MM-dd HH:mm:ss:SSS.
	 */
	public static String addYearsByFormatter(int years, String dateFormat) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.YEAR, years);
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(cal.getTime());
	}

	/**
	 * get first day of next month in specified date format.
	 * 
	 * @param dateFormat
	 *            the formatter of date, such as:yyyy-MM-dd HH:mm:ss:SSS.
	 */
	public static String firstDayOfNextMonth(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(cal.getTime());
	}

	/**
	 * get first day of specified month and specified year in specified date
	 * format.
	 * 
	 * @param year
	 *            the year of the date.
	 * @param month
	 *            the month of the date.
	 * @param dateFormat
	 *            the formatter of date, such as:yyyy-MM-dd HH:mm:ss:SSS.
	 */
	public static String firstDayOfMonth(int year, int month, String dateFormat) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(cal.getTime());
	}

	/**
	 * get first day of specified month of current year in specified date
	 * format.
	 * 
	 * @param month
	 *            the month of the date.
	 * @param dateFormat
	 *            the formatter of date, such as:yyyy-MM-dd HH:mm:ss:SSS.
	 */
	public static String firstDayOfMonth(int month, String dateFormat) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(cal.getTime());
	}

	/**
	 * get the system current milliseconds.
	 */
	public static String getMilSecNow() {
		return String.valueOf(System.currentTimeMillis());
	}

	public static void main(String[] args) {
		System.out.println(DateTimeUtil.getCurrentDateTime());
		System.out.println(DateTimeUtil.getDateTime());
		System.out.println(DateTimeUtil.getCurrentDate());
		System.out.println(DateTimeUtil.getCurrentTime());
		System.out.println(DateTimeUtil.getTime());
		System.out.println(DateTimeUtil.addDaysByFormatter(+5, "yyyy-MM-dd"));
		System.out.println(DateTimeUtil.addDaysByFormatter(-5, "yyyy-MM-dd"));
		System.out.println(DateTimeUtil.addMonthsByFormatter(+4, "yyyy-MM-dd"));
		System.out.println(DateTimeUtil.addMonthsByFormatter(-4, "yyyy-MM-dd"));
		System.out.println(DateTimeUtil.addYearsByFormatter(+3, "yyyy-MM-dd"));
		System.out.println(DateTimeUtil.addYearsByFormatter(-3, "yyyy-MM-dd"));
		System.out.println(System.getProperty("user.dir"));
		System.out.println("###"
				+ DateTimeUtil.formatedTime("yyyy-MM-dd HH:mm:ss:SSS"));
		System.out.println("###" + new Date().getTime());
	}
}
