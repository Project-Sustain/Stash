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
	private String nodeString;
	
	public HeartbeatResponse() {
		
		resultFlag = new ArrayList<Boolean>();
		geohashOfClique = new ArrayList<String>();
		geohashAntipodeOfClique = new ArrayList<String>();
		direction = new ArrayList<Integer>();
	}
	
	@Override
	public void serialize(SerializationOutputStream out) throws IOException {
		
		out.writeBooleanCollection(resultFlag);
		out.writeStringCollection(geohashOfClique);
		out.writeStringCollection(geohashAntipodeOfClique);
		out.writeIntegerCollection(direction);
		out.writeString(nodeString);
		
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
		
		this.nodeString = in.readString();
		
	}

	public void addEntry(boolean b, String geohashKey, String geohashAntipode, int direction2) {
		
		resultFlag.add(b);
		geohashOfClique.add(geohashKey);
		geohashAntipodeOfClique.add(geohashAntipode);
		direction.add(direction2);
		
		
	}

	public List<Boolean> getResultFlag() {
		return resultFlag;
	}

	public void setResultFlag(List<Boolean> resultFlag) {
		this.resultFlag = resultFlag;
	}

	public List<String> getGeohashOfClique() {
		return geohashOfClique;
	}

	public void setGeohashOfClique(List<String> geohashOfClique) {
		this.geohashOfClique = geohashOfClique;
	}

	public List<String> getGeohashAntipodeOfClique() {
		return geohashAntipodeOfClique;
	}

	public void setGeohashAntipodeOfClique(List<String> geohashAntipodeOfClique) {
		this.geohashAntipodeOfClique = geohashAntipodeOfClique;
	}

	public List<Integer> getDirection() {
		return direction;
	}

	public void setDirection(List<Integer> direction) {
		this.direction = direction;
	}

	public String getNodeString() {
		return nodeString;
	}

	public void setNodeString(String nodetring) {
		this.nodeString = nodetring;
	}
	
	

}
