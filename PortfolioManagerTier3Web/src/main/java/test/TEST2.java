package test;

import java.util.ArrayList;
import java.util.List;

import com.nsa.portfoliomanager.classes.marketdata.Instrument;
import com.nsa.portfoliomanager.classes.portfolio.Portfolio;
import com.nsa.portfoliomanager.web.angularjs.A_LOAD;
import com.nsa.portfoliomanager.web.display.DisplayPNLRealized;

public class TEST2 {

	public static void main(String[] args) {
		
		
		A_LOAD a = new A_LOAD();
		
		List<DisplayPNLRealized> ldp = new ArrayList<DisplayPNLRealized>();
		
		for (Portfolio ptf : a.PORTFOLIOS){
			if ( ptf.getId().equals(Integer.parseInt("1"))){
				for (Instrument i : a.TOOL.getInstruments(ptf)  ){
					if ( a.TOOL.hasClosePositions(ptf, i) || a.TOOL.getDividend(ptf, i) > 0 )
						ldp.add( new DisplayPNLRealized(ptf, i) );
				}
			}
		}
		
	}
	

	
}
