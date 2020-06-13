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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Instrument other = (Instrument) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
					return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equalsIgnoreCase((other.symbol)))
					return false;
		return true;
	}
	
}
