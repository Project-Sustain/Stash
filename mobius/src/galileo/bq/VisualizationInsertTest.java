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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Scanner;
import java.util.TimeZone;

import galileo.dataset.Block;

public class VisualizationInsertTest {
	
	private static void processFile(String filepath, GalileoConnector gc) throws Exception {
		
		// CREATING FS1
		
		try {
			insertData(filepath, gc, "testfs1", 1);
			Thread.sleep(5000);
		} finally {
			gc.disconnect();
		}
	}
	/**
	 * @param filepath
	 * @param gc
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 * @throws IOException
	 */
	private static void insertData(String filepath, GalileoConnector gc, String fsName, int mode)
			throws FileNotFoundException, UnsupportedEncodingException, Exception, IOException {
		FileInputStream inputStream = null;
		Scanner sc = null;
		
		try{
			inputStream = new FileInputStream(filepath);
			sc = new Scanner(inputStream);
			StringBuffer data = new StringBuffer();
			System.out.println("Start Reading CSV File");
			String previousDay = null;
			int rowCount = 0;
			Calendar c = Calendar.getInstance();
			c.setTimeZone(TimeZone.getTimeZone("GMT"));
			String lastLine = "";
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if(line.trim().isEmpty())
					continue;
				String tmpvalues[] = line.split(",");
				if (line.contains("epoch_time")) {
					continue;
				}
				if (Float.parseFloat(tmpvalues[1]) == 0.0f && Float.parseFloat(tmpvalues[2]) == 0.0f) {
					continue;
				}
				if (line.contains("NaN") || line.contains("null")) {
					line.replace("NaN", "0.0");
					line.replace("null", "0.0");
				}
				long epoch = GalileoConnector.reformatDatetime(tmpvalues[0]);
				c.setTimeInMillis(epoch);
				String currentDay = String.format("%d-%d-%d", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1,
						c.get(Calendar.DAY_OF_MONTH));
				
				// DOING FOR ONE DAY AT A TIME
				if (previousDay != null && !currentDay.equals(previousDay)) {
					String allLines = data.toString();
					System.out.println("Creating a block for " + previousDay + " GMT having " + rowCount + " rows");
					System.out.println(lastLine);
					
					/*Using the lastline to create metadata */
					Block tmp = GalileoConnector.createBlock(lastLine, allLines.substring(0, allLines.length() - 1), fsName, mode);
					if (tmp != null) {
						gc.store(tmp);
					}
					data = new StringBuffer();
					rowCount = 0;
				}
				previousDay = currentDay;
				data.append(line + "\n");
				lastLine = line;
				rowCount++;
			}
			
			String allLines = data.toString();
			System.out.println("Creating a block for " + previousDay + " GMT having " + rowCount + " rows");
			System.out.println(lastLine);
			Block tmp = GalileoConnector.createBlockViz(lastLine, allLines.substring(0, allLines.length() - 1));
			if (tmp != null) {
				gc.store(tmp);
			}
			// note that Scanner suppresses exceptions
			if (sc.ioException() != null) {
				throw sc.ioException();
			}
			} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (sc != null) {
				sc.close();
			}
			
		}
		
	}

	
	public static void main(String[] args1) {
		String args[] = new String[3];
		args[0] = "lattice-1.cs.colostate.edu";
		args[1] = "5634";
		args[2] = "/s/chopin/b/grad/sapmitra/Documents/Prophecy/fs1.csv";
		
		System.out.println(args.length);
		if (args.length != 3) {
			System.out.println(
					"Usage: ConvertCSVFileToGalileo [galileo-hostname] [galileo-port-number] [path-to-csv-file]");
			System.exit(0);
		} else {
			try {
				
				GalileoConnector gc = new GalileoConnector(args[0], 5634);
				System.out.println(args[0] + "," + Integer.parseInt(args[1]));
				File file = new File(args[2]);
				if (file.isFile()) {
					System.out.println("processing - " + args[2]);
					processFile(args[2], gc);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("Data successfully inserted into galileo");
		System.exit(0);
	}
	
}
