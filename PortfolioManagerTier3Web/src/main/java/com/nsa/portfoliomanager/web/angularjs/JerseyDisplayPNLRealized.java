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
import com.nsa.portfoliomanager.web.display.DisplayPNLRealized;



@Path("/pnlrealized/{id}")
public class JerseyDisplayPNLRealized {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<DisplayPNLRealized> produceJSON( @PathParam("id") String id ) {
		
		List<DisplayPNLRealized> ldp = new ArrayList<DisplayPNLRealized>();
		
		for (Portfolio ptf : A_LOAD.PORTFOLIOS){
			if ( ptf.getId().equals(Integer.parseInt(id))){
				for (Instrument i : A_LOAD.TOOL.getInstruments(ptf)  ){
					if ( A_LOAD.TOOL.hasClosePositions(ptf, i) || A_LOAD.TOOL.getDividend(ptf, i) > 0 )
						ldp.add( new DisplayPNLRealized(ptf, i) );
					
				}
			}
		}
		
		return ldp; 
	}
	
}

