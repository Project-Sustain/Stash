package galileo.util;

import java.util.List;

import galileo.graph.SummaryStatistics;

/**
 * Lists the partial cell requirements of blocks, if any
 * @author sapmitra
 *
 */
public class CellRequirements {

	private String blockPath;
	// int 
	// 1 means no processing needed
	// 2 means partial processing needed - look into cellsMissing
	// 3 means full processing needed
	private int requirementMode;
	private List<String> cellsMissing;
	private SummaryStatistics[] summariesFoundInCache;

	// NOTHING RELEVANT FOUND IN CACHE
	public CellRequirements(String blockPath) {
		this.blockPath = blockPath;
		this.requirementMode = 3;
	}
	
	public List<String> getCellsMissing() {
		return cellsMissing;
	}
	public void setCellsMissing(List<String> cellsMissing) {
		this.cellsMissing = cellsMissing;
	}
	public SummaryStatistics[] getSummariesFoundInCache() {
		return summariesFoundInCache;
	}
	public void setSummariesFoundInCache(SummaryStatistics[] summariesFoundInCache) {
		this.summariesFoundInCache = summariesFoundInCache;
	}
	public int getRequirementMode() {
		return requirementMode;
	}
	public void setRequirementMode(int requirementMode) {
		this.requirementMode = requirementMode;
	}
	public String getBlockPath() {
		return blockPath;
	}
	public void setBlockPath(String blockPath) {
		this.blockPath = blockPath;
	}
	
}
