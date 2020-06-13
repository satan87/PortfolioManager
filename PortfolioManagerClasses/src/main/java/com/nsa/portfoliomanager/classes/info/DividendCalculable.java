package com.nsa.portfoliomanager.classes.info;

import java.util.Date;
import com.nsa.portfoliomanager.classes.marketdata.Instrument;
import com.nsa.portfoliomanager.classes.portfolio.Portfolio;

public interface DividendCalculable {

	public Double getDividend(Portfolio portfolio);
	
	public Double getDividend(Portfolio portfolio , Instrument instrument);
	
	public Double getDividend(Portfolio portfolio , Instrument instrument, Date begin , Date end);
}
