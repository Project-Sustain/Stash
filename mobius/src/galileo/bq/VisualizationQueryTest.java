package galileo.bq;
/* 
 * 
 * All rights reserved.
 * 
 * CSU EDF Project
 * 
 * This program read a csv-formatted file and send each line to the galileo server
 */

import java.util.ArrayList;
import java.util.List;

import galileo.comm.VisualizationRequest;
import galileo.dataset.Coordinates;

public class VisualizationQueryTest {

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
	
	private static void processRequest(GalileoConnector gc) throws Exception {
		
		// CREATING FS1
		
		VisualizationRequest vr = new VisualizationRequest(); 
		vr.setFsName("testfs1");
		
		float lat1 = 40.32f;
		float lat2 = 40.68f;
		
		float lon1 = -105.17f;
		float lon2 = -104.7f;
		
		Coordinates c1 = new Coordinates(lat2, lon1);
		Coordinates c2 = new Coordinates(lat2, lon2);
		Coordinates c3 = new Coordinates(lat1, lon2);
		Coordinates c4 = new Coordinates(lat1, lon1);
		//Coordinates c5 = new Coordinates(36.78f, -107.64f);
		
		List<Coordinates> cl = new ArrayList<Coordinates>();
		cl.add(c1); cl.add(c2); cl.add(c3); cl.add(c4);
		
		vr.setPolygon(cl);
		vr.setTime("2019-03-25-xx");
		
		/*vr.setSpatialResolution(2);
		vr.setTemporalResolution(2);*/
		
		
		vr.setSpatialResolution(6);
		vr.setTemporalResolution(3);
		
		List<String> sumFt = new ArrayList<String>();
		sumFt.add("fs_feature1");
		sumFt.add("fs_feature2");
		vr.setReqFeatures(sumFt);
		
		try {
			for(int i=0 ;i < 2; i++) {
				gc.visualize(vr);
				Thread.sleep(1000);
			}
			
		} finally {
			gc.disconnect();
		}
	}
	
	// [START Main]
	/**
	 * Based on command line argument, call processFile method to store the data
	 * at galileo server
	 * 
	 * @param args
	 */
	public static void main(String[] args1) {
		String args[] = new String[2];
		args[0] = "lattice-1.cs.colostate.edu";
		args[1] = "5634";
		
		if (args.length != 2) {
			System.out.println("Usage: VisualizationQueryTest [galileo-hostname] [galileo-port-number]");
			System.exit(0);
		} else {
			try {
				GalileoConnector gc = new GalileoConnector(args[0], Integer.parseInt(args[1]));
				System.out.println(args[0] + "," + Integer.parseInt(args[1]));
				
				processRequest(gc);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("Visualization Finished");
		System.exit(0);
	}
	// [END Main]
}
