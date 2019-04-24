package galileo.bq;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ExtractValues {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		
		ExtractValues e = new ExtractValues();
		
		e.readFile("/s/chopin/b/grad/sapmitra/Documents/Prophecy/results/testfile", 10, 9);
		
		
	}
	
	public void readFile(String filePath, int group, int eachGroup) throws FileNotFoundException {
		
	    Scanner scanner = new Scanner(new File(filePath));
	    //scanner.useDelimiter(" ");
	 
	    List<String> lines = new ArrayList<String>();
	    while(scanner.hasNext()) {
	    	//System.out.println(scanner.nextLine());
	    	lines.add(scanner.nextLine());
	    }
	    
	    
	    scanner.close();
	    
	    
	    readLines(lines, group, eachGroup);
		
	}
	
	
	public void readLines(List<String> lines, int totalNumOfGroups, int inEachGroup) {
		
		//System.out.println(">>>"+totalNumOfGroups);
		List<List<Float>> values = new ArrayList<List<Float>>();
		
		for(int i=0; i< inEachGroup; i++) {
			values.add(new ArrayList<Float>());
		}
		
		int count = 0;
		
		for(String line: lines) {
			
			if(!line.contains("ENTIRE")) {
				continue;
			}
			
			float tm = Float.valueOf(line.split(":")[2].trim());
			
			values.get(count).add(tm);
			
			count++;
			if(count == inEachGroup) 
				count = 0;
			
		}
		
		
		for(List<Float> vals : values) {
			
			Collections.sort(vals);
			
			System.out.println(vals);
			int count1 = 0;
			
			float tot = 0f;
			
			for(Float v : vals) {
				
				if(count1 == 0 || count1 == vals.size()-1) {
					count1++;
					continue;
				}
				
				//System.out.println(v);
				tot+=v;
				
				count1++;
				
			}
			
			System.out.println(tot/(totalNumOfGroups-2));
			
		}
		 
	}

}
