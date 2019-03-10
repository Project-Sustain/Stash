package galileo.dht;

public class TimeoutChecker implements Runnable{

	private long wait_time;
	private NodeInfoRequestHandler nr;
	private long startTime;
	
	public TimeoutChecker(NodeInfoRequestHandler nr, long WAIT_TIME) {
		startTime = System.currentTimeMillis();
		
	}
	@Override
	public void run() {
		if((System.currentTimeMillis() - startTime > wait_time) && nr.isWaitTimeCheck()) {
			nr.closeRequest();
		}
		
	}
	
	
	

}
