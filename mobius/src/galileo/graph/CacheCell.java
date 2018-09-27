package galileo.graph;

import galileo.util.GeoHash;
import galileo.util.SpatialBorder;

public class CacheCell {
	
	private String spatialInfo;
	private String temporalInfo;
	
	private int spatialResolution;
	private int temporalResolution;
	
	private SummaryStatistics[] stats;
	
	private String spatialParent;
	private String temporalParent;
	
	private String[] spatialChildren;
	private String[] temporalChildren;
	
	private SpatialBorder spatialNeighbors;
	private String[] temporalNeighbors;

	private boolean hasParent;
	private boolean hasChildren;
	
	
	private float freshCount;
	
	/**
	 * CREATING A NEW CACHE CELL
	 * UPDATE THE NEIGHBOR INFO AND OBJECT REFERENCES
	 * @param stats
	 * @param numChildren
	 * @param numNeighbors
	 * @param numParents
	 * @param spatiotemporalInfo The spatial and temporal strings that define this cell block
	 * @param spatialResolution
	 * @param temporalResolution
	 */
	
	public CacheCell(SummaryStatistics[] stats, int numChildren, int numNeighbors, int numParents, String spatiotemporalInfo,
			int spatialResolution, int temporalResolution) {
		
		this.stats = stats;
		this.spatialResolution = spatialResolution;
		this.temporalResolution = temporalResolution;
		
		String[] components = spatiotemporalInfo.split(",");
		
		this.spatialInfo = components[0];
		this.temporalInfo = components[1];
		
		if(spatialResolution > 1)
			spatialParent = GeoHash.getSpatialParent(spatialInfo, 1);
		else 
			spatialParent = null;
		if(temporalResolution > 1)
			temporalParent = GeoHash.getTemporalParent(temporalInfo);
		else
			temporalParent = null;
		

		hasParent = true;
		
		if(spatialParent == null && temporalParent == null)
			hasParent = false;
		
		if(spatialResolution < SpatiotemporalHierarchicalCache.totalSpatialLevels )
			spatialChildren = GeoHash.getSpatialChildren(spatialInfo, 1);
		else 
			spatialChildren = null;
		
		if(temporalResolution < SpatiotemporalHierarchicalCache.totalTemporalLevels)
			temporalChildren = GeoHash.getTemporalChildren(temporalInfo, temporalResolution);
		else
			temporalChildren = null;
		
		hasChildren = true;
		
		if(spatialChildren == null && temporalChildren == null)
			hasChildren = false;
		
		spatialNeighbors = GeoHash.getSpatialNeighbours(spatialInfo, spatialResolution);
		temporalNeighbors = GeoHash.getTemporalNeighbors(temporalInfo, temporalResolution);
		
		this.freshCount = 1;
		
	}

	public String getSpatialInfo() {
		return spatialInfo;
	}


	public void setSpatialInfo(String spatialInfo) {
		this.spatialInfo = spatialInfo;
	}


	public String getTemporalInfo() {
		return temporalInfo;
	}

	public void setTemporalInfo(String temporalInfo) {
		this.temporalInfo = temporalInfo;
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

	public SummaryStatistics[] getStats() {
		return stats;
	}

	public void setStats(SummaryStatistics[] stats) {
		this.stats = stats;
	}

	public float getFreshCount() {
		return freshCount;
	}

	public void setFreshCount(float freshCount) {
		this.freshCount = freshCount;
	}

	public String getSpatialParent() {
		return spatialParent;
	}

	public void setSpatialParent(String spatialParent) {
		this.spatialParent = spatialParent;
	}

	public String getTemporalParent() {
		return temporalParent;
	}

	public void setTemporalParent(String temporalParent) {
		this.temporalParent = temporalParent;
	}

	public String[] getSpatialChildren() {
		return spatialChildren;
	}

	public void setSpatialChildren(String[] spatialChildren) {
		this.spatialChildren = spatialChildren;
	}

	public String[] getTemporalChildren() {
		return temporalChildren;
	}

	public void setTemporalChildren(String[] temporalChildren) {
		this.temporalChildren = temporalChildren;
	}

	public boolean isHasParent() {
		return hasParent;
	}

	public void setHasParent(boolean hasParent) {
		this.hasParent = hasParent;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public SpatialBorder getSpatialNeighbors() {
		return spatialNeighbors;
	}

	public void setSpatialNeighbors(SpatialBorder spatialNeighbors) {
		this.spatialNeighbors = spatialNeighbors;
	}

	public String[] getTemporalNeighbors() {
		return temporalNeighbors;
	}

	public void setTemporalNeighbors(String[] temporalNeighbors) {
		this.temporalNeighbors = temporalNeighbors;
	}
	

}
