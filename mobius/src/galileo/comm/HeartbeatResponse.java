package galileo.comm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import galileo.event.Event;
import galileo.serialization.SerializationException;
import galileo.serialization.SerializationInputStream;
import galileo.serialization.SerializationOutputStream;

public class HeartbeatResponse implements Event {

	private List<Boolean> resultFlag;
	private List<String> geohashOfClique;
	private List<String> geohashAntipodeOfClique;
	private List<Integer> direction;
	
	@Override
	public void serialize(SerializationOutputStream out) throws IOException {
		
		out.writeBooleanCollection(resultFlag);
		out.writeStringCollection(geohashOfClique);
		out.writeStringCollection(geohashAntipodeOfClique);
		out.writeIntegerCollection(direction);
		
	}

	@Deserialize
	public HeartbeatResponse(SerializationInputStream in) throws IOException, SerializationException {
		
		resultFlag = new ArrayList<Boolean>();
		geohashOfClique = new ArrayList<String>();
		geohashAntipodeOfClique = new ArrayList<String>();
		direction = new ArrayList<Integer>();
		
		in.readBooleanCollection(this.resultFlag);
		in.readStringCollection(this.geohashOfClique);
		in.readStringCollection(this.geohashAntipodeOfClique);
		in.readIntegerCollection(direction);
		
	}
	
	public HeartbeatResponse(String geoHash, String antipode, boolean result, int direction) {
		this.cpuUtil = cpuUtil2;
		this.guestTreeSize = guestTreeSize2;
		this.heapMem = heapUsage;
		this.hostString = hostString;
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

	public String getHostString() {
		return hostString;
	}

	public void setHostString(String hostString) {
		this.hostString = hostString;
	}

}
