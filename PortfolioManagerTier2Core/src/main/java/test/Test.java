package test;

import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.nsa.portfoliomanager.classes.marketdata.Country;
import com.nsa.portfoliomanager.classes.marketdata.Instrument;
import com.nsa.portfoliomanager.classes.marketdata.Stock;
import com.nsa.portfoliomanager.classes.pnl.PNL;
import com.nsa.portfoliomanager.classes.portfolio.Portfolio;
import com.nsa.portfoliomanager.classes.print.Print;
import com.nsa.portfoliomanager.classes.print.PrintString;
import com.nsa.portfoliomanager.classes.transaction.BUYSELL;
import com.nsa.portfoliomanager.classes.transaction.Cash;
import com.nsa.portfoliomanager.classes.transaction.Trade;
import com.nsa.portfoliomanager.classes.transaction.Transaction;
import com.nsa.portfoliomanager.classes.transaction.TransactionComparator;
import com.nsa.portfoliomanager.core.calculs.InfoImpl;
import com.nsa.portfoliomanager.core.out.OutImplementation;
import com.nsa.portfoliomanager.core.pnl.PNLImpl;


@SuppressWarnings("unused")
public class Test {

	public static void main(String[] args) {
		
		System.out.println("START");
		
		Stock TBE = new Stock("TBE",new Country("Canada"),"Twin Butte Energy Ltd");
		Stock BBD = new Stock("BBD.B",new Country("Canada"),"Bombardier Inc.");
		Stock RMM = new Stock("RMM.UN",new Country("Canada"),"Retrocom Real Estate");
		Stock BXE = new Stock("BXE",new Country("Canada"),"Bellatrix Exploration Ltd");
		Stock PXT = new Stock("PXT",new Country("Canada"),"Bellatrix Exploration Ltd");
		Stock CNE = new Stock("CNE",new Country("Canada"),"Bellatrix Exploration Ltd");
		
		//List<Portfolio> myList = new OutImplementation().getPortfolios();
		
		//for (Portfolio p : myList){System.out.println( "name : " + p.getName() + "   id=" + p.getId() );}
		
		List<Portfolio> lp = new OutImplementation().getPortfolios(Paths.get("/data/dev/tomcat/v8/webapps/portfolios.csv"));
		Portfolio p=lp.get(0);
		
		Collections.sort(p.getTransaction(),TransactionComparator.compareDateEffective());
		
		InfoImpl cb = new InfoImpl();
		Print ps = new PrintString();
		
		Double cash = cb.getCashInvested(p);
		cash += cb.getDividend(p);
		
		for (Transaction t : p.getTransaction()){
			if ( t instanceof Trade ){
				if ( ((Trade) t).getBuySell().equals(BUYSELL.BUY)){
					cash = cash - ( ((Trade) t).getPrice() * ((Trade) t).getQuantity() );
					cash = cash - ((Trade) t).getCommission();
				}else{
					cash = cash + ( ((Trade) t).getPrice() * -((Trade) t).getQuantity() );
					cash = cash - ((Trade) t).getCommission();
				}
			}
		}

		
		System.out.println("cash : " + cash );
		
		
		System.out.println("cash v2 : " + cb.getCashAvailable(p) );
		
		LocalDate ld =  LocalDate.of(2014, Month.JUNE, 18);
		System.out.println("cash v3 : " + cb.getCashAvailable(p,Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() )) );
		
		ld =  LocalDate.of(2014, Month.JUNE, 20);
		System.out.println("cash v3 : " + cb.getCashAvailable(p,Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() )) );
		
		System.out.println( "Com : " + cb.getCommissionpayed(p) );
		System.out.println( "Com TBE : " + cb.getCommissionpayed(p,TBE) );
		
		

		
		System.out.println("END");

	}

}
