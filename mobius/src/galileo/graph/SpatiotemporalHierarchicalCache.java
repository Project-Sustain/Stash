package galileo.graph;

import java.util.List;
import java.util.logging.Logger;

import galileo.dataset.Coordinates;
import galileo.fs.GeospatialFileSystem;
import galileo.util.STRelatives;

public class SpatiotemporalHierarchicalCache {
	private static final Logger logger = Logger.getLogger("galileo");
	// EACH LEVEL IS A 2D SPARSE MATRIX
	private SparseSpatiotemporalMatrix[] cacheLevels;
	
	private int totalRooms = 0;
	
	// spatial level 1 means geohash of length 1
	// temporal levels are year, month, day, hr
	public static int totalSpatialLevels = 6;
	public static int totalTemporalLevels = 4;
	
	
	/**
	 * CREATES A CACHE OF SAME DIMENSIONS OF THE CALLING CACHE
	 * @author sapmitra
	 * @return
	 */
	public SpatiotemporalHierarchicalCache getNewCache() {
		
		SpatiotemporalHierarchicalCache newCache = new SpatiotemporalHierarchicalCache(totalSpatialLevels, totalTemporalLevels);
		
		return newCache;
	}
	
	public SpatiotemporalHierarchicalCache(int totalSpatialLevels, int totalTemporalLevels) {
		
		SpatiotemporalHierarchicalCache.totalSpatialLevels = totalSpatialLevels;
		SpatiotemporalHierarchicalCache.totalTemporalLevels = totalTemporalLevels;
		
		cacheLevels = new SparseSpatiotemporalMatrix[totalSpatialLevels*totalTemporalLevels];
		
		for(int i=0; i < totalSpatialLevels; i++) {
			for(int j=0; j < totalTemporalLevels; j++) {
				
				int levelNum = (j)*totalSpatialLevels + i;
				cacheLevels[levelNum] = new SparseSpatiotemporalMatrix(j+1, i+1, this);
			}
		}
	}
	
	public SpatiotemporalHierarchicalCache() {
		
		cacheLevels = new SparseSpatiotemporalMatrix[totalSpatialLevels*totalTemporalLevels];
		
		for(int i=0; i < totalSpatialLevels; i++) {
			for(int j=0; j < totalTemporalLevels; j++) {
				
				int levelNum = j*totalSpatialLevels + i;
				cacheLevels[levelNum] = new SparseSpatiotemporalMatrix(j+1, i+1, this);
			}
		}
	}
	

	
	/**
	 * GET THE PARTICULAR LEVEL/INDEX FOR THE MATRIX FOR A GIVEN SPATIAL AND TEMPORAL RESOLUTION
	 * 
	 * @author sapmitra
	 * @param spatialResolution geohash length
	 * @param temporalResolution numbered as yr,month,day,hr
	 * @return
	 */
	public int getCacheLevel(int spatialResolution, int temporalResolution) {
		
		if(spatialResolution < 0 || spatialResolution > totalSpatialLevels || temporalResolution < 0 || temporalResolution > totalTemporalLevels )
			return -1;
		
		int levelNum = (temporalResolution-1)*totalSpatialLevels + (spatialResolution-1);
		
		return levelNum;
	}
	
	
	/**
	 * Given a level number, return the individual spatial and temporal resolution of this level.
	 * @author sapmitra
	 * @param spatioTemporalLevel
	 * @return
	 */
	
	public int[] getSpatioTemporalResolutionsFromLevel(int spatioTemporalLevel) {
		
		int spatialResolution = (spatioTemporalLevel%totalSpatialLevels) + 1;
		int temporalResolution = (spatioTemporalLevel/totalSpatialLevels) + 1;
		
		int[] levels = {spatialResolution, temporalResolution};
		return levels;
		
	}
	
	/**
	 * RETURNED AS SPATIAL, TEMPORAL, SPATIOTEMPORAL
	 * @author sapmitra
	 * @param level
	 * @return
	 */
	public int[] getParentLevels(int level) {
		
		int spatialResolution = (level%totalSpatialLevels) + 1;
		int temporalResolution = (level/totalSpatialLevels) + 1;
		
		int[] parents = {getCacheLevel(spatialResolution-1, temporalResolution), getCacheLevel(spatialResolution, temporalResolution-1),
				getCacheLevel(spatialResolution-1, temporalResolution-1)};
		
		
		return parents;
		
	}
	
	/**
	 * RETURNED AS SPATIAL, TEMPORAL, SPATIOTEMPORAL
	 * @author sapmitra
	 * @param level
	 * @return
	 */
	public int[] getChildrenLevels(int level) {
		
		int spatialResolution = (level%totalSpatialLevels) + 1;
		int temporalResolution = (level/totalSpatialLevels) + 1;
		
		int[] children = {getCacheLevel(spatialResolution+1, temporalResolution), getCacheLevel(spatialResolution, temporalResolution+1),
				getCacheLevel(spatialResolution+1, temporalResolution+1)};
		
		
		return children;
	}
	
	

	public SparseSpatiotemporalMatrix[] getCacheLevels() {
		return cacheLevels;
	}

	public void setCacheLevels(SparseSpatiotemporalMatrix[] cacheLevels) {
		this.cacheLevels = cacheLevels;
	}
	
	/**
	 * ADDING/UPDATING A CACHE CELL
	 * @author sapmitra
	 * @param summ
	 * @param key "space,time"
	 * @param spatialResolution
	 * @param temporalResolution
	 */
	/*public void addCell(SummaryStatistics[] summ, String key, int spatialResolution, int temporalResolution, String eventId) {
		
		int id = getCacheLevel(spatialResolution, temporalResolution);
		
		if(id < totalSpatialLevels*totalTemporalLevels && cacheLevels[id] != null) {
			cacheLevels[id].addCell(summ, key);
		}
		
	}*/
	
	/**
	 * SAME AS THE PREVIOUS METHOD 
	 * @author sapmitra
	 * @param summ
	 * @param key
	 * @param cacheLevel
	 * @param qt2 
	 * @param qt1 
	 * @param polygon 
	 * @param freshnessMultiplier 
	 */
	public boolean addCell(SummaryStatistics[] summ, String key, int cacheLevel, List<Coordinates> polygon, long qt1, long qt2, 
			String eventId, long eventTime, int freshnessMultiplier) {
		
		boolean newEntry = false;
		if(cacheLevel < totalSpatialLevels*totalTemporalLevels && cacheLevels[cacheLevel] != null) {
			//logger.info("RIKI: REACHED INSIDE");
			newEntry = cacheLevels[cacheLevel].addCell(summ, key, polygon, qt1, qt2, eventId, eventTime, freshnessMultiplier);
		}
		
		return newEntry;
	}
	
	/**
	 * Adds 1 to a pre-existing cell if it gets accessed
	 * 
	 * @author sapmitra
	 * @param key
	 * @param cacheLevel
	 * @param eventTime 
	 * @param eventId 
	 * @param qt2 
	 * @param qt1 
	 * @param polygon 
	 * @param freshnessMultiplier 
	 */
	public void incrementCell(String key, int cacheLevel, List<Coordinates> polygon, long qt1, long qt2, String eventId, long eventTime, int freshnessMultiplier) {
		
		if(cacheLevel < totalSpatialLevels*totalTemporalLevels && cacheLevels[cacheLevel] != null) {
			cacheLevels[cacheLevel].updateCellFreshness(key, polygon, qt1, qt2, eventId, eventTime, freshnessMultiplier);
		}
		
	}
	
	/**
	 * This is second hand dispersion for cell that was not actually accessed.
	 * 
	 * @author sapmitra
	 * @param key
	 * @param cacheLevel
	 * @param eventTime 
	 * @param eventId 
	 * @param polygon
	 * @param qt1
	 * @param qt2
	 * @param eventId
	 * @param eventTime
	 * @param freshnessMultiplier 
	 */
	
	public void disperseToCell(String key, int cacheLevel, String eventId, long eventTime, int freshnessMultiplier) {
		
		if(cacheLevel < totalSpatialLevels*totalTemporalLevels && cacheLevels[cacheLevel] != null) {
			cacheLevels[cacheLevel].disperseCellFreshness(key, eventId, eventTime, freshnessMultiplier);
		}
		
	}
	
	
	
	/**
	 * RETURNS THE EXACT SPARSE SPATIOTEMPORAL MATRIX/CACHE FOR A GIVEN LEVEL
	 * @author sapmitra
	 * @param level - spatiotemporal level
	 * @return
	 */
	public SparseSpatiotemporalMatrix getSpecificCache(int level) {
		
		return cacheLevels[level];
		
	}
	
	public static void main(String arg[]) {
		
		SpatiotemporalHierarchicalCache s = new SpatiotemporalHierarchicalCache();
		
		System.out.println(s.getCacheLevel(2, 3));
		
	}

	public int getTotalSpatialLevels() {
		return totalSpatialLevels;
	}

	public void setTotalSpatialLevels(int totalSpatialLevels) {
		this.totalSpatialLevels = totalSpatialLevels;
	}

	public int getTotalTemporalLevels() {
		return totalTemporalLevels;
	}

	public void setTotalTemporalLevels(int totalTemporalLevels) {
		this.totalTemporalLevels = totalTemporalLevels;
	}

	/**
	 * INCREMENT ENTRY COUNT AND RETURN TRUE IF OVERFLOW
	 * @author sapmitra
	 * @param totalInserted
	 * @return
	 */
	public boolean addEntryCount(int totalInserted, int totalAllowed) {
		totalRooms+=totalInserted;
		
		if(totalRooms > totalAllowed) {
			return true;
		} else {
			return false;
		}
		
	}

	public int getTotalRooms() {
		return totalRooms;
	}

	public void setTotalRooms(int totalRooms) {
		this.totalRooms = totalRooms;
	}
	
	public void decrementTotalRooms(int rem) {
		this.totalRooms -= rem;
	}
	
	
	
	public void populateClique(List<CliqueContainer> cliquesToAdd, long eventTime) {
		
		
		for(CliqueContainer cc : cliquesToAdd) {
			
			int count = 0;
			for(int l : cc.getLevels()) {
				
				List<CacheCell> cellsToAdd = cc.getCells().get(count);
				
				for(CacheCell c : cellsToAdd) {
					boolean newEntry = cacheLevels[l].addGuestCell(c.getStats(), c.getCellKey(), c.getSpatialResolution(), c.getTemporalResolution(), "guest", eventTime);
					if(newEntry)
						totalRooms++;
				}
				
				
				count++;
			}
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
