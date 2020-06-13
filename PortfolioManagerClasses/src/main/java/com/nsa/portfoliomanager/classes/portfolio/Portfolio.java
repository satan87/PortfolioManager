package com.nsa.portfoliomanager.classes.portfolio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.nsa.portfoliomanager.classes.transaction.Transaction;
import com.nsa.portfoliomanager.classes.transaction.TransactionComparator;

/**
 *  Portfolio
 *   
 * @author Nicolas Savoini
 * @version 1.0
 *
 */
public class Portfolio {
	
	private Integer id = 1 ;
	private String name = "My First Portfolio";
	private boolean real = true;
	private List<Transaction> transaction;
	
	
	public Portfolio(){
		super();
		transaction = new ArrayList<>();
	}
	public Portfolio(Integer id){
		super();
		this.id=id;
		transaction = new ArrayList<>();
	}
	public Portfolio(Integer id, String name){
		super();
		this.id=id;
		this.name=name;
		transaction = new ArrayList<>();
	}
	public Portfolio(Integer id, String name , boolean real){
		super();
		this.id=id;
		this.name=name;
		this.real=real;
		transaction = new ArrayList<>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public boolean isReal() {
		return real;
	}
	public void setReal(boolean real) {
		this.real = real;
	}

	
	public List<Transaction> getTransaction() {
		return transaction;
	}

	public void setTransation(List<Transaction> transaction) {
		this.transaction = transaction;
	}
	
	public void addTransaction(Transaction t){
		
		if (t.getId() == -1)
			t.setId( this.getNextTransactionId() );
		this.transaction.add(t);
		
		Collections.sort(this.transaction , TransactionComparator.compareDateEffective());
	}
	
	public Integer getNextTransactionId(){
		Integer max = 0;
		for (int i = 0 ; i < this.transaction.size();i++){
			if (this.transaction.get(i).getId() > max){
				max = this.transaction.get(i).getId();
			}
		}
		return ++max;	
	}
	
	
	
	

}
