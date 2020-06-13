package com.nsa.portfoliomanager.classes.transaction;

import java.util.Date;

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
	
	public Double getAmount() {
		return amount;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String print() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cash amount of: " + amount);
		builder.append(" per ").append(super.print());
		return builder.toString();
	}
	
}
