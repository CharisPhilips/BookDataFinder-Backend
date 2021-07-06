package com.bdf.common;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static int getDiffDays(Date dtStart, Date dtEnd) {
		
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		
		start.setTime(dtStart);
		end.setTime(dtEnd);
		
		Date startDate = start.getTime();
		Date endDate = end.getTime();
		
		long startTime = startDate.getTime();
		long endTime = endDate.getTime();
		
		long diffTime = endTime - startTime;
		long diffDays = diffTime / (1000 * 60 * 60 * 24);
		return (int) diffDays;
	}
}
