package galileo.comm;

import java.io.IOException;

import galileo.event.Event;
import galileo.serialization.SerializationOutputStream;

public class VisualizationResponse implements Event {

	@Override
	public void serialize(SerializationOutputStream out) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String arg[]) {
		
		System.out.println(TemporalType.YEAR.getType());
		System.out.println(TemporalType.MONTH.getType());
		System.out.println(TemporalType.HOUR_OF_DAY.getType());
	}

}
