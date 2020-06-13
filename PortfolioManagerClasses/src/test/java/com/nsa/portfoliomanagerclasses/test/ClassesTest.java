package com.nsa.portfoliomanagerclasses.test;

import static org.junit.Assert.assertEquals;
import java.util.Calendar;


import org.junit.Test;
import com.nsa.portfoliomanager.classes.format.DateFormator;


public class ClassesTest {
	
	@Test
	public void testFormat() {
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2020);
		calendar.set(Calendar.MONTH, 5);
		calendar.set(Calendar.DAY_OF_MONTH, 13);
		
		assertEquals("20200613", DateFormator.yyyymmdd(calendar.getTime()));
	}

}
