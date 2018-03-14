package test;

import java.nio.file.Paths;
import java.util.List;

import com.nsa.portfoliomanager.classes.marketdata.Instrument;
import com.nsa.portfoliomanager.classes.marketdata.Stock;
import com.nsa.portfoliomanager.classes.portfolio.Portfolio;
import com.nsa.portfoliomanager.classes.transaction.Trade;
import com.nsa.portfoliomanager.core.calculs.InfoImpl;
import com.nsa.portfoliomanager.core.out.OutImplementation;
import com.nsa.portfoliomanager.core.pnl.PNLImpl;




public class Test_Display_Pos {

	public static void main(String[] args) {

		
		//Portfolio p = new OutImplementation().getPortfolio(Paths.get("C:\\win32app\\419988\\dev\\workspace\\portfolios.csv"),1);
		Portfolio p = new OutImplementation().getPortfolio(Paths.get("/data/dev/tomcat/v8/webapps/portfolios.csv"),1);
		InfoImpl tool = new InfoImpl();
		List<Instrument> li = tool.getInstruments(p);
		
		
		
		String sline="NAME	Symbol	Shares\n";
		for ( int i = 0 ; i < li.size() ; i++){
			//Name
			sline += ((Stock)li.get(i)).getName() + "\t";
			//Symbol
			sline += li.get(i).getSymbol() + "\t";
			
			//sline += new PriceServiceYahoo(li.get(i).getSymbol() , li.get(i).getCountry().getName()).getRTPrice() + "\t";
				
			PNLImpl pnl = new PNLImpl(p, li.get(i) );
			
			//Shares
			if ( tool.hasOpenPositions(p,li.get(i)) ){
				sline +=  tool.openPositionQuantity(p, li.get(i)) + " shares \t" ;
				sline += pnl.getPNLUnrealized(false) + " wc : " + pnl.getPNLUnrealized(true) + "\t";
				
				sline += pnl.getPNLRealized(false) + " wc : " + pnl.getPNLRealized(true) + "\t";
				
				sline += " div= " + tool.getDividend(p, li.get(i)) + "\t";
				
			}else
			{
				sline += "0 shares \t" ;
				sline += pnl.getPNLRealized(false) + " wc : " + pnl.getPNLRealized(true) + "\t";
				sline += " div= " + tool.getDividend(p, li.get(i), ((Trade)pnl.getTransactionsForPNLRealized().get(0)).getDateEffective(), ((Trade)pnl.getTransactionsForPNLRealized().get(1)).getDateEffective()); 
			}
			sline += "\n";
			
		}
		System.out.println(sline);

	}

}
