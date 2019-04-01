package galileo.graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import galileo.bmp.CorrectedBitmap;
import galileo.comm.TemporalType;
import galileo.fs.GeospatialFileSystem;
import galileo.net.NetworkDestination;
import galileo.serialization.ByteSerializable;
import galileo.serialization.SerializationException;
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
	
	private static final Logger logger = Logger.getLogger("galileo");
	
	public CliqueContainer(String key) {
		
		this.geohashKey = key;
		levels = new ArrayList<Integer>();
		cells = new ArrayList<List<CacheCell>>();
		
	}
	
	public String toString() {
		String ret = geohashKey;
		ret += "****"+levels +"****";
		ret += cells;
		
		return ret;
	}
	
	/**
	 * FOR A SPATIOTEMPORAL LEVEL, RETURN THE BITMAP AT THAT LEVEL
	 * @author sapmitra
	 * @return
	 */
	public CorrectedBitmap getBitmapAtLevel(int level) {
		
		CorrectedBitmap ret = null;
		
		int index = levels.indexOf(level);
		
		if(index >= 0) {
			
			return bitmaps.get(index);
			
		}
		
		return ret;
		
		
	}
	
	
	public List<CacheCell> getCacheCellsAtLevel(int level) {
		
		List<CacheCell> retCells = null;
		
		int index = levels.indexOf(level);
		
		if(index >= 0) {
			
			return cells.get(index);
			
		}
		
		return retCells;
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
			
			CorrectedBitmap bm = null;
			
			int[] levels = fs.getStCache().getSpatioTemporalResolutionsFromLevel(currentLevel);
			
			if(fs.isSubBlockLevel(levels[0], levels[1])) {
				
				// CREATE A SET OF TEMPORARY BITMAPS
				bm = createBitmapForClique(fs, levels[0], levels[1], cellRow);
				
				bitmaps.add(bm);
				
			} else {
				bitmaps.add(bm);
			}
			
			
			
			count++;
			
		}
		
		this.bitmaps = bitmaps;
		
		logger.info("RIKI: BITMAP CALCULATED: "+bitmaps);
	}
	
	
	/**
	 * POPULATES A BITMAP FOR A SINGLE LEVEL OF CACHE CELLS IN A CLIQUE.
	 * THIS IS TO BE MAINTAINED ON THE DISTRESSED NODE SIDE
	 * 
	 * @author sapmitra
	 * @param temporaryBitmap
	 * @param currentLevel
	 * @param spatialLevel
	 * @param temporalLevel
	 * @param cellsInThisLevel
	 */
	public CorrectedBitmap createBitmapForClique(GeospatialFileSystem fs, int spatialLevel, int temporalLevel, List<CacheCell> cellsInThisLevel) {
		
		CorrectedBitmap temporaryBitmap = new CorrectedBitmap();
		
		for(CacheCell cell : cellsInThisLevel) {
			
			String tokens[] = cell.getCellKey().split("\\$\\$");
			
			logger.info("RIKI: CACHE CELL KEY: "+cell.getCellKey());
			
			
			int fsSpatialResolution = fs.getGeohashPrecision();
			int fsTemporalResolution = fs.getTemporalType().getType();
			
			String choppedGeohash = tokens[1].substring(fsSpatialResolution);
			
			int spatialSize = (int)java.lang.Math.pow(32, choppedGeohash.length());
			
			String dateString = tokens[0];
			
			String timeTokens[] = dateString.split("-");
			
			// THE START TIME FOR THE BLOCK ASSOSSIATED WITH THIS CLIQUE
			long cliqueStartTimeStamp = GeoHash.getStartTimeStamp(timeTokens[0], timeTokens[1], timeTokens[2], timeTokens[3], fs.getTemporalType());
			
			long cellTimestamp = GeoHash.getStartTimeStamp(dateString, TemporalType.getTypeFromLevel(temporalLevel));
			
			// returns a number between 0 and 31 for single character
			
			if()
			long spatialIndex = GeoHash.hashToLong(choppedGeohash);
			
			DateTime startDate = new DateTime(cliqueStartTimeStamp, DateTimeZone.UTC);
			
			long temporalIndex = TemporalType.getTemporalIndex(startDate, cellTimestamp, temporalLevel);
			
			int bitIndex = (int)(temporalIndex*spatialSize + spatialIndex);
			
			temporaryBitmap.set(bitIndex);
				
			
		}
		temporaryBitmap.applyUpdates();
		
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
		
		//out.writeInt(levels.size());
		
		for(List<CacheCell> cellRow: cells)
			out.writeSerializableCollection(cellRow);
		
	}
	
	@Deserialize
	public CliqueContainer(SerializationInputStream in) throws IOException, SerializationException  {
		
		this.geohashKey = in.readString();
		this.geohashAntipode = in.readString();
		this.direction = in.readInt();
		this.totalCliqueSize = in.readInt();
		
		levels = new ArrayList<Integer>();
		
		in.readIntegerCollection(levels);
		
		cells = new ArrayList<List<CacheCell>>();
		
		for(int i=0; i< levels.size(); i++) {
			
			List<CacheCell> lCells = new ArrayList<>();
			
			in.readSerializableCollection(CacheCell.class, lCells);
			
			cells.add(lCells);
			
		}
		
		
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
