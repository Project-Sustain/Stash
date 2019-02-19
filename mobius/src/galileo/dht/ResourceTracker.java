package galileo.dht;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import galileo.comm.HeartbeatRequest;
import galileo.comm.VisualizationEvent;
import galileo.net.NetworkDestination;

public class ResourceTracker implements Runnable{
	
	private boolean stop_flag = false;
	private static final Logger logger = Logger.getLogger("galileo");
	
	// TIME INTERVAL IN MILLISECONDS
	private long waitTime = 10*1000;
	
	@Override
	public void run() {
		
		while(!stop_flag) {
			// QUERY NODES
			try {
				
				handleHeartbeatEvent();
				
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				logger.warning("RIKI: SOMETHING WENT WRONG WITH THE RESOURCE TRACKER.");
				e.printStackTrace();
			}
		}
		
	}
	
	private void handleHeartbeatEvent() {
		
		String queryId = String.valueOf(System.currentTimeMillis());
		HeartbeatRequest vEvent = new HeartbeatRequest(queryId);
		
		try {
			ClientRequestHandler reqHandler = new ClientRequestHandler(new ArrayList<NetworkDestination>(nodes), context, this);
			
			/* Sending out query to all nodes */
			reqHandler.handleRequest(vEvent, response);
			this.requestHandlers.add(reqHandler);
		} catch (IOException ioe) {
			logger.log(Level.SEVERE,
					"Failed to initialize a ClientRequestHandler. Sending unfinished response back to client",
					ioe);
			try {
				context.sendReply(response);
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Failed to send response back to original client", e);
			}
		}
		
	}

	public void kill() {
		
		this.stop_flag = true;
		logger.info("RIKI: RESOURCE TRACKER SHUTTING DOWN.");
		
	}

}
