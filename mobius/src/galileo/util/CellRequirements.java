package galileo.util;

import java.util.List;
import java.util.Map;

import galileo.bmp.Bitmap;
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

	// NOTHING RELEVANT FOUND IN CACHE
	public CellRequirements(String blockPath) {
		
		this.blockPath = blockPath;
		this.requirementMode = 3;
	}
	
	// EVERYTHING FOUND IN CACHE
	// FETCH FROM CACHE
	public CellRequirements(String blockPath, int requirementMode) {
		
		this.blockPath = blockPath;
		this.requirementMode = requirementMode;
		
	}

	public CellRequirements(String block, int requirementMode, List<String> keysToBeFetchedFromCache) {
		this.blockPath = block;
		this.requirementMode = requirementMode;
		this.cellsMissing = keysToBeFetchedFromCache;
	}

	public List<String> getCellsMissing() {
		return cellsMissing;
	}
	public void setCellsMissing(List<String> cellsMissing) {
		this.cellsMissing = cellsMissing;
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
