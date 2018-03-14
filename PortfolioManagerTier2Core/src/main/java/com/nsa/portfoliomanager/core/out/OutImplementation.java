package com.nsa.portfoliomanager.core.out;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.SearchResult;

import com.nsa.portfoliomanager.classes.format.Dates;
import com.nsa.portfoliomanager.classes.marketdata.Country;
import com.nsa.portfoliomanager.classes.marketdata.Instrument;
import com.nsa.portfoliomanager.classes.marketdata.Stock;
import com.nsa.portfoliomanager.classes.portfolio.Portfolio;
import com.nsa.portfoliomanager.classes.transaction.Cash;
import com.nsa.portfoliomanager.classes.transaction.Dividend;
import com.nsa.portfoliomanager.classes.transaction.Trade;
import com.nsa.portfoliomanager.datas.data.DataCSV;



public class OutImplementation implements Out {
	
	private Path myFile = Paths.get("C:\\win32app\\419988\\dev\\workspace\\portfolios.csv");

	@Override
	public List<Portfolio> getPortfolios(Path path) {
		
		if (path!=null)
			this.myFile = path;
		
		List<Portfolio> lp = new ArrayList<Portfolio>();
		
		
		//List<String> xmls = new DataCSV(Paths.get("D:\\win32app\\Tomcat 7.0\\webapps\\portfolios.csv")).getPortfolios();		
		List<String> xmls = new DataCSV(this.myFile).getPortfolios();
		
		for (String  xml : xmls){
			
			Portfolio p = new Portfolio();
			
			//ID
			String sID = xml.substring( xml.indexOf("<id>") + 4 , xml.indexOf("</id>") );
			p.setId( Integer.parseInt(sID) );		
			
			//NAME
			String sName = xml.substring( xml.indexOf("<name>") + 6 , xml.indexOf("</name>") );
			p.setName(sName);
			
			//Real
			String sREAL = xml.substring( xml.indexOf("<real>") + 6 , xml.indexOf("</real>") );
			if (sREAL.equalsIgnoreCase("yes") || sREAL.equalsIgnoreCase("true"))
				p.setReal(true);
			else
				p.setReal(false);
			
			//Transactions
			String sTransactions = xml.substring( xml.indexOf("<transactions>") + 14 , xml.indexOf("</transactions>") );
			String[] st = sTransactions.split("</transaction>");
			
			for (int it = 0 ; it < st.length ; it++){
				
				String sTYPE = st[it].substring( st[it].indexOf("<type>") + 6 , st[it].indexOf("</type>") );
				
				//CASH
				if (sTYPE.equals("cash")){
					String sDATE = st[it].substring( st[it].indexOf("<date>") + 6 , st[it].indexOf("</date>") );
					String sAMOUNT = st[it].substring( st[it].indexOf("<amount>") + 8 , st[it].indexOf("</amount>") );
					String sCOMMENT = st[it].substring( st[it].indexOf("<comment>") + 9 , st[it].indexOf("</comment>") );
					
					Cash c = new Cash();
					
					try {
						c.setDateEffective(Dates.yyyyMMdd.parse(sDATE) );
						c.setDateExecution(Dates.yyyyMMdd.parse(sDATE) );
					} catch (ParseException e) {}
					
					c.setAmount(Double.parseDouble(sAMOUNT));
					c.setComment(sCOMMENT);
					
					p.addTransaction(c);
					
				}
				
				
				//TRADE
				if (sTYPE.equals("trade")){
					String sDATE = st[it].substring( st[it].indexOf("<date>") + 6 , st[it].indexOf("</date>") );
					String sINSTRUMENT = st[it].substring( st[it].indexOf("<instrument>") + 12 , st[it].indexOf("</instrument>") );
					String sNAME = st[it].substring( st[it].indexOf("<name>") + 6 , st[it].indexOf("</name>") );
					String sCOUNTRY = st[it].substring( st[it].indexOf("<country>") + 9 , st[it].indexOf("</country>") );
					String sPRICE = st[it].substring( st[it].indexOf("<price>") + 7 , st[it].indexOf("</price>") );
					String sQUANTITY = st[it].substring( st[it].indexOf("<quantity>") + 10 , st[it].indexOf("</quantity>") );
					String sCOMMISSION = st[it].substring( st[it].indexOf("<commission>") + 12 , st[it].indexOf("</commission>") );
					String sCOMMENT = st[it].substring( st[it].indexOf("<comment>") + 9 , st[it].indexOf("</comment>") );
					
					Trade t = new Trade();
					
					try {
						t.setDateEffective(Dates.yyyyMMdd.parse(sDATE) );
						t.setDateExecution(Dates.yyyyMMdd.parse(sDATE) );
					} catch (ParseException e) {}
					
					//TODO - construteur + country
					Instrument instrument = new Stock();
					instrument.setSymbol(sINSTRUMENT);
					((Stock)instrument).setName(sNAME);
					if ( sCOUNTRY.equalsIgnoreCase("CA"))
						instrument.setCountry(new Country("Canada"));
					else
						instrument.setCountry(new Country("US"));
					
					t.setInstrument(instrument);
					t.setPrice(Double.parseDouble(sPRICE));
					t.setQuantity(Double.parseDouble(sQUANTITY));
					t.setCommission(Double.parseDouble(sCOMMISSION));				
					t.setComment(sCOMMENT);
					
					p.addTransaction(t);
					
				}
				
				
				//DIVIDEND
				if (sTYPE.equals("dividend")){
					String sDATE0 = st[it].substring( st[it].indexOf("<dateAnnounced>") + 15 , st[it].indexOf("</dateAnnounced>") );
					String sDATE1 = st[it].substring( st[it].indexOf("<dateExecuted>") + 14 , st[it].indexOf("</dateExecuted>") );
					String sDATE2= st[it].substring( st[it].indexOf("<dateEffective>") + 15 , st[it].indexOf("</dateEffective>") );
					String sINSTRUMENT = st[it].substring( st[it].indexOf("<instrument>") + 12 , st[it].indexOf("</instrument>") );
					String sAMOUNT = st[it].substring( st[it].indexOf("<amount>") + 8 , st[it].indexOf("</amount>") );
					String sCOMMISSION = st[it].substring( st[it].indexOf("<commission>") + 12 , st[it].indexOf("</commission>") );
					String sCOMMENT = st[it].substring( st[it].indexOf("<comment>") + 9 , st[it].indexOf("</comment>") );
					
					Dividend t = new Dividend();
					
					try {
						t.setDateAnnouncement(Dates.yyyyMMdd.parse(sDATE0) );
						t.setDateEffective(Dates.yyyyMMdd.parse(sDATE1) );
						t.setDateExecution(Dates.yyyyMMdd.parse(sDATE2) );
					} catch (ParseException e) {}
					
					//TODO - construteur + country
					Instrument instrument = new Stock();
					instrument.setSymbol(sINSTRUMENT);
					((Stock)instrument).setName(sINSTRUMENT);
					instrument.setCountry(new Country("Canada"));
					
					t.setInstrument(instrument);
					
					t.setAmount(Double.parseDouble(sAMOUNT));
					t.setCommission(Double.parseDouble(sCOMMISSION));				
					t.setComment(sCOMMENT);
					
					p.addTransaction(t);
					
				}
				
			}
			
			lp.add(p);
		}
				
		return lp;
	}
	
	
	@Override
	public Portfolio getPortfolio(Path path , Integer idPortfolio) {
		
		if (path!=null)
			this.myFile = path;
		
		List<Portfolio> lp = this.getPortfolios(path);
		
		for (Portfolio ptf : lp){
			if (ptf.getId().equals(idPortfolio))
				return ptf;
		}
		
		
		return null;
	}
	

}
