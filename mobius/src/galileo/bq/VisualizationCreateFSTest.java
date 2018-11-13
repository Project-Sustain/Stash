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

import galileo.dataset.Block;
import galileo.dataset.SpatialHint;
import galileo.dataset.feature.FeatureType;
import galileo.util.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;

public class VisualizationCreateFSTest {

	// [START processFile]
	/**
	 * read each line from the csv file and send it to galileo server
	 * 
	 * @param pathtothefile
	 *            path to the csv file
	 * @param galileoconnector
	 *            GalileoConnector instance
	 * @throws Exception
	 */
	private static boolean FS_CREATED = false;
	
	private static void processFile(String filepath, GalileoConnector gc) throws Exception {
		
		// CREATING FS1
		if( ! FS_CREATED ) {
			List<Pair<String, FeatureType>> featureList1 = new ArrayList<>();
	  		
			featureList1.add(new Pair<>("epoch_time", FeatureType.FLOAT));
			featureList1.add(new Pair<>("gps_abs_lat", FeatureType.FLOAT));
			featureList1.add(new Pair<>("gps_abs_lon", FeatureType.FLOAT));
			featureList1.add(new Pair<>("fs_feature1", FeatureType.FLOAT));
			featureList1.add(new Pair<>("fs_feature2", FeatureType.FLOAT));
			
			List<String> sumHints = new ArrayList<String>();
			sumHints.add("fs_feature1");
			sumHints.add("fs_feature2");
			
			SpatialHint sp1 = new SpatialHint("gps_abs_lat", "gps_abs_lon");
			String temporalHint1 = "epoch_time";
			
			gc.createFSViz("testfs1", sp1, featureList1, temporalHint1, 1, sumHints);
			
			FS_CREATED = true;
			
			
		}
		
		gc.disconnect();
		
	}
	
	
	
	public static void main(String[] args1) {
		String args[] = new String[3];
		args[0] = "phoenix.cs.colostate.edu";
		args[1] = "5634";
		args[2] = "/s/chopin/b/grad/sapmitra/Documents/Conflux/fs1.csv";
		
		System.out.println(args.length);
		if (args.length != 3) {
			System.out.println(
					"Usage: ConvertCSVFileToGalileo [galileo-hostname] [galileo-port-number] [path-to-csv-file]");
			System.exit(0);
		} else {
			try {
				GalileoConnector gc = new GalileoConnector("phoenix.cs.colostate.edu", 5634);
				System.out.println(args[0] + "," + Integer.parseInt(args[1]));
				File file = new File(args[2]);
				if (file.isFile()) {
					System.out.println("processing - " + args[2]);
					processFile(args[2], gc);
				} /*else {
					if (file.isDirectory()) {
						File[] files = file.listFiles();
						for (File f : files) {
							if (f.isFile())
								System.out.println("processing - " + f);
							processFile(f.getAbsolutePath(), gc);
						}
					}
				}*/
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("Data successfully inserted into galileo");
		System.exit(0);
	}
	// [END Main]
}
