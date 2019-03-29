package galileo.bq;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import galileo.comm.TemporalType;
import galileo.dataset.Coordinates;
import galileo.dataset.SpatialRange;
import galileo.dht.CacheCleanupService;
import galileo.graph.SpatiotemporalHierarchicalCache;
import galileo.graph.SummaryStatistics;
import galileo.util.GeoHash;

public class CacheCleaningTest {

	public Random random = new Random();
	public final static char[] charMap = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	
	public static void main(String[] args) throws ParseException {
		
		SpatiotemporalHierarchicalCache shc = new SpatiotemporalHierarchicalCache();
		CacheCleaningTest cct = new CacheCleaningTest();
		
		int totalElementsInCache = 200000;
		int finalReducedElements = (int)(totalElementsInCache*0.75);
		
		cct.populateCacheWithRandomStuff(shc, totalElementsInCache, 6, 4);
		
		CacheCleanupService ccs = new CacheCleanupService(null, null, shc, finalReducedElements);
		
		System.out.println("STARTING PRUNING");
		long st = System.currentTimeMillis();
		ccs.pruneCache();
		long diff = System.currentTimeMillis()-st;
		System.out.println("TIME "+diff);

		
	}
	
	
	public int getCacheLevel(int spatialResolution, int temporalResolution, int totalSpatialLevels) {
		
		int levelNum = (temporalResolution-1)*totalSpatialLevels + (spatialResolution-1);
		
		return levelNum;
	}
	
	public TemporalType getTemporalType(int level) {
		if(level == 1) {
			return TemporalType.YEAR;
		} else if(level == 2) {
			return TemporalType.MONTH;
		} else if(level == 3) {
			return TemporalType.DAY_OF_MONTH;
		} else {
			return TemporalType.HOUR_OF_DAY;
		}
	}
	
	
	public void populateCacheWithRandomStuff(SpatiotemporalHierarchicalCache shc, int totalElements, int spatialLevels, int temporalLevels) throws ParseException {
		
		
		Map<Integer, List<String>> randomKeys = generateRandomKeys(totalElements, spatialLevels, temporalLevels);
		
		//System.out.println(randomKeys);
		
		for(Integer i: randomKeys.keySet()) {
			//System.out.println("FOR "+i);
			for(String key : randomKeys.get(i)) {
				SummaryStatistics[] summaries = new SummaryStatistics[4];
				
				for(int j=0; j< 4; j++) {
					SummaryStatistics ss = new SummaryStatistics();
					summaries[j] = ss;
				}
				
				String components[] = key.split("\\$\\$");
				
				SpatialRange range1 = GeoHash.decodeHash(components[1]);
				
				Coordinates c1 = new Coordinates(range1.getLowerBoundForLatitude(), range1.getLowerBoundForLongitude());
				Coordinates c2 = new Coordinates(range1.getUpperBoundForLatitude(), range1.getLowerBoundForLongitude());
				Coordinates c3 = new Coordinates(range1.getUpperBoundForLatitude(), range1.getUpperBoundForLongitude());
				Coordinates c4 = new Coordinates(range1.getLowerBoundForLatitude(), range1.getUpperBoundForLongitude());
				
				ArrayList<Coordinates> cs1 = new ArrayList<Coordinates>();
				cs1.add(c1);cs1.add(c2);cs1.add(c3);cs1.add(c4);
				
				int temporalResolution = (i / spatialLevels)+1;
				
				TemporalType tt = getTemporalType(temporalResolution);
				long qt1 = GeoHash.getStartTimeStamp(components[0], tt);
				long qt2 = GeoHash.getEndTimeStamp(components[0], tt);
				
				
				
				//shc.addCell(summaries, key, i, cs1, qt1, qt2, "ee", 5l);
			}
			
			//System.out.println(randomKeys.get(i).size());
		}
		
		
	}
	


	private Map<Integer, List<String>> generateRandomKeys(int totalElements, int spatialLevels, int temporalLevels) {
		
		Map<Integer, List<String>> randomKeys = new HashMap<Integer, List<String>>();
		
		int totalLevels = spatialLevels*temporalLevels;
		
		float[] randomSharesAmongLevels = randomSharesAmongLevels(totalLevels, spatialLevels);
		
		int currentLevelNum = 0;
		
		int balance = 0;
		
		for(int cSpatial = 1; cSpatial <= spatialLevels; cSpatial++) {
			
			for(int cTemporal = 1; cTemporal <= temporalLevels; cTemporal++) {
				
				List<String> keysForThisLevel = new ArrayList<String>();
				
				int levelId = getCacheLevel(cSpatial, cTemporal, spatialLevels);
				
				int numElementsInLevel = (int) (totalElements*randomSharesAmongLevels[currentLevelNum]) + balance;
				int multiplier = 1;
				
				if(cTemporal == 4) {
					multiplier = 20*12*28*24;
				} else if(cTemporal == 3) {
					multiplier = 20*12*28;
				} else if(cTemporal == 2) {
					multiplier = 20*12;
				} else if(cTemporal == 1) {
					multiplier = 20;
				}
				int totalElementsPossible = (int)(Math.pow(32,cSpatial) * (multiplier-1)); 
				
				if(numElementsInLevel > totalElementsPossible) {
					balance+= (numElementsInLevel - totalElementsPossible);
					numElementsInLevel = totalElementsPossible;
				}
				
				System.out.println("FOR "+currentLevelNum+" "+totalElementsPossible+" "+numElementsInLevel);
				int totalElementsFound = 0;
				
				while(totalElementsFound < numElementsInLevel) {
					
					String randGeohash = getRandomGeohash(cSpatial);
					String randomDate = getRandomDate(cTemporal);
					
					String randomKey = randomDate+"$$"+randGeohash;
					
					if(!keysForThisLevel.contains(randomKey)) {
						keysForThisLevel.add(randomKey);
						totalElementsFound++;
					}
					
					//if(totalElements%10 == 0)
					//System.out.println(totalElementsFound);
				}
				randomKeys.put(levelId, keysForThisLevel);
				
				currentLevelNum++;
			}
			
		}
		System.out.println("DONE GENERATING RANDOM KEYS");
		return randomKeys;
	}
	
	
	public String getRandomDate(int tLevel) {
		
		String timeString = "";
		
		int year = random.nextInt(18) + 2000;
		int month = random.nextInt(12)+1;
		int day = random.nextInt(28) + 1;
		int hour = random.nextInt(24);
		
		int[] elements = {year, month, day, hour};
		
		for(int i=0; i < tLevel; i++) {
			
			timeString+= elements[i]+"-";
			
		}
		
		for(int i=0; i< 4-tLevel; i++) {
			timeString+= "xx-";
		}
		
		timeString = timeString.substring(0, timeString.length() - 1);
		
		return timeString;
		
	}
	
	public String getRandomGeohash(int precision) {
		
		String geoString = "";
		for(int i=0; i < precision; i++) {
			
			int num = random.nextInt(32);
			
			geoString+=charMap[num];
			
		}
		
		return geoString;
		
	}
	
	

	public float[] randomSharesAmongLevels(int totalLevels, int totalSpatialLevels) {
		
	    int[] numForLevels = new int[totalLevels];
	    float[] shareForLevels = new float[totalLevels];
	    
	    float total = 0f;
	    
	    for (int i = 0; i < totalLevels; i++) {
	    	
	    	int multiplier = 1;
			
	    	int cSpatial = (i/totalSpatialLevels)+1;
	    	int cTemporal = (i/totalSpatialLevels)+1;
	    			
			if(cTemporal == 4) {
				multiplier = 20*12*28*24;
			} else if(cTemporal == 3) {
				multiplier = 20*12*28;
			} else if(cTemporal == 2) {
				multiplier = 20*12;
			} else if(cTemporal == 1) {
				multiplier = 20;
			}
			
			int totalElementsPossible = (int)(Math.pow(32,cSpatial) * (multiplier-1)); 
	    	
	        numForLevels[i] = totalElementsPossible;
	        total+= numForLevels[i];
	    }
	    
	    for (int i = 0; i < totalLevels; i++) {
	    	shareForLevels[i] = numForLevels[i]/total;
	        
	    }
	    
	    return shareForLevels;
	}
	
	
	public float[] randomSharesAmongLevelsBackup(int totalLevels, int totalSpatialLevels) {
		
		
	    int[] numForLevels = new int[totalLevels];
	    float[] shareForLevels = new float[totalLevels];
	    
	    float total = 0f;
	    
	    for (int i = 0; i < totalLevels; i++) {
	    	
	    	int multiplier = 1;
			
	    	int cSpatial = (i/totalSpatialLevels)+1;
	    	int cTemporal = (i/totalSpatialLevels)+1;
	    			
			if(cTemporal == 4) {
				multiplier = 20*12*28*24;
			} else if(cTemporal == 3) {
				multiplier = 20*12*28;
			} else if(cTemporal == 2) {
				multiplier = 20*12;
			} else if(cTemporal == 1) {
				multiplier = 20;
			}
			int totalElementsPossible = (int)(Math.pow(32,cSpatial) * (multiplier-1)); 
	    	
	        numForLevels[i] = random.nextInt(100);
	        total+= numForLevels[i];
	    }
	    
	    for (int i = 0; i < totalLevels; i++) {
	    	shareForLevels[i] = (int)(Math.ceil(numForLevels[i]/total));
	        
	    }
	    
	    return shareForLevels;
	}

}
