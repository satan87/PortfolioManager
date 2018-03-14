package com.nsa.portfoliomanager.core.calculs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.nsa.portfoliomanager.classes.info.Info;
import com.nsa.portfoliomanager.classes.marketdata.Instrument;
import com.nsa.portfoliomanager.classes.portfolio.Portfolio;
import com.nsa.portfoliomanager.classes.transaction.BUYSELL;
import com.nsa.portfoliomanager.classes.transaction.Cash;
import com.nsa.portfoliomanager.classes.transaction.Dividend;
import com.nsa.portfoliomanager.classes.transaction.Trade;
import com.nsa.portfoliomanager.classes.transaction.Transaction;
import com.nsa.portfoliomanager.classes.transaction.TransactionComparator;

public class  InfoImpl  implements Info{

	
	//CASH
	@Override
	public  Double getCashInvested(Portfolio portfolio) {
		
		Double cashInvested = 0.0;
		
		for (Transaction t  : portfolio.getTransaction()){
			if ( t instanceof Cash ){
				cashInvested = cashInvested + ((Cash)t).getAmount();
			}
		}
		
		return cashInvested;
	}

	@Override
	public  Double getCashInvested(Portfolio portfolio, Date date, boolean before) {
		Double cashInvested = 0.0;
		
		for (Transaction t  : portfolio.getTransaction()){
			if ( t instanceof Cash ){
				if ( (before && (date.after(t.getDateEffective()) ||  ( !date.after(t.getDateEffective()) && !date.before(t.getDateEffective())) )  ) || ( !before && date.before(t.getDateEffective()))  )
					cashInvested = cashInvested + ((Cash)t).getAmount();
			}
		}
		
		return cashInvested;
	}
	
	/**
	 * Return the amount of cash available
	 * Formula is : Cash Available = Cash invested + Amount Sell + Dividend - Amount Buy - commission  
	 * 
	 * @param portfolio
	 * @return Double
	 */
	@Override
	public Double getCashAvailable(Portfolio portfolio){
		
		Double r = 0.00;
		r = this.getCashInvested(portfolio) + this.getDividend(portfolio);
				
		for (Transaction t : portfolio.getTransaction()){
			if ( t instanceof Trade ){
				if ( ((Trade) t).getBuySell().equals(BUYSELL.BUY)){
					r = r - ( ((Trade) t).getPrice() * ((Trade) t).getQuantity() );
				}else{
					r = r + ( ((Trade) t).getPrice() * -((Trade) t).getQuantity() );
				}
				r = r - ((Trade) t).getCommission();
			}
		}

		return r;
	}
	
	
	/**
	 * Return the amount of cash available BEFORE a specific Date
	 * Formula is : Cash Available = Cash invested + Amount Sell + Dividend - Amount Buy - commission  
	 * 
	 * @param portfolio date
	 * @return Double
	 */
	@Override
	public Double getCashAvailable(Portfolio portfolio , Date date){
		
		Double r = 0.00;
		r = this.getCashInvested(portfolio, date,true) ;
				
		for (Transaction t : portfolio.getTransaction()){
			if ( !t.getDateExecution().after(date) ){
				
				if ( t instanceof Trade ){
					if ( ((Trade) t).getBuySell().equals(BUYSELL.BUY)){
						r = r - ( ((Trade) t).getPrice() * ((Trade) t).getQuantity() );
					}else{
						r = r + ( ((Trade) t).getPrice() * -((Trade) t).getQuantity() );
					}
					r = r - ((Trade) t).getCommission();
				}
				
				if ( t instanceof Dividend ){
					r = r + ((Dividend) t).getAmount() - ((Dividend) t).getCommission() ;
				}
			}
		}

		return r;
	}
	
	// COMMISSION
	/**
	 * Return the amount of commission payed on All Transactions (Cash / Trade / Dividend)
	 * 
	 * @param portfolio 
	 * @return Double
	 */
	@Override
	public Double getCommissionpayed(Portfolio portfolio){
		Double r = 0.00;
		for (Transaction t : portfolio.getTransaction()){
			r = r + t.getCommission();
		}
		return r;
	}
	
	@Override
	/**
	 * Return the amount of commission payed on Trade / Dividend Transactions for a specific Instrument
	 * 
	 * @param portfolio instrument
	 * @return Double
	 */
	public Double getCommissionpayed(Portfolio portfolio , Instrument instrument){
		Double r = 0.00;
		for (Transaction t : portfolio.getTransaction()){
			if ( t instanceof Trade  && ((Trade)t).getInstrument().equals(instrument) )
				r = r + t.getCommission();
			
			if ( t instanceof Dividend  && ((Dividend)t).getInstrument().equals(instrument) )
				r = r + t.getCommission();
		}
		return r;
	}
	
	
	

	//DIVIDEND
		public Double getDividend(Portfolio portfolio){
			Double r = 0.00;
			for (Transaction t : portfolio.getTransaction()){
				if ( t instanceof Dividend ){
					r += ((Dividend) t).getAmount();
				}
			}
			return r;
		}
		public Double getDividend(Portfolio portfolio , Instrument instrument){
			Double r = 0.00;
			for (Transaction t : portfolio.getTransaction()){
				if ( t instanceof Dividend ){
					if (((Dividend) t).getInstrument().equals(instrument)){
						r += ((Dividend) t).getAmount();
					}
				}
			}
			return r;
		}
		public Double getDividend(Portfolio portfolio , Instrument instrument, Date begin , Date end){
			Double r = 0.00;
			for (Transaction t : portfolio.getTransaction()){
				if ( t instanceof Dividend ){
					if (((Dividend) t).getInstrument().equals(instrument) && t.getDateExecution().after(begin) && t.getDateExecution().before(end)){
						r += ((Dividend) t).getAmount();
					}
				}
			}
			return r;
		}
		
	
		
		
		
		
	//PORTFOLIO
	@Override
	public List<Instrument> getInstruments(Portfolio portfolio) {
		
		List<Instrument> li = new ArrayList<Instrument>();
		
		for(Transaction t : portfolio.getTransaction()){
			if ( t instanceof Trade ){
				Instrument instrument = ((Trade)t).getInstrument();
				if (!li.contains(instrument)){
					li.add(instrument);
				}
			}
		}
		
		return li;
	}

	
	@Override
	public  boolean hasOpenPositions(Portfolio portfolio){
		boolean hop = false;
		
		List<Instrument> li = this.getInstruments(portfolio);
		
		for ( Instrument instrument : li){
			if (this.hasOpenPositions(portfolio, instrument) ){
				hop=true;
			}
		}
		
		return hop;
	}

	@Override
	public boolean hasOpenPositions(Portfolio portfolio, Instrument instrument) {
		boolean hop = false;
		Double quantity = 0.00;
		
		for (Transaction t : portfolio.getTransaction()){
			if ( t instanceof Trade){
				Trade trade = (Trade)t;
				if ( trade.getInstrument().equals(instrument)){
					quantity += trade.getQuantity();
				}
			}
		}
		
		if ( quantity != 0.00)
			hop = true;
		
		return hop;
	}
		
	@Override
	public boolean hasOpenPositions(Portfolio portfolio, Instrument instrument, Date at) {
		boolean hop = false;
		Double quantity = 0.00;
		
		for (Transaction t : portfolio.getTransaction()){
			if ( t instanceof Trade){
				Trade trade = (Trade)t;
				if ( !trade.getDateEffective().after(at) ){
					if ( trade.getInstrument().equals(instrument)){
						quantity += trade.getQuantity();
					}
				}
			}
		}
		
		if ( quantity != 0.00)
			hop = true;
		
		return hop;
	}
	
	@Override
	public  boolean hasClosePositions(Portfolio portfolio){
		boolean hcp = false;

		for ( Instrument instrument : this.getInstruments(portfolio)){
			if (this.hasClosePositions(portfolio, instrument) ){
				hcp=true;
			}
		}
		return hcp;
	}
	
	@Override
	public  boolean hasClosePositions(Portfolio portfolio, Instrument instrument){

		boolean hasBuy = false;
		boolean hasSell = false;

		for ( Transaction t : portfolio.getTransaction() ){
			if ( t instanceof Trade && ((Trade)t).getInstrument().equals(instrument) ){
				if ( ((Trade)t).getQuantity() > 0 ){
					hasBuy = true;
				}else{
					hasSell = true;
				}
			}
		}
		
		if ( hasBuy && hasSell )
			return true;
		else
			return false;
	}
	
	@Override
	public  boolean hasClosePositions(Portfolio portfolio, Instrument instrument, Date at){

		boolean hasBuy = false;
		boolean hasSell = false;

		for ( Transaction t : portfolio.getTransaction() ){
			if ( t instanceof Trade && ((Trade)t).getInstrument().equals(instrument) &&  !((Trade)t).getDateEffective().after(at) ){
				if ( ((Trade)t).getQuantity() > 0 ){
					hasBuy = true;
				}else{
					hasSell = true;
				}
			}
		}
		if ( hasBuy && hasSell )
			return true;
		else
			return false;
	}
	
	
	@Override
	public Double openPositionQuantity(Portfolio portfolio, Instrument instrument) {
		boolean hop = false;
		Double quantity = 0.00;
			
		for (Transaction t : portfolio.getTransaction()){
			if ( t instanceof Trade){
				Trade trade = (Trade)t;
				if ( trade.getInstrument().equals(instrument)){
					quantity += trade.getQuantity();
				}
			}
		}
		
		return quantity;
	}
		
		@Override
		public Double openPositionQuantity(Portfolio portfolio, Instrument instrument, Date at) {
			boolean hop = false;
			Double quantity = 0.00;
			
			for (Transaction t : portfolio.getTransaction()){
				if ( t.getDateEffective().before(at) ){
					if ( t instanceof Trade){
						Trade trade = (Trade)t;
						if ( trade.getInstrument().equals(instrument)){
							quantity += trade.getQuantity();
						}
					}
				}
			}
			
			return quantity;
		}
		

		
		@Override
		public Date FirstTransaction(Portfolio portfolio) {
			Collections.sort(portfolio.getTransaction(),TransactionComparator.compareDateEffective());
			return portfolio.getTransaction().get(0).getDateEffective();
		}

		@Override
		public Date LastTransaction(Portfolio portfolio) {
			Collections.sort(portfolio.getTransaction(),TransactionComparator.compareDateEffective());
			return portfolio.getTransaction().get( portfolio.getTransaction().size()-1 ).getDateEffective();
		}
		
		
		
		// STATS
		public Integer getNumberOfTrades(Portfolio p){
			Integer nb = 0;
			for (Transaction t : p.getTransaction()){
				if ( t instanceof Trade )
					nb++;
			}
			return nb;
		}
		
		public Integer getNumberOfOpenPosition(Portfolio p){
			Integer nb = 0 ;
			for ( Instrument i : this.getInstruments(p) ){
				if ( this.hasOpenPositions(p, i) )
					nb++;
			}
			return nb;
		}
		
		
}
