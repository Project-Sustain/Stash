package galileo.dht;

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

public class GuestTreeCleanupService implements Runnable{
	
	private List<NetworkDestination> allOtherNodes;
	private NetworkDestination currentNode;
	private BlockingQueue<GalileoMessage> currentQueue;
	private Map<String, GeospatialFileSystem> fsMap;
	
	private Map<String,NodeResourceInfo> nodesResourceMap = new HashMap<String,NodeResourceInfo>();
	
	private static final Logger logger = Logger.getLogger("galileo");
	
	private boolean stop_flag = false;
	
	// TIME INTERVAL IN MILLISECONDS
	private static final long WAIT_TIME = 10*1000;
	
	
	public GuestTreeCleanupService(List<NodeInfo> allNodes, BlockingQueue<GalileoMessage> blockingQueue, Map<String, GeospatialFileSystem> fsMap) {
		
		
			
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
			
		
		
	}
	
	@Override
	public void run() {
		
		while(!stop_flag) {
			// QUERY NODES
			try {
				logger.info("RIKI: ABOUT TO SEND OUT HEARTBEATS");
				
				// SENDING OF HEARTBEAT REQUEST AND COMBINING OF THE RESPONSES
				
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
	
	
	
	private NodeResourceInfo getCurrentMachineInfo() {
		
		int totalGuestTreeSize = 0;
		for(GeospatialFileSystem fs : fsMap.values()) {
			
			
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
