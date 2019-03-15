/*package galileo.dht;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import galileo.comm.HeartbeatRequest;
import galileo.fs.GeospatialFileSystem;
import galileo.net.GalileoMessage;
import galileo.net.NetworkDestination;

public class ResourceTracker implements Runnable{
	
	private List<NetworkDestination> allOtherNodes;
	private NetworkDestination currentNode;
	private BlockingQueue<GalileoMessage> currentQueue;
	private Map<String, GeospatialFileSystem> fsMap;
	
	private Map<String,NodeResourceInfo> nodesResourceMap = new HashMap<String,NodeResourceInfo>();
	
	// THE MAIN REQUEST HANDLER
	private NodeInfoRequestHandler reqHandler;
	
	private static final Logger logger = Logger.getLogger("galileo");
	
	private boolean stop_flag = false;
	
	// TIME INTERVAL IN MILLISECONDS
	private static final long WAIT_TIME = 10*1000;
	
	*//**
	 * TAKE ALL NODES EXCEPT THE LAST ONE, WHICH SHOULD BE THE CURRENT NODE
	 * @param allNodes
	 * @param fsMap 
	 * @param blockingQueue 
	 *//*
	public ResourceTracker(List<NodeInfo> allNodes, BlockingQueue<GalileoMessage> blockingQueue, Map<String, GeospatialFileSystem> fsMap) {
		
		try {
			
			this.currentQueue = blockingQueue;
			this.fsMap = fsMap;
			
			allOtherNodes = new ArrayList<NetworkDestination>();

			int cnt = 0;

			// THE LAST NODE IN THE NETWORK INFO FILE IS MADE THE RESOURCE TRACKER
			for (NodeInfo n : allNodes) {
				
				NetworkDestination nd = (NetworkDestination) n;
				nodesResourceMap.put(nd.stringRepresentation(), null);
				
				if (cnt == allNodes.size() - 1)
					continue;

				allOtherNodes.add(nd);
				cnt++;
			}

			currentNode = (NetworkDestination) (allNodes.get(allNodes.size() - 1));
			reqHandler = new NodeInfoRequestHandler(allOtherNodes, nodesResourceMap);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		
		while(!stop_flag) {
			// QUERY NODES
			try {
				logger.info("RIKI: ABOUT TO SEND OUT HEARTBEATS");
				
				// SENDING OF HEARTBEAT REQUEST AND COMBINING OF THE RESPONSES
				handleHeartbeatEvent();
				
				logger.info("RIKI: ABOUT TO START WAITING FOR 10 SECS");
				
				// SLEEPING FOR 10 SECS
				Thread.sleep(WAIT_TIME);
				
				logger.info("RIKI: FINISHED WAITING FOR 10 SECS");
				
			} catch (InterruptedException e) {
				logger.warning("RIKI: SOMETHING WENT WRONG WITH THE RESOURCE TRACKER.");
				e.printStackTrace();
			}
		}
		
	}
	
	private void handleHeartbeatEvent() {
		
		// IF ALREADY A SET OF HEARTBEATS IS BEING DEALT WITH,
		// DO NOT SEND OUT A NEW SET OF HEARTBEATS
		if(!reqHandler.isCurrentlyBusy()) {
			
			String queryId = String.valueOf(System.currentTimeMillis());
			
			HeartbeatRequest hbEvent= new HeartbeatRequest(queryId);
			
			// FOR THE RESOURCE TRACKER MACHINE, JUST QUERY ITSELF
			// NO NEED TO SEND OUT A REQUEST
			NodeResourceInfo nr_current = getCurrentMachineInfo();
			
			 Sending out heartbeats to all nodes 
			reqHandler.handleRequest(hbEvent, currentNode.stringRepresentation(), nr_current);
		}
	}
	
	private NodeResourceInfo getCurrentMachineInfo() {
		
		int totalGuestTreeSize = 0;
		for(GeospatialFileSystem fs : fsMap.values()) {
			
			totalGuestTreeSize+=fs.getGuestCache().getTotalRooms();
		}
		
		int messageQueueSize = currentQueue.size();
		
		
		Runtime rt = Runtime.getRuntime();
		
		
		NodeResourceInfo nr = new NodeResourceInfo(messageQueueSize, totalGuestTreeSize, rt.freeMemory());
		
		
		return nr;
		
	}
	
	
	public void kill() {
		
		this.stop_flag = true;
		logger.info("RIKI: RESOURCE TRACKER SHUTTING DOWN.");
		
	}

	public List<NetworkDestination> getAllOtherNodes() {
		return allOtherNodes;
	}

	public void setAllOtherNodes(List<NetworkDestination> allOtherNodes) {
		this.allOtherNodes = allOtherNodes;
	}

	public NetworkDestination getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(NetworkDestination currentNode) {
		this.currentNode = currentNode;
	}

	public boolean isStop_flag() {
		return stop_flag;
	}

	public void setStop_flag(boolean stop_flag) {
		this.stop_flag = stop_flag;
	}

	public Map<String,NodeResourceInfo> getNodesResourceInfo() {
		return nodesResourceMap;
	}

	public void setNodesResourceInfo(Map<String,NodeResourceInfo> nodesResourceInfo) {
		this.nodesResourceMap = nodesResourceInfo;
	}


}
*/