var app = angular.module('myApp', []);

app.controller('myCtrl', function ($scope, $http, $interval) 
{
	//We take NY date
	var d = new Date();
		
	$scope.ptfs = {};
	$scope.ptfs.select = -1;
	$scope.ptfs.test = 0;
	
	$scope.ptfs.displays = new Array();
	$scope.ptfs.pc = new Array();
	$scope.ptfs.pnlr = new Array();
	
	//Search
	$scope.ptfs.searchTradeType = "";
	$scope.ptfs.searchTradeSymbol = "";
	$scope.ptfs.searchTradeInstrument = "";
	$scope.ptfs.searchTradeQuantity = "";
			
			
	$http.get('rest/portfolios').success(function(data) {
    		$scope.ptfs.portfolios = data;		
    		$scope.ptfs.portfolios.forEach(function(t){
    			$scope.ptfs.displays.push(new displayTable(t.portfolio.id,"open",false));		
    			$scope.ptfs.displays.push(new displayTable(t.portfolio.id,"close",false));
    			$scope.ptfs.displays.push(new displayTable(t.portfolio.id,"tradeList",false));
    			
    			//$scope.ptfs.pc.push(new portfolioPNLClose(t.portfolio.id,"a"));
    			$http.get('rest/position/' + t.portfolio.id )
    			.success(function(data) {
    				$scope.ptfs.pc.push(new portfolioPNLClose(t.portfolio.id,data));	
  				})
  				.error(function(f){
  					$scope.ptfs.pc.push(new portfolioPNLClose(t.portfolio.id,"error"));	
  				});
  				
  				$http.get('rest/pnlrealized/' + t.portfolio.id )
    			.success(function(data) {
    				$scope.ptfs.pnlr.push(new portfolioPNLRealized(t.portfolio.id,data));	
  				})
  				.error(function(f){
  					$scope.ptfs.pnlr.push(new portfolioPNLRealized(t.portfolio.id,"error"));	
  				});
  			
    		});
    		

  	});

  	
	// TOGGLE
	$scope.ptfs.setToggle = function(s,p){
		$scope.ptfs.displays.forEach(function(d){
			if ( d.table == s ){
				if (d.portfolio == p){
					d.display = !d.display;
				}
			}
		});
	}
	$scope.ptfs.getToggle = function(s,p){
		var r;
		$scope.ptfs.displays.forEach(function(d){
			if ( d.table == s ){
				if (d.portfolio == p){
					r = d.display;
				}
			}
		});
		return r;
	}
	
	//Get Trade List
	$scope.ptfs.getTradeList = function(p){
		var r;
		$scope.ptfs.portfolios.forEach(function(pc){
			if ( pc.portfolio.id == p ){
				r = pc.portfolio.transaction;
			}
		});
		return r;
	}
	
	
	//Get PNL Open
	$scope.ptfs.getPNLOpen = function(p){
		var r;
		$scope.ptfs.pc.forEach(function(pc){
			if ( pc.portfolio == p ){
				r = pc.positions;
			}
		});
		return r;
	}
	
	//GET PNL REALIZED
	$scope.ptfs.getPNLRealized = function(p){
		var r;
		$scope.ptfs.pnlr.forEach(function(pc){
			if ( pc.portfolio == p ){
				r = pc.pnlRealized;
			}
		});
		return r;
	}
	
	//REFRESH
	var refreshPrices = $interval(function(){
		$scope.ptfs.test++;

		$scope.ptfs.pc.forEach(function(pc){
  			pc.positions.forEach(function (pcr){
  				
  				var s = pcr.instrument.symbol;
  				if ( pcr.instrument.country.name == "Canada" ){
  					s = pcr.instrument.symbol.replace(".","-") + ".TO";
  				}else{
					s = pcr.instrument.symbol.replace(".","-");
				}
				
  				if (pcr.instrument.symbol == "WKA.P"){
					s="WKA-P.V";
				}
				
  				
	  			$.get("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20%28%22"+s+"%22%29&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys",
				function(data) { 		      		
		       		
		       			//alert("ok1");
		       			var minst = {
	  					instrument : pcr.instrument.symbol,
	  					country :  pcr.instrument.country.name,
	  					lastPrice : data.query.results.quote.LastTradePriceOnly,
						closePrice : data.query.results.quote.PreviousClose
	  				};
		       			$http.post('rest/myprices', minst ).success(function(data) {
		    				//alert("ok3");
		    			});
		    		});
  			});
  			
  		}); 
  		 		
	},600000);
	
	var refreshPNL = $interval(function(){	
    		$scope.ptfs.pc = new Array();
    		$scope.ptfs.portfolios.forEach(function(t){
    			$http.get('rest/position/' + t.portfolio.id )
    			.success(function(data) {
    				$scope.ptfs.pc.push(new portfolioPNLClose(t.portfolio.id,data));	
  			})
  			.error(function(f){
  				$scope.ptfs.pc.push(new portfolioPNLClose(t.portfolio.id,"error"));	
  			});	
    		});
  
  		 		
	},300000);
	
	//SEARCHES
	
	$scope.ptfs.searchTradeTypeF = function(r){
		if ( $scope.ptfs.searchTradeType != "" ){
			if (r.type.toLowerCase().indexOf( $scope.ptfs.searchTradeType.toLowerCase() ) >= 0 )
				return true;
			else
				return false;
		}else
			return true;
	}
	
	$scope.ptfs.searchTradeSymbolF = function(r){
		if ( $scope.ptfs.searchTradeSymbol != "" ){
			if ( r.instrument != null && r.instrument.symbol.toLowerCase().indexOf( $scope.ptfs.searchTradeSymbol.toLowerCase() ) >= 0 )
				return true;
			else
				return false;
		}else
			return true;
	}
	
	$scope.ptfs.searchTradeInstrumentF = function(r){
		if ( $scope.ptfs.searchTradeInstrument != "" ){
			if ( r.instrument != null && r.instrument.name.toLowerCase().indexOf( $scope.ptfs.searchTradeInstrument.toLowerCase() ) >= 0 )
				return true;
			else
				return false;
		}else
			return true;
	}
	$scope.ptfs.searchTradeQuantityF = function(r){
		if ( $scope.ptfs.searchTradeQuantity != "" ){
			if ( r.instrument != null && String(r.quantity).indexOf( $scope.ptfs.searchTradeQuantity ) >= 0 )
				return true;
			else
				return false;
		}else
			return true;
	}
	
	
	//Totals
	$scope.ptfs.totals = function(ptfid , item){
		var r = 0.0; 
		//close
		if (item == "sumPNLClosePNL"){
			$scope.ptfs.pnlr.forEach(function(pc){
				if ( pc.portfolio == ptfid ){
					 pc.pnlRealized.forEach(function(pcr){
						r = r + pcr.pnl;		 	
					});
				}
			});
		}
		if (item == "sumPNLCloseCOMMISSION"){
			$scope.ptfs.pnlr.forEach(function(pc){
				if ( pc.portfolio == ptfid ){
					 pc.pnlRealized.forEach(function(pcr){
						r = r + pcr.commissionPayed;		 	
					});
				}
			});
		}
		if (item == "sumPNLClosePNLWITHCOMMISSION"){
			$scope.ptfs.pnlr.forEach(function(pc){
				if ( pc.portfolio == ptfid ){
					 pc.pnlRealized.forEach(function(pcr){
						r = r + pcr.pnlwithCommission;		 	
					});
				}
			});
		}
		if (item == "sumPNLCloseDIVIDEND"){
			$scope.ptfs.pnlr.forEach(function(pc){
				if ( pc.portfolio == ptfid ){
					 pc.pnlRealized.forEach(function(pcr){
						r = r + pcr.dividend;		 	
					});
				}
			});
		}
		if (item == "sumPNLClosePNLREALIZED"){
			$scope.ptfs.pnlr.forEach(function(pc){
				if ( pc.portfolio == ptfid ){
					 pc.pnlRealized.forEach(function(pcr){
						r = r + pcr.pnlwithCommission + pcr.dividend;		 	
					});
				}
			});
		}
		//OPEN
		if (item == "sumPNLOpenCost"){
			$scope.ptfs.pc.forEach(function(p){
				if ( p.portfolio == ptfid ){
					p.positions.forEach(function(p2){
						r = r + p2.cost;
					});
				}
			});
		}
		if (item == "sumPNLOpenActualValue"){
			$scope.ptfs.pc.forEach(function(p){
				if ( p.portfolio == ptfid ){
					p.positions.forEach(function(p2){
						r = r + p2.marketValueLastPrice;
					});
				}
			});
		}
		if (item == "sumPNLOpenPositionLast"){
			$scope.ptfs.pc.forEach(function(p){
				if ( p.portfolio == ptfid ){
					p.positions.forEach(function(p2){
						r = r + p2.positionLastPrice;
					});
				}
			});
		}
		if (item == "sumPNLOpenPositionLastCommission"){
			$scope.ptfs.pc.forEach(function(p){
				if ( p.portfolio == ptfid ){
					p.positions.forEach(function(p2){
						r = r + p2.positionLastPriceWithCommission;
					});
				}
			});
		}
		if (item == "sumPNLOpenDayGain"){
			$scope.ptfs.pc.forEach(function(p){
				if ( p.portfolio == ptfid ){
					p.positions.forEach(function(p2){
						r = r + p2.dayGain;
					});
				}
			});
		}
		
		return r;
	}
  	
  	
  	//CLASSES
  	function displayTable(p,t,d){
		this.portfolio = p;
		this.table = t;
		this.display = d;
	}
	
	function portfolioPNLClose(p,po){
		this.portfolio = p;
		this.positions = po;
	}
	
	function portfolioPNLRealized(p,po){
		this.portfolio = p;
		this.pnlRealized = po;
	}
  	

  	
  	
});


