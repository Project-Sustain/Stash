package galileo.comm;

import java.io.IOException;

import galileo.event.Event;
import galileo.serialization.SerializationException;
import galileo.serialization.SerializationInputStream;
import galileo.serialization.SerializationOutputStream;

public class WipeCacheEvent implements Event{
	
	private String fsName;

	@Override
	public void serialize(SerializationOutputStream out) throws IOException {
		
		out.writeString(fsName);
		
	}
	
	
	@Deserialize
	public WipeCacheEvent(SerializationInputStream in) throws IOException, SerializationException {
		
		fsName = in.readString();
	}


	public WipeCacheEvent(String fsName) {
		this.fsName = fsName;
	}


	public String getFsName() {
		return fsName;
	}


	public void setFsName(String fsName) {
		this.fsName = fsName;
	}
	
	

}
