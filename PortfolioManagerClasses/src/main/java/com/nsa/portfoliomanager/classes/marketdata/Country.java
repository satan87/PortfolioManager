package com.nsa.portfoliomanager.classes.marketdata;


/**
 *  THis class is for a specific country.
 *   
 * 
 * @author Nicolas Savoini
 * @version 1.0
 *
 */
public class Country {
	
	private String name;
	private String currency;
	private String marketName;
	private String googleNotation;
	private String yahooNotation;
	
	public Country(){
		super();
	}
	public Country(String name){
		super();
		this.name=name;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public String getGoogleNotation() {
		return googleNotation;
	}
	public void setGoogleNotation(String googleNotation) {
		this.googleNotation = googleNotation;
	}
	public String getYahooNOtation() {
		return yahooNotation;
	}
	public void setYahooNOtation(String yahooNOtation) {
		this.yahooNotation = yahooNOtation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Country other = (Country) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equalsIgnoreCase((other.name)))
					return false;
		return true;
	}
	
	
	

}
