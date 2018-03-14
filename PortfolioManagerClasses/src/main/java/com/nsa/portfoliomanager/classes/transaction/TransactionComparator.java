package com.nsa.portfoliomanager.classes.transaction;

import java.util.Comparator;

public class TransactionComparator {
	
	public static Comparator<Transaction> compareId() {
		return Comparator.comparing((Transaction mt) -> mt.getId());
	}
	
	public static Comparator<Transaction> compareDateEffective() {
		return Comparator.comparing((Transaction mt) -> mt.getDateEffective());
	}

}
