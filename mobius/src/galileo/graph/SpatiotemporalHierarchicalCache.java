package galileo.graph;

import java.util.List;

import galileo.dataset.Coordinates;
import galileo.util.STRelatives;

public class SpatiotemporalHierarchicalCache {
	
	// EACH LEVEL IS A 2D SPARSE MATRIX
	private SparseSpatiotemporalMatrix[] cacheLevels;
	
	// spatial level 1 means geohash of length 1
	// temporal levels are year, month, day, hr
	public static int totalSpatialLevels = 6;
	public static int totalTemporalLevels = 4;
	
	public SpatiotemporalHierarchicalCache(int totalSpatialLevels, int totalTemporalLevels) {
		
		cacheLevels = new SparseSpatiotemporalMatrix[totalSpatialLevels*totalTemporalLevels];
		
		for(int i=0; i < totalSpatialLevels; i++) {
			for(int j=0; j < totalTemporalLevels; i++) {
				cacheLevels[i] = new SparseSpatiotemporalMatrix(j+1, i+1, this);
			}
		}
	}
	
	public SpatiotemporalHierarchicalCache() {
		
		cacheLevels = new SparseSpatiotemporalMatrix[totalSpatialLevels*totalTemporalLevels];
		
		for(int i=0; i < totalSpatialLevels; i++) {
			for(int j=0; j < totalTemporalLevels; i++) {
				cacheLevels[i] = new SparseSpatiotemporalMatrix(j+1, i+1, this);
			}
		}
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
	 */
	public STRelatives addCell(SummaryStatistics[] summ, String key, int cacheLevel, List<Coordinates> polygon, long qt1, long qt2, String eventId, long eventTime) {
		STRelatives str = null;
		
		if(cacheLevel < totalSpatialLevels*totalTemporalLevels && cacheLevels[cacheLevel] != null) {
			str = cacheLevels[cacheLevel].addCell(summ, key, polygon, qt1, qt2, eventId, eventTime);
		}
		
		return str;
		
	}
	
	/**
	 * Adds 1 to a pre-existing cell if it gets accessed
	 * 
	 * @author sapmitra
	 * @param key
	 * @param cacheLevel
	 */
	public STRelatives incrementCell(String key, int cacheLevel) {
		
		STRelatives str = null;
		if(cacheLevel < totalSpatialLevels*totalTemporalLevels && cacheLevels[cacheLevel] != null) {
			cacheLevels[cacheLevel].updateCellFreshness(key);
		}
		
		return str;
		
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
		
		int levelNum = (temporalResolution-1)*totalSpatialLevels + (spatialResolution-1);
		
		return levelNum;
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

}
