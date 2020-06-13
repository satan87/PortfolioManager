package com.nsa.portfoliomanager.classes.pnl;

import java.util.List;
import com.nsa.portfoliomanager.classes.transaction.Transaction;

public interface PNL {

	public List<Transaction> getTransactionInvolved(); // Reals Deals used
	
	public List<Transaction> getTransactions(); // 4 max., Might be Real / Hypothetical trades
	
	public Double getPNLRealized(Boolean commission, Boolean dividend);
	
	public Double getPNLUnrealized(Boolean commission, Boolean dividend);
		
}
