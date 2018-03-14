package com.nsa.portfoliomanager.core.out;

import java.nio.file.Path;
import java.util.List;

import com.nsa.portfoliomanager.classes.portfolio.Portfolio;


public interface Out {

	public List<Portfolio> getPortfolios(Path path);	
	public Portfolio getPortfolio(Path path, Integer idPortfolio);
	
	
}
