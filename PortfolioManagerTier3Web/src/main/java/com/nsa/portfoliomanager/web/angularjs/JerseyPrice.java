package com.nsa.portfoliomanager.web.angularjs;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.nsa.portfoliomanager.priceservice.service.StockPrice;
import com.nsa.portfoliomanager.priceservice.yahoo.PriceServiceYahoo;


@Path("/price/{symbol}")
public class JerseyPrice {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Double produceJSON(  @PathParam("symbol") String symbol ) {
		
		System.out.println("symbol = " + symbol);
		
		Double myPrice = 0.00;
		
		PriceServiceYahoo ps = new PriceServiceYahoo();
		
		StockPrice sp = null;
		
		if ( symbol.contains(".TO") || symbol.contains(".to") ){
			sp = new StockPrice(symbol.replace(".TO", "").replace(".to",""),"CANADA");
		}
		else
			sp = new StockPrice(symbol,"US");
		
		ps.getInfo(sp) ;
		myPrice = sp.getLastPrice();
		
		return myPrice;
	}
	
	/*
	@POST
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public void insertPrice(  ){
		
	}
	*/
}

