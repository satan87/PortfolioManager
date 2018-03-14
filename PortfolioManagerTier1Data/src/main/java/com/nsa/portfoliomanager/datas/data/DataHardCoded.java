package com.nsa.portfoliomanager.datas.data;

import java.util.ArrayList;
import java.util.List;

public class DataHardCoded implements Data {

	@Override
	public List<String> getPortfolios() {
		
		String a = "<xml><id>1</id><name>Portfolio 1</name></xml>";
		String b = "<xml><id>2</id><name>Portfolio 2</name></xml>";
		
		ArrayList<String> r = new ArrayList<String>();
		r.add(a);r.add(b);
		
		return r;
		
	}


	
	
}
