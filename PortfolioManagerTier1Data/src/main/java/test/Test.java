package test;

import java.nio.file.Paths;
import java.util.List;

import com.nsa.portfoliomanager.datas.data.DataCSV;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("START");
		
		
		//List<String> l = new DataCSV().getPortfolios();
		
		//System.out.println(l.get(1));
		
		//System.out.println( new DataCSV().getPortfolio(1) );
		
		System.out.println( new DataCSV(Paths.get("C:/win32app/419988/dev/workspace/portfolios.csv")).getPortfolios().get(0) );
		System.out.println( new DataCSV(Paths.get("C:/win32app/419988/dev/workspace/portfolios.csv")).getPortfolios().get(1) );
		//System.out.println( new DataCSV("C:\\win32app\\419988\\dev\\workspace\\portfolio.csv","C:\\win32app\\419988\\dev\\workspace\\cash.csv","C:\\win32app\\419988\\dev\\workspace\\trade.csv","C:\\win32app\\419988\\dev\\workspace\\dividend.csv").getPortfolio(1));
		
		System.out.println("END");
		
	}

}
