package galileo.bq;
/* 
 * Copyright (c) 2015, Colorado State University. Written by Duck Keun Yang 2015-08-02
 * 
 * All rights reserved.
 * 
 * CSU EDF Project
 * 
 * This program read a csv-formatted file and send each line to the galileo server
 */

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TimeZone;

import galileo.dataset.Block;
import galileo.util.GeoHash;

public class VisualizationNAMInsertTest {
	
	private static void processFile(String filepath, GalileoConnector gc, int frm, int till) throws Exception {
		
		// CREATING FS1
		
	
		List<File> files = getAllFiles(filepath);
		
		processFiles(files, gc, "namfs", frm, till);
		
		
	
		
	}
	
	
	public static List<File> getAllFiles(String baseDir) throws Exception {
		
    	//File[] files = null;
    	
    	List<File> allFiles = new ArrayList<File>();
    	
    	for(int i=1; i< 13; i++) {
    		
    		String fileName = "nam_Ignite_2013_lattice-"+i;
    		
    		System.out.println(baseDir+fileName);
    		
    		File file = new File(baseDir+fileName);
    		
    		if (file.isDirectory()) {
    			
    			if(file.listFiles() != null) {
    				
    				for(File f : file.listFiles()) {
    					allFiles.add(f);
    				}
    				//allFiles.addAll(Arrays.asList(file.listFiles()));
    			}
    			
    			
    			//files = file.listFiles();
    			
    		} else {
    			System.out.println("WRONG PATH INSERTED "+fileName);
    		}
    		
    	}
    	
    	
		
		return allFiles;
		
	}
	
	private static void processFiles(List<File> files, GalileoConnector gc, String fsName, int frm, int till) throws Exception {
		
		FileInputStream inputStream = null;
		Scanner sc = null;
		
		
		int count = 0;
		System.out.println("TOTAL FILES:"+ files.size());
		
		for(File f : files) {
			
			if(count % 20 == 0)
				System.out.println("=========COUNT:========= "+count+" "+f.getAbsolutePath());
			
			if(count < frm) {
				count++;
				continue;
			}
			
			if(count > till) {
				count++;
				break;
			}
			
			
			count++;
			//System.out.println("\n\n============="+count+"============\n\n");
			/*if(count < 100)
				System.out.println("\n\n============="+count+"============\n\n");
			if(count%100 == 0)
				System.out.println("\n\n============="+count+"============\n\n");*/
			//System.out.println("processing - " + f);
			String filepath = f.getAbsolutePath();
			//System.out.println("======COUNT======"+count+" "+System.currentTimeMillis());
			//Getting date string
			String[] tokens = filepath.split("/");
			String fileName = tokens[tokens.length - 1];
			
			String dateString = fileName.substring(0, fileName.length() - 3);
			String ghash = fileName.substring(fileName.length() - 2, fileName.length() - 1);
			
			inputStream = new FileInputStream(filepath);
			sc = new Scanner(inputStream);
			
			Map<String, StringBuffer> keyToLines = new HashMap<String, StringBuffer>();
			
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				
				line = line.replaceAll("null", "0");
				
				if(line.trim().length() > 0) {
					readLines(line, keyToLines, dateString);
				}
				
			}
			inputStream.close();
			//System.out.println("TOTAL LINES: "+keyToLines.keySet().size());
			
			
			for(String key: keyToLines.keySet()) {
				
				String data = keyToLines.get(key).toString();
				//System.out.println(entries.size());
				
				//System.out.println("IS DATA EMPTY?");
				if(data.trim().isEmpty()) {
					continue;
				}
				
				String firstLine = "";
				
				firstLine = data.split("\\r?\\n")[0];
				
				//System.out.println("BEFORE BLOCK CREATION");
				Block tmp = GalileoConnector.createBlockNam(firstLine, data.substring(0, data.length() - 1), fsName, 0,1,2);
				
				if (tmp != null) {
					gc.store(tmp);
				}
				
				
			}
			//System.out.println("HELLO");
			Thread.sleep(10);
		}
		System.out.println("EXITING...");
		gc.disconnect();
		
		
	}




	/**
	 * Reads a file and groups lines by key
	 * @author sapmitra
	 * @return 
	 */
	public static void readLines(String line, Map<String, StringBuffer> keyToLines,String dateString) {
		
		String tokens[] = line.split(",");
		
		double lat = Double.valueOf(tokens[0]);
		double lng = Double.valueOf(tokens[1]);
		
		
		String geohash = GeoHash.encode((float)lat, (float)lng, 4);
		//System.out.println(geohash);
		
		String key = dateString+"-"+geohash;
		
		StringBuffer recs = null;
		if(keyToLines.get(key) == null) {
			recs = new StringBuffer(line+"\n");
			keyToLines.put(key, recs);
		} else {
			recs = keyToLines.get(key);
			recs.append(line+"\n");
		}
		
	}
	
	public static long getTimestamp(String year, String month, String day, String hour, String mins) {
		
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT"));
		c.set(Calendar.DAY_OF_MONTH, Integer.valueOf(day));
		c.set(Calendar.YEAR, Integer.valueOf(year));
		c.set(Calendar.MONTH, Integer.valueOf(month) - 1);
		
		c.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour));
		c.set(Calendar.MINUTE, Integer.valueOf(mins));
		c.set(Calendar.SECOND, 0);
		
		long baseTime = c.getTimeInMillis();
		
		return baseTime;
	}

	
	public static void main(String[] arg) throws Exception {
		
		String baseDir = arg[1];
		String machine = arg[0];
		
		int frm = Integer.valueOf(arg[2]);
		int till = Integer.valueOf(arg[3]);
		
		
		GalileoConnector gc = new GalileoConnector(machine, 5634);
		
		System.out.println("FROM TO" + frm +" "+till);
		processFile(baseDir, gc, frm, till);
	}
	
}
