package galileo.comm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import galileo.event.Event;
import galileo.graph.SummaryWrapper;
import galileo.serialization.SerializationException;
import galileo.serialization.SerializationInputStream;
import galileo.serialization.SerializationOutputStream;

public class VisualizationResponse implements Event {

	private List<String> keys;
	
	private List<SummaryWrapper> summaries;
	
	@Override
	public void serialize(SerializationOutputStream out) throws IOException {
		out.writeStringCollection(keys);
		out.writeSerializableCollection(summaries);
		
	}
	
	public VisualizationResponse(SerializationInputStream in) throws IOException, SerializationException {
		
		this.keys = new ArrayList<String>();
		this.summaries = new ArrayList<SummaryWrapper>();
		in.readStringCollection(keys);
		in.readSerializableCollection(SummaryWrapper.class, summaries);
		
	}
	
	public static void main(String arg[]) {
		
		System.out.println(TemporalType.YEAR.getType());
		System.out.println(TemporalType.MONTH.getType());
		System.out.println(TemporalType.HOUR_OF_DAY.getType());
	}

	public VisualizationResponse() {}

	public List<String> getKeys() {
		return keys;
	}

	public void setKeys(List<String> keys) {
		this.keys = keys;
	}

	public List<SummaryWrapper> getSummaries() {
		return summaries;
	}

	public void setSummaries(List<SummaryWrapper> summaries) {
		this.summaries = summaries;
	}

}
