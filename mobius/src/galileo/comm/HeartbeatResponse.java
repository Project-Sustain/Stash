package galileo.comm;

import java.io.IOException;

import galileo.event.Event;
import galileo.serialization.SerializationOutputStream;

public class HeartbeatResponse implements Event {

	private float cpuUtil;
	private float guestTreeSize;
	private float heapMem;
	
	@Override
	public void serialize(SerializationOutputStream out) throws IOException {
		
		
	}

}
