package galileo.graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import galileo.bmp.CorrectedBitmap;
import galileo.comm.TemporalType;
import galileo.fs.GeospatialFileSystem;
import galileo.net.NetworkDestination;
import galileo.serialization.ByteSerializable;
import galileo.serialization.SerializationInputStream;
import galileo.serialization.SerializationOutputStream;
import galileo.util.GeoHash;

public class CliqueContainer implements ByteSerializable{
	
	private String geohashKey;
	private List<Integer> levels = null;
	private List<List<CacheCell>> cells = null;
	
	private List<CorrectedBitmap> bitmaps;
	
	private String geohashAntipode;
	private int direction;
	
	private int totalCliqueSize = 0;
	
	private NetworkDestination replicatedNode = null;
	
	public CliqueContainer(String key) {
		
		this.geohashKey = key;
		levels = new ArrayList<Integer>();
		cells = new ArrayList<List<CacheCell>>();
		
	}
	
	public void addCells(int level, List<CacheCell> cells) {

		this.levels.add(level);
		
		this.cells.add(cells);
		
		totalCliqueSize += cells.size();
		
		
	}
	
	/**
	 * CREATES A LEVEL-WISE BITMAP OF ALL THE CELLS CONTAINED IN A CLIQUE 
	 * @author sapmitra
	 * @param fs
	 * @return
	 */
	
	
	public void calculateBitmap(GeospatialFileSystem fs) {
		
		List<CorrectedBitmap> bitmaps = new ArrayList<CorrectedBitmap>();
		
		int count = 0;
		
		for(List<CacheCell> cellRow: cells) {
			
			int currentLevel = levels.get(count);
			
			int[] levels = fs.getStCache().getSpatioTemporalResolutionsFromLevel(currentLevel);
			
			// CREATE A SET OF TEMPORARY BITMAPS
			CorrectedBitmap bm = createBitmapForCLique(fs, currentLevel, levels[0], levels[1], cellRow);
			
			bitmaps.add(bm);
			count++;
			
		}
		
		this.bitmaps = bitmaps;
	}
	
	
	/**
	 * POPULATES A BITMAP FOR A SINGLE LEVEL OF CACHE CELLS IN A CLIQUE
	 * 
	 * @author sapmitra
	 * @param temporaryBitmap
	 * @param currentLevel
	 * @param spatialLevel
	 * @param temporalLevel
	 * @param cellsInThisLevel
	 */
	public CorrectedBitmap createBitmapForCLique(GeospatialFileSystem fs, int currentLevel, int spatialLevel, int temporalLevel, List<CacheCell> cellsInThisLevel) {
		
		CorrectedBitmap temporaryBitmap = new CorrectedBitmap();
		
		for(CacheCell cells : cellsInThisLevel) {
			
			int spatialSize = (int)java.lang.Math.pow(32, spatialLevel);
			
			String tokens[] = cells.getCellKey().split("\\$\\$");
			
			String choppedGeohash = tokens[0];
			String dateString = tokens[1];
			
			String timeTokens[] = dateString.split("-");
			
			// THE START TIME FOR THE BLOCK ASSOSSIATED WITH THIS CLIQUE
			long cliqueStartTimeStamp = GeoHash.getStartTimeStamp(timeTokens[0], timeTokens[1], timeTokens[2], timeTokens[3], fs.getTemporalType());
			
			long cellTimestamp = GeoHash.getStartTimeStamp(dateString, TemporalType.getTypeFromLevel(temporalLevel));
			
			// returns a number between 0 and 31 for single character
			long spatialIndex = GeoHash.hashToLong(choppedGeohash);
			
			DateTime startDate = new DateTime(cliqueStartTimeStamp, DateTimeZone.UTC);
			
			long temporalIndex = TemporalType.getTemporalIndex(startDate, cellTimestamp, temporalLevel);
			
			int bitIndex = (int)(temporalIndex*spatialSize + spatialIndex);
			
			temporaryBitmap.set(bitIndex);
				
			
		}
		
		return temporaryBitmap;
				
		
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

	public String getGeohashKey() {
		return geohashKey;
	}

	public void setGeohashKey(String geohashKey) {
		this.geohashKey = geohashKey;
	}

	public int getTotalCliqueSize() {
		return totalCliqueSize;
	}

	public void setTotalCliqueSize(int totalCliqueSize) {
		this.totalCliqueSize = totalCliqueSize;
	}

	public String getGeohashAntipode() {
		return geohashAntipode;
	}

	public void setGeohashAntipode(String geohashAntipode) {
		this.geohashAntipode = geohashAntipode;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	@Override
	public void serialize(SerializationOutputStream out) throws IOException {
		
		out.writeString(geohashKey);
		out.writeString(geohashAntipode);
		out.writeInt(direction);
		out.writeInt(totalCliqueSize);
		
		out.writeIntegerCollection(levels);
		
		out.writeInt(levels.size());
		
		for(List<CacheCell> cellRow: cells)
			out.writeSerializableCollection(cellRow);
		
	}
	
	@Deserialize
	public CliqueContainer(SerializationInputStream in) {
		
	}

	public List<CorrectedBitmap> getBitmaps() {
		return bitmaps;
	}

	public void setBitmaps(List<CorrectedBitmap> bitmaps) {
		this.bitmaps = bitmaps;
	}

	public NetworkDestination getReplicatedNode() {
		return replicatedNode;
	}

	public void setReplicatedNode(NetworkDestination replicatedNode) {
		this.replicatedNode = replicatedNode;
	}

	
	

}
