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
	
	private AtomicBoolean cleanUpInitiated ;
	private List<String> peList;
	private SpatiotemporalHierarchicalCache stCache;
	private int total_allowed;
	
	public CacheCleanupService(AtomicBoolean cleanUpInitiated, List<String> peList, SpatiotemporalHierarchicalCache stCache, int total_allowed) {
		
		this.cleanUpInitiated = cleanUpInitiated;
		this.peList = peList;
		this.stCache = stCache;
		this.total_allowed = total_allowed;
	}

	@Override
	public void run() {
		
		if(!(cleanUpInitiated.get())) {
			
			cleanUpInitiated.set(true);
			
			while(checkPE()) {
				// blocking
			}
			
			pruneCache();
			
			cleanUpInitiated.set(false);
			
		}
		
		
	}
	
	public boolean checkPE() {
		
		synchronized(peList) {
			if(peList.size() <= 0)
				return true;
			return false;
		}
		
	}
	
	
	/**
	 * Handles pruning for a single FS
	 * @author sapmitra
	 * @param targetFS
	 */
	
	private void pruneCache() {
		
		
		synchronized(stCache) {
			
			Map<String, Float> keyValues = new HashMap<String, Float>();
			
			SparseSpatiotemporalMatrix[] cacheLevels = stCache.getCacheLevels();
			
			for(int i=0; i< cacheLevels.length; i++) {
				
				SparseSpatiotemporalMatrix currentLevel = cacheLevels[i];
				
				if(currentLevel != null) {
					HashMap<String, CacheCell> currentFloor = currentLevel.getCells();
					
					for(String key : currentFloor.keySet()) {
						
						CacheCell cacheCell = currentFloor.get(key);
						float fr = cacheCell.getCorrectedFreshness();
						
						keyValues.put(i+"@"+key, fr);
						
					}
				}
				
			}
			
			Map<Integer, List<String>> elementsToTrim = getElementsToTrim(keyValues, total_allowed);
			
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
