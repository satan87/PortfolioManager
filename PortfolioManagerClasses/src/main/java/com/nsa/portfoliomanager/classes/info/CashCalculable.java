package com.nsa.portfoliomanager.classes.info;

import java.util.Date;

import com.nsa.portfoliomanager.classes.portfolio.Portfolio;

public interface CashCalculable {
	
	public Double getCashInvested(Portfolio portfolio);
	
	public Double getCashInvested(Portfolio portfolio, Date date, boolean before);
	
	public Double getCashAvailable(Portfolio portfolio);
	
	public Double getCashAvailable(Portfolio portfolio , Date date);
}
