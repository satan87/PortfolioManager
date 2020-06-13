package com.nsa.portfoliomanager.classes.format;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormator {
	
	private DateFormator() {
		//Do nothing
	}
	
	public static String yyyymmdd(Date date) {
		return  new SimpleDateFormat("yyyyMMdd").format(date);
	}
	
}
