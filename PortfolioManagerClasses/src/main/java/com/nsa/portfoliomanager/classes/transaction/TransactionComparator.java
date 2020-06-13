package com.nsa.portfoliomanager.classes.transaction;

import java.util.Comparator;

public class TransactionComparator {
	
	private TransactionComparator() {
	    throw new IllegalStateException("Utility class");
	}
	
	public static Comparator<Transaction> compareId() {
		return Comparator.comparing(Transaction::getId);
	}
	
	public static Comparator<Transaction> compareDateEffective() {
		return Comparator.comparing(Transaction::getDateEffective);
	}

}
