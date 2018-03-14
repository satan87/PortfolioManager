package test;

import com.nsa.portfoliomanager.priceservice.service.StockPrice;
import com.nsa.portfoliomanager.priceservice.yahoo.PriceServiceYahoo;

public class TestPrice {

	public static void main(String[] args) {
	
		PriceServiceYahoo ps = new PriceServiceYahoo();
		
		StockPrice sp = new StockPrice("BXE","CANADA");
		ps.getInfo(sp) ;
		System.out.println( sp.getFullName() + " : " + sp.getLastPrice() );
		
		sp = new StockPrice("TER","CANADA");
		ps.getInfo(sp) ;
		System.out.println( sp.getFullName() + " : " + sp.getLastPrice() );
		
		
		String symbol="PXT.TO";
		
		sp = null;
		if ( symbol.contains(".TO") )
			sp = new StockPrice(symbol.replace(".TO", ""),"CANADA");
		else
			sp = new StockPrice(symbol,"US");
		ps.getInfo(sp) ;
		System.out.println( sp.getFullName() + " : " + sp.getLastPrice() );
	}

}
