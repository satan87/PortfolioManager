package com.nsa.portfoliomanager.classes.transaction;

import java.util.Date;

/**
 *  CASH class extends Transaction.
 *  It defines 1 Cash Transaction ( a buy or a sell )
 *   
 * @author Nicolas Savoini
 * @version 1.0
 *
 */

public class Cash extends Transaction {

	private Double amount;
	
	public Cash(){
		super();
	}
	public Cash(Date dateExecution, Date dateEffective,String comment, Double amount){
		super(dateExecution,dateEffective,comment);
		this.amount = amount;
	}
	public Cash(Date dateExecution, Date dateEffective,Double commission, Double amount){
		super(dateExecution,dateEffective,commission);
		this.amount = amount;
	}
	public Cash(Date dateExecution, Date dateEffective,String comment,Double commission, Double amount){
		super(dateExecution,dateEffective,comment,commission);
		this.amount = amount;
	}
	
	
	// Getter / Setter
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@Override
	public String getType(){
		return "Cash";
	}
	
}
