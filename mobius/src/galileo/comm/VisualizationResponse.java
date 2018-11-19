package galileo.comm;

import java.io.IOException;
import java.util.Map;

import galileo.event.Event;
import galileo.graph.SummaryWrapper;
import galileo.serialization.SerializationOutputStream;

public class VisualizationResponse implements Event {

	private String summariesJSON;
	@Override
	public void serialize(SerializationOutputStream out) throws IOException {
		// TODO Auto-generated method stub
		
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

}
