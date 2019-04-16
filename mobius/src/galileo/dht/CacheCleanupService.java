package galileo.dht;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

import galileo.fs.GeospatialFileSystem;
import galileo.graph.CacheCell;
import galileo.graph.SparseSpatiotemporalMatrix;
import galileo.graph.SpatiotemporalHierarchicalCache;

/**
 * 
 * @author sapmitra
 *
 *	HANDLES ALL BLOCKS WITH THE SAME BLOCK KEY : TIME$$SPACE
 */

public class CacheCleanupService implements Runnable{
	
	private GeospatialFileSystem fs ;
	private List<String> peList;
	private SpatiotemporalHierarchicalCache stCache;
	private int total_allowed;
	private static final Logger logger = Logger.getLogger("galileo");
	
	public CacheCleanupService(GeospatialFileSystem fs, List<String> peList, SpatiotemporalHierarchicalCache stCache, int total_allowed) {
		
		this.fs = fs;
		this.peList = peList;
		this.stCache = stCache;
		this.total_allowed = total_allowed;
	}

	@Override
	public void run() {
		
		logger.info("RIKI: CACHE CLEANUP STARTED");
		
		if(!(fs.cleanUpInitiated.get())) {
			
			fs.cleanUpInitiated.set(true);
			
			logger.info("RIKI: BEFORE THIS");
			while(checkPE()) {
				// blocking
			}
			
			logger.info("RIKI: AFTER THIS");
			pruneCache();
			
			fs.cleanUpInitiated.set(false);
			
		}
		
		logger.info("RIKI: CACHE CLEANUP ENDED. ROOMS LEFT AFTER CLEANING: "+ stCache.getTotalRooms());
		
		
	}
	
	/**
	 * RETURNS TRUE IF >=1 PROCESSES ARE IN ENTERED STATE
	 * @author sapmitra
	 * @return
	 */
	public boolean checkPE() {
		
		synchronized(peList) {
			if(peList.size() > 0)
				return true;
			return false;
		}
		
	}
	
	
	/**
	 * Handles pruning for a single FS
	 * @author sapmitra
	 * @param targetFS
	 */
	
	public void pruneCache() {
		
		long currentTime = System.currentTimeMillis();
		synchronized(stCache) {
			
			Map<String, Float> keyValues = new HashMap<String, Float>();
			
			SparseSpatiotemporalMatrix[] cacheLevels = stCache.getCacheLevels();
			
			for(int i=0; i< cacheLevels.length; i++) {
				
				SparseSpatiotemporalMatrix currentLevel = cacheLevels[i];
				
				if(currentLevel != null) {
					HashMap<String, CacheCell> currentFloor = currentLevel.getCells();
					
					for(String key : currentFloor.keySet()) {
						
						CacheCell cacheCell = currentFloor.get(key);
						float fr = cacheCell.getCorrectedFreshness(currentTime);
						
						keyValues.put(i+"@"+key, fr);
						
					}
				}
				
			}
			
			Map<Integer, List<String>> elementsToTrim = getElementsToTrim(keyValues, total_allowed);
			
			logger.info("RIKI: TOTAL ROOMS: "+stCache.getTotalRooms()+" TO REMOVE: "+ elementsToTrim.size());
			
			// REMOVING UNWANTED STALE ENTRIES
			for(int i=0; i< cacheLevels.length; i++) {
				
				if(elementsToTrim.get(i) != null) {
					
					SparseSpatiotemporalMatrix currentLevel = cacheLevels[i];
					
					if(currentLevel != null) {
						HashMap<String, CacheCell> currentFloor = currentLevel.getCells();
						
						for(String key : elementsToTrim.get(i)) {
							
							currentFloor.remove(key);
							
						}
					}
					
				}
				
			}
			
			stCache.setTotalRooms(total_allowed);
			
		}
		
	}
	
	/**
	 * FIGURING OUT WHICH CACHE ENTRIES TO REMOVE BASED ON FRESHNES VALUES
	 * 
	 * @author sapmitra
	 * @param cacheEntries
	 * @param entriesAllowed - The number of entries allowed in the cache
	 */
	public Map<Integer, List<String>> getElementsToTrim(Map<String, Float> cacheEntries, int entriesAllowed) {
		
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
        
        Map<Integer, List<String>> entriesToRemove = new HashMap<Integer, List<String>>();
        
        for(Map.Entry<String, Float> entry:list){
        	
        	i++;
        	
        	if(i > entriesAllowed) {
        		String key = entry.getKey();
        		
        		String[] tokens = key.split("@");
        		
        		int level = Integer.valueOf(tokens[0]);
        		String stKey = tokens[1];
        		
        		List<String> keys = entriesToRemove.get(level);
        		
        		if(keys == null) {
        			keys = new ArrayList<String>();
        			entriesToRemove.put(level, keys);
        		}
        		
        		keys.add(stKey);
        		
        	}
        }
        
        //System.out.println(entriesToRemove);
		
		return entriesToRemove;
	}


}
