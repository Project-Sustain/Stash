package galileo.comm;

import java.io.IOException;
import java.util.List;

import galileo.event.Event;
import galileo.graph.CliqueContainer;
import galileo.serialization.SerializationException;
import galileo.serialization.SerializationInputStream;
import galileo.serialization.SerializationOutputStream;

public class HeartbeatRequest implements Event {

	private String eventID;
	private List<CliqueContainer> cliquesToSend;
	
	public HeartbeatRequest(String queryId) {
		this.eventID = queryId;
	}
	
	
	public HeartbeatRequest(List<CliqueContainer> cliquesToSend) {
		
		this.cliquesToSend = cliquesToSend;
		
	}
	
	@Override
	public void serialize(SerializationOutputStream out) throws IOException {
		
		out.writeString(eventID);
		
	}
	
	@Deserialize
	public HeartbeatRequest(SerializationInputStream in) throws IOException, SerializationException {
		
		eventID = in.readString();
		
	}
	
	public String getEventID() {
		return eventID;
	}
	public void setEventID(String eventID) {
		this.eventID = eventID;
	}

}
