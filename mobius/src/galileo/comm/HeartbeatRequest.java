package galileo.comm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import galileo.event.Event;
import galileo.graph.CliqueContainer;
import galileo.serialization.SerializationException;
import galileo.serialization.SerializationInputStream;
import galileo.serialization.SerializationOutputStream;

public class HeartbeatRequest implements Event {
	
	private String nodeString;

	private List<CliqueContainer> cliquesToSend;
	
	public HeartbeatRequest(List<CliqueContainer> cliquesToSend, String nodeString) {
		
		this.nodeString = nodeString;
		this.cliquesToSend = cliquesToSend;
		
	}
	
	@Override
	public void serialize(SerializationOutputStream out) throws IOException {
		
		out.writeString(nodeString);
		out.writeSerializableCollection(cliquesToSend);
		
	}
	
	@Deserialize
	public HeartbeatRequest(SerializationInputStream in) throws IOException, SerializationException {
		
		nodeString = in.readString();
		cliquesToSend = new ArrayList<CliqueContainer>();
		in.readSerializableCollection(CliqueContainer.class, cliquesToSend);
		
	}

	public List<CliqueContainer> getCliquesToSend() {
		return cliquesToSend;
	}

	public void setCliquesToSend(List<CliqueContainer> cliquesToSend) {
		this.cliquesToSend = cliquesToSend;
	}

	public String getNodeString() {
		return nodeString;
	}

	public void setNodeString(String nodeString) {
		this.nodeString = nodeString;
	}

}
