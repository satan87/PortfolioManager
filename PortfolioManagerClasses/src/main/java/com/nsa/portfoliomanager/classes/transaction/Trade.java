package com.nsa.portfoliomanager.classes.transaction;

import java.util.Date;

import com.nsa.portfoliomanager.classes.marketdata.Instrument;

/**
 *  Trade class extends Transaction.
 *  It defines 1 Trade ( a buy or a sell )
 *   
 * @author Nicolas Savoini
 * @version 1.0
 *
 */

public class Trade extends Transaction {

	private Instrument instrument;
	private Double price;
	private Double quantity;
	
	public Trade(){
		super();
	}
	public Trade(Date dateExecution, Date dateEffective,String comment, Instrument instrument, Double price, Double quantity){
		super(dateExecution,dateEffective,comment);
		this.instrument = instrument;
		this.price = price;
		this.quantity = quantity;
	}
	public Trade(Date dateExecution, Date dateEffective,Double commission, Instrument instrument, Double price, Double quantity){
		super(dateExecution,dateEffective,commission);
		this.instrument = instrument;
		this.price = price;
		this.quantity = quantity;
	}
	public Trade(Date dateExecution, Date dateEffective,String comment,Double commission, Instrument instrument, Double price, Double quantity){
		super(dateExecution,dateEffective,comment,commission);
		this.instrument = instrument;
		this.price = price;
		this.quantity = quantity;
	}

	
	
	// Getter / Setter
	public Instrument getInstrument() {
		return instrument;
	}
	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	
	@Override
	public String getType(){
		return new String("Trade");
	}
	
	
	// Function
	/**
	 * String representing if the trade is a BUY or a SELL
	 * @return String
	 */
	
	public String getBuySell(){
		if (this.quantity > 0)
			return BUYSELL.BUY;
		
		return BUYSELL.SELL;
	}
	
}
