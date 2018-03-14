package com.nsa.portfoliomanager.classes.format;

import java.text.SimpleDateFormat;

public final class Dates {
	
	private Dates() {
	    throw new IllegalStateException("Dates class");
	}

	public static final SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
	
}
