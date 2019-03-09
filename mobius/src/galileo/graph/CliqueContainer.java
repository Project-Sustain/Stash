package galileo.graph;

import java.util.ArrayList;
import java.util.List;

public class CliqueContainer {
	
	private String geohashKey;
	private List<Integer> levels = null;
	private List<List<CacheCell>> cells = null;
	
	public CliqueContainer(String key) {
		this.geohashKey = key;
		levels = new ArrayList<Integer>();
		cells = new ArrayList<List<CacheCell>>();
	}
	
	public void addCells(int level, List<CacheCell> cells) {

		this.levels.add(level);
		
		this.cells.add(cells);
		
		
	}
	
	
	
	
	
	public List<Integer> getLevels() {
		return levels;
	}
	public void setLevels(List<Integer> levels) {
		this.levels = levels;
	}
	public List<List<CacheCell>> getCells() {
		return cells;
	}
	public void setCells(List<List<CacheCell>> cells) {
		this.cells = cells;
	}
	
	

}
