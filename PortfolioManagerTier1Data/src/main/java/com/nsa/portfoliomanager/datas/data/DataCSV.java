package com.nsa.portfoliomanager.datas.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataCSV implements Data {
	
	private Path myCompleteFile;
		
	public DataCSV(){
		super();
	}
	
	public DataCSV(Path myFile){
		super();
		this.myCompleteFile = myFile; 
	}
	
	
	@Override
	public List<String> getPortfolios() {
		

		List<String> ls = null;
		List<String> r = new ArrayList<String>();
		List<Integer> lid = new ArrayList<Integer>();
		
		try {
			ls = Files.readAllLines(myCompleteFile);
			
			// We checks the protfolios
			for (String st : ls){
				
				if ( !st.startsWith("#") ){				
					String[] t = st.split(",");
					if ( t[1].equalsIgnoreCase("portfolio") ){
						if ( !lid.contains(Integer.parseInt(t[0])) )
							lid.add(Integer.parseInt(t[0]));
					}
				}
			}
			
			for (int li = 0 ; li < lid.size() ; li++){
				String xml="";
				for (String st : ls){
					
					if ( !st.startsWith("#") ){
					
						//We split the line
						String[] t = st.split(",");
					
						if ( t[0].equals(lid.get(li).toString()) ){
					
							if (t[1].equalsIgnoreCase("portfolio")){
								//We take the id / name / real
								String id = t[0];
								String name = t[2];
								String real = t[3];
								
								xml = "<xml><id>"+id+"</id><name>"+name+"</name><real>"+real+"</real>";
								
								xml = xml + "<transactions>";
								// We start loading all the transactions
							}
							
							if (t[1].equalsIgnoreCase("cash")){
								xml = xml + "<transaction>";
								xml = xml + "<type>" + t[1] + "</type>";
								xml = xml + "<date>" + t[2] + "</date>";
								xml = xml + "<amount>" + t[4] + "</amount>";
								xml = xml + "<commission>" + t[5] + "</commission>";
								if ( t.length>6 )
									xml = xml + "<comment>" + t[6] + "</comment>";
								else
									xml = xml + "<comment></comment>";
								
								xml = xml + "</transaction>";
										
										
							}
							
							
							if (t[1].equalsIgnoreCase("trade")){
								xml = xml + "<transaction>";
								xml = xml + "<id>-1</id>";
								xml = xml + "<type>" + t[1] + "</type>";
								xml = xml + "<date>" + t[2] + "</date>";
								xml = xml + "<instrument>" + t[4] + "</instrument>";
								xml = xml + "<name>" + t[5] + "</name>";
								xml = xml + "<country>" + t[6] + "</country>";
								xml = xml + "<price>" + t[7] + "</price>"; 
								xml = xml + "<quantity>" + t[8] + "</quantity>";
								xml = xml + "<commission>" + t[9] + "</commission>";
								
								if ( t.length>10 )
									xml = xml + "<comment>" + t[10] + "</comment>";
								else
									xml = xml + "<comment></comment>";
								xml = xml + "</transaction>";
							}
							
							if (t[1].equalsIgnoreCase("dividend")){
								xml = xml + "<transaction>";
								xml = xml + "<type>" + t[1] + "</type>";
								xml = xml + "<dateAnnounced>" + t[2] + "</dateAnnounced>";
								xml = xml + "<dateExecuted>" + t[3] + "</dateExecuted>";
								xml = xml + "<dateEffective>" + t[4] + "</dateEffective>";
								xml = xml + "<instrument>" + t[5] + "</instrument>";
								xml = xml + "<amount>" + t[6] + "</amount>";
								xml = xml + "<commission>" + t[7] + "</commission>";
								if ( t.length>8 )
									xml = xml + "<comment>" + t[8] + "</comment>";
								else
									xml = xml + "<comment></comment>";
								
								xml = xml + "</transaction>";
							}
						}
					}
				}

				xml = xml + "</transactions>";
				xml = xml + "</xml>";
						r .add(xml);
			}
			
		} catch (IOException e) {			
			e.printStackTrace();
		}
			
		

			
		return r;
		
		
		
		
		
	}

	
	
}
