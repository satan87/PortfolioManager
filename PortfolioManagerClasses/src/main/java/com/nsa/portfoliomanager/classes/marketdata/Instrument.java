package com.nsa.portfoliomanager.classes.marketdata;

/**
 *  Parent class Instrument
 *  Could be : Stock, Bond etc ...
 *   
 * 
 * @author Nicolas Savoini
 * @version 1.0
 *
 */
public class Instrument {
	
	private String symbol;
	private Country country;
	
	
	public Instrument(){
		super();
	}
	public Instrument (String symbol){
		this.symbol = symbol;
	}
	public Instrument (String symbol,Country country){
		this.symbol = symbol;
		this.country = country;
	}
	
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	
	
	@Override
	public boolean equals(Object other){
		boolean equal = false;
		
		if ( other instanceof Instrument ){
			
			if ( this.symbol.compareToIgnoreCase(((Instrument)other).getSymbol()) == 0 ){
				if ( this.country.equals( ((Instrument)other).getCountry()) ){
					equal= true;
				}else{
					equal = false;
				}
			}else{
				equal = false;
			}
		}else{
			equal = false;
		}
		
		return equal;
	}

}
