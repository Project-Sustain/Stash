package galileo.graph;

public class SpatioTemporalHierarchicalCache {
	
	private SparseSpatiotemporalMatrix[] cacheLevels;
	// spatial level 1 means geohash of length 1
	// temporal levels are year, month, day, hr
	private int totalSpatialLevels = 7;
	private int totalTemporalLevels = 4;
	
	public SpatioTemporalHierarchicalCache() {
		
		cacheLevels = new SparseSpatiotemporalMatrix[totalSpatialLevels*totalTemporalLevels];
	}

	public SparseSpatiotemporalMatrix[] getCacheLevels() {
		return cacheLevels;
	}

	public void setCacheLevels(SparseSpatiotemporalMatrix[] cacheLevels) {
		this.cacheLevels = cacheLevels;
	}
	
	public int getCacheLevel(int spatialResolution, int temporalResolution) {
		
		int levelNum = (spatialResolution-1)*totalTemporalLevels + (temporalResolution-1);
		
		return levelNum;
	}
	
	public static void main(String arg[]) {
		
		SpatioTemporalHierarchicalCache s = new SpatioTemporalHierarchicalCache();
		
		System.out.println(s.getCacheLevel(2, 3));
		
	}

}
