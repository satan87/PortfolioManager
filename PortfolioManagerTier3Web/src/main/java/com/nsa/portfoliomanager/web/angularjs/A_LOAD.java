package com.nsa.portfoliomanager.web.angularjs;

import java.nio.file.Paths;
import java.util.List;
import com.nsa.portfoliomanager.classes.portfolio.Portfolio;
import com.nsa.portfoliomanager.core.calculs.InfoImpl;
import com.nsa.portfoliomanager.core.out.OutImplementation;


public class A_LOAD {

	public static List<Portfolio> PORTFOLIOS = new OutImplementation().getPortfolios(Paths.get("D:/win32app/Tomcat 7.0/webapps/portfolios.csv"));
	//public static List<Portfolio> PORTFOLIOS = new OutImplementation().getPortfolios(Paths.get("C:\\win32app\\419988\\dev\\workspace\\portfolios.csv"));
	//public static List<Portfolio> PORTFOLIOS = new OutImplementation().getPortfolios(Paths.get("/data/dev/tomcat/v8/webapps/portfolios.csv"));
	public static InfoImpl TOOL = new InfoImpl();
	
	
	
}
