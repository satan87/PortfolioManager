package com.nsa.portfoliomanager.classes.transaction;

import java.util.Date;

import com.nsa.portfoliomanager.classes.format.DateFormator;
import com.nsa.portfoliomanager.classes.marketdata.Instrument;

public class Dividend extends Transaction {

	private Date dateAnnouncement = new Date();
	private Instrument instrument;
	private Double amount;
	
	public Dividend(){
		super();
	}
	public Dividend(Date dateExecution, Date dateEffective,String comment, Date dateAnnouncement,Instrument instrument,Double amount){
		super(dateExecution,dateEffective,comment);
		this.dateAnnouncement=dateAnnouncement;
		this.instrument=instrument;
		this.amount = amount;
	}
	public Dividend(Date dateExecution, Date dateEffective,Double commission, Date dateAnnouncement,Instrument instrument,Double amount){
		super(dateExecution,dateEffective,commission);
		this.dateAnnouncement=dateAnnouncement;
		this.instrument=instrument;
		this.amount = amount;
	}
	public Dividend(Date dateExecution, Date dateEffective,String comment,Double commission, Date dateAnnouncement,Instrument instrument,Double amount){
		super(dateExecution,dateEffective,comment,commission);
		this.dateAnnouncement=dateAnnouncement;
		this.instrument=instrument;
		this.amount = amount;
	}
	
	
	// Getter / Setter
	public Date getDateAnnouncement() {
		return dateAnnouncement;
	}
	public void setDateAnnouncement(Date dateAnnouncement) {
		this.dateAnnouncement = dateAnnouncement;
	}
	
	public Instrument getInstrument() {
		return instrument;
	}
	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
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
		builder.append("Dividend of: " + amount);
		builder.append(" for " + instrument.getSymbol());
		builder.append(" announced on: " + DateFormator.yyyymmdd(dateAnnouncement));
		builder.append(" per ").append(super.print());
		return builder.toString();
	}

}
