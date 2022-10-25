package com.monsterclickgame.utils;

import java.util.Calendar;
import java.util.Date;

import com.badlogic.gdx.utils.TimeUtils;

public final class CustomTimeUtils {
	static public int getHoursOfDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(TimeUtils.millis()));
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	static public boolean isNight() {
		int hour = 0;
		boolean isNight = false;
		
		hour = getHoursOfDay();
		if (hour <= 4 || hour >= 20) {
			isNight = true;
		}
		
		return isNight;
	}
	
	static public String formatMillisInTime(long millis) {
		long second = (millis / 1000) % 60;
		long minute = (millis / (1000 * 60)) % 60;
		
		return String.format("%02d:%02d", minute, second);
	}
}
