package galileo.comm;

import java.io.IOException;

import galileo.event.Event;
import galileo.serialization.SerializationException;
import galileo.serialization.SerializationInputStream;
import galileo.serialization.SerializationOutputStream;

public class VisualizationResponse implements Event {

	
	private String summariesJSON;
	
	@Override
	public void serialize(SerializationOutputStream out) throws IOException {
		out.writeString(summariesJSON);
		
	}
	
	public static void main(String arg[]) {
		
		System.out.println(TemporalType.YEAR.getType());
		System.out.println(TemporalType.MONTH.getType());
		System.out.println(TemporalType.HOUR_OF_DAY.getType());
	}

	public String getSummariesJSON() {
		return summariesJSON;
	}

	public void setSummariesJSON(String summariesJSON) {
		this.summariesJSON = summariesJSON;
	}
	
	public VisualizationResponse(SerializationInputStream in) throws IOException, SerializationException {
		this.summariesJSON = in.readString();
	}
	
	public VisualizationResponse() {}

}
