package com.nsa.portfoliomanagerclasses.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import com.nsa.portfoliomanager.classes.format.DateFormator;
import com.nsa.portfoliomanager.classes.marketdata.Country;
import com.nsa.portfoliomanager.classes.marketdata.Instrument;
import com.nsa.portfoliomanager.classes.transaction.Cash;
import com.nsa.portfoliomanager.classes.transaction.Dividend;
import com.nsa.portfoliomanager.classes.transaction.Trade;
import com.nsa.portfoliomanager.classes.transaction.Transaction;


public class ClassesTest {
	
	private Country france = new Country("France");
	private Country canada = new Country("Canada");
	private Country france2 = new Country("FRANCE");
	
	
	@Test
	public void testFormat() {
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2020);
		calendar.set(Calendar.MONTH, 5);
		calendar.set(Calendar.DAY_OF_MONTH, 13);
		
		assertEquals("20200613", DateFormator.yyyymmdd(calendar.getTime()));
	}
	
	@Test
	public void countryEquality() {
		france.setCurrency("EUR");
		france2.setCurrency("CAD");
		assertEquals(france, france2);
		assertNotEquals(france, canada);
	}
	
	@Test 
	public void instrumentEquality() {
		Instrument apple = new Instrument("AAPL", france);
		Instrument apple2 = new Instrument("AAPL", canada);
		Instrument apple3 = new Instrument("AAPL", france2);
		assertEquals(apple, apple3);
		assertNotEquals(apple, apple2);
	}
	
	@Test
	public void printTransactionTrade() {
		Transaction trade = new Trade(new Date(), new Date(), "comment",	1.00, new Instrument("AAPL", france), 10.00, 100.00);
		assertEquals("Buy 100.0 of AAPL for 10.0 per Transaction: -1 Executed On: 20200613 Effectived On: 20200613 Commission: 1.0 Comment: comment", trade.print());
	}
	
	@Test
	public void printTransactionCash() {
		Transaction cash = new Cash();
		((Cash) cash).setAmount(120.0);
		assertEquals("Cash amount of: 120.0 per Transaction: -1 Executed On: 20200613 Effectived On: 20200613", cash.print());
	}
	
	@Test
	public void printTransactionDividend() {
		Transaction dividend = new Dividend();
		((Dividend) dividend).setAmount(120.0);
		((Dividend) dividend).setInstrument(new Instrument("AAPL", france));
		assertEquals("Dividend of: 120.0 for AAPL announced on: 20200613 per Transaction: -1 Executed On: 20200613 Effectived On: 20200613", dividend.print());
	}

}
