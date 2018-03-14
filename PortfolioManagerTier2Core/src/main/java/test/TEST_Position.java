package test;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.TemporalField;
import java.util.Date;

import com.nsa.portfoliomanager.classes.marketdata.Instrument;
import com.nsa.portfoliomanager.classes.portfolio.Portfolio;
import com.nsa.portfoliomanager.core.calculs.InfoImpl;
import com.nsa.portfoliomanager.core.out.OutImplementation;
import com.nsa.portfoliomanager.priceservice.google.PriceServiceYahoo;
import com.nsa.portfoliomanager.priceservice.service.StockPrice;

public class TEST_Position {

	public static void main(String[] args) {
		
		
		Portfolio p = new OutImplementation().getPortfolio(1);

		InfoImpl info = new InfoImpl();
		PriceServiceYahoo ps = new PriceServiceYahoo();
		LocalDate ld =  LocalDate.of(2014, Month.DECEMBER, 29);
		
		for (int j = 0 ; j < 2  ; j++){
		
			Date d = Date.from( ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant() );
			System.out.println(d);
			Double pos = 0.00;
			
			for (Instrument i : info.getInstruments(p)){
				
				if (info.hasOpenPositions(p, i, d)){
					StockPrice sp = new StockPrice(i.getSymbol(), i.getCountry().getName() , d );
					ps.getInfo(sp);
					
					System.out.println( "Instrument " + i.getSymbol() + " open for " + info.openPositionQuantity(p, i,d) + " at " +  sp.getPrice() + " equals POS of : " + info.openPositionQuantity(p, i) * sp.getPrice());
					pos += info.openPositionQuantity(p, i,d) * sp.getPrice(); 
				}
			}
			
			System.out.println("POS without cash :" + pos);
			
			pos += info.getCashAvailable(p, d);
			System.out.println(info.getCashAvailable(p, d));
			
			System.out.println("POS with cash :" + pos);
			
			ld = ld.plusDays(1);
		}
		
	}

}
