package galileo.comm;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import galileo.event.Event;
import galileo.serialization.SerializationException;
import galileo.serialization.SerializationInputStream;
import galileo.serialization.SerializationOutputStream;
import galileo.serialization.ByteSerializable.Deserialize;

public class VisualizationEventResponse implements Event{

	private String 
	@Override
	public void serialize(SerializationOutputStream out) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	@Deserialize
	public VisualizationEventResponse(SerializationInputStream in) throws IOException, SerializationException {
		id = in.readString();
		isDryRun = in.readBoolean();
		elapsedTime = in.readLong();
		header = new JSONArray(in.readString());
		jsonResults = new JSONObject(in.readString());
		buildBlockDestinations();
	}

	@Override
	public void serialize(SerializationOutputStream out) throws IOException {
		out.writeString(id);
		out.writeBoolean(isDryRun);
		out.writeLong(elapsedTime);
		out.writeString(header.toString());
		out.writeString(jsonResults.toString());
	}

}
