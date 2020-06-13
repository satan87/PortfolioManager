package com.nsa.portfoliomanager.classes.info;

import com.nsa.portfoliomanager.classes.marketdata.Instrument;
import com.nsa.portfoliomanager.classes.portfolio.Portfolio;

public interface CommissionCalculable {
	
	public Double getCommissionpayed(Portfolio portfolio);
	
	public Double getCommissionpayed(Portfolio portfolio , Instrument instrument);
}
