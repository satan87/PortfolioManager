package com.nsa.portfoliomanager.classes.marketdata;

/**
 *  Stock
 *   
 * 
 * @author Nicolas Savoini
 * @version 1.0
 *
 */
public class Stock extends Instrument{

	private String name;
	
	public Stock(){
		super();
	}
	
	public Stock(String symbol, Country country, String name){
		super(symbol,country);
		this.name=name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
