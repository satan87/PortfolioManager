package com.nsa.portfoliomanager.classes.info;

import java.util.Date;
import java.util.List;
import com.nsa.portfoliomanager.classes.marketdata.Instrument;
import com.nsa.portfoliomanager.classes.portfolio.Portfolio;

public interface Toolable {
	public Date firstTransaction(Portfolio p);
	public Date lastTransaction(Portfolio p);
	
	public List<Instrument> getInstruments(Portfolio portfolio);
	public boolean hasOpenPositions(Portfolio portfolio);
	public boolean hasOpenPositions(Portfolio portfolio, Instrument instrument);
	public boolean hasOpenPositions(Portfolio portfolio, Instrument instrument, Date at);
	
	public Double openPositionQuantity(Portfolio portfolio, Instrument instrument);
	public Double openPositionQuantity(Portfolio portfolio, Instrument instrument, Date at); 
	
	public boolean hasClosePositions(Portfolio portfolio);
	public boolean hasClosePositions(Portfolio portfolio , Instrument instrument);
	public boolean hasClosePositions(Portfolio portfolio , Instrument instrument, Date at);
}
