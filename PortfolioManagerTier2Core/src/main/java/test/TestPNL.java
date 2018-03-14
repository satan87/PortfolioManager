package test;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

import com.nsa.portfoliomanager.classes.marketdata.Country;
import com.nsa.portfoliomanager.classes.marketdata.Stock;
import com.nsa.portfoliomanager.classes.portfolio.Portfolio;
import com.nsa.portfoliomanager.classes.transaction.Trade;
import com.nsa.portfoliomanager.core.out.OutImplementation;
import com.nsa.portfoliomanager.core.pnl.PNLImpl;

public class TestPNL {

	public static void main(String[] args) {
		System.out.println("START");
		
		Portfolio p = new OutImplementation().getPortfolio(Paths.get("C:\\win32app\\419988\\dev\\workspace\\portfolios.csv"),1);
		
		Stock BXE = new Stock("CNE",new Country("Canada"),"Canacol Energy");
		Stock AAPL = new Stock("AAPL",new Country("US"),"Apple");
		Stock GRPN = new Stock("GRPN",new Country("US"),"GGroupon");
		
		LocalDate ld =  LocalDate.of(2014, Month.JUNE, 24);
		p.addTransaction(new Trade(Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ),Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ), "", 0.1, BXE, 9.16, 10.00));
		
		ld =  LocalDate.of(2014, Month.JUNE, 30);
		p.addTransaction(new Trade(Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ), Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ), "", 0.1, BXE, 9.23, -10.00));
		
		ld =  LocalDate.of(2014, Month.AUGUST, 18);
		p.addTransaction(new Trade(Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ), Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ), "", 0.19, BXE, 7.88, 19.00));
		
		ld =  LocalDate.of(2014, Month.NOVEMBER, 18);
		p.addTransaction(new Trade(Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ), Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ), "", 0.38, BXE, 5.17, 38.00));
		
		ld =  LocalDate.of(2014, Month.DECEMBER, 01);
		p.addTransaction(new Trade(Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ), Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ), "", 0.43, BXE, 4.52, 43.00));
		
		PNLImpl pnl = new PNLImpl(p, BXE,4.28);
		
		System.out.println( "PNL AAPL Realized = " + pnl.getPNLRealized(false) + " / " + pnl.getPNLRealized(true) );
		System.out.println( "PNL AAPL UNRealized = " + pnl.getPNLUnrealized(false) + " / " + pnl.getPNLUnrealized(true) );
		
		/*		  	
		Bellatrix Exploration...	BXE	Buy	Jun 24, 2014	10.00	9.16	-91.70	0.10
		Bellatrix Exploration...	BXE	Sell	Jun 30, 2014	10.00	9.23	92.20	0.10 -> 0.5
	
		Bellatrix Exploration...	BXE	Buy	Aug 18, 2014	19.00	7.88	-149.91	0.19
		Bellatrix Exploration...	BXE	Buy	Nov 18, 2014	38.00	5.17	-196.84	0.38
		Bellatrix Exploration...	BXE	Buy	Dec 1, 2014	43.00	4.52	-194.79	0.43 -> -116
		
		 */
		
		System.out.println("END");

	}

}
