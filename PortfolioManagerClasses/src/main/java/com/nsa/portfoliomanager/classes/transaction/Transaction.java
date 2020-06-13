package com.nsa.portfoliomanager.classes.transaction;

import java.util.Date;

import com.nsa.portfoliomanager.classes.format.DateFormator;
import com.nsa.portfoliomanager.classes.print.Printable;

public abstract class Transaction implements Printable {
	
	private Integer id = -1;
	private Date dateExecution = new Date();
	private Date dateEffective = new Date();
	private Double commission = 0.00;
	private String comment = "";
	
	public  Transaction(){
		super();
	}
	
	public Transaction(Date dateExecution){
		super();
		this.dateExecution = dateExecution;
	}
	public Transaction(Date dateExecution, Date dateEffective){
		super();
		this.dateExecution = dateExecution;
		this.dateEffective = dateEffective;
	}
	public Transaction(Date dateExecution, Date dateEffective,String comment){
		super();
		this.dateExecution = dateExecution;
		this.dateEffective = dateEffective;
		this.comment = comment;
	}
	public Transaction(Date dateExecution, Date dateEffective,Double commission){
		super();
		this.dateExecution = dateExecution;
		this.dateEffective = dateEffective;
		this.commission = commission;
	}
	public Transaction(Date dateExecution, Date dateEffective,String comment, Double commission){
		super();
		this.dateExecution = dateExecution;
		this.dateEffective = dateEffective;
		this.comment = comment;
		this.commission = commission;
	}
	
	
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Date getDateExecution() {
		return dateExecution;
	}
	
	public void setDateExecution(Date dateExecution) {
		this.dateExecution = dateExecution;
	}
	
	public Date getDateEffective() {
		return dateEffective;
	}
	
	public void setDateEffective(Date dateEffective) {
		this.dateEffective = dateEffective;
	}
	
	public Double getCommission() {
		return commission;
	}
	
	public void setCommission(Double commission) {
		this.commission = commission;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String print() {
		StringBuilder builder = new StringBuilder();
		builder.append("Transaction: " + id);
		builder.append(" Executed On: " + DateFormator.yyyymmdd(dateExecution));
		builder.append(" Effectived On: " + DateFormator.yyyymmdd(dateEffective));
		if (commission != 0) {
			builder.append(" Commission: " + commission);
		}
		if (!comment.isBlank()) {
			builder.append(" Comment: " + comment);
		}
		return builder.toString();
	}

}
