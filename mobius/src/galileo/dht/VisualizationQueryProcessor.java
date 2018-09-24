package galileo.dht;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import galileo.bmp.Bitmap;
import galileo.bmp.BitmapException;
import galileo.bmp.GeoavailabilityGrid;
import galileo.bmp.GeoavailabilityMap;
import galileo.bmp.GeoavailabilityQuery;
import galileo.dataset.Coordinates;
import galileo.dataset.Metadata;
import galileo.dataset.feature.Feature;
import galileo.dataset.feature.FeatureSet;
import galileo.dataset.feature.FeatureType;
import galileo.fs.GeospatialFileSystem;
import galileo.graph.FeaturePath;
import galileo.graph.MetadataGraph;
import galileo.graph.Path;
import galileo.graph.SummaryStatistics;
import galileo.query.Query;
import galileo.util.Pair;
import galileo.util.PathFragments;

/**
 * 
 * @author sapmitra
 *
 */
/* This handles one fragment of a path */
public class VisualizationQueryProcessor implements Runnable{
	
private static final Logger logger = Logger.getLogger("galileo");
	
	/* Represents the path that will be queried. Allblocks in this path will be looked into. */
	//private Path<Feature, String> path;
	private List<String> blocks;
	private GeoavailabilityQuery geoQuery;
	private GeoavailabilityGrid grid;
	private GeospatialFileSystem fs1;
	private Bitmap queryBitmap;
	private int spatialResolution;
	private int temporalResolution;
	private List<String> reqFeatures;
	
	private Map<String,SummaryStatistics[]> resultSummaries;
	
	
	public VisualizationQueryProcessor(GeospatialFileSystem fs1, List<String> blocks, GeoavailabilityQuery gQuery,
			GeoavailabilityGrid grid, Bitmap queryBitmap, int spatialResolution, int temporalResolution, List<String> reqFeatures) {
		
		this.fs1 = fs1;
		this.geoQuery = gQuery;
		this.grid = grid;
		this.queryBitmap = queryBitmap;
		this.blocks = blocks;
		this.spatialResolution = spatialResolution;
		this.temporalResolution = temporalResolution;
		this.reqFeatures = reqFeatures;
		
	}

	@Override
	public void run() {
		
		try {
			
			/* This thread is created one for each path */
			this.resultSummaries = this.fs1.queryLocalSummary(this.blocks, this.geoQuery, this.grid, this.queryBitmap,
					spatialResolution, temporalResolution, reqFeatures);
			
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
		return fs1;
	}

	public void setFs1(GeospatialFileSystem fs1) {
		this.fs1 = fs1;
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

	public List<String> getReqFeatures() {
		return reqFeatures;
	}

	public void setReqFeatures(List<String> reqFeatures) {
		this.reqFeatures = reqFeatures;
	}

}
