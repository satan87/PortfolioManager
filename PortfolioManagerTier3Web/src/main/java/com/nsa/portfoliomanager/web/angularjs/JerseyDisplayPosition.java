package com.nsa.portfoliomanager.web.angularjs;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.nsa.portfoliomanager.classes.marketdata.Instrument;
import com.nsa.portfoliomanager.classes.portfolio.Portfolio;
import com.nsa.portfoliomanager.core.pnl.PNLImpl;
import com.nsa.portfoliomanager.priceservice.service.StockPrice;
import com.nsa.portfoliomanager.priceservice.yahoo.PriceServiceYahoo;
import com.nsa.portfoliomanager.web.display.DisplayPositionRT;


@Path("/position/{id}")
public class JerseyDisplayPosition {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<DisplayPositionRT> produceJSON( @PathParam("id") String id ) {
		
		List<DisplayPositionRT> ldp = new ArrayList<DisplayPositionRT>();
		
		Portfolio p = null;
		PNLImpl pnl = null;
		
		for (Portfolio ptf : A_LOAD.PORTFOLIOS){
			if ( ptf.getId().equals(Integer.parseInt(id))){
				p=ptf;
			}
		}
		
		if (p!=null){
			PriceServiceYahoo ps = new PriceServiceYahoo();
			
			for (Instrument i : A_LOAD.TOOL.getInstruments(p)  ){
				
				if ( A_LOAD.TOOL.hasOpenPositions(p, i) ){
					DisplayPositionRT dp = new DisplayPositionRT();
					
					//Set Instrument
					dp.setInstrument(i);
					
					//Set Quantity
					dp.setQuantity( A_LOAD.TOOL.openPositionQuantity(p, i) );
					
					//SET PRICE FROM MARKET
					StockPrice sp = new StockPrice( i.getSymbol() ,i.getCountry().getName() );
					ps.getInfo(sp);
					dp.setLastPrice( sp.getLastPrice() );
					//TODO
					dp.setPreviousLastPrice( sp.getLastPrice() );
					dp.setClosePrice( sp.getClosePrice() );
					
					//PNL
					//Set COST
					pnl = new PNLImpl(p, i);
					dp.setCost( pnl.getPNLUnrealized(false));
					dp.setCostWithCommission(pnl.getPNLUnrealized(true));
					
					//We add the line
					ldp.add(dp);
				}
			}
		}
		return ldp; 
	}
	
}

