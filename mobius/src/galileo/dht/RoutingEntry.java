package galileo.dht;

import galileo.graph.CliqueContainer;
import galileo.net.NetworkDestination;

public class RoutingEntry {
	
	private CliqueContainer clique;
	
	private NetworkDestination helperNode;
	
	private long insertTime;
	
	public RoutingEntry() {
		
	}
	
	
	public RoutingEntry(CliqueContainer clique, NetworkDestination helperNode, long insertTime) {
		
		this.clique = clique;
		this.helperNode = helperNode;
		this.insertTime = insertTime;
		
	}
	

	public CliqueContainer getClique() {
		return clique;
	}

	public void setClique(CliqueContainer clique) {
		this.clique = clique;
	}

	public NetworkDestination getHelperNode() {
		return helperNode;
	}

	public void setHelperNode(NetworkDestination helperNode) {
		this.helperNode = helperNode;
	}


	public long getInsertTime() {
		return insertTime;
	}


	public void setInsertTime(long insertTime) {
		this.insertTime = insertTime;
	}
}
