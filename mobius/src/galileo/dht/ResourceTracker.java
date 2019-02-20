package galileo.dht;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import galileo.comm.HeartbeatRequest;
import galileo.comm.VisualizationEvent;
import galileo.net.NetworkDestination;

public class ResourceTracker implements Runnable{
	
	private List<NetworkDestination> allOtherNodes;
	private NetworkDestination currentNode;
	private static final Logger logger = Logger.getLogger("galileo");
	
	private boolean stop_flag = false;
	private List<NodeResourceInfo> nodesResourceInfo = new ArrayList<NodeResourceInfo>();
	
	// TIME INTERVAL IN MILLISECONDS
	private long waitTime = 10*1000;
	
	
	public ResourceTracker(List<NodeInfo> allNodes) {

		allOtherNodes = new ArrayList<NetworkDestination>();
		
		int cnt = 0;
		
		for(NodeInfo n : allNodes) {
			
			if(cnt == allNodes.size() - 1)
				continue;
			
			allOtherNodes.add((NetworkDestination)n);
			cnt++;
		}
		currentNode = (NetworkDestination)(allNodes.get(allNodes.size() - 1));
		
	}
	
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
		
		HeartbeatRequest hbEvent= new HeartbeatRequest(queryId);
		
		try {
			HeartbeatRequestHandler reqHandler = new HeartbeatRequestHandler(allOtherNodes);
			
			/* Sending out query to all nodes */
			reqHandler.handleRequest(hbEvent, null);
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
	
	
	public static NodeResourceInfo getMachineStats() {
		NodeResourceInfo nr = new NodeResourceInfo();
		return nr;
	}

	public void kill() {
		
		this.stop_flag = true;
		logger.info("RIKI: RESOURCE TRACKER SHUTTING DOWN.");
		
	}

	public List<NodeInfo> getAllOtherNodes() {
		return allOtherNodes;
	}

	public void setAllOtherNodes(List<NodeInfo> allOtherNodes) {
		this.allOtherNodes = allOtherNodes;
	}

	public NodeInfo getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(NodeInfo currentNode) {
		this.currentNode = currentNode;
	}

	public boolean isStop_flag() {
		return stop_flag;
	}

	public void setStop_flag(boolean stop_flag) {
		this.stop_flag = stop_flag;
	}

	public List<NodeResourceInfo> getNodesResourceInfo() {
		return nodesResourceInfo;
	}

	public void setNodesResourceInfo(List<NodeResourceInfo> nodesResourceInfo) {
		this.nodesResourceInfo = nodesResourceInfo;
	}

	public long getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(long waitTime) {
		this.waitTime = waitTime;
	}


}
