package com.example.Stareyou.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowTime {
	public static String getInterval(Date createAt) {
		// 定义最终返回的结果字符串。
		String interval = null;

		long millisecond = new Date().getTime() - createAt.getTime();

		long second = millisecond / 1000;

		if (second <= 0) {
			second = 0;
		}

		if (second == 0) {
			interval = "刚刚";
		} else if (second < 30) {
			interval = second + "秒以前";
		} else if (second >= 30 && second < 60) {
			interval = "半分钟前";
		} else if (second >= 60 && second < 60 * 60) {
			long minute = second / 60;
			interval = minute + "分钟前";
		} else if (second >= 60 * 60 && second < 60 * 60 * 24) {
			long hour = (second / 60) / 60;
			if (hour <= 3) {
				interval = hour + "小时前";
			} else {
				interval = "今天" + getFormatTime(createAt, "hh:mm");
			}
		} else if (second >= 60 * 60 * 24 && second <= 60 * 60 * 24 * 2) {
			interval = "昨天" + getFormatTime(createAt, "hh:mm");
		} else if (second >= 60 * 60 * 24 * 2 && second <= 60 * 60 * 24 * 7) {
			long day = ((second / 60) / 60) / 24;
			interval = day + "天前";
		} else if (second >= 60 * 60 * 24 * 7) {
			interval = getFormatTime(createAt, "MM-dd hh:mm");
		} else if (second >= 60 * 60 * 24 * 365) {
			interval = getFormatTime(createAt, "YYYY-MM-dd hh:mm");
		} else {
			interval = "0";
		}
		// 最后返回处理后的结果。
		return interval;
	}

	public static String getFormatTime(Date date, String sdf) {
		return (new SimpleDateFormat(sdf)).format(date);
	}

	// string转换Date
	public static Date conversion(String s) {
		try {
			java.text.SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = formatter.parse(s);
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 倒计时工具类
	public static String getHours(long time) {
		long second = time / 1000;
		long hour = second / 60 / 60;
		long minute = (second - hour * 60 * 60) / 60;
		long sec = (second - hour * 60 * 60) - minute * 60;

		String rHour = "";
		String rMin = "";
		String rSs = "";
		// 时
		if (hour < 10) {
			rHour = "0" + hour;
		} else {
			rHour = hour + "";
		}
		// 分
		if (minute < 10) {
			rMin = "0" + minute;
		} else {
			rMin = minute + "";
		}
		// 秒
		if (sec < 10) {
			rSs = "0" + sec;
		} else {
			rSs = sec + "";
		}
		// return hour + "小时" + minute + "分钟" + sec + "秒";
		return rHour + ":" + rMin + ":" + rSs;

	}

}
