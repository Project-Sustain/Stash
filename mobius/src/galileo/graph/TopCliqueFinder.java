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
	private static final int N = 4096;
	
	public static Map<String, CliqueContainer> getTopKCliques(SpatiotemporalHierarchicalCache searchCache, int spatialPartitioning) {
		
		long currentTime = System.currentTimeMillis();
		
		synchronized(searchCache) {
			
			return scanCacheForTopN(searchCache, spatialPartitioning, currentTime);
			
		}
		
		
	}
	
	public static Map<String, CliqueContainer> scanCacheForTopN(SpatiotemporalHierarchicalCache stCache, int spatialPartitioning, long currentTime) {
		
		// All the cache cells, with their freshness values
		Map<String, CacheCell> keyValues = new HashMap<String, CacheCell>();
		
		SparseSpatiotemporalMatrix[] cacheLevels = stCache.getCacheLevels();
		
		for(int i = 0; i < cacheLevels.length; i++) {
			
			SparseSpatiotemporalMatrix currentLevel = cacheLevels[i];
			
			if(currentLevel != null) {
				HashMap<String, CacheCell> currentRooms = currentLevel.getCells();
				
				for(String key : currentRooms.keySet()) {
					
					CacheCell cacheCell = currentRooms.get(key);
					
					keyValues.put(i+"@"+key, cacheCell);
					
				}
			}
			
		}
		
		// GET THE TOP N HOTTEST TILES
		Map<Integer, List<CacheCell>> topNTiles = getTopNTiles(keyValues, N, currentTime);
		
		// CREATE CLIQUES OUT OF TOP N TILES
		// CLIQUES ARE PARTITIONED BY THE SPATIAL PARTITIONING OF THE FILESYSTEM
		Map<String, CliqueContainer> allCliques = calculateTopNCliquesFromTiles(topNTiles, currentTime, spatialPartitioning);
		
		return allCliques;
	}
	
	
	private static Map<String, CliqueContainer> calculateTopNCliquesFromTiles(Map<Integer, List<CacheCell>> topNTiles, long currentTime, int spatialPartitioning) {
		
		Map<String, CliqueContainer> allCliques = new HashMap<>();
		
		for(int level : topNTiles.keySet()) {
			
			List<CacheCell> tilesAtThisLevel = topNTiles.get(level);
			
			Map<String, List<CacheCell>> groupedTiles = new HashMap<String, List<CacheCell>>();
			
			for(CacheCell cc : tilesAtThisLevel) {
				
				String geohash = cc.getSpatialInfo();
				
				String geohashKey = geohash.substring(0, spatialPartitioning);
				
				if(!groupedTiles.containsKey(geohashKey)) {
					List<CacheCell> tiles = new ArrayList<CacheCell>();
					tiles.add(cc);
					groupedTiles.put(geohashKey, tiles);
					
				} else {
					groupedTiles.get(geohashKey).add(cc);
				}
			}
			
			for(String geohashKey: groupedTiles.keySet()) {
				
				List<CacheCell> tiles = groupedTiles.get(geohashKey);
				
				if(tiles != null && tiles.size() > 0) {
					CliqueContainer cCon;
					if(allCliques.get(geohashKey) != null) {
						
						cCon = allCliques.get(geohashKey);
					} else {
						cCon = new CliqueContainer(geohashKey);
						allCliques.put(geohashKey, cCon);
					}
					
					cCon.addCells(level, tiles);
					
				}
				 
			}
			
			
		}
		
		
		
		return allCliques;
	}




	/**
	 * FIGURING OUT WHICH CACHE ENTRIES ARE THE HOTTEST TOP N
	 * 
	 * @author sapmitra
	 * key is level@cache_key
	 * @param keyValues
	 * @param entriesAllowed - The number of entries allowed in top N
	 * @param currentTime 
	 */
	public static Map<Integer, List<CacheCell>> getTopNTiles(Map<String, CacheCell> keyValues, int entriesAllowed, long currentTime) {
		
		// SORTING BASED ON VALUES
		Set<Entry<String, CacheCell>> set = keyValues.entrySet();
        List<Entry<String, CacheCell>> list = new ArrayList<Entry<String, CacheCell>>(set);
        
        Collections.sort( list, new Comparator<Map.Entry<String, CacheCell>>()
        {
            public int compare( Map.Entry<String, CacheCell> o1, Map.Entry<String, CacheCell> o2 )
            {
            	Float f1 = o1.getValue().getCorrectedFreshness(currentTime);
            	Float f2 = o2.getValue().getCorrectedFreshness(currentTime);
                return (f2.compareTo(f1));
            }
        } );
        
        int i = 0;
        
        Map<Integer, List<CacheCell>> topNTiles = new HashMap<Integer, List<CacheCell>>();
        
        // GROUPING TOP N TILES BY THEIR LEVELS
        for(Map.Entry<String, CacheCell> entry : list){
        	
        	if(i < entriesAllowed) {
        		
        		String key = entry.getKey();
        		
        		CacheCell val = entry.getValue();
        		
        		String[] tokens = key.split("@");
        		
        		int level = Integer.valueOf(tokens[0]);
        		String stKey = tokens[1];
        		
        		List<CacheCell> cells = topNTiles.get(level);
        		
        		if(cells == null) {
        			cells = new ArrayList<CacheCell>();
        			topNTiles.put(level, cells);
        		}
        		
        		cells.add(val);
        		
        	}
        	
        	i++;
        }
        
        //System.out.println(entriesToRemove);
		
		return topNTiles;
	}

}
