package test;

import static org.junit.Assert.*;

import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nsa.portfoliomanager.classes.marketdata.Country;
import com.nsa.portfoliomanager.classes.marketdata.Stock;
import com.nsa.portfoliomanager.classes.portfolio.Portfolio;
import com.nsa.portfoliomanager.classes.transaction.Trade;
import com.nsa.portfoliomanager.core.out.OutImplementation;
import com.nsa.portfoliomanager.core.pnl.PNLImpl;

import test.ExLoad;

public class ClassesTest {
	
	private static Portfolio p;
	private static Portfolio pa;
	private static Stock CNE;
	private static Stock BBD;
	private static Stock AAPL;
	private static Stock GRPN;
	private static DecimalFormat nf = new DecimalFormat("#0.00");
	
	@BeforeClass
	public static void setUpOneTime(){
		 //p = ExLoad.getPortfolioExemple();
		p = new OutImplementation().getPortfolio(Paths.get("C:\\win32app\\419988\\dev\\workspace\\portfolios.csv"),1);
		CNE = new Stock("CNE",new Country("Canada"),"Student Transportation");
		BBD = new Stock("BBD.B",new Country("Canada"),"Bombardier Inc.");
		AAPL = new Stock("AAPL",new Country("US"),"Apple");
		GRPN = new Stock("GRPN",new Country("US"),"Groupon");
	}
	
	@Before
	public void setUp(){
		pa = new Portfolio();
	}
	
	
	//PNL
	// 0 Deal
	@Test
	public void PNL0dealsPNLRealized(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 10.0, CNE, 10.0, 10.0));
		
		assertEquals((Double)0.00 , new PNLImpl(pa, BBD).getPNLRealized(false) );
	}
	@Test
	public void PNL0dealsPNLRealizedComm(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 10.0, CNE, 10.0, 10.0));
		
		assertEquals((Double)0.00 , new PNLImpl(pa, BBD).getPNLRealized(true) );
	}
	
	// 1 DEAL
	@Test
	public void testPNL1DealUNrealized(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 10.0, 10.0));
		assertEquals( "10.00", nf.format(new PNLImpl(pa, AAPL,11.00).getPNLUnrealized(false))  );
	}
	@Test
	public void testPNL1DealUNrealizedComm(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 10.0, 10.0));
		assertEquals( "9.80", nf.format(new PNLImpl(pa, AAPL,11.00).getPNLUnrealized(true))  );
	}
	
	//2DEALS
	//2DEALS - Buy Aggreg
	@Test
	public void test_PNL_2Deals_Aggreg_buy(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 10.0, 10.0));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 10.0, 15.0));
		assertEquals( "150.00", nf.format(new PNLImpl(pa, AAPL,16.00).getPNLUnrealized(false))  );
	}
	@Test
	public void test_PNL_2Deals_Aggreg_buy_Comm(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 10.0, 10.0));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 10.0, 15.0));
		assertEquals( "149.60", nf.format(new PNLImpl(pa, AAPL,16.00).getPNLUnrealized(true))  );
	}
	
	//2DEALS - Sell Aggreg
	@Test
	public void test_PNL_2Deals_Aggreg_sell(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 15.0, -10.0));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 10.0, -10.0));
		assertEquals( "50.00", nf.format(new PNLImpl(pa, AAPL,10.00).getPNLUnrealized(false))  );
	}

	
	//2DEALS - Buy / SELL
	@Test
	public void test_PNL_2Deals_Buy_Sell_R(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 10.0, 10.0));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 15.0, -10.0));
		assertEquals( "50.00", nf.format(new PNLImpl(pa, AAPL).getPNLRealized(false))  );
	}
	@Test
	public void test_PNL_2Deals_Buy_Sell_R_C(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 10.0, 10.0));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 15.0, -10.0));
		assertEquals( "49.80", nf.format(new PNLImpl(pa, AAPL).getPNLRealized(true))  );
	}
	@Test
	public void test_PNL_2Deals_Buy_Sell_UR(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 10.0, 10.0));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 15.0, -10.0));
		assertEquals( "0.00", nf.format(new PNLImpl(pa, AAPL).getPNLUnrealized(false))  );
	}
	@Test
	public void test_PNL_2Deals_Buy_Sell_UR_C(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 10.0, 10.0));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 15.0, -10.0));
		assertEquals( "0.00", nf.format(new PNLImpl(pa, AAPL).getPNLUnrealized(true))  );
	}
	
	//2DEALS - SELL /  BUY
	@Test
	public void test_PNL_2Deals_Sell_Buy_R(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 10.0, -10.0));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 15.0, 10.0));
		assertEquals( "-50.00", nf.format(new PNLImpl(pa, AAPL).getPNLRealized(false))  );
	}
	@Test
	public void test_PNL_2Deals_Sell_Buy_R_C(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 10.0, -10.0));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 15.0, 10.0));
		assertEquals( "-50.20", nf.format(new PNLImpl(pa, AAPL).getPNLRealized(true))  );
	}
	@Test
	public void test_PNL_2Deals_Sell_Buy_UR(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 10.0, -10.0));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 15.0, 10.0));
		assertEquals( "0.00", nf.format(new PNLImpl(pa, AAPL).getPNLUnrealized(false))  );
	}
	@Test
	public void test_PNL_2Deals_Sell_Buy_UR_C(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 10.0, -10.0));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 15.0, 10.0));
		assertEquals( "0.00", nf.format(new PNLImpl(pa, AAPL).getPNLUnrealized(true))  );
	}

	//N deasl
	@Test
	public void test_PNL_NDeals_Aggreg_buy(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 10.0, 10.0));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 10.0, 15.0));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 20.0, 15.0));
		assertEquals( "90.00", nf.format(new PNLImpl(pa, AAPL,16.00).getPNLUnrealized(false))  );
	}
	@Test
	public void test_PNL_NDeals_Aggreg_buy_C(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 10.0, 10.0));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 10.0, 15.0));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 20.0, 15.0));
		assertEquals( "89.40", nf.format(new PNLImpl(pa, AAPL,16.00).getPNLUnrealized(true))  );
	}
	@Test
	public void test_PNL_NDeals_Aggreg_sell(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 20.0, -1.0));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 19.0, -2.0));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 18.0, -3.0));
		assertEquals( "22.00", nf.format(new PNLImpl(pa, AAPL,15.00).getPNLUnrealized(false))  );
	}
	@Test
	public void test_PNL_NDeals_Aggreg_sell_C(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 20.0, -1.0));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 19.0, -2.0));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.10, AAPL, 18.0, -3.0));
		assertEquals( "21.40", nf.format(new PNLImpl(pa, AAPL,15.00).getPNLUnrealized(true))  );
	}
	
	
	
	//N Deals Rasl case investopedia
	@Test
	public void test_PNL_NDeals_Investopedia_GRPN(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 10.21, 1200.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 7.39, 300.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 6.08, 2000.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 7.73, -3500.00));
		assertEquals( "426.00", nf.format(new PNLImpl(pa, GRPN).getPNLRealized(false))  );
	}
	@Test
	public void test_PNL_NDeals_Investopedia_GRPN_1(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 10.21, 1200.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 7.73, -3500.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 7.39, 300.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 6.08, 2000.00));
		assertEquals( "426.00", nf.format(new PNLImpl(pa, GRPN).getPNLRealized(false))  );
	}
	@Test
	public void test_PNL_NDeals_Investopedia_GRPN_2(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 10.21, 1200.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 7.39, 300.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 7.73, -3500.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 6.08, 2000.00));
		assertEquals( "426.00", nf.format(new PNLImpl(pa, GRPN).getPNLRealized(false))  );
	}
	@Test
	public void test_PNL_NDeals_Investopedia_GRPN_3(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 7.73, -3500.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 10.21, 1200.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 7.39, 300.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 6.08, 2000.00));
		assertEquals( "426.00", nf.format(new PNLImpl(pa, GRPN).getPNLRealized(false))  );
	}
	@Test
	public void test_PNL_NDeals_Investopedia_GRNP_C(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 10.21, 1200.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 7.73, -3500.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 7.39, 300.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 6.08, 2000.00));
		assertEquals( "346.04", nf.format(new PNLImpl(pa, GRPN).getPNLRealized(true))  );
	}
	
	
	//N Deals - BOTH PNL
	@Test
	public void test_PNL_NDeals_BOTH_R(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 10.00, 100.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 15.00, 500.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 18.00, -300.00));
		assertEquals( "1150.00", nf.format(new PNLImpl(pa, GRPN,20.00).getPNLRealized(false))  );
	}
	@Test
	public void test_PNL_NDeals_BOTH_UR(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 10.00, 100.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 15.00, 500.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 18.00, -300.00));
		assertEquals( "1750.00", nf.format(new PNLImpl(pa, GRPN,20.00).getPNLUnrealized(false))  );
	}
	
	@Test
	public void test_PNL_NDeals_BOTH_R2(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 10.00, -100.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 15.00, -500.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 18.00, 300.00));
		assertEquals( "-1150.00", nf.format(new PNLImpl(pa, GRPN,20.00).getPNLRealized(false))  );
	}
	@Test
	public void test_PNL_NDeals_BOTH_UR2(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 10.00, -100.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 15.00, -500.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 19.99, GRPN, 18.00, 300.00));
		assertEquals( "-1750.00", nf.format(new PNLImpl(pa, GRPN,20.00).getPNLUnrealized(false))  );
	}
	
	@Test
	public void test_PNL_NDeals_BOTH_12(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.1, GRPN, 9.16, 10.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.1, GRPN, 9.23, -10.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.19, GRPN, 7.88, 19.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.38, GRPN, 5.17, 38.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.43, GRPN, 4.52, 43.00));
		assertEquals( "0.70", nf.format(new PNLImpl(pa, GRPN,4.09).getPNLRealized(false))  );
	}
	@Test
	public void test_PNL_NDeals_BOTH_12C(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.1, GRPN, 9.16, 10.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.1, GRPN, 9.23, -10.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.19, GRPN, 7.88, 19.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.38, GRPN, 5.17, 38.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.43, GRPN, 4.52, 43.00));
		assertEquals( "0.50", nf.format(new PNLImpl(pa, GRPN,4.09).getPNLRealized(true))  );
	}
	@Test
	public void test_PNL_NDeals_BOTH_121(){
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.1, GRPN, 9.16, 10.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.19, GRPN, 9.23, -10.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.19, GRPN, 7.88, 19.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.38, GRPN, 5.17, 38.00));
		pa.addTransaction(new Trade(new Date(), new Date(), "", 0.43, GRPN, 4.52, 43.00));
		assertEquals( "-131.54", nf.format(new PNLImpl(pa, GRPN,4.09).getPNLUnrealized(false))  );
	}

}
