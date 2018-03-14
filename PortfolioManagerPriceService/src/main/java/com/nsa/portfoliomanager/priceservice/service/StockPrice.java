package com.nsa.portfoliomanager.priceservice.service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StockPrice {
	
	private String instrument;
	private String fullName;
	private String country;
	private Double lastPrice;
	private Double ClosePrice;
	private Date date = new Date();
	
	public StockPrice(){
		super();
	}
	public StockPrice(String instrument , String country){
		super();
		this.instrument=instrument;
		this.country=country;
	}
	public StockPrice(String instrument , String country, Date date){
		super();
		this.instrument=instrument;
		this.country=country;
		this.date = date;
	}
	public StockPrice(StockPrice sp){
		super();
		this.instrument = sp.getInstrument();
		this.fullName = sp.getFullName();
		this.country = sp.getCountry();
		this.lastPrice = sp.getLastPrice();
		this.ClosePrice = sp.getClosePrice();
		this.date = sp.getDate();
	}
	
	
	public String getInstrument() {
		return instrument;
	}
	
	public String getFullName() {
		return fullName;
	}
	public String getCountry() {
		return country;
	}
	public Date getDate() {
		return date;
	}
	
	public String getDateS(){
		if ( this.date != null){
			return new SimpleDateFormat("yyyyMMdd").format(this.date);
		}else{
			return null;
		}
	}
	
	
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}
	public Double getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(Double lastPrice) {
		this.lastPrice = lastPrice;
	}
	public Double getClosePrice() {
		return ClosePrice;
	}
	public void setClosePrice(Double closePrice) {
		ClosePrice = closePrice;
	}

	@Override
	public boolean equals(Object a){
		if ( a == null || ( a!=null && !(a instanceof StockPrice)) ) {
			return false;
		}
		
		StockPrice sec = (StockPrice)a;
		if ( this.instrument.toLowerCase().equals(sec.getInstrument().toLowerCase()) && 
					( this.getDateS().equals( sec.getDateS() ) )
				)
			return true;
		
		return false;
	}
	

}
