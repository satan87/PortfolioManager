package test;


import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

import com.nsa.portfoliomanager.classes.marketdata.Country;
import com.nsa.portfoliomanager.classes.marketdata.Stock;
import com.nsa.portfoliomanager.classes.portfolio.Portfolio;
import com.nsa.portfoliomanager.classes.transaction.Cash;
import com.nsa.portfoliomanager.classes.transaction.Trade;

public class ExLoad {
	
	//LocalDate ld =  LocalDate.of(2014, Month.JUNE, 18);
	//Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() )
	
	public static Portfolio getPortfolioExemple(){
		
		Portfolio p = new Portfolio(1,"Can Real",true);
		
		//Cash
		LocalDate ld =  LocalDate.of(2014, Month.JUNE, 18);
		p.addTransaction( new Cash( Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) , Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) ,"First Deposit", 0.00 , 1000.00) );
		
		ld =  LocalDate.of(2014, Month.NOVEMBER, 24);
		p.addTransaction( new Cash( Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) , Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) ,"Fifth Deposit",0.00,500.00) );
		
		
		ld =  LocalDate.of(2014, Month.JULY, 16);
		p.addTransaction( new Cash( Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) , Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) ,"Second Deposit",0.00,2000.00) );
		
		ld =  LocalDate.of(2014, Month.SEPTEMBER, 24);
		p.addTransaction( new Cash( Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) , Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) ,"Third Deposit",0.00,500.00) );
		
		ld =  LocalDate.of(2014, Month.OCTOBER, 16);
		p.addTransaction( new Cash( Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) , Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) ,"Fourth Deposit",0.00,500.00) );
		
		
		ld =  LocalDate.of(2014, Month.NOVEMBER, 26);
		p.addTransaction( new Cash( Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) , Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) ,"Sitxh Deposit",0.00,150.00) );
		
		ld =  LocalDate.of(2014, Month.DECEMBER, 1);
		p.addTransaction( new Cash( Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) , Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) ,"Seventh Deposit",0.00,800.00) );
		
		
		// Transaction
		Stock TBE = new Stock("TBE",new Country("Canada"),"Twin Butte Energy Ltd");
		Stock BBD = new Stock("BBD.B",new Country("Canada"),"Bombardier Inc.");
		Stock RMM = new Stock("RMM.UN",new Country("Canada"),"Retrocom Real Estate");
		Stock BXE = new Stock("BXE",new Country("Canada"),"Bellatrix Exploration Ltd");
		Stock STB = new Stock("STB",new Country("Canada"),"Student Transportation");
		Stock CNE = new Stock("CNE",new Country("Canada"),"Student Transportation");
		
		ld =  LocalDate.of(2014, Month.JUNE, 19);
		p.addTransaction( new Trade(Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) , Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) ,"First Buy",2.00,TBE,1.895,200.00) );
		p.addTransaction( new Trade(Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) , Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) ,"",1.00,BBD,3.85,100.00) );
		
		ld =  LocalDate.of(2014, Month.NOVEMBER, 12);
		p.addTransaction( new Trade(Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) , Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) ,"",1.00,BBD,4.03,-100.00) );
		
		ld =  LocalDate.of(2014, Month.DECEMBER, 19);
		p.addTransaction( new Trade(Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) , Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) ,"",1.00,BBD,4.00,100.00) );
		
		
		ld =  LocalDate.of(2014, Month.JUNE, 20);
		p.addTransaction( new Trade(Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) , Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) ,"",0.20,RMM,4.60,20.00) );
		ld =  LocalDate.of(2014, Month.NOVEMBER	, 21);
		p.addTransaction( new Trade(Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) , Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) ,"",0.40,RMM,4.15,40.00) );
		//ld =  LocalDate.of(2014, Month.NOVEMBER	, 22);
		//p.addTransaction( new Trade(Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) , Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) ,"",RMM,4.15,1.00,0.01) );
		
		
		ld =  LocalDate.of(2014, Month.JUNE, 24);
		p.addTransaction( new Trade(Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) , Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) ,"",0.10,BXE,9.16,10.00) );
		
		
		ld =  LocalDate.of(2014, Month.NOVEMBER, 25);
		p.addTransaction( new Trade(Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) , Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) ,"",0.30,STB,7.23,30.00) );
		
		
		ld =  LocalDate.of(2014, Month.DECEMBER, 17);
		p.addTransaction( new Trade(Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) , Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) ,"",0.01,CNE,2.05,1.00) );
		p.addTransaction( new Trade(Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) , Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) ,"",0.01,CNE,2.10,-1.00) );
		
		ld =  LocalDate.of(2014, Month.DECEMBER, 19);
		p.addTransaction( new Trade(Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) , Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) ,"",0.02,CNE,2.88,2.00) );
		p.addTransaction( new Trade(Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) , Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) ,"",0.02,CNE,2.48,-2.00) );
		
		ld =  LocalDate.of(2014, Month.DECEMBER, 22);
		p.addTransaction( new Trade(Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) , Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) ,"",0.02,CNE,2.68,2.00) );
		p.addTransaction( new Trade(Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) , Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() ) ,"",0.02,CNE,2.78,-2.00) );
		
		
		return p;
		
	}
	

}
