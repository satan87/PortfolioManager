package test;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nsa.portfoliomanager.priceservice.memory.InMemoryPrices;
import com.nsa.portfoliomanager.priceservice.service.StockPrice;
import com.nsa.portfoliomanager.priceservice.yahoo.PriceServiceYahoo;

public class TEST {

	public static void main(String[] args) {
		
		PriceServiceYahoo ps = new PriceServiceYahoo();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		StockPrice a = new StockPrice("CHR.B","CANADA");
		a.setLastPrice(25.00);
		InMemoryPrices.getInstance().put(a);
		
		StockPrice sp = new StockPrice("STB","CANADA");


		ps.getInfo(sp) ;
		System.out.println( "test :" + sp.getFullName() + " : " + sp.getLastPrice() + " (close=" + sp.getClosePrice() + ")  on " + df.format(sp.getDate()));
		

		
		/*
		sp = new StockPrice("STB","Canada" , new Date(2014,11,29));
		ps.getInfo(sp) ;
		System.out.println( sp.getFullName() + " : " + sp.getPrice() + " on " + df.format(sp.getDate()));
		
		sp = new StockPrice("STB","Canada" , new Date(2014,11,8));
		ps.getInfo(sp) ;
		System.out.println( sp.getFullName() + " : " + sp.getPrice() + " on " + df.format(sp.getDate()));
		
		sp = new StockPrice("STB","Canada" , new Date(2014,11,01));
		ps.getInfo(sp) ;
		System.out.println( sp.getFullName() + " : " + sp.getPrice() + " on " + df.format(sp.getDate()));
		
		
		sp = new StockPrice("CHR.B","Canada");
		ps.getInfo(sp) ;
		System.out.println( sp.getFullName() + " : " + sp.getPrice() + " on " + df.format(sp.getDate()));
		
		sp = new StockPrice("RMM.UN","Canada");
		ps.getInfo(sp) ;
		System.out.println( sp.getFullName() + " : " + sp.getPrice() + " on " + df.format(sp.getDate()));
		

		List<StockPrice> sps = new ArrayList<StockPrice>();
		sps.add(new StockPrice("STB","Canada"));
		sps.add(new StockPrice("CHR.B","Canada"));
		sps.add(new StockPrice("STB","Canada" , new Date(2014,11,29)));
		ps.getInfo(sps) ;
		*/
		
		System.out.println("END");
		
	}

}
