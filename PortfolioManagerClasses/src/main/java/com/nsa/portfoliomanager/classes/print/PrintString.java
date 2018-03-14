package com.nsa.portfoliomanager.classes.print;

import java.util.List;

import com.nsa.portfoliomanager.classes.portfolio.Portfolio;
import com.nsa.portfoliomanager.classes.transaction.Cash;
import com.nsa.portfoliomanager.classes.transaction.Trade;
import com.nsa.portfoliomanager.classes.transaction.Transaction;

public class PrintString implements Print{

	@Override
	public String TransactionList(Portfolio p) {
		String tl="";
		
		for (Transaction t  : p.getTransaction()){
			
			tl += " Transaction Number : " + t.getId() + " on : " + t.getDateEffective() + " " ; 
			
			if ( t instanceof Trade ){
				Trade c = (Trade)t;
				tl += c.getBuySell() + " " + c.getQuantity() + " of " + c.getInstrument().getSymbol() + " for " + c.getPrice()  ;
			}
			if ( t instanceof Cash ){
				Cash c = (Cash)t;
				tl += "For " + c.getAmount() + " Commission was : " + c.getCommission();
				
			}
			
			
			tl += " Comment : " + t.getComment() + "\n";
		}
		return tl;
	}

	@Override
	public String TransactionList(List<Transaction> transactions) {
String tl="";
		
		for (Transaction t  : transactions){
			
			tl += " Transaction Number : " + t.getId() + " on : " + t.getDateEffective() + " " ; 
			
			if ( t instanceof Trade ){
				Trade c = (Trade)t;
				tl += c.getBuySell() + " " + c.getQuantity() + " of " + c.getInstrument().getSymbol() + " for " + c.getPrice() ;
			}
			if ( t instanceof Cash ){
				Cash c = (Cash)t;
				tl += "For " + c.getAmount() + " Commission was : " + c.getCommission();
				
			}
			
			
			tl += " Comment : " + t.getComment() + "\n";
		}
		return tl;
	}
	
	
	

}
