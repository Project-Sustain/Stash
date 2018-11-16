package galileo.graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import galileo.serialization.ByteSerializable;
import galileo.serialization.SerializationException;
import galileo.serialization.SerializationInputStream;
import galileo.serialization.SerializationOutputStream;

public class SummaryWrapper implements ByteSerializable{
	
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

	@Override
	public void serialize(SerializationOutputStream out) throws IOException {
		List<SummaryStatistics> elements = Arrays.asList(stats);
		out.writeSerializableCollection(elements);
		
	}
	
	@Deserialize
	public SummaryWrapper(SerializationInputStream in) throws IOException, SerializationException {
		
		List<SummaryStatistics> elements = new ArrayList<SummaryStatistics>();
		in.readSerializableCollection(SummaryStatistics.class, elements);
		stats = elements.toArray(new SummaryStatistics[elements.size()]);
		
	}
	
	
	
	

}
