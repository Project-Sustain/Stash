package galileo.comm;

import java.io.IOException;

import galileo.event.Event;
import galileo.serialization.SerializationException;
import galileo.serialization.SerializationInputStream;
import galileo.serialization.SerializationOutputStream;

public class HeartbeatResponse implements Event {

	private float cpuUtil;
	private float guestTreeSize;
	private float heapMem;
	private String reqEventId;
	
	@Override
	public void serialize(SerializationOutputStream out) throws IOException {
		
		out.writeString(reqEventId);
		out.writeFloat(guestTreeSize);
		out.writeFloat(heapMem);
		out.writeFloat(cpuUtil);
		
	}

	@Deserialize
	public HeartbeatResponse(SerializationInputStream in) throws IOException, SerializationException {
		
		this.reqEventId = in.readString();
		this.guestTreeSize = in.readFloat();
		this.heapMem = in.readFloat();
		this.cpuUtil = in.readFloat();
		
	}
	
	public float getCpuUtil() {
		return cpuUtil;
	}

	public void setCpuUtil(float cpuUtil) {
		this.cpuUtil = cpuUtil;
	}

	public float getGuestTreeSize() {
		return guestTreeSize;
	}

	public void setGuestTreeSize(float guestTreeSize) {
		this.guestTreeSize = guestTreeSize;
	}

	public float getHeapMem() {
		return heapMem;
	}

	public void setHeapMem(float heapMem) {
		this.heapMem = heapMem;
	}

	public String getReqEventId() {
		return reqEventId;
	}

	public void setReqEventId(String reqEventId) {
		this.reqEventId = reqEventId;
	}

}
