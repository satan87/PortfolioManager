package com.nsa.portfoliomanager.core.pnl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.nsa.portfoliomanager.classes.marketdata.Instrument;
import com.nsa.portfoliomanager.classes.portfolio.Portfolio;
import com.nsa.portfoliomanager.classes.transaction.BUYSELL;
import com.nsa.portfoliomanager.classes.transaction.Trade;
import com.nsa.portfoliomanager.classes.transaction.Transaction;
import com.nsa.portfoliomanager.classes.transaction.TransactionComparator;

public class PNLImpl {

	private Portfolio p;
	private Instrument instrument;
	
	//Trades list
	private List<Transaction> base = new ArrayList<>(); // Trades Involved
	
	private Trade t1 = null;
	private Trade t2 = null;
	private Trade t3 = null;
	private Trade t4 = null;
	
	// QTY ...
	/*
	private Double quantityBuy = 0.00; // Absolute value for all BUY deals
	private Double amountBuy = 0.00;
	private Double commissionBuy = 0.00;
	
	private Double quantitySell = 0.00; // Absolute value for all SELL deals
	private Double amountSell = 0.00;
	private Double commissionSell = 0.00;
	*/
	
	// PNL - Realized
	private Double amountRealized = 0.00;
	private Double commissionRealized = 0.00;
	
	// PNL - UNRealized
	private Double amountUnrealized = 0.00;
	private Double actualPrice = 0.00;
	private Double commissionUnrealized = 0.00;
	
	// CONSTRUCTOR
	public PNLImpl(Portfolio portfolio , Instrument instrument){
		super();
		this.p=portfolio;
		this.instrument=instrument;
		this.setBase();
		this.calcul();
	}
	public PNLImpl(Portfolio portfolio , Instrument instrument, Double price){
		super();
		this.p=portfolio;
		this.instrument=instrument;
		this.actualPrice=price;
		this.setBase();
		this.calcul();
	}
	
	// SETS - PRIVATE
	private void setBase(){
		for ( Transaction t : p.getTransaction() ){
			if ( t instanceof Trade ){
				if ( ((Trade)t).getInstrument().equals(this.instrument)){
					base.add((Trade)t);
					/*
					if ( ((Trade)t).getBuySell().equals(BUYSELL.BUY) ){
						quantityBuy += ((Trade)t).getQuantity();
						amountBuy += ((Trade)t).getQuantity() * ((Trade)t).getPrice();
						commissionBuy += ((Trade)t).getCommission();
					}else{
						quantitySell += Math.abs( ((Trade)t).getQuantity() );
						amountSell += Math.abs( ((Trade)t).getQuantity() ) * ((Trade)t).getPrice();
						commissionSell += ((Trade)t).getCommission();
					}
					*/
				}
			}
		}
		Collections.sort(base,TransactionComparator.compareDateEffective());
	}
	
	
	// GET - PUBLIC
	public List<Transaction> getTransactionInvolved(){
		return base;
	}
	public List<Transaction> getTransactionsForPNLRealized(){
		List<Transaction> lt = new ArrayList<Transaction>();
		if (t1!=null){
			lt.add(t1);
		}
		if (t2!=null){
			lt.add(t2);
		}
		return lt;
	}
	
	public List<Transaction> getTransactionsForPNLUnrealized(){
		List<Transaction> lt = new ArrayList<Transaction>();
		if (t3!=null){
			lt.add(t3);
		}
		if (t3!=null){
			lt.add(t3);
		}
		return lt;
	}
	
	public Double getPNLRealized(Boolean commission){
		if (commission)
			return (this.amountRealized - this.commissionRealized );
		else
			return this.amountRealized;
	}
	
	public Double getPNLUnrealized(Boolean commission){
		if (this.amountUnrealized!=null){
			if (commission)
				return (this.amountUnrealized - this.commissionUnrealized );
			else
				return this.amountUnrealized;
		}
		return null;
	}
	
	
	// FUNCTION - PRIVATE
	private void calcul(){
		
		String direction = "";
		
		Double qtyBuy = 0.00;
		Double qtySell = 0.00;
		Double amountBuy = 0.00;
		Double amountSell = 0.00;
		Double commissionBuy = 0.00;
		Double commissionSell= 0.00;
		
		Trade tm1 = null;
		
		if ( base.size() == 1 ){ // 1 Deal -> only UR PNL
			t3 = ((Trade)base.get(0));
			t4 = this.generateHypotheticaTrade(t3);
			this.amountUnrealized = this.getResult(t3, t4);
			this.commissionUnrealized = t3.getCommission() + t4.getCommission();
			
		}else{
		
			//We go through all trades
			for ( int i = 0 ; i < base.size() ; i++ ){
				Trade tencours = ((Trade)base.get(i));
						
				// For the first deal
				if ( i == 0 ){
					direction = tencours.getBuySell();
					if ( tencours.getBuySell().equals(BUYSELL.BUY) ){
						qtyBuy = tencours.getQuantity();
						amountBuy = tencours.getQuantity() * tencours.getPrice();
						commissionBuy = tencours.getCommission();
					}
					else{
						qtySell = tencours.getQuantity();
						amountSell = -tencours.getQuantity() * tencours.getPrice();
						commissionSell = tencours.getCommission();
					}
					tm1 = tencours;
					
				}else{
					
					if ( direction.equals("") ){
						direction = tencours.getBuySell();
					}
					
					// Same direction
					if ( tencours.getBuySell().equals(direction) ){
						
						if ( tencours.getBuySell().equals(BUYSELL.BUY) ){
							qtyBuy += tencours.getQuantity();
							amountBuy += tencours.getQuantity() * tencours.getPrice();
							commissionBuy += tencours.getCommission();
						}
						else{
							qtySell += tencours.getQuantity();
							amountSell += -tencours.getQuantity() * tencours.getPrice();
							commissionSell += tencours.getCommission();
						}
						
						if ( t3 != null && tencours.getBuySell().equals(BUYSELL.BUY) ){							
							Double p = Math.abs( (( t3.getPrice() * t3.getQuantity() ) + ( tencours.getPrice() *  tencours.getQuantity())) / ( t3.getQuantity() + tencours.getQuantity() ) );
							t3 = new Trade(new Date(), new Date(), "", t3.getCommission() + tencours.getCommission() , this.instrument, p , qtyBuy ) ;
						}
						if ( t3 != null && tencours.getBuySell().equals(BUYSELL.SELL) ){							
							Double p = Math.abs( (( t3.getPrice() * t3.getQuantity() ) + ( tencours.getPrice() *  tencours.getQuantity())) / ( t3.getQuantity() + tencours.getQuantity() ) );
							t3 = new Trade(new Date(), new Date(), "" , t3.getCommission() + tencours.getCommission() , this.instrument, p , qtySell ) ;
						}
						
						
						if ( t3 == null && tencours.getBuySell().equals(BUYSELL.BUY) ){ // We aggreg First + second Buys
							Double p = amountBuy / qtyBuy;
							t3 = new Trade(new Date(), new Date(), "", tm1.getCommission() + tencours.getCommission() , this.instrument, p , qtyBuy ) ;
						}
						if ( t3 == null && tencours.getBuySell().equals(BUYSELL.SELL) ){ // We aggreg First + second Sells
							Double p = amountSell / -qtySell;
							t3 = new Trade(new Date(), new Date(), "" , tm1.getCommission() + tencours.getCommission(), this.instrument, p , qtySell ) ;
						}
						
						t4 = this.generateHypotheticaTrade(t3);
						this.amountUnrealized = this.getResult(t3, t4);
						this.commissionUnrealized = t3.getCommission() + t4.getCommission();
							
					}else{ // NOT the same direction
						
						// SAME QUANTITY
						if ( direction.equals(BUYSELL.BUY) && qtyBuy.equals(-tencours.getQuantity())){ // We close the actual BUY
							
							if (t1==null){
								t1 = new Trade(tm1.getDateExecution(), tm1.getDateEffective() , "" , commissionBuy, this.instrument , amountBuy / qtyBuy , qtyBuy );
								t2 = new Trade( tencours.getDateExecution() , tencours.getDateEffective() , "" , tencours.getCommission(), this.instrument ,tencours.getPrice() , tencours.getQuantity() );
								this.amountRealized = this.getResult(t1, t2);
								this.commissionRealized = t1.getCommission() + t2.getCommission();
								
							}else{
								
								//Second PNL R for this instrument, we need to aggreg them
								Trade t11 = new Trade( new Date(), new Date(), "" , tencours.getCommission() , this.instrument ,tencours.getPrice() , tencours.getQuantity() );
								Trade t22 = new Trade(tencours.getDateExecution() , tencours.getDateEffective()  , ""  , commissionBuy, this.instrument , amountBuy / qtyBuy , qtyBuy);
								
								if ( t1.getBuySell().equals(t22.getBuySell()) ){
									Double p = Math.abs( ( (t1.getQuantity()*t1.getPrice()) + (t22.getQuantity()*t22.getPrice()) ) / (t1.getQuantity() + t22.getQuantity()) );
									t1 = new Trade( new Date() , new Date(), "", t1.getCommission() + t22.getCommission()  , this.instrument , p , t1.getQuantity()+t22.getQuantity() );
										
									Double p2 = Math.abs( ( (t2.getQuantity()*t2.getPrice()) + (t11.getQuantity()*t11.getPrice()) ) / (t2.getQuantity() + t11.getQuantity()) );
									t2 = new Trade( new Date() , new Date(), "" , t2.getCommission() + t11.getCommission(), this.instrument , p2 , t2.getQuantity()+t11.getQuantity()  );
									
								}else{
									Double p = Math.abs( ( (t1.getQuantity()*t1.getPrice()) + (t11.getQuantity()*t11.getPrice()) ) / (t1.getQuantity() + t11.getQuantity()) );
									t1 = new Trade( new Date() , new Date(), "", t1.getCommission() + t11.getCommission() , this.instrument , p , t1.getQuantity()+t11.getQuantity()  );
										
									Double p2 = Math.abs( ( (t2.getQuantity()*t2.getPrice()) + (t22.getQuantity()*t22.getPrice()) ) / (t2.getQuantity() + t22.getQuantity()) );
									t2 = new Trade( new Date() , new Date(), "" , t2.getCommission() + t22.getCommission() , this.instrument , p2 , t2.getQuantity()+t22.getQuantity() );
									
								}
								t11=t22=null;
								this.amountRealized = this.getResult(t1, t2);
								this.commissionRealized = t1.getCommission() + t2.getCommission();
							}
							
							direction = "";
							qtyBuy = qtySell =  0.00;
							amountBuy = amountSell = 0.00;
							commissionBuy = commissionSell = 0.00;
							this.amountUnrealized=0.00;
							this.commissionUnrealized=0.00;
							
						}
						
						if ( direction.equals(BUYSELL.SELL) && qtySell.equals(-tencours.getQuantity())){ // We close the actual SELL
							
							if (t1==null){
								t1 = new Trade(tm1.getDateExecution(), tm1.getDateEffective()  , "", commissionSell , this.instrument , amountSell / -qtySell , qtySell );
								t2 = new Trade( tencours.getDateExecution() , tencours.getDateEffective() , "", tencours.getCommission() , this.instrument ,tencours.getPrice() , tencours.getQuantity() );
								this.amountRealized = this.getResult(t1, t2);
								this.commissionRealized = t1.getCommission() + t2.getCommission();
							}else{
								
								//Second PNL R for this instrument, we need to aggreg them
								Trade t11 = new Trade( new Date(), new Date(), "", commissionSell , this.instrument ,amountSell / -qtySell , qtySell  );
								Trade t22 = new Trade(tencours.getDateExecution() , tencours.getDateEffective()  , "", tencours.getCommission() , this.instrument , tencours.getPrice() , tencours.getQuantity()   );
								
								if ( t1.getBuySell().equals(t22.getBuySell()) ){
									Double p = Math.abs( ( (t1.getQuantity()*t1.getPrice()) + (t22.getQuantity()*t22.getPrice()) ) / (t1.getQuantity() + t22.getQuantity()) );
									t1 = new Trade( new Date() , new Date(), "" , t1.getCommission() + t22.getCommission(), this.instrument , p , t1.getQuantity()+t22.getQuantity()  );
										
									Double p2 = Math.abs( ( (t2.getQuantity()*t2.getPrice()) + (t11.getQuantity()*t11.getPrice()) ) / (t2.getQuantity() + t11.getQuantity()) );
									t2 = new Trade( new Date() , new Date(), "" , t2.getCommission() + t11.getCommission(), this.instrument , p2 , t2.getQuantity()+t11.getQuantity()  );
									
								}else{
									Double p = Math.abs( ( (t1.getQuantity()*t1.getPrice()) + (t11.getQuantity()*t11.getPrice()) ) / (t1.getQuantity() + t11.getQuantity()) );
									t1 = new Trade( new Date() , new Date(), "", t1.getCommission() + t11.getCommission() , this.instrument , p , t1.getQuantity()+t11.getQuantity()  );
										
									Double p2 = Math.abs( ( (t2.getQuantity()*t2.getPrice()) + (t22.getQuantity()*t22.getPrice()) ) / (t2.getQuantity() + t22.getQuantity()) );
									t2 = new Trade( new Date() , new Date(), "", t2.getCommission() + t22.getCommission() , this.instrument , p2 , t2.getQuantity()+t22.getQuantity()  );
									
								}
								t11=t22=null;
								this.amountRealized = this.getResult(t1, t2);
								this.commissionRealized = t1.getCommission() + t2.getCommission();
								
								
							}
							
							direction = "";
							qtyBuy = qtySell =  0.00;
							amountBuy = amountSell = 0.00;
							commissionBuy = commissionSell = 0.00;	
							this.amountUnrealized=0.00;
							this.commissionUnrealized=0.00;
						}
						
						
						
						
						// Different Qty
						if ( direction.equals(BUYSELL.BUY) && qtyBuy > -tencours.getQuantity() ){ // We close the actual BUY
							// DIRECTION BUY -> More Buy
							//PNL R
							if (t1==null){
								t1 = new Trade(tm1.getDateExecution(), tm1.getDateEffective()  , "", ((commissionBuy/qtyBuy)*-tencours.getQuantity()) , this.instrument , amountBuy / qtyBuy , -tencours.getQuantity()   );
								t2 = new Trade( tencours.getDateExecution() , tencours.getDateEffective() , "" , tencours.getCommission(), this.instrument ,tencours.getPrice() , tencours.getQuantity() );
								this.amountRealized = this.getResult(t1, t2);
								this.commissionRealized = t1.getCommission() + t2.getCommission();
							}else{
								
								//Second PNL R for this instrument, we need to aggreg them
								Trade t11 = new Trade( new Date(), new Date(), "", tencours.getCommission() , this.instrument , tencours.getPrice() , tencours.getQuantity() );
								Trade t22 = new Trade(tencours.getDateExecution() , tencours.getDateEffective()  , "" , ((commissionBuy/qtyBuy)*-tencours.getQuantity()), this.instrument , amountBuy / qtyBuy , -tencours.getQuantity()   );
								
								if ( t1.getBuySell().equals(t22.getBuySell()) ){
									Double p = Math.abs( ( (t1.getQuantity()*t1.getPrice()) + (t22.getQuantity()*t22.getPrice()) ) / (t1.getQuantity() + t22.getQuantity()) );
									t1 = new Trade( new Date() , new Date(), "", t1.getCommission() + t22.getCommission() , this.instrument , p , t1.getQuantity()+t22.getQuantity()  );
										
									Double p2 = Math.abs( ( (t2.getQuantity()*t2.getPrice()) + (t11.getQuantity()*t11.getPrice()) ) / (t2.getQuantity() + t11.getQuantity()) );
									t2 = new Trade( new Date() , new Date(), "", t2.getCommission() + t11.getCommission() , this.instrument , p2 , t2.getQuantity()+t11.getQuantity()  );
									
								}else{
									Double p = Math.abs( ( (t1.getQuantity()*t1.getPrice()) + (t11.getQuantity()*t11.getPrice()) ) / (t1.getQuantity() + t11.getQuantity()) );
									t1 = new Trade( new Date() , new Date(), "" , t1.getCommission() + t11.getCommission() , this.instrument , p , t1.getQuantity()+t11.getQuantity() );
										
									Double p2 = Math.abs( ( (t2.getQuantity()*t2.getPrice()) + (t22.getQuantity()*t22.getPrice()) ) / (t2.getQuantity() + t22.getQuantity()) );
									t2 = new Trade( new Date() , new Date(), "", t2.getCommission() + t22.getCommission() , this.instrument , p2 , t2.getQuantity()+t22.getQuantity()  );
									
								}
								t11=t22=null;
								this.amountRealized = this.getResult(t1, t2);
								this.commissionRealized = t1.getCommission() + t2.getCommission();
								
							}
							
							direction = BUYSELL.BUY;
							amountBuy = ((amountBuy / qtyBuy) * (qtyBuy + tencours.getQuantity()) );
							commissionBuy =  ((commissionBuy/qtyBuy)* (qtyBuy +tencours.getQuantity()));
							qtyBuy = qtyBuy + tencours.getQuantity();
							qtySell = amountSell = commissionSell = 0.00;
							
							//PNL UR
							t3 = new Trade(new Date(), new Date() , "" , commissionBuy , this.instrument , amountBuy / qtyBuy , qtyBuy   );
							t4 = this.generateHypotheticaTrade(t3);
							this.amountUnrealized = this.getResult(t3, t4);
							this.commissionUnrealized = t3.getCommission() + t4.getCommission();
						}else{
						
						
							if ( direction.equals(BUYSELL.BUY) && qtyBuy < -tencours.getQuantity() ){ // We close the actual BUY
								// DIRECTION BUY -> More Sell
								//PNL R
								if (t1==null){
									t1 = new Trade(tm1.getDateExecution(), tm1.getDateEffective()  , "" , commissionBuy, this.instrument , amountBuy / qtyBuy , qtyBuy   );
									t2 = new Trade( tencours.getDateExecution() , tencours.getDateEffective() , "" , (tencours.getCommission() / -tencours.getQuantity()) * qtyBuy , this.instrument ,tencours.getPrice() , -qtyBuy  );
									this.amountRealized = this.getResult(t1, t2);
									this.commissionRealized = t1.getCommission() + t2.getCommission();
								}else{
									
									//Second PNL R for this instrument, we need to aggreg them
									Trade t11 = new Trade( new Date(), new Date(), "" , (tencours.getCommission() / -tencours.getQuantity()) * qtyBuy, this.instrument ,tencours.getPrice() , -qtyBuy  );
									Trade t22 = new Trade(tencours.getDateExecution() , tencours.getDateEffective()  , "" , commissionBuy , this.instrument , amountBuy / qtyBuy , qtyBuy  );
									
									if ( t1.getBuySell().equals(t22.getBuySell()) ){
										Double p = Math.abs( ( (t1.getQuantity()*t1.getPrice()) + (t22.getQuantity()*t22.getPrice()) ) / (t1.getQuantity() + t22.getQuantity()) );
										t1 = new Trade( new Date() , new Date(), "" , t1.getCommission() + t22.getCommission(), this.instrument , p , t1.getQuantity()+t22.getQuantity()  );
											
										Double p2 = Math.abs( ( (t2.getQuantity()*t2.getPrice()) + (t11.getQuantity()*t11.getPrice()) ) / (t2.getQuantity() + t11.getQuantity()) );
										t2 = new Trade( new Date() , new Date(), "", t2.getCommission() + t11.getCommission() , this.instrument , p2 , t2.getQuantity()+t11.getQuantity()  );
										
									}else{
										Double p = Math.abs( ( (t1.getQuantity()*t1.getPrice()) + (t11.getQuantity()*t11.getPrice()) ) / (t1.getQuantity() + t11.getQuantity()) );
										t1 = new Trade( new Date() , new Date(), "" , t1.getCommission() + t11.getCommission() , this.instrument , p , t1.getQuantity()+t11.getQuantity() );
											
										Double p2 = Math.abs( ( (t2.getQuantity()*t2.getPrice()) + (t22.getQuantity()*t22.getPrice()) ) / (t2.getQuantity() + t22.getQuantity()) );
										t2 = new Trade( new Date() , new Date(), "", t2.getCommission() + t22.getCommission() , this.instrument , p2 , t2.getQuantity()+t22.getQuantity()  );
										
									}
									t11=t22=null;
									this.amountRealized = this.getResult(t1, t2);
									this.commissionRealized = t1.getCommission() + t2.getCommission();
									
								}
								
								direction = BUYSELL.SELL;
								qtySell = tencours.getQuantity() + qtyBuy;
								amountSell = -qtySell * tencours.getPrice();
								commissionSell =  tencours.getCommission() - t2.getCommission();
								qtyBuy = amountBuy = commissionBuy = 0.00;
								
								//PNL UR
								t3 = new Trade(new Date(), new Date() , "" , commissionSell, this.instrument , amountSell / -qtySell , qtySell   );
								t4 = this.generateHypotheticaTrade(t3);
								this.amountUnrealized = this.getResult(t3, t4);
								this.commissionUnrealized = t3.getCommission() + t4.getCommission();
							}else{
	
								
								if ( direction.equals(BUYSELL.SELL) && -qtySell > tencours.getQuantity() ){ // We close the actual SELL
									// DIRECTION SELL -> Less buy
									//PNL R
									if (t1==null){
										t1 = new Trade( tm1.getDateExecution(), tm1.getDateEffective() , ""  , (commissionSell/-qtySell)*tencours.getQuantity(), this.instrument ,amountSell / -qtySell , -tencours.getQuantity() );
										t2 = new Trade(tencours.getDateExecution() , tencours.getDateEffective()  , "" , tencours.getCommission() , this.instrument , tencours.getPrice() , tencours.getQuantity()  );
										this.amountRealized = this.getResult(t1, t2);
										this.commissionRealized = t1.getCommission() + t2.getCommission();
										
									}else{
										
										//Second PNL R for this instrument, we need to aggreg them
										Trade t11 = new Trade( new Date(), new Date(), "" , (commissionSell/-qtySell)*tencours.getQuantity() , this.instrument ,amountSell / -qtySell , -tencours.getQuantity() );
										Trade t22 = new Trade(tencours.getDateExecution() , tencours.getDateEffective()  , "" , tencours.getCommission() , this.instrument , tencours.getPrice() , tencours.getQuantity()  );
										
										if ( t1.getBuySell().equals(t22.getBuySell()) ){
											Double p = Math.abs( ( (t1.getQuantity()*t1.getPrice()) + (t22.getQuantity()*t22.getPrice()) ) / (t1.getQuantity() + t22.getQuantity()) );
											t1 = new Trade( new Date() , new Date(), "", t1.getCommission() + t22.getCommission()  , this.instrument , p , t1.getQuantity()+t22.getQuantity() );
												
											Double p2 = Math.abs( ( (t2.getQuantity()*t2.getPrice()) + (t11.getQuantity()*t11.getPrice()) ) / (t2.getQuantity() + t11.getQuantity()) );
											t2 = new Trade( new Date() , new Date(), "" , t2.getCommission() + t11.getCommission(), this.instrument , p2 , t2.getQuantity()+t11.getQuantity()  );
											
										}else{
											Double p = Math.abs( ( (t1.getQuantity()*t1.getPrice()) + (t11.getQuantity()*t11.getPrice()) ) / (t1.getQuantity() + t11.getQuantity()) );
											t1 = new Trade( new Date() , new Date(), "" , t1.getCommission() + t11.getCommission(), this.instrument , p , t1.getQuantity()+t11.getQuantity()  );
												
											Double p2 = Math.abs( ( (t2.getQuantity()*t2.getPrice()) + (t22.getQuantity()*t22.getPrice()) ) / (t2.getQuantity() + t22.getQuantity()) );
											t2 = new Trade( new Date() , new Date(), "" , t2.getCommission() + t22.getCommission(), this.instrument , p2 , t2.getQuantity()+t22.getQuantity()  );
											
										}
										t11=t22=null;
										this.amountRealized = this.getResult(t1, t2);
										this.commissionRealized = t1.getCommission() + t2.getCommission();
										
									}
									direction = BUYSELL.SELL;
									amountSell = amountSell - ( tencours.getQuantity() * ( amountSell/-qtySell ) );
									commissionSell =  ((commissionSell/qtySell) * (qtySell+tencours.getQuantity()));
									qtySell = qtySell + tencours.getQuantity();
									qtyBuy = amountBuy = commissionBuy = 0.00;
									
									//PNL UR
									t3 = new Trade(new Date(), new Date() , "" , commissionSell, this.instrument , amountSell / -qtySell , qtySell   );
									t4 = this.generateHypotheticaTrade(t3);
									this.amountUnrealized = this.getResult(t3, t4);
									this.commissionUnrealized = t3.getCommission() + t4.getCommission();
								}else{
									
									if ( direction.equals(BUYSELL.SELL) && -qtySell < tencours.getQuantity() ){ // We close the actual SELL
										// DIRECTION SELL -> Less sell
										//PNL R
										if (t1==null){
											t1 = new Trade( tm1.getDateExecution(), tm1.getDateEffective() , ""  , commissionSell, this.instrument ,amountSell / -qtySell , qtySell );
											t2 = new Trade(tencours.getDateExecution() , tencours.getDateEffective()  , ""  , (tencours.getCommission()/tencours.getQuantity()) * -qtySell , this.instrument , tencours.getPrice() , tencours.getQuantity() + qtySell );
											this.amountRealized = this.getResult(t1, t2);
											this.commissionRealized = t1.getCommission() + t2.getCommission();
										}else{
											
											//Second PNL R for this instrument, we need to aggreg them
											Trade t11 = new Trade( new Date(), new Date(), "" , (tencours.getCommission()/tencours.getQuantity()) * -qtySell , this.instrument , tencours.getPrice() , tencours.getQuantity() + qtySell  );
											Trade t22 = new Trade(tencours.getDateExecution() , tencours.getDateEffective()  , ""  , commissionSell, this.instrument ,amountSell / -qtySell , qtySell );
											
											if ( t1.getBuySell().equals(t22.getBuySell()) ){
												Double p = Math.abs( ( (t1.getQuantity()*t1.getPrice()) + (t22.getQuantity()*t22.getPrice()) ) / (t1.getQuantity() + t22.getQuantity()) );
												t1 = new Trade( new Date() , new Date(), "" , t1.getCommission() + t22.getCommission() , this.instrument , p , t1.getQuantity()+t22.getQuantity() );
													
												Double p2 = Math.abs( ( (t2.getQuantity()*t2.getPrice()) + (t11.getQuantity()*t11.getPrice()) ) / (t2.getQuantity() + t11.getQuantity()) );
												t2 = new Trade( new Date() , new Date(), "" , t2.getCommission() + t11.getCommission()  , this.instrument , p2 , t2.getQuantity()+t11.getQuantity());
												
											}else{
												Double p = Math.abs( ( (t1.getQuantity()*t1.getPrice()) + (t11.getQuantity()*t11.getPrice()) ) / (t1.getQuantity() + t11.getQuantity()) );
												t1 = new Trade( new Date() , new Date(), "" , t1.getCommission() + t11.getCommission() , this.instrument , p , t1.getQuantity()+t11.getQuantity() );
													
												Double p2 = Math.abs( ( (t2.getQuantity()*t2.getPrice()) + (t22.getQuantity()*t22.getPrice()) ) / (t2.getQuantity() + t22.getQuantity()) );
												t2 = new Trade( new Date() , new Date(), "", t2.getCommission() + t22.getCommission() , this.instrument , p2 , t2.getQuantity()+t22.getQuantity()  );
												
											}
											t11=t22=null;
											this.amountRealized = this.getResult(t1, t2);
											this.commissionRealized = t1.getCommission() + t2.getCommission();
											
										}
										
										direction = BUYSELL.BUY;
										qtyBuy = tencours.getQuantity() + qtySell;
										amountBuy = tencours.getPrice() * qtyBuy;
										commissionBuy =  (tencours.getCommission()/tencours.getQuantity()) * (tencours.getQuantity() +  qtySell);
										qtySell = amountSell = commissionSell = 0.00;
										
										//PNL UR
										t3 = new Trade(new Date(), new Date() , "", commissionBuy , this.instrument , amountBuy / qtyBuy , qtyBuy  );
										t4 = this.generateHypotheticaTrade(t3);
										this.amountUnrealized = this.getResult(t3, t4);
										this.commissionUnrealized = t3.getCommission() + t4.getCommission();
									}
									
								}
							}
						}
						
						
					}//End Else, not the same direction
						
					
				} // End, not the first Trade in the loop
					
				
			} // END FOR
		}
				
	}
	
	private Trade generateHypotheticaTrade(Trade t){
		
		Trade t1 = null;
		t1 = new Trade( t.getDateExecution() , t.getDateEffective() , "" , t.getCommission(), t.getInstrument() , this.actualPrice , -t.getQuantity()  );
		return t1;
	}
	
	
	private Double getResult(Trade trade1 , Trade trade2){	
		return -(trade2.getPrice()*trade2.getQuantity()) - (trade1.getPrice()*trade1.getQuantity());
	}
		
}
