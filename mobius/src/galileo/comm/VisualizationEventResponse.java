package galileo.comm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import galileo.event.Event;
import galileo.graph.SummaryWrapper;
import galileo.serialization.SerializationException;
import galileo.serialization.SerializationInputStream;
import galileo.serialization.SerializationOutputStream;

public class VisualizationEventResponse implements Event{

	private List<SummaryWrapper> summaries;
	private List<String> keys;
	
	public VisualizationEventResponse(List<SummaryWrapper> summaries, List<String> keys) {
		
		this.summaries = summaries;
		this.keys = keys;
		
	}
	
	@Override
	public void serialize(SerializationOutputStream out) throws IOException {
		// TODO Auto-generated method stub
		out.writeSerializableCollection(summaries);
		out.writeStringCollection(keys);
	}
	
	@Deserialize
	public VisualizationEventResponse(SerializationInputStream in) throws IOException, SerializationException {
		summaries = new ArrayList<SummaryWrapper>();
		in.readSerializableCollection(SummaryWrapper.class, summaries);
		in.readStringCollection(keys);
	}

	
}
