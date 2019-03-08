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
		long currentTime = System.currentTimeMillis();
		synchronized(searchCache) {
			
			scanCacheForTopN(searchCache, cliqueDepth, currentTime);
			
		}
		
		
	}
	
	
	
	
	public static void scanCacheForTopN(SpatiotemporalHierarchicalCache stCache, int cliqueDepth, long currentTime) {
		
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
		
		// CREATE POSSIBLE CLIQUES OUT OF TOP N TILES
		Map<Integer, List<String>> centralTilesForCliques = calculateTopNCliquesFromTiles(topNTiles, cliqueDepth, stCache, currentTime);
		
		
	}
	
	
	
	/**
	 * GET THE TOP N WEIGHTED ACTUAL CLIQUES
	 * @author sapmitra
	 * @param topNTilesGrouped
	 * @param cliqueDepth
	 * @param stCache
	 * @param currentTime 
	 * @return
	 */
	public static Map<Integer, List<String>> calculateTopNCliquesFromTiles(Map<Integer, List<CacheCell>> topNTilesGrouped, int cliqueDepth, 
			SpatiotemporalHierarchicalCache stCache, long currentTime) {
		
		Map<String, Float> candidates = getCandidateCliques(topNTilesGrouped, cliqueDepth, stCache, currentTime);
		// FOR EACH TILE, GET PARENT AND CHILDREN
		return null;
	}
	
	/**
	 * RETURNS CLIQUE'S CHILD NODE STRING -> WEIGHT
	 * @author sapmitra
	 * @param topNTilesGrouped
	 * @param cliqueDepth - for now, it is 1 - one level up, one level down
	 * @param stCache 
	 * @param currentTime 
	 * @return
	 */
	public static Map<String, Float> getCandidateCliques(Map<Integer, List<CacheCell>> topNTilesGrouped, int cliqueDepth, 
			SpatiotemporalHierarchicalCache stCache, long currentTime) {
		
		
		Map<String, Float> cliques = new HashMap<String, Float>();
		
		// FOR EACH TILE
		
		// IMAGINE IT AS A CHILD, A GRAND-CHILD AND A PARENT
		for(Map.Entry<Integer, List<CacheCell>> entry : topNTilesGrouped.entrySet()){
			
			int level = entry.getKey();
			
			// RETURNED AS SPATIAL, TEMPORAL, SPATIOTEMPORAL
			int[] parentLevels = stCache.getParentLevels(level);
			int[] childrenLevels = stCache.getChildrenLevels(level);
			
			
			
			// POPULATE CANDIDATE CLIQUES FOR EACH CELL INTO A LIST OF CANDIDATES
			getCliqueCombinationsFromCell(cell, cacheCellLevel, parentLevels, childrenLevels, allCandidateCliques, topNTilesGrouped);
			
		}
		
		
		return null;
	}
	
	
	/**
	 * Given a cell, check the 3 possible cliques that is possible
	 * @author sapmitra
	 * @return
	 */
	public static void getCliqueCombinationsFromCell(CacheCell cell, int cacheCellLevel, int[] parentLevels, int[] childrenLevels,
			Map<String, Float> allCandidateCliques, Map<Integer, List<CacheCell>> topNTilesGrouped, long currentTime) {
		
		String cellKey = cell.getCellKey();
		String cellSpatialInfo = cell.getSpatialInfo();
		String cellTemporalInfo = cell.getTemporalInfo();
		
		String spatialParent = cell.getSpatialParent();
		String temporalParent = cell.getTemporalParent();
		
		List<String> spatialChildren = cell.getSpatialChildren();
		List<String> temporalChildren = cell.getTemporalChildren();
		
		double currentCellWeight = cell.getCorrectedFreshness(currentTime);
		
		/******IMAGINE THIS CELL IS THE BASE GENERATION - CASE 1*******/
		
		givenBaseTileEvaluateCliqueWeight(cellKey, allCandidateCliques, topNTilesGrouped, parentLevels, childrenLevels, currentCellWeight, 
				spatialParent, temporalParent, cellSpatialInfo, cellTemporalInfo, spatialChildren, temporalChildren, currentTime);
		
		
		
		/******IMAGINE THIS CELL IS THE PARENT GENERATION - CASE 2*******/
		
		String cellSpatialParent = spatialParent+"$$"+cellTemporalInfo;
		String cellTemporalParent = cellSpatialInfo+"$$"+temporalParent;
		
		
		
		
		
		
		// IMAGINE IF THIS CELL WAS THE PARENT
		
		
	}
	
	
	/**
	 * Given a cache cell, calculate the corresponding basic clique - 1 parent up, 1 child down
	 * @author sapmitra
	 * @param cell
	 * @param allCandidateCliques
	 * @param topNTilesGrouped
	 * @param parentLevels
	 * @param childrenLevels	 
	 * @param currentTime */
	public static void givenBaseTileEvaluateCliqueWeight(String cellKey, Map<String, Float> allCandidateCliques, Map<Integer, List<CacheCell>> topNTilesGrouped,
			int[] parentLevels, int[] childrenLevels, double currentCellWeight, String spatialParent, String temporalParent,
			String cellSpatialInfo, String cellTemporalInfo, List<String> spatialChildren, List<String> temporalChildren, long currentTime) {
		
		if(!allCandidateCliques.containsKey(cellKey)) {
			
			float cliqueWeight = 0f;
			
			cliqueWeight += currentCellWeight;
			
			// FINDNG SPATIAL PARENT
			if(spatialParent!= null && spatialParent.length() > 0) {
				
				String spatialLevelParent = spatialParent+"$$"+cellTemporalInfo;
				
				// Checking if the spatialParent is in the hot list
				
				int sParentLvl = parentLevels[0];
				
				// IF THIS HAS A SPATIAL PARENT LEVEL AND THERE ARE EXISTING HOT TILES IN THAT PARENT LEVEL
				if(sParentLvl >=0 && topNTilesGrouped.get(sParentLvl)!=null) {
					cliqueWeight += CacheCell.getExistingCell(topNTilesGrouped.get(sParentLvl), spatialLevelParent, currentTime);
				}
				
			}
			
			// FINDING TEMPORAL PARENT
			if(temporalParent!= null && temporalParent.length() > 0) {
				
				String temporalLevelParent = cellSpatialInfo+"$$"+temporalParent;
				
				// Checking if the spatialParent is in the hot list
				
				int tParentLvl = parentLevels[1];
				
				// IF THIS HAS A SPATIAL PARENT LEVEL AND THERE ARE EXISTING HOT TILES IN THAT PARENT LEVEL
				if(tParentLvl >=0 && topNTilesGrouped.get(tParentLvl)!=null) {
					cliqueWeight += CacheCell.getExistingCell(topNTilesGrouped.get(tParentLvl), temporalLevelParent, currentTime);
				}
				
			}
			
			
			int sChildrenLvl = childrenLevels[0];
			for(String sc : spatialChildren) {
				
				String childKey = sc+"$$"+cellTemporalInfo;
				
				cliqueWeight += CacheCell.getExistingCell(topNTilesGrouped.get(sChildrenLvl), childKey);
				
			}
			
			int tChildrenLvl = childrenLevels[1];
			for(String tc : temporalChildren) {
				
				String childKey = cellSpatialInfo+"$$"+tc;
				
				cliqueWeight += CacheCell.getExistingCell(topNTilesGrouped.get(tChildrenLvl), childKey);
				
			}
			
			if(cliqueWeight > 0)
				allCandidateCliques.put(cellKey, cliqueWeight);
			
			
		}
		
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
