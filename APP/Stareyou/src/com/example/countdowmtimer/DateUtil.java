package com.example.countdowmtimer;

/**
 * 时间操作类
 * 
 * @author Tiny
 * 
 */

public class DateUtil {
	public static String getHour3(long second) {
		String hours, minutes, secs;
		long hour = second / 60 / 60;
		long minute = (second - hour * 60 * 60) / 60;
		long sec = (second - hour * 60 * 60) - minute * 60;
		if (0 < hour && hour < 10) {
			hours = "0" + hour;
		} else if (0 == hour) {
			hours = "00";
		} else {
			hours = "" + hour;
		}
		if (0 < minute && minute < 10) {
			minutes = "0" + minute;
		} else if (0 == minute) {
			minutes = "00";
		} else {
			minutes = "" + minute;
		}
		if (0 < sec && sec < 10) {
			secs = "0" + sec;
		} else if (0 == sec) {
			secs = "00";
		} else {
			secs = "" + sec;
		}
		return hours + ":" + minutes + ":" + secs;

	}
}
