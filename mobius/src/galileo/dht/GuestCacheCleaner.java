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
 */

public class GuestCacheCleaner{
	
	private GeospatialFileSystem fs ;
	private List<String> peList;
	private Map<String, SpatiotemporalHierarchicalCache> guestCache;
	private long HELPER_TIME;
	private static final Logger logger = Logger.getLogger("galileo");
	
	public GuestCacheCleaner(GeospatialFileSystem fs, List<String> peList, Map<String, SpatiotemporalHierarchicalCache> guestCache, int total_allowed, long helperTimeout) {
		
		this.fs = fs;
		this.peList = peList;
		this.guestCache = guestCache;
		this.HELPER_TIME = helperTimeout;
	}

	public void clean() {
		
		//logger.info("RIKI: GUEST CACHE CLEANUP STARTED");
		
		if(!(fs.guestCleanUpInitiated.get())) {
			
			fs.guestCleanUpInitiated.set(true);
			
			while(checkPE()) {
				// blocking
			}
			
			pruneCache();
			
			fs.guestCleanUpInitiated.set(false);
			
		}
		
		//logger.info("GC END");
		
		
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
	 * Handles pruning of guest tree for a single FS
	 * @author sapmitra
	 * @param targetFS
	 */
	
	public void pruneCache() {
		
		long currentTime = System.currentTimeMillis();
		
		synchronized(guestCache) {
			
			for(SpatiotemporalHierarchicalCache cache : guestCache.values()) {
				
				SparseSpatiotemporalMatrix[] cacheLevels = cache.getCacheLevels();
				
				int rem = 0;
				for(int i=0; i< cacheLevels.length; i++) {
					
					SparseSpatiotemporalMatrix currentLevel = cacheLevels[i];
					
					if(currentLevel != null) {
						HashMap<String, CacheCell> currentFloor = currentLevel.getCells();
						
						List<String> keysToRemove = new ArrayList<String>();
						
						for(String key : currentFloor.keySet()) {
							
							CacheCell cacheCell = currentFloor.get(key);
							boolean toRemove = cacheCell.checkForFreshness(currentTime, HELPER_TIME);
							
							if(toRemove)
								keysToRemove.add(key);
							
						}
						
						rem+=keysToRemove.size();
						
						// REMOVING UNWANTED STALE ENTRIES
						for(String key : keysToRemove) {
							currentFloor.remove(key);
						}
					}
					
				}
				
				cache.decrementTotalRooms(rem);
				
			}
			
			
		}
		
	}
	

}
