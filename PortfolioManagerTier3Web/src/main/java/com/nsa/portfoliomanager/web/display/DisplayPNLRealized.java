package com.nsa.portfoliomanager.web.display;

import sun.misc.InnocuousThread;

import com.nsa.portfoliomanager.classes.marketdata.Instrument;
import com.nsa.portfoliomanager.classes.portfolio.Portfolio;
import com.nsa.portfoliomanager.classes.transaction.Trade;
import com.nsa.portfoliomanager.core.calculs.InfoImpl;
import com.nsa.portfoliomanager.core.pnl.PNLImpl;

public class DisplayPNLRealized {

	private Portfolio portfolio;
	private Instrument instrument;
	
	private InfoImpl info = new InfoImpl();
	private PNLImpl pnl = null;
	
	public DisplayPNLRealized(Portfolio portfolio, Instrument instrument){
		this.portfolio = portfolio;
		this.instrument = instrument;
		pnl = new PNLImpl(this.portfolio, this.instrument);
	}
	
	
	//GET Instrument
	public Instrument getInstrument() {
		return instrument;
	}
	
	
	//GET
	public Double getQuantity(){
		Double r = -1.00;
		if ( pnl.getTransactionsForPNLRealized().size() > 0 && pnl.getTransactionsForPNLRealized().get(0)!=null )
			r = ((Trade)pnl.getTransactionsForPNLRealized().get(0)).getQuantity();
		return r;
	}
	
	public Double getBuyCost(){
		Double r = -1.00;
		if ( pnl.getTransactionsForPNLRealized().size() > 0 &&  pnl.getTransactionsForPNLRealized().get(0)!=null )
			r = ((Trade)pnl.getTransactionsForPNLRealized().get(0)).getPrice();
		return r;
	}
	public Double getSellCost(){
		Double r = -1.00;
		if ( pnl.getTransactionsForPNLRealized().size() > 0 && pnl.getTransactionsForPNLRealized().get(1)!=null )
			r = ((Trade)pnl.getTransactionsForPNLRealized().get(1)).getPrice();
		return r;
	}
	public Double getDividend(){
		Double r = 0.00;
		if ( pnl.getTransactionsForPNLRealized() != null && pnl.getTransactionsForPNLRealized().size() == 2 && pnl.getTransactionsForPNLRealized().get(0)!=null && pnl.getTransactionsForPNLRealized().get(1)!=null ){
			r = info.getDividend(portfolio, instrument, ((Trade)pnl.getTransactionsForPNLRealized().get(0)).getDateEffective(), ((Trade)pnl.getTransactionsForPNLRealized().get(1)).getDateEffective());
		}else{
			r = info.getDividend(this.portfolio, this.instrument);
		}
		return r;
	}
	
	public Double getPNL(){
		return pnl.getPNLRealized(false);
	}

	public Double getPNLWithCommission(){
		return pnl.getPNLRealized(true);
	}
	
	public Double getCommissionPayed(){
		return pnl.getPNLRealized(true) - pnl.getPNLRealized(false);
	}
	
	public Double getCommissionPayedPercentage(){
		return ( this.getCommissionPayed() / this.getPNL() );
	}

	public Double getPNLOverCashInvested(){
		return (this.getPNL() / info.getCashInvested(this.portfolio));
	}
	
	public Double getPNLOverCashInvestedWithCommission(){
		return (this.getPNLWithCommission() / info.getCashInvested(this.portfolio));
	}
	
	
	
}
