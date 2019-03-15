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

	private List<CliqueContainer> cliquesToSend;
	
	public HeartbeatRequest(List<CliqueContainer> cliquesToSend) {
		
		this.cliquesToSend = cliquesToSend;
		
	}
	
	@Override
	public void serialize(SerializationOutputStream out) throws IOException {
		
		out.writeSerializableCollection(cliquesToSend);
		
	}
	
	@Deserialize
	public HeartbeatRequest(SerializationInputStream in) throws IOException, SerializationException {
		
		cliquesToSend = new ArrayList<CliqueContainer>();
		in.readSerializableCollection(CliqueContainer.class, cliquesToSend);
		
	}

}
