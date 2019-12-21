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
import java.util.concurrent.ThreadLocalRandom;

import galileo.client.EventPublisher;
import galileo.comm.GalileoEventMap;
import galileo.comm.QueryResponse;
import galileo.comm.VisualizationRequest;
import galileo.comm.VisualizationResponse;
import galileo.comm.WipeCacheRequest;
import galileo.dataset.Coordinates;
import galileo.event.BasicEventWrapper;
import galileo.event.EventWrapper;
import galileo.graph.SummaryWrapper;
import galileo.net.ClientMessageRouter;
import galileo.net.GalileoMessage;
import galileo.net.MessageListener;
import galileo.net.NetworkDestination;

public class VisualizationQueryLatencyRspTest implements MessageListener {
	
	private static GalileoEventMap eventMap = new GalileoEventMap();
	private static EventWrapper wrapper = new BasicEventWrapper(eventMap);
	
	private static boolean cachingOn = true;
	
	
	public static List<VisualizationRequest> createRandomVisualizationRequest(String polygonSize, int sl, int tl) {
		
		List<VisualizationRequest> reqs = new ArrayList<VisualizationRequest>();
		
		float startLat = 15f;
		float endLat = 48f;
		
		float startLong = -130.0f;
		float endLong = -69.0f;
		
		
		// Country Wide
		float latLength = 16f;
		float longLength = 32f;
		
		if(polygonSize.equalsIgnoreCase("country")) {
			latLength = 16f;
			longLength = 32f;
			
		} else if(polygonSize.equalsIgnoreCase("state")) {
			latLength = 4f;
			longLength = 8f;
			
		} else if(polygonSize.equalsIgnoreCase("county")) {
			latLength = 0.5f;
			longLength = 1.0f;
			
		} else if(polygonSize.equalsIgnoreCase("city")) {
			latLength = 0.2f;
			longLength = 0.4f;
		}
		
		float lat1 = (float)ThreadLocalRandom.current().nextDouble(startLat, endLat - latLength);
		
		while(lat1+latLength > endLat) {
			lat1 = (float)ThreadLocalRandom.current().nextDouble(startLat, endLat - latLength);
		}
		
		float lon1 = (float)ThreadLocalRandom.current().nextDouble(startLong, endLong - longLength);
		
		while(lon1+longLength > endLong) {
			lon1 = (float)ThreadLocalRandom.current().nextDouble(startLong, endLong - longLength);
		}
		
		reqs.addAll(createVisualizationRequest(polygonSize, sl, tl, lat1, lon1));
		
	
		return reqs;
	}
	
	
	
	public static List<VisualizationRequest> createVisualizationRequest(String polygonSize, int sl, int tl, float Olat1, float Olon1) {
		
		
		List<VisualizationRequest> reqs = new ArrayList<VisualizationRequest>();
		
		// Country Wide
		float latLength = 16f;
		float longLength = 32f;
		
		if(polygonSize.equalsIgnoreCase("country")) {
			latLength = 16f;
			longLength = 32f;
			
		} else if(polygonSize.equalsIgnoreCase("state")) {
			latLength = 4f;
			longLength = 8f;
			
		} else if(polygonSize.equalsIgnoreCase("county")) {
			latLength = 0.5f;
			longLength = 1.0f;
			
		} else if(polygonSize.equalsIgnoreCase("city")) {
			latLength = 0.2f;
			longLength = 0.4f;
		}
		
		// THE COUNTRY BOUNDS
		float lat1 = Olat1;
		float lon1 = Olon1;
		
		float Olat2 = Olat1+latLength;
		float Olon2 = Olon1+longLength;
		
		// lat1,lat2,lon1,lon2 are the current bounds
		
		float lat2 = Olat2;
		float lon2 = Olon2;
		
		String[] levels = {"country", "country", "state", "county", "city"};

		// THE SUMMARIES WE ARE REQUESTING
		List<String> sumHints = new ArrayList<String>();
		sumHints.add("geopotential_height_lltw");
		sumHints.add("water_equiv_of_accum_snow_depth_surface");
		sumHints.add("drag_coefficient_surface");
		sumHints.add("v-component_of_wind_tropopause");
		sumHints.add("downward_short_wave_rad_flux_surface");
		sumHints.add("u-component_of_wind_maximum_wind");
		
		for(String l : levels) {
			

			if(l.equalsIgnoreCase("country")) {
				latLength = 15f;
				longLength = 30f;
				
			} else if(l.equalsIgnoreCase("state")) {
				latLength = 4f;
				longLength = 7f;
				
			} else if(l.equalsIgnoreCase("county")) {
				latLength = 0.5f;
				longLength = 1.0f;
				
			} else if(l.equalsIgnoreCase("city")) {
				latLength = 0.2f;
				longLength = 0.4f;
			}
			
			
			if(!l.equals("country")) {
				
				lat1 = (float)ThreadLocalRandom.current().nextDouble(Olat1, Olat2);
				
				while(lat1+latLength > Olat2) {
					lat1 = (float)ThreadLocalRandom.current().nextDouble(Olat1, Olat2);
				}
				
				lon1 = (float)ThreadLocalRandom.current().nextDouble(Olon1, Olon2);
				
				while(lon1+longLength > Olon2) {
					lon1 = (float)ThreadLocalRandom.current().nextDouble(Olon1, Olon2);
				}
				
				lat2 = lat1+latLength;
				lon2 = lon1+longLength;
				
			} else {
				
				lat1 = Olat1;
				lon1 = Olon1;
				
				lat2 = Olat2;
				lon2 = Olon2;
				
				
			}
			
			//System.out.println("******************************"+(i+1));
			List<Coordinates> cl = new ArrayList<Coordinates>();
			
			VisualizationRequest vr = new VisualizationRequest(); 
			
			vr.setCachingOn(cachingOn);
			//System.out.println("CACHING: "+cachingOn);
			vr.setFsName("namfs");
			
			Coordinates c1 = new Coordinates(lat2, lon1);
			Coordinates c2 = new Coordinates(lat2, lon2);
			Coordinates c3 = new Coordinates(lat1, lon2);
			Coordinates c4 = new Coordinates(lat1, lon1);
			//Coordinates c5 = new Coordinates(36.78f, -107.64f);
			
			cl.add(c1); cl.add(c2); cl.add(c3); cl.add(c4);
			
			//System.out.println("COORDINATES: "+cl);
			
			vr.setPolygon(cl);
			vr.setTime("2013-02-02-xx");
			
			vr.setSpatialResolution(sl);
			vr.setTemporalResolution(tl);
			
			
			vr.setReqFeatures(sumHints);
			
			reqs.add(vr);
			
		}
		
		
		
		/*System.out.println("COORDINATES: "+cl);*/
		
		
		/*vr.setSpatialResolution(2);
		vr.setTemporalResolution(2);*/
		
		
		return reqs;
	}
	
	@Override
	public void onConnect(NetworkDestination endpoint) {
	}

	@Override
	public void onDisconnect(NetworkDestination endpoint) {
	}

	@Override
	public void onMessage(GalileoMessage message) {
		try {
			VisualizationResponse response = (VisualizationResponse) wrapper.unwrap(message);
			
			List<String> keys = response.getKeys();
			List<SummaryWrapper> summaries = response.getSummaries();
			
			System.out.println("RSP SIZE: "+summaries.size());
			/*
			for(int i=0; i < summaries.size(); i++) {
				System.out.println(keys.get(i));
				System.out.println(summaries.get(i));
				System.out.println("=================");
			}
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	

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
		
		try {
			
			ClientMessageRouter messageRouter = new ClientMessageRouter();
			
			VisualizationQueryLatencyRspTest vqt = new VisualizationQueryLatencyRspTest();
			
			messageRouter.addListener(vqt);
			
			
			List<VisualizationRequest> reqs = createRandomVisualizationRequest("country", 6, 3);
			
			int i=0;
			
			for(VisualizationRequest vr : reqs) {
				//gc.visualize(vr);
				System.out.println("POLYGON: " + vr.getPolygon());
				messageRouter.sendMessage(gc.server, EventPublisher.wrapEvent(vr));
				if(i == 0)
					Thread.sleep(10*1000);
				else 
					Thread.sleep(3*1000);
				i++;
			}
			
		} finally {
			
			Thread.sleep(10*1000);
			gc.disconnect();
		}
	}
	
	
	private static void processSoloRequest(GalileoConnector gc, String qSize) throws Exception {
		
		try {
			
			for(int j = 0; j< 10; j++) {
			
				ClientMessageRouter messageRouter = new ClientMessageRouter();
				
				VisualizationQueryLatencyRspTest vqt = new VisualizationQueryLatencyRspTest();
				
				messageRouter.addListener(vqt);
				
				
				List<VisualizationRequest> reqs = createRandomVisualizationRequest("country", 6, 3);
				
				int indx = 1;
				
				if(qSize.equals("country"))
					indx = 1;
				else if(qSize.equals("state"))
					indx = 2;
				else if(qSize.equals("county"))
					indx = 3;
				else if(qSize.equals("city"))
					indx = 4;
				
				VisualizationRequest vr = reqs.get(indx);
				
				System.out.println("POLYGON: " + vr.getPolygon() +" CACHING: "+vr.isCachingOn());
				messageRouter.sendMessage(gc.server, EventPublisher.wrapEvent(vr));
					
				Thread.sleep(6*1000);
				
				System.out.println("WIPING OUT....");
				WipeCacheRequest wr = new WipeCacheRequest("namfs");
				messageRouter.sendMessage(gc.server, EventPublisher.wrapEvent(wr));
				Thread.sleep(7*1000);
				System.out.println("CACHE WIPED OUT.");
				
				
					
				
			}
			
		} finally {
			
			Thread.sleep(10*1000);
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
		args[0] = "lattice-121.cs.colostate.edu";
		args[1] = "5634";
		
		// NO STASH
		cachingOn = false;
		boolean cachingWipe = true;
		
		cachingWipe = false;
		
		if (args.length != 2) {
			System.out.println("Usage: VisualizationQueryTest [galileo-hostname] [galileo-port-number]");
			System.exit(0);
		} else {
			if(cachingWipe) {
				try {
					GalileoConnector gc = new GalileoConnector(args[0], Integer.parseInt(args[1]));
					ClientMessageRouter messageRouter = new ClientMessageRouter();
					WipeCacheRequest wr = new WipeCacheRequest("namfs");
					messageRouter.sendMessage(gc.server, EventPublisher.wrapEvent(wr));
					Thread.sleep(7*1000);
					System.out.println("CACHE WIPED OUT.");
				} catch(Exception e) {}
				
			} else {
				try {
					GalileoConnector gc = new GalileoConnector(args[0], Integer.parseInt(args[1]));
					System.out.println(args[0] + "," + Integer.parseInt(args[1]));
					
					//processRequest(gc);
					
					processSoloRequest(gc, "city");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Visualization Finished");
		System.exit(0);
	}
	// [END Main]
}
