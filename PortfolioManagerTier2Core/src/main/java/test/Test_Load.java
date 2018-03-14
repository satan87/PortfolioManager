package test;

import java.nio.file.Paths;
import java.util.List;

import com.nsa.portfoliomanager.classes.portfolio.Portfolio;
import com.nsa.portfoliomanager.core.out.OutImplementation;

public class Test_Load {

	public static void main(String[] args) {

		System.out.println("Start");

		
		List<Portfolio> lp = new OutImplementation().getPortfolios(Paths.get("C:\\win32app\\419988\\dev\\workspace\\portfolios.csv"));
		
		Portfolio p = new OutImplementation().getPortfolio(Paths.get("C:\\win32app\\419988\\dev\\workspace\\portfolios.csv"),2);
		
		
		
		System.out.println("end");
	}

}
