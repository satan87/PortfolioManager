package com.nsa.portfoliomanager.classes.transaction;

import com.nsa.portfoliomanager.classes.print.Printable;

public enum Direction implements Printable {
	BUY,
	SELL;

	@Override
	public String print() {
		if (this == Direction.BUY) {
			return "Buy";
		}
		return "Sell";
	}
	

}