package com.nsa.portfoliomanager.priceservice.service;

import java.util.List;

public interface PriceService {

	public void getInfo(StockPrice stockPrice);
	public void getInfo(List<StockPrice> stockPrices);
	
	
}
