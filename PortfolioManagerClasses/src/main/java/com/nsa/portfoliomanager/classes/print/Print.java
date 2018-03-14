package com.nsa.portfoliomanager.classes.print;

import java.util.List;
import com.nsa.portfoliomanager.classes.portfolio.Portfolio;
import com.nsa.portfoliomanager.classes.transaction.Transaction;

public interface Print {

	public String TransactionList(Portfolio p);
	public String TransactionList(List<Transaction> transactions);
	
}
