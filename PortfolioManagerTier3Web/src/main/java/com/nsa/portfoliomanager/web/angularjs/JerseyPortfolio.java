package com.nsa.portfoliomanager.web.angularjs;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.nsa.portfoliomanager.classes.marketdata.Instrument;
import com.nsa.portfoliomanager.classes.portfolio.Portfolio;
import com.nsa.portfoliomanager.classes.transaction.Trade;
import com.nsa.portfoliomanager.classes.transaction.Transaction;
import com.nsa.portfoliomanager.web.display.DisplayPortfolio;


@Path("/portfolios")
public class JerseyPortfolio {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<DisplayPortfolio> produceJSON(  ) {
		
		List<DisplayPortfolio> ldp = new ArrayList<DisplayPortfolio>();
		
		for (Portfolio p : A_LOAD.PORTFOLIOS){
			DisplayPortfolio dp = new DisplayPortfolio();
			
			dp.setPortfolio(p);			
			//Cash Invested
			dp.setCashInvested( A_LOAD.TOOL.getCashInvested(p) );
			//Cash Remaining
			dp.setCashRemaining( A_LOAD.TOOL.getCashAvailable(p) );			
			//Number of Trades
			dp.setNumberOfTrades( A_LOAD.TOOL.getNumberOfTrades(p) );
			//NUmber of Instruments
			dp.setNumberOfInstruments( A_LOAD.TOOL.getInstruments(p).size() );
			//Number of Open position
			dp.setNumberOfOpenPostions( A_LOAD.TOOL.getNumberOfOpenPosition(p) );
			
			ldp.add(dp);
		}
		
		
		return ldp;
	}
	
}

