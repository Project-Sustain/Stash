package galileo.graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

import galileo.comm.HeartbeatRequest;
import galileo.dataset.Metadata;
import galileo.dataset.SpatialProperties;
import galileo.dataset.SpatialRange;
import galileo.dht.NodeInfo;
import galileo.dht.NodeInfoRequestHandler;
import galileo.dht.NodeResourceInfo;
import galileo.dht.PartitionException;
import galileo.dht.Partitioner;
import galileo.dht.StorageNode;
import galileo.dht.hash.HashException;
import galileo.fs.GeospatialFileSystem;
import galileo.net.NetworkDestination;
import galileo.util.GeoHash;

public class HotspotTransferCoordinator implements Runnable{
	
	// ALL NODES OTHER THAN THE CURRENT NODE IN QUESTION
	private List<NetworkDestination> allOtherNodes;
	private NetworkDestination currentNode;
	private String currentHostName;
	
	private GeospatialFileSystem fs;
	// THE MAIN REQUEST HANDLER
	private NodeInfoRequestHandler reqHandler;
	
	private static final Logger logger = Logger.getLogger("galileo");
	
	// TIME INTERVAL IN MILLISECONDS
	// TIME WE WAIT FOR ALL HEARTBEAT MESSAGES TO COME BACK
	private static final long WAIT_TIME = 3*1000;
	
	
	/**
	 * TAKE ALL NODES EXCEPT THE LAST ONE, WHICH SHOULD BE THE CURRENT NODE
	 * @param storageNode 
	 * @param allNodes
	 * @param fs 
	 * @param blockingQueue 
	 */
	public HotspotTransferCoordinator(StorageNode storageNode, List<NodeInfo> allNodes, GeospatialFileSystem fs, String currentHostName, String currentCanonicalHostName) {
		
		try {
			
			this.fs = fs;
			
			allOtherNodes = new ArrayList<NetworkDestination>();

			for (NodeInfo n : allNodes) {
				
				NetworkDestination nd = (NetworkDestination) n;
				
				String nodeName = n.getHostname();
				
				// IF THIS IS THE CURRENT NODE
				if (nodeName.equals(currentHostName) || nodeName.equals(currentCanonicalHostName)) {
					currentNode = n;
					this.currentHostName = currentHostName;
					continue;
				}
				
				allOtherNodes.add(nd);
			}

			reqHandler = new NodeInfoRequestHandler(allOtherNodes, fs, currentNode, WAIT_TIME);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public void run() {
		
		logger.info("RIKI: ABOUT TO SEND OUT HEARTBEATS");
		
		/* Sending out heartbeats to all nodes */
		reqHandler.handleRequest();

		logger.info("RIKI: ABOUT TO START WAITING FOR 3 SECS FOR ALL HEARTBEATS TO COME BACK");
		
		// THE FOLLOWING ARE TO BE TAKEN CARE OF IN THE NodeInfoRequestHandler
		/**
		 * STEP 2: READ NODES USAGE STATS FROM RESPONSE
		 * STEP 3: CALCULATE THE HOTTEST CLIQUES IN THE CACHE TREE
		 * STEP 4: FOR EACH CLIQUE, FIGURE OUT THE CANDIDATE NODES TO REPLICATE IN
		 * STEP 5: WAIT FOR ACK FROM THOSE NODES ABOUT SUCCESSFUL SAVE
		 * STEP 6: UPDATE ROUTING TABLE, HOTSPOTHANDLED TRUE
		 */
		
		
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


}
