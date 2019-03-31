package galileo.dht;

public class TimeoutChecker implements Runnable{

	private long wait_time;
	private TileHandoffHandler nr;
	private long startTime;
	
	public TimeoutChecker(TileHandoffHandler nr, long WAIT_TIME) {
		startTime = System.currentTimeMillis();
		wait_time = WAIT_TIME;
		
	}
	
	@Override
	public void run() {
		
		try {
			Thread.sleep(wait_time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(nr == null) {
			return;
		}
		
		// IF AFTER WAIT_TIME, NOT ALL RESPONSES HAVE COME IN, TIME TO STOP
		if(nr.isWaitTimeCheck()) {
			nr.closeRequest();
		}
		
	}
	
	
	

}
