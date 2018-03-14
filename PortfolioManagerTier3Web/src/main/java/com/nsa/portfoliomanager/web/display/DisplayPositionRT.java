package com.nsa.portfoliomanager.web.display;

import com.nsa.portfoliomanager.classes.marketdata.Instrument;

public class DisplayPositionRT {
	
	private Instrument instrument;
	private Double cost;
	private Double costWithCommission;
	private Double lastPrice;
	private Double previousLastPrice;
	private Double closePrice;
	private Double quantity;

	public Instrument getInstrument() {
		return instrument;
	}
	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}
	
	//COST
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
		
	public Double getCostWithCommission() {
		return costWithCommission;
	}
	public void setCostWithCommission(Double costWithCommission) {
		this.costWithCommission = costWithCommission;
	}
	
	//LAST PRICE
	public Double getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(Double lastPrice) {
		this.lastPrice = lastPrice;
	}
	
	//PREVIOUS LAST PRICE
	public Double getPreviousLastPrice() {
		return previousLastPrice;
	}
	public void setPreviousLastPrice(Double previousLastPrice) {
		this.previousLastPrice = previousLastPrice;
	}
	
	//CLOSE PRICE
	public Double getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(Double closePrice) {
		this.closePrice = closePrice;
	}
	
	//QUANTITY OPEN
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	
	
	//Calcul
	public Double getCostPerShare() {
		if ( this.quantity!=null && this.cost != null )
			return this.cost / this.quantity ;
		else
			return 0.00;
	}
	
	public Double getMarketValueLastPrice() {
		if ( this.quantity!=null && this.lastPrice != null )
			return this.quantity * this.lastPrice;
		else
			return 0.00;
	}
	
	public Double getDayChange(){
		if ( this.lastPrice!=null && this.lastPrice != -1 && this.closePrice!=null && this.closePrice!=-1 ){
			return this.lastPrice - this.closePrice;
		}else
			return null;
	}
	
	public Double getDayGain(){
		if ( this.lastPrice!=null && this.lastPrice != -1 && this.closePrice!=null && this.closePrice!=-1 && this.quantity!=null){
			return (this.lastPrice - this.closePrice) * this.quantity ;
		}else
			return null;
	}
	
	
	public Double getPositionLastPrice() {
		return this.getMarketValueLastPrice() + this.cost;
	}
	public Double getPositionLastPriceWithCommission() {
		return this.getMarketValueLastPrice() + this.costWithCommission;
	}
	
	public Double getPositionPourcentageLastPrice(){
		return (this.getPositionLastPrice()  / -this.cost);
	}
	public Double getPositionPourcentageLastPriceWithCommission(){
		return (this.getPositionLastPriceWithCommission()  / -this.costWithCommission);
	}
	
	
	
	
}
