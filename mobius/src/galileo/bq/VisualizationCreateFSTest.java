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

import java.util.ArrayList;
import java.util.List;

import galileo.dataset.SpatialHint;
import galileo.dataset.feature.FeatureType;
import galileo.util.Pair;

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
	
	private static void createFileSystem(GalileoConnector gc) throws Exception {
		
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
			
			SpatialHint spHint = new SpatialHint("gps_abs_lat", "gps_abs_lon");
			String temporalHint1 = "epoch_time";
			
			gc.createFSViz("testfs1", spHint, featureList1, temporalHint1, sumHints);
			
			
			System.out.println("CREATION INITIATED");
			FS_CREATED = true;
			
			Thread.sleep(2000);
		}
		
		gc.disconnect();
		
	}
	
	
	
	public static void main(String[] args1) {
		String args[] = new String[2];
		args[0] = "lattice-1.cs.colostate.edu";
		args[1] = "5634";
		
		System.out.println(args.length);
		if (args.length != 2) {
			System.out.println("Usage: ConvertCSVFileToGalileo [galileo-hostname] [galileo-port-number]");
			System.exit(0);
		} else {
			try {
				GalileoConnector gc = new GalileoConnector(args[0], 5634);
				System.out.println(args[0] + "," + Integer.parseInt(args[1]));
				
				createFileSystem(gc);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("Data successfully inserted into galileo");
		System.exit(0);
	}
	
	
}
