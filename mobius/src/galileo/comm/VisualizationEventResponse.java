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

	private boolean needsRedirection = false;
	
	private List<SummaryWrapper> summaries;
	private List<String> keys;
	private String hostName;
	private int hostPort;
	
	private String nodeString;
	private List<String> helperNodes;
	
	public VisualizationEventResponse(List<SummaryWrapper> summaries, List<String> keys, String hostname, int port) {
		
		this.summaries = summaries;
		this.keys = keys;
		this.hostName = hostname;
		this.hostPort = port;
		
	}
	
	public VisualizationEventResponse(List<String> nodes, String currentNode) {
		needsRedirection = true;
		this.helperNodes = nodes;
		this.nodeString = currentNode;
	}
	
	public VisualizationEventResponse() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void serialize(SerializationOutputStream out) throws IOException {
		
		out.writeBoolean(needsRedirection);
		
		if(!needsRedirection) {
			out.writeSerializableCollection(summaries);
			out.writeStringCollection(keys);
			out.writeString(hostName);
			out.writeInt(hostPort);
		} else {
			
			out.writeString(nodeString);
			out.writeStringCollection(helperNodes);
		}
	}
	
	@Deserialize
	public VisualizationEventResponse(SerializationInputStream in) throws IOException, SerializationException {
		
		this.needsRedirection = in.readBoolean();
		
		if(!needsRedirection) {
			summaries = new ArrayList<SummaryWrapper>();
			in.readSerializableCollection(SummaryWrapper.class, summaries);
			
			keys = new ArrayList<String>();
			in.readStringCollection(keys);
			this.hostName = in.readString();
			this.hostPort = in.readInt();
		} else {
			this.nodeString = in.readString();
			helperNodes = new ArrayList<String>();
			in.readStringCollection(helperNodes);
		}
		
	}

	public List<SummaryWrapper> getSummaries() {
		return summaries;
	}

	public void setSummaries(List<SummaryWrapper> summaries) {
		this.summaries = summaries;
	}

	public List<String> getKeys() {
		return keys;
	}

	public void setKeys(List<String> keys) {
		this.keys = keys;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getHostPort() {
		return hostPort;
	}

	public void setHostPort(int hostPort) {
		this.hostPort = hostPort;
	}

	
}
