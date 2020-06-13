package com.nsa.portfoliomanager.classes.info;

import com.nsa.portfoliomanager.classes.portfolio.Portfolio;

public interface Statisticable {
	public Integer getNumberOfTrades(Portfolio p);
	public Integer getNumberOfOpenPosition(Portfolio p);
}
