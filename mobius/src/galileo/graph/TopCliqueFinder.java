package galileo.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class TopCliqueFinder {
	
	// THE NUMBER OF TOP TILES TO FILTER OUT OF THE CACHE
	// THESE ARE TO BE USED TO FIND THE TOP CLIQUES
	private static final int N = 2014;
	
	public static void getTopKCliques(SpatiotemporalHierarchicalCache searchCache, int cliqueDepth) {
		
		synchronized(searchCache) {
			
		}
		
		
	}
	
	
	
	public static void getCandidateCliques() {
		
	}
	
	
	public void scanCacheForTopN(SpatiotemporalHierarchicalCache stCache) {
		
		// All the cacheKeys, with their freshness values
		Map<String, Float> keyValues = new HashMap<String, Float>();
		
		SparseSpatiotemporalMatrix[] cacheLevels = stCache.getCacheLevels();
		
		for(int i = 0; i < cacheLevels.length; i++) {
			
			SparseSpatiotemporalMatrix currentLevel = cacheLevels[i];
			
			if(currentLevel != null) {
				HashMap<String, CacheCell> currentRooms = currentLevel.getCells();
				
				for(String key : currentRooms.keySet()) {
					
					CacheCell cacheCell = currentRooms.get(key);
					float fr = cacheCell.getCorrectedFreshness();
					
					keyValues.put(i+"@"+key, fr);
					
				}
			}
			
		}
		
		Map<Integer, List<String>> topNTiles = getTopNTiles(keyValues, N);
		
		// CREATE POSSIBLE CLIQUES OUT OF TOP N TILES
		
		
		
		stCache.setTotalRooms(total_allowed);
		
	
		
	}
	
	
	
	
	public static void calculateTopNCliquesFromTiles(Map<Integer, List<String>> topNTilesGrouped) {
		
		
		
		
	}
	
	
	/**
	 * FIGURING OUT WHICH CACHE ENTRIES ARE THE HOTTEST TOP N
	 * 
	 * @author sapmitra
	 * @param cacheEntries
	 * @param entriesAllowed - The number of entries allowed in top N
	 */
	public static Map<Integer, List<String>> getTopNTiles(Map<String, Float> cacheEntries, int entriesAllowed) {
		
		// SORTING BASED ON VALUES
		Set<Entry<String, Float>> set = cacheEntries.entrySet();
        List<Entry<String, Float>> list = new ArrayList<Entry<String, Float>>(set);
        Collections.sort( list, new Comparator<Map.Entry<String, Float>>()
        {
            public int compare( Map.Entry<String, Float> o1, Map.Entry<String, Float> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );
        
        int i=0;
        
        Map<Integer, List<String>> topNTiles = new HashMap<Integer, List<String>>();
        
        for(Map.Entry<String, Float> entry:list){
        	
        	if(i < entriesAllowed) {
        		String key = entry.getKey();
        		
        		String[] tokens = key.split("@");
        		
        		int level = Integer.valueOf(tokens[0]);
        		String stKey = tokens[1];
        		
        		List<String> keys = topNTiles.get(level);
        		
        		if(keys == null) {
        			keys = new ArrayList<String>();
        			topNTiles.put(level, keys);
        		}
        		
        		keys.add(stKey);
        		
        	}
        	
        	i++;
        }
        
        //System.out.println(entriesToRemove);
		
		return topNTiles;
	}

}
