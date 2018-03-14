getStockPrice = function(instrument){
	// SPECIFIC
	
	//alert(instrument.name + " / " + instrument.country);
	if (instrument.name == "WKA.P"){
		return (0.15);
	}
	else{
		var s = "";
		
		if ( instrument.country == "CA" || instrument.country == "CANADA" )
			s = instrument.name.replace(".","-") + ".TO";
		else
			s = instrument.name.replace(".","-");
		
		$.get("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20%28%22"+s+"%22%29&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys",
		function(data) { 		      		
	       		//alert(data.query.results.quote.LastTradePriceOnly);
	       		return (data.query.results.quote.LastTradePriceOnly);
	    	});
	    	return (-1);
	}
}


getTest = function(){
	return "test nsa ok";
}

