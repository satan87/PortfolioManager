package test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.nsa.portfoliomanager.classes.marketdata.Instrument;
import com.nsa.portfoliomanager.classes.portfolio.Portfolio;
import com.nsa.portfoliomanager.core.out.OutImplementation;
import com.nsa.portfoliomanager.priceservice.service.StockPrice;
import com.nsa.portfoliomanager.priceservice.yahoo.PriceServiceYahoo;
import com.nsa.portfoliomanager.web.angularjs.A_LOAD;
import com.nsa.portfoliomanager.web.display.DisplayPNLRealized;
import com.nsa.portfoliomanager.web.display.DisplayPositionRT;


public class Test {

	public static void main(String[] args) {
		
		System.out.println("Start");
		A_LOAD a = new A_LOAD();
		//List<Portfolio> lp = new OutImplementation().getPortfolios( Paths.get( ClassLoader.getSystemResource("portfolios.csv").getFile() ) );
		
		//List<DisplayPositionRT> ldp = new ArrayList<DisplayPositionRT>();
		
		//List<Portfolio> lp = new OutImplementation().getPortfolios(Paths.get("C:\\win32app\\419988\\dev\\workspace\\portfolios.csv"));
		List<Portfolio> lp = new OutImplementation().getPortfolios(Paths.get("/data/dev/tomcat/v8/webapps/portfolios.csv"));
		Portfolio p=null;
		
		for (Portfolio ptf : lp){
			if ( ptf.getId().equals(Integer.parseInt("1"))){
				p=ptf;
			}
		}
		
				
				List<DisplayPNLRealized> ldp = new ArrayList<DisplayPNLRealized>();
				
				for (Portfolio ptf : a.PORTFOLIOS){
					if ( ptf.getId().equals(1)){
						for (Instrument i : a.TOOL.getInstruments(ptf)  ){
							if ( a.TOOL.hasClosePositions(ptf, i) )
								ldp.add( new DisplayPNLRealized(ptf, i) );
							
						}
					}
				}

	
		
		System.out.println("End");
	}

}
