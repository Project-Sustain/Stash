package galileo.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import galileo.dataset.Coordinates;
import galileo.util.STRelatives;

/**
 * 2D Sparse matrix maintained as a hashmap. 
 * The key is the spatialInfo,temporalInfo of a cell.
 * @author sapmitra
 *
 */
public class SparseSpatiotemporalMatrix {
	
	// THE MATRIX IS MAINTAINED AS A HASHMAP
	private HashMap<String, CacheCell> cells;
	// THE NUMBER OF CHILDREN EACH CELL CAN HAVE
	private int numChildren;
	private int numParents;
	private int spatialResolution;
	private int temporalResolution;
	private SpatiotemporalHierarchicalCache cache;
	
	public SparseSpatiotemporalMatrix(int temporalResolution, int spatialResolution, SpatiotemporalHierarchicalCache cache) {
		
		this.cache = cache;
		cells = new HashMap<String, CacheCell>();
		this.spatialResolution = spatialResolution;
		this.temporalResolution = temporalResolution;
		
		int temporalChildren = 0;
		int spatialChildren = 32;
		
		if(spatialResolution >= 7)
			spatialChildren = 0;
		
		if(temporalResolution == 1) {
			//year
			temporalChildren = 12;
		} else if (temporalResolution == 2) {
			//month
			temporalChildren = 31;
		} else if (temporalResolution == 3) {
			//day
			temporalChildren = 24;
		}
		
		numChildren = temporalChildren*spatialChildren;
		
		numParents = 0;
		
		if(spatialResolution == 1 && temporalResolution == 1)
			numParents = 0;
		else if(spatialResolution == 1 || temporalResolution == 1)
			numParents = 1;
		else if(spatialResolution > 1 && temporalResolution > 1)
			numParents = 3;
	}
	
	/**
	 * Looks for a matching cell.
	 * If not exist, create a new one.
	 * @author sapmitra
	 * @param summ
	 * @param key
	 * @param eventTime 
	 * @param eventId 
	 * @param eventId 
	 * @param temporalResolution2 
	 * @param spatialResolution2 
	 */
	
	public STRelatives addCell(SummaryStatistics[] summ, String key, List<Coordinates> polygon, long qt1, long qt2, String eventId, long eventTime) {
		
		// The new summary replaces the old cache summary, whatever may be the case
		CacheCell c = cells.get(key);
		
		// This cell is empty
		c = new CacheCell(summ, numChildren, 16, numParents, key, spatialResolution, temporalResolution, eventId, eventTime);
		
		cells.put(key, c);
			
		STRelatives str = c.getRelativesForCell(polygon, qt1, qt2);
		
		return str;
		
	}
	
	
	public void updateCellFreshness(String key) {
		
		CacheCell c = cells.get(key);
		c.incrementFreshness(1f);
		
	}
	
	/**
	 * ONCE A CELL HAS BEEN ACCESSED, ITS UPDATE COUNTER HAS TO BE INCREASED,
	 * ALONG WITH ITS PARENT,CHILDREN AND NEIGHBORS
	 * 
	 * @author sapmitra
	 */
	public void updateCellAndDisperse(String key) {
		
		CacheCell c = cells.get(key);
		
		
	}
	

	public HashMap<String, CacheCell> getCells() {
		return cells;
	}

	public void setCells(HashMap<String, CacheCell> cells) {
		this.cells = cells;
	}

	public int getNumChildren() {
		return numChildren;
	}

	public void setNumChildren(int numChildren) {
		this.numChildren = numChildren;
	}

	public int getSpatialResolution() {
		return spatialResolution;
	}

	public void setSpatialResolution(int spatialResolution) {
		this.spatialResolution = spatialResolution;
	}

	public int getTemporalResolution() {
		return temporalResolution;
	}

	public void setTemporalResolution(int temporalResolution) {
		this.temporalResolution = temporalResolution;
	}

	

}
