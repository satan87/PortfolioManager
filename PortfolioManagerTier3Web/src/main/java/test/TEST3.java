package test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.nsa.portfoliomanager.classes.marketdata.Instrument;
import com.nsa.portfoliomanager.classes.portfolio.Portfolio;
import com.nsa.portfoliomanager.core.calculs.InfoImpl;
import com.nsa.portfoliomanager.core.out.OutImplementation;
import com.nsa.portfoliomanager.core.pnl.PNLImpl;
import com.nsa.portfoliomanager.priceservice.memory.InMemoryPrices;
import com.nsa.portfoliomanager.priceservice.service.StockPrice;
import com.nsa.portfoliomanager.priceservice.yahoo.PriceServiceYahoo;
import com.nsa.portfoliomanager.web.angularjs.A_LOAD;
import com.nsa.portfoliomanager.web.display.DisplayPNLRealized;
import com.nsa.portfoliomanager.web.display.DisplayPositionRT;

public class TEST3 {

	public static void main(String[] args) {
		

		System.out.println("START");
		List<DisplayPositionRT> ldp = new ArrayList<DisplayPositionRT>();
		InfoImpl TOOL = new InfoImpl();
		
		Portfolio p = new OutImplementation().getPortfolios(Paths.get("C:\\win32app\\419988\\dev\\workspace\\portfolios.csv")).get(0);
		PNLImpl pnl = null;
				
		if (p!=null){
			PriceServiceYahoo ps = new PriceServiceYahoo();

			for (Instrument i : TOOL.getInstruments(p)  ){
				
				if ( TOOL.hasOpenPositions(p, i) ){
					DisplayPositionRT dp = new DisplayPositionRT();

					//Set Instrument
					dp.setInstrument(i);
					
					//Set Quantity
					dp.setQuantity( TOOL.openPositionQuantity(p, i) );

					//SET PRICE FROM MARKET
					
					
					StockPrice sp = new StockPrice( i.getSymbol() ,i.getCountry().getName() );
					
					ps.getInfo(sp);
					
					dp.setLastPrice( sp.getLastPrice() );
					//TODO
					dp.setPreviousLastPrice( sp.getLastPrice() );
					dp.setClosePrice( sp.getClosePrice() );
					
					//PNL
					//Set COST
					pnl = new PNLImpl(p, i);
					dp.setCost( pnl.getPNLUnrealized(false));
					dp.setCostWithCommission(pnl.getPNLUnrealized(true));
					
					//We add the line
					ldp.add(dp);
				}
			}
		}
		
		
		System.out.println("END");
	}

}
