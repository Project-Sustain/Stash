package galileo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import galileo.graph.SummaryStatistics;

/**
 * ONE FOR EACH PATH.
 * KEEPS INFORMATION OF WHAT CELLS FOR EACH BLOCK NEEDS PROCESSING.
 * USED ONLY IN SUB_BLOCK SCENARIO.
 * 
 * @author sapmitra
 *
 */
public class PathRequirements {
	
	private boolean entireBlockCached = true;
	// List of all the cache cells needed
	private List<String> cacheCellKeys;
	// The contents of "cacheCells" once fetched
	private Map<String, SummaryStatistics[]> summariesFoundInCache;
	private List<CellRequirements> perBlockRequirementInfo;
	
	public Map<String, SummaryStatistics[]> getSummariesFoundInCache() {
		return summariesFoundInCache;
	}
	public void setSummariesFoundInCache(Map<String, SummaryStatistics[]> summariesFoundInCache) {
		this.summariesFoundInCache = summariesFoundInCache;
	}
	
	public void addCellrequirements (CellRequirements cr) {
		
		if(perBlockRequirementInfo == null) {
			perBlockRequirementInfo = new ArrayList<CellRequirements>();
		}
		
		perBlockRequirementInfo.add(cr);
		
	}
	public List<String> getCacheCellKeys() {
		return cacheCellKeys;
	}
	public void setCacheCellKeys(List<String> cacheCells) {
		this.cacheCellKeys = cacheCells;
	}
	public List<CellRequirements> getPerBlockRequirementInfo() {
		return perBlockRequirementInfo;
	}
	public void setPerBlockRequirementInfo(List<CellRequirements> perBlockRequirementInfo) {
		this.perBlockRequirementInfo = perBlockRequirementInfo;
	}
	public boolean isEntireBlockCached() {
		return entireBlockCached;
	}
	public void setEntireBlockCached(boolean entireBlockCached) {
		this.entireBlockCached = entireBlockCached;
	}

}
