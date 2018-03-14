package com.nsa.portfoliomanager.classes.info;

import java.util.Date;
import java.util.List;

import com.nsa.portfoliomanager.classes.marketdata.Instrument;
import com.nsa.portfoliomanager.classes.portfolio.Portfolio;

public interface Info {
	
	//CALCUL
	//CASH
	public Double getCashInvested(Portfolio portfolio);
	public Double getCashInvested(Portfolio portfolio, Date date, boolean before);
	
	public Double getCashAvailable(Portfolio portfolio);
	public Double getCashAvailable(Portfolio portfolio , Date date);
	
	// COMMISSION
	public Double getCommissionpayed(Portfolio portfolio);
	public Double getCommissionpayed(Portfolio portfolio , Instrument instrument);
	
	//DIVIDEND
	public Double getDividend(Portfolio portfolio);
	public Double getDividend(Portfolio portfolio , Instrument instrument);
	public Double getDividend(Portfolio portfolio , Instrument instrument, Date begin , Date end);
	

	//TOOL
	//PORTFOLIO
	public Date FirstTransaction(Portfolio p);
	public Date LastTransaction(Portfolio p);
	
	public List<Instrument> getInstruments(Portfolio portfolio);
	public boolean hasOpenPositions(Portfolio portfolio);
	public boolean hasOpenPositions(Portfolio portfolio, Instrument instrument);
	public boolean hasOpenPositions(Portfolio portfolio, Instrument instrument, Date at);
	
	public Double openPositionQuantity(Portfolio portfolio, Instrument instrument);
	public Double openPositionQuantity(Portfolio portfolio, Instrument instrument, Date at); 
	
	public boolean hasClosePositions(Portfolio portfolio);
	public boolean hasClosePositions(Portfolio portfolio , Instrument instrument);
	public boolean hasClosePositions(Portfolio portfolio , Instrument instrument, Date at);
	
	// STATS
	public Integer getNumberOfTrades(Portfolio p);
	public Integer getNumberOfOpenPosition(Portfolio p);
	
}
