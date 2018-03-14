package com.nsa.portfoliomanager.web.angularjs;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.nsa.portfoliomanager.priceservice.memory.InMemoryPrices;
import com.nsa.portfoliomanager.priceservice.service.StockPrice;
import com.nsa.portfoliomanager.web.angularjs.post.PostStockPrice;


@Path("/myprices/")
public class JerseyInMemoryPrices {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<StockPrice> produceJSON() {
		return InMemoryPrices.getInstance().getStockPrices();
	}
		
	@GET
	@Path("{country}/{symbol}")
	@Produces(MediaType.APPLICATION_JSON)
	public StockPrice produceJSON( @PathParam("country") String country , @PathParam("symbol") String symbol ) {
		return InMemoryPrices.getInstance().get( new StockPrice(symbol, country)  );
	}
	
	@POST
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public void create(PostStockPrice psp)
	{
		StockPrice sp = new StockPrice(psp.getInstrument(), psp.getCountry());
		sp.setLastPrice(psp.getLastPrice());
		sp.setClosePrice(psp.getClosePrice());
		
		InMemoryPrices.getInstance().put( sp );
	}
	  
}

