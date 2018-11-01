package galileo.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class STRelatives {
	
	// Level-> Keys
	private Map<Integer, List<String>> parents;
	private Map<Integer, List<String>> children;
	private List<String> neighbors;
	
	public Map<Integer, List<String>> getParents() {
		return parents;
	}
	public void setParents(Map<Integer, List<String>> parents) {
		this.parents = parents;
	}
	public Map<Integer, List<String>> getChildren() {
		return children;
	}
	public void setChildren(Map<Integer, List<String>> children) {
		this.children = children;
	}
	public List<String> getNeighbors() {
		return neighbors;
	}
	public void setNeighbors(List<String> neighbors) {
		this.neighbors = neighbors;
	}
	
	

}
