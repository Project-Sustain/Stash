package galileo.graph;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import galileo.fs.GeospatialFileSystem;
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
	
	private List<String> spatialChildren;
	private List<String> temporalChildren;
	
	private List<String> spatialNeighbors;
	private List<String> temporalNeighbors;

	private boolean hasParent;
	private boolean hasChildren;
	
	
	private float freshness;
	private long lastAccessed;
	
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
		
		this.temporalInfo = components[0];
		this.spatialInfo = components[1];
		
		/* NEIGHBORS FOR THIS LEVEL */
		spatialNeighbors = GeoHash.getSpatialNeighboursSimplified(spatialInfo, GeospatialFileSystem.SPATIAL_SPREAD);
		temporalNeighbors = GeoHash.getTemporalNeighbors(temporalInfo, temporalResolution);
		
		/* PARENTS ONE LEVEL UP */
		if(spatialResolution > 1)
			spatialParent = GeoHash.getSpatialParent(spatialInfo, 1);
		else 
			spatialParent = null;
		
		if(temporalResolution > 1)
			temporalParent = GeoHash.getTemporalParent(temporalInfo, temporalResolution);
		else
			temporalParent = null;
		
		hasParent = true;
		
		if(spatialParent == null && temporalParent == null)
			hasParent = false;
		
		/* CHILDREN ONE LEVEL DOWN */
		if(spatialResolution < SpatiotemporalHierarchicalCache.totalSpatialLevels )
			spatialChildren = GeoHash.getSpatialChildren(spatialInfo);
		else 
			spatialChildren = null;
		
		if(temporalResolution < SpatiotemporalHierarchicalCache.totalTemporalLevels)
			temporalChildren = GeoHash.getTemporalChildren(temporalInfo, temporalResolution);
		else
			temporalChildren = null;
		
		hasChildren = true;
		
		if(spatialChildren == null && temporalChildren == null)
			hasChildren = false;
		
		this.freshness = 1;
		
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

	public float getFreshness() {
		return freshness;
	}

	public void setFreshness(float freshness) {
		this.freshness = freshness;
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

	
	
	public void incrementFreshness(float val) {
		this.freshness += val;
	}
	
	
	public static void main(String arg[]) {
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(new Date()); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 280); // adds one hour
	    System.out.println(cal.getTime()); 
		
	}

	public List<String> getSpatialNeighbors() {
		return spatialNeighbors;
	}

	public void setSpatialNeighbors(List<String> spatialNeighbors) {
		this.spatialNeighbors = spatialNeighbors;
	}

	public List<String> getTemporalNeighbors() {
		return temporalNeighbors;
	}

	public void setTemporalNeighbors(List<String> temporalNeighbors) {
		this.temporalNeighbors = temporalNeighbors;
	}

	public long getLastAccessed() {
		return lastAccessed;
	}

	public void setLastAccessed(long lastAccessed) {
		this.lastAccessed = lastAccessed;
	}

	public List<String> getSpatialChildren() {
		return spatialChildren;
	}

	public void setSpatialChildren(List<String> spatialChildren) {
		this.spatialChildren = spatialChildren;
	}

	public List<String> getTemporalChildren() {
		return temporalChildren;
	}

	public void setTemporalChildren(List<String> temporalChildren) {
		this.temporalChildren = temporalChildren;
	}

}
