package galileo.comm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import galileo.event.Event;
import galileo.serialization.SerializationException;
import galileo.serialization.SerializationInputStream;
import galileo.serialization.SerializationOutputStream;

public class WipeCacheRequest implements Event{
	
	private String fsName;
	
	private int startLevel;
	
	private String timeString;
	
	private int frac;

	@Override
	public void serialize(SerializationOutputStream out) throws IOException {
		
		out.writeString(fsName);
		
		if(timeString == null || timeString.length() <= 0) {
			out.writeBoolean(false);
			
		} else {
			out.writeBoolean(true);
			out.writeInt(startLevel);
			out.writeString(timeString);
			out.writeInt(frac);
		}
		
	}
	
	
	@Deserialize
	public WipeCacheRequest(SerializationInputStream in) throws IOException, SerializationException {
		

		fsName = in.readString();
		
		boolean hasOthers = in.readBoolean();
		
		if(hasOthers) {
			startLevel = in.readInt();
			timeString = in.readString();
			frac = in.readInt();
		} else {
			timeString = null;
		}
		
		
	}


	public WipeCacheRequest(String fsName) {
		this.fsName = fsName;
	}


	public String getFsName() {
		return fsName;
	}


	public void setFsName(String fsName) {
		this.fsName = fsName;
	}


	public String getTimeString() {
		return timeString;
	}


	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}


	public int getStartLevel() {
		return startLevel;
	}


	public void setStartLevel(int startLevel) {
		this.startLevel = startLevel;
	}
	


	public int getFrac() {
		return frac;
	}


	public void setFrac(int frac) {
		this.frac = frac;
	}
	
	

}
