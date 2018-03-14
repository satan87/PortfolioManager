package com.nsa.portfoliomanager.web.display;

import com.nsa.portfoliomanager.classes.portfolio.Portfolio;

public class DisplayPortfolio {
	
	private Portfolio portfolio;
	private Double cashInvested;
	private Double cashRemaining;
	private Integer numberOfTrades;
	private Integer numberOfInstruments;
	private Integer numberOfOpenPostions;
	
	
	public Portfolio getPortfolio() {
		return portfolio;
	}
	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}
	public Double getCashInvested() {
		return cashInvested;
	}
	public void setCashInvested(Double cashInvested) {
		this.cashInvested = cashInvested;
	}
	public Double getCashRemaining() {
		return cashRemaining;
	}
	public void setCashRemaining(Double cashRemaining) {
		this.cashRemaining = cashRemaining;
	}
	public Integer getNumberOfTrades() {
		return numberOfTrades;
	}
	public void setNumberOfTrades(Integer numberOfTrades) {
		this.numberOfTrades = numberOfTrades;
	}
	public Integer getNumberOfInstruments() {
		return numberOfInstruments;
	}
	public void setNumberOfInstruments(Integer numberOfInstruments) {
		this.numberOfInstruments = numberOfInstruments;
	}
	public Integer getNumberOfOpenPostions() {
		return numberOfOpenPostions;
	}
	public void setNumberOfOpenPostions(Integer numberOfOpenPostions) {
		this.numberOfOpenPostions = numberOfOpenPostions;
	}
	
	
	
	
	
}
