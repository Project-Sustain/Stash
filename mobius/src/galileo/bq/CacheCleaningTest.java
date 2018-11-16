package galileo.bq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import galileo.graph.SpatiotemporalHierarchicalCache;
import galileo.util.GeoHash;

public class CacheCleaningTest {

	public Random random = new Random();
	public final static char[] charMap = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	
	public static void main(String[] args) {
		
		SpatiotemporalHierarchicalCache shc = new SpatiotemporalHierarchicalCache();
		CacheCleaningTest cct = new CacheCleaningTest();
		cct.populateCacheWithRandomStuff(shc, 200, 6, 4);

	}
	
	public int getCacheLevel(int spatialResolution, int temporalResolution, int totalSpatialLevels) {
		
		int levelNum = (temporalResolution-1)*totalSpatialLevels + (spatialResolution-1);
		
		return levelNum;
	}
	
	
	public void populateCacheWithRandomStuff(SpatiotemporalHierarchicalCache shc, int totalElements, int spatialLevels, int temporalLevels) {
		
		Map<Integer, List<String>> randomKeys = generateRandomKeys(totalElements, spatialLevels, temporalLevels);
		
		System.out.println(randomKeys);
		
		for(Integer i: randomKeys.keySet()) {
			System.out.println(randomKeys.get(i).size());
		}
		
	}


	private Map<Integer, List<String>> generateRandomKeys(int totalElements, int spatialLevels, int temporalLevels) {
		
		Map<Integer, List<String>> randomKeys = new HashMap<Integer, List<String>>();
		int totalLevels = spatialLevels*temporalLevels;
		
		float[] randomSharesAmongLevels = randomSharesAmongLevels(totalLevels);
		
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
				int totalElementsPossible = (int)(Math.pow(32,cSpatial) * multiplier); 
				
				if(numElementsInLevel > totalElementsPossible) {
					balance+= (numElementsInLevel - totalElementsPossible);
				}
				
				numElementsInLevel = totalElementsPossible;
				int totalElementsFound = 0;
				
				while(totalElementsFound < numElementsInLevel) {
					
					String randGeohash = getRandomGeohash(cSpatial);
					String randomDate = getRandomDate(cTemporal);
					
					String randomKey = randomDate+"$$"+randGeohash;
					
					if(!keysForThisLevel.contains(randomKey)) {
						keysForThisLevel.add(randomKey);
						totalElementsFound++;
					}
				}
				randomKeys.put(levelId, keysForThisLevel);
				
				currentLevelNum++;
			}
			
		}
		
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
	
	

	public float[] randomSharesAmongLevels(int totalLevels) {
		

	    int[] numForLevels = new int[totalLevels];
	    float[] shareForLevels = new float[totalLevels];
	    
	    float total = 0f;
	    for (int i = 0; i < totalLevels; i++) {
	        numForLevels[i] = random.nextInt(100);
	        total+= numForLevels[i];
	    }
	    
	    for (int i = 0; i < totalLevels; i++) {
	    	shareForLevels[i] = numForLevels[i]/total;
	        
	    }
	    
	    return shareForLevels;
	}

}
