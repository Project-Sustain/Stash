package galileo.comm;

import java.io.IOException;

import galileo.event.Event;
import galileo.serialization.SerializationException;
import galileo.serialization.SerializationInputStream;
import galileo.serialization.SerializationOutputStream;

public class HeartbeatRequest implements Event {

	public String eventID;
	
	public HeartbeatRequest(String queryId) {
		this.eventID = queryId;
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
