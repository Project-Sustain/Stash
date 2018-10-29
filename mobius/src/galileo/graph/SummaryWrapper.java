package galileo.graph;

public class SummaryWrapper {
	
	// SPECIFIES WHETHER THIS ENTRY NEEDS TO BE PUT/UPDATED IN THE CACHE OR NOT
	private boolean needsInsertion = false;
	
	private SummaryStatistics[] stats;
	
	public SummaryWrapper(boolean needsInsertion, SummaryStatistics[] stats) {
		
		this.needsInsertion = needsInsertion;
		this.stats = stats;
	}

	public boolean isNeedsInsertion() {
		return needsInsertion;
	}

	public void setNeedsInsertion(boolean needsInsertion) {
		this.needsInsertion = needsInsertion;
	}

	public SummaryStatistics[] getStats() {
		return stats;
	}

	public void setStats(SummaryStatistics[] stats) {
		this.stats = stats;
	}
	
	

}
