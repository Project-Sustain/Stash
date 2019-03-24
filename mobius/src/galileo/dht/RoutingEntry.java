package galileo.dht;

import galileo.graph.CliqueContainer;
import galileo.net.NetworkDestination;

public class RoutingEntry {
	
	private CliqueContainer clique;
	
	private NetworkDestination helperNode;
	

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
}
