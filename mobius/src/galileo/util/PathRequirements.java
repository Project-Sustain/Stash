package galileo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import galileo.graph.SummaryStatistics;

public class PathRequirements {
	
	private Map<String, SummaryStatistics[]> summariesFoundInCache;
	private List<CellRequirements> perBlockRequirementInfo;
	
	public Map<String, SummaryStatistics[]> getSummariesFoundInCache() {
		return summariesFoundInCache;
	}
	public void setSummariesFoundInCache(Map<String, SummaryStatistics[]> summariesFoundInCache) {
		this.summariesFoundInCache = summariesFoundInCache;
	}
	public List<CellRequirements> getPerBlockRequirements() {
		return perBlockRequirementInfo;
	}
	public void setPerBlockRequirements(List<CellRequirements> perBlockRequirements) {
		this.perBlockRequirementInfo = perBlockRequirements;
	}
	
	public void addCellrequirements (CellRequirements cr) {
		
		if(perBlockRequirementInfo == null) {
			perBlockRequirementInfo = new ArrayList<CellRequirements>();
		}
		
		perBlockRequirementInfo.add(cr);
		
	}

}
