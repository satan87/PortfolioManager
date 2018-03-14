package com.nsa.portfoliomanager.priceservice.memory;

import java.util.ArrayList;
import java.util.List;

import com.nsa.portfoliomanager.priceservice.service.StockPrice;

public class InMemoryPrices {
	
	private static InMemoryPrices instance;
	private List<StockPrice> stockPrices;
	
	public InMemoryPrices(){
		super();
		this.stockPrices = new ArrayList<StockPrice>(20);
	}
	
	public static InMemoryPrices getInstance(){
		if (instance==null) {
			instance=new InMemoryPrices();
		} 
		return instance;
	}

	public List<StockPrice> getStockPrices() {
		return stockPrices;
	}

	public void put(StockPrice stockPrice){
		if ( !this.stockPrices.contains(stockPrice) ){
			this.stockPrices.add(  new StockPrice(stockPrice)  );
		}
		else{
			this.stockPrices.remove(stockPrice);
			this.stockPrices.add(  new StockPrice(stockPrice)  );
		}
	}
	
	public StockPrice get(StockPrice stockPrice){
		if ( this.stockPrices.contains(stockPrice) ){
			return this.stockPrices.get( this.stockPrices.indexOf(stockPrice) );
		}else
			return null;
	}
	
	
}
