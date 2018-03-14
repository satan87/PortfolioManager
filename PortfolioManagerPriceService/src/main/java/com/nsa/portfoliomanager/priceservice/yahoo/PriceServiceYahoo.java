package com.nsa.portfoliomanager.priceservice.yahoo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.nsa.portfoliomanager.priceservice.memory.InMemoryPrices;
import com.nsa.portfoliomanager.priceservice.service.PriceService;
import com.nsa.portfoliomanager.priceservice.service.StockPrice;

public class PriceServiceYahoo implements PriceService {

	
	
	private String urlQuote = "http://finance.yahoo.com/d/quotes.csv?s=";
	private String urlQuoteEnd =  "&f=nl1p";
	
	private String urlQuoteHisto ="http://query.yahooapis.com/v1/public/yql?env=http%3A%2F%2Fdatatables.org%2Falltables.env&format=json&q=select%20Close%20from%20yahoo.finance.historicaldata%20where%20startDate=%27";
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	//$.get("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20%28%22"+s+"%22%29&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys",
	
	private URL yahoo;
	
	public PriceServiceYahoo() {
		super();
		System.setProperty("java.net.useSystemProxies", "true");
		/*
		System.setProperty("http.proxyHost", "ncproxy1");
		System.setProperty("http.proxyPort", "8080");
		
		final String authUser = "419988";
		final String authPassword = "R3f@or!!7";
		Authenticator.setDefault(
		   new Authenticator() {
		      public PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication(
		               authUser, authPassword.toCharArray());
		      }
		   }
		);
		System.setProperty("http.proxyUser", authUser);
		System.setProperty("http.proxyPassword", authPassword);
		*/
	}

	// FUNCTION
	private void connect(StockPrice sp){
		try {
			
			if ( sp != null )
			{
				String i = "";
				
				if ( sp.getCountry()==null )
					i = sp.getInstrument().replace(".", "-");
				else{
					if ( sp.getCountry().equalsIgnoreCase("canada") ){
						i = sp.getInstrument().replace(".", "-")+".TO";
					}else{
						i = sp.getInstrument().replace(".", "-");
					}
				}
				
				yahoo = new URL (urlQuote + i + urlQuoteEnd);
				URLConnection connection = yahoo.openConnection();
				InputStreamReader is = new InputStreamReader(connection.getInputStream());
				BufferedReader br = new BufferedReader(is);
				
				// Parse CSV Into Array
				String line = br.readLine();
				
				//We replace the "," in the name
				Integer pos1 = line.indexOf("\"");
				Integer pos2 = line.indexOf("\"",pos1+1);
				line = line.substring(pos1, pos2).replaceAll(",", " ") + line.substring(pos2+1);							
				
				//We split the chain
				String[] stockinfo = line.split(","); 
				
				sp.setFullName( stockinfo[0] );
				
				if ( df.format(sp.getDate()).equals(df.format(new Date())) ){

					sp.setLastPrice( Double.valueOf(stockinfo[1]) );
					sp.setClosePrice( Double.valueOf(stockinfo[2]) );
					
				}else{
					
					String d = df.format(sp.getDate());
					yahoo = new URL (urlQuoteHisto + d + "%27%20and%20endDate=%27" + d + "%27%20and%20symbol=%27" + i + "%27");
					connection = yahoo.openConnection();
					is = new InputStreamReader(connection.getInputStream());
					br = new BufferedReader(is);
					
					// Parse CSV Into Array
					line = br.readLine();
					line = line.substring(line.indexOf("Close")+8);
					line = line.substring(0, line.indexOf("\""));
					
					try{
						sp.setLastPrice( Double.valueOf( line ) );
					}catch(Exception e){
						sp.setLastPrice(0.00);
					}
				}
				
			}
			
		} catch (IOException e) {
			
			//we check in memory
			if ( InMemoryPrices.getInstance().get(sp)!=null ){
				sp.setLastPrice(InMemoryPrices.getInstance().get(sp).getLastPrice());
				sp.setClosePrice(InMemoryPrices.getInstance().get(sp).getClosePrice());
			}else{
				sp.setFullName( sp.getInstrument() );
				sp.setLastPrice(-1.00);
			}
			
		}catch(Exception e){
			sp.setFullName( sp.getInstrument() );
			sp.setLastPrice(-1.00);
		}
	}
	
	
	
	
	//GET PRICES
	@Override
	public void getInfo(StockPrice stockPrice){
		this.connect(stockPrice);
	}


	@Override
	public void getInfo(List<StockPrice> stockPrices){
		for (StockPrice sp : stockPrices){
			this.connect(sp);
		}
	}
	
	

	
	
}
