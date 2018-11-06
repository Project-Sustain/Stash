package galileo.dht;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import galileo.bmp.Bitmap;
import galileo.bmp.GeoavailabilityGrid;
import galileo.bmp.GeoavailabilityQuery;
import galileo.fs.GeospatialFileSystem;
import galileo.graph.SummaryStatistics;
import galileo.util.PathRequirements;

/**
 * 
 * @author sapmitra
 *
 *	HANDLES ALL BLOCKS WITH THE SAME BLOCK KEY : TIME$$SPACE
 */

public class VisualizationQueryProcessor implements Runnable{
	
private static final Logger logger = Logger.getLogger("galileo");
	
	/* Represents the path that will be queried. All blocks in this path will be looked into. */

	//private Path<Feature, String> path;
	private List<String> blocks;
	private GeoavailabilityQuery geoQuery;
	private GeoavailabilityGrid grid;
	private GeospatialFileSystem fs;
	private Bitmap queryBitmap;
	private int spatialResolution;
	private int temporalResolution;
	private List<Integer> summaryPosns;
	private boolean needMoreGrouping;
	private String blocksKey;
	private PathRequirements pathReqs;
	
	// BLOCK KEY WITH A LIST OF SUMMARIES - ONE FOR EACH REQUESTED FEATURES
	private Map<String,SummaryStatistics[]> resultSummaries;
	
	// FOR SUPER RESOLUTION
	public VisualizationQueryProcessor(GeospatialFileSystem fs1, List<String> blocks, GeoavailabilityQuery gQuery,
			GeoavailabilityGrid grid, Bitmap queryBitmap, int spatialResolution, int temporalResolution, List<Integer> summaryPosns, 
			boolean needMoreGrouping, String blockKey) {
		
		this.fs = fs1;
		this.geoQuery = gQuery;
		this.grid = grid;
		this.queryBitmap = queryBitmap;
		this.blocks = blocks;
		this.spatialResolution = spatialResolution;
		this.temporalResolution = temporalResolution;
		this.summaryPosns = summaryPosns;
		this.needMoreGrouping = needMoreGrouping;
		this.blocksKey = blockKey;
		
	}

	// FOR SUB RESOLUTION
	public VisualizationQueryProcessor(GeospatialFileSystem fs, PathRequirements pathReqs,
			GeoavailabilityQuery geoQuery, GeoavailabilityGrid blockGrid, Bitmap queryBitmap, int spatialResolution,
			int temporalResolution, List<Integer> summaryPosns, boolean needMoreGrouping, String blockKey) {
		
		this.fs = fs;
		this.geoQuery = geoQuery;
		this.grid = blockGrid;
		this.queryBitmap = queryBitmap;
		this.pathReqs = pathReqs;
		this.spatialResolution = spatialResolution;
		this.temporalResolution = temporalResolution;
		this.summaryPosns = summaryPosns;
		this.needMoreGrouping = needMoreGrouping;
		this.blocksKey = blockKey;
		
	}

	@Override
	public void run() {
		
		try {
			
			
			if(!needMoreGrouping) {
				// SUPER-BLOCK LEVEL
				
				/* This thread is created one for each path */
				this.resultSummaries = this.fs.queryLocalSummaryForSuperResolution(this.blocks, this.geoQuery, this.grid, this.queryBitmap,
						spatialResolution, temporalResolution, summaryPosns, needMoreGrouping, blocksKey);
			} else {
				// SUB-BLOCK LEVEL
				
				/* This thread is created one for each path */
				this.resultSummaries = this.fs.queryLocalSummaryForSubResolution(this.pathReqs, this.geoQuery, this.grid, this.queryBitmap,
						spatialResolution, temporalResolution, summaryPosns, needMoreGrouping, blocksKey);
			}
			
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			logger.log(Level.SEVERE, "Something went wrong while querying FS2 for neighbor block. No results obtained.\n" + e.getMessage());
		}
		
		
	}

	public Map<String,SummaryStatistics[]> getResultSummaries() {
		return resultSummaries;
	}

	public void setResultSummaries(Map<String,SummaryStatistics[]> resultSummaries) {
		this.resultSummaries = resultSummaries;
	}

	public List<String> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<String> blocks) {
		this.blocks = blocks;
	}

	public GeoavailabilityQuery getGeoQuery() {
		return geoQuery;
	}

	public void setGeoQuery(GeoavailabilityQuery geoQuery) {
		this.geoQuery = geoQuery;
	}

	public GeoavailabilityGrid getGrid() {
		return grid;
	}

	public void setGrid(GeoavailabilityGrid grid) {
		this.grid = grid;
	}

	public GeospatialFileSystem getFs1() {
		return fs;
	}

	public void setFs1(GeospatialFileSystem fs1) {
		this.fs = fs1;
	}

	public Bitmap getQueryBitmap() {
		return queryBitmap;
	}

	public void setQueryBitmap(Bitmap queryBitmap) {
		this.queryBitmap = queryBitmap;
	}

	public int getSpatialResolution() {
		return spatialResolution;
	}

	public void setSpatialResolution(int spatialResolution) {
		this.spatialResolution = spatialResolution;
	}

	public int getTemporalResolution() {
		return temporalResolution;
	}

	public void setTemporalResolution(int temporalResolution) {
		this.temporalResolution = temporalResolution;
	}

	public static Logger getLogger() {
		return logger;
	}

	public List<Integer> getSummaryPosns() {
		return summaryPosns;
	}

	public void setSummaryPosns(List<Integer> summaryPosns) {
		this.summaryPosns = summaryPosns;
	}

}
