package galileo.dht;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import galileo.util.GeoHash;
import galileo.util.Pair;
import galileo.util.PathFragments;

/**
 * 
 * @author sapmitra
 *
 */
/* This handles one fragment of a path */
public class VisualizationSummaryProcessor implements Runnable{
	
	private static final Logger logger = Logger.getLogger("galileo");
	
	/* Represents the path that will be queried. Records from all blocks in this path has been loaded here. */
	private List<String[]> featurePaths;
	private Query query;
	private GeoavailabilityGrid grid;
	private Bitmap queryBitmap;
	private GeospatialFileSystem gfs;
	private List<Integer> summaryPosns;
	private int spatialResolution;
	private int temporalResolution;
	
	private Map<String, SummaryStatistics[]> localSummary;
	
	
	/**
	 * 
	 * @param gfs
	 * @param featurePaths: all necessary records
	 * @param query
	 * @param grid
	 * @param queryBitmap
	 * @param temporalResolution 
	 * @param spatialResolution 
	 */
	public VisualizationSummaryProcessor(GeospatialFileSystem gfs, List<String[]> featurePaths, Query query, GeoavailabilityGrid grid, 
			Bitmap queryBitmap, List<Integer> summaryPosns, int spatialResolution, int temporalResolution) {
		
		this.featurePaths = featurePaths;
		this.query = query;
		this.grid = grid;
		this.queryBitmap = queryBitmap;
		this.gfs = gfs;
		
		this.summaryPosns = summaryPosns;
		this.localSummary = new HashMap<String, SummaryStatistics[]>();
		
		this.spatialResolution = spatialResolution;
		this.temporalResolution = temporalResolution;
	}
	
	/**
	 * Using the Feature attributes found in the provided Metadata, a path is
	 * created for insertion into the Metadata Graph.
	 */
	protected FeaturePath<String> createPath(String physicalPath, Metadata meta) {
		FeaturePath<String> path = new FeaturePath<String>(physicalPath, meta.getAttributes().toArray());
		return path;
	}
	
	/**
	 * This handles one fragment
	 */
	@Override
	public void run() {
		
		try{
			
			int latOrder = -1, lngOrder = -1, index = 0, temporalOrder = -1;
			for (Pair<String, FeatureType> columnPair : gfs.getFeatureList()) {
				if (columnPair.a.equalsIgnoreCase(gfs.getSpatialHint().getLatitudeHint()))
					latOrder = index++;
				else if (columnPair.a
						.equalsIgnoreCase(gfs.getSpatialHint().getLongitudeHint()))
					lngOrder = index++;
				else if (columnPair.a
						.equalsIgnoreCase(gfs.getTemporalHint()))
					temporalOrder = index++;
				else
					index++;
			}
			//logger.log(Level.INFO, "RIKI: LocalParallelQueryProcessor PATHS "+featurePaths);
			if (queryBitmap != null) {
				//logger.log(Level.INFO, "RIKI: LocalParallelQueryProcessor PATHS1 "+featurePaths);
				

				GeoavailabilityMap<String[]> geoMap = new GeoavailabilityMap<String[]>(grid);
				Iterator<String[]> pathIterator = this.featurePaths.iterator();
				while (pathIterator.hasNext()) {
					String[] features = pathIterator.next();
					float lat = Float.valueOf(features[latOrder]);
					float lon = Float.valueOf(features[lngOrder]);
					if (!Float.isNaN(lat) && !Float.isNaN(lon))
						geoMap.addPoint(new Coordinates(lat, lon), features);
					pathIterator.remove();
				}
				/*each string[] is a line of record*/
				//logger.log(Level.INFO, "RIKI: LocalParallelQueryProcessor PATHS2 "+featurePaths);
				for (List<String[]> paths : geoMap.query(queryBitmap).values()) 
					this.featurePaths.addAll(paths);
				//logger.log(Level.INFO, "RIKI: LocalParallelQueryProcessor PATHS3 "+featurePaths);
				
			}
			if (this.featurePaths.size() > 0) {
				//logger.log(Level.INFO, "RIKI: LocalParallelQueryProcessor PATHS4 "+featurePaths);
				MetadataGraph temporaryGraph = new MetadataGraph();
				Iterator<String[]> pathIterator = this.featurePaths.iterator();
				while (pathIterator.hasNext()) {
					String[] features = pathIterator.next();
					try {
						Metadata metadata = new Metadata();
						FeatureSet featureset = new FeatureSet();
						for (int i = 0; i < features.length; i++) {
							Pair<String, FeatureType> pair = gfs.getFeatureList().get(i);
							if (pair.b == FeatureType.FLOAT)
								featureset.put(new Feature(pair.a, Float.valueOf(features[i])));
							if (pair.b == FeatureType.INT)
								featureset.put(new Feature(pair.a, Integer.valueOf(features[i])));
							if (pair.b == FeatureType.LONG)
								featureset.put(new Feature(pair.a, Long.valueOf(features[i])));
							if (pair.b == FeatureType.DOUBLE)
								featureset.put(new Feature(pair.a, Double.valueOf(features[i])));
							if (pair.b == FeatureType.STRING)
								featureset.put(new Feature(pair.a, features[i]));
						}
						metadata.setAttributes(featureset);
						Path<Feature, String> featurePath = createPath("/nopath", metadata);
						temporaryGraph.addPath(featurePath);
					} catch (Exception e) {
						logger.warning(e.getMessage());
					}
					pathIterator.remove();
				}
				
				List<Path<Feature, String>> evaluatedPaths;
				
				if(query != null) {
					evaluatedPaths = temporaryGraph.evaluateQuery(query);
					
				} else {
					evaluatedPaths = temporaryGraph.getAllPaths();
				}
				
				// EACH OF THE EVALUATED PATHS ARE TO BE USED TO SUMMARISE
				// CREATE KEY FROM THE SPATIOTEMPORAL FEATURES
				// CREATE A MAP OF KEY TO SUMMARY[]
				
				for (Path<Feature, String> path : evaluatedPaths) {
					
					String[] featureValues = new String[summaryPosns.size()];
					int indx = 0;
					
					float sp1 = 0;
					float sp2 = 0;
					float tmp = 0;
					
					for (Feature feature : path.getLabels()) {
						
						if(latOrder == indx) {
							sp1 = feature.getFloat();
						}
						
						if(lngOrder == indx) {
							sp2 = feature.getFloat();
						}
						
						if(temporalOrder == indx) {
							tmp = feature.getFloat();
						}
						
						if(summaryPosns.contains(indx)) {
							int ind = summaryPosns.indexOf(indx);
							featureValues[ind] = feature.getString();
							
						}
						indx++;
					}
					
					// evaluate key using the resolutions
					String summaryKey = GeoHash.getSummaryKey(sp1, sp2, tmp, spatialResolution, temporalResolution);
					
					
					
					this.featurePaths.add(featureValues);
				}
				
			}
			
			
		} catch (BitmapException e) {
			logger.log(Level.SEVERE, "Something went wrong while querying the filesystem.", e);
		}
	
		
		
	}

	public List<String[]> getFeaturePaths() {
		return featurePaths;
	}

	public void setFeaturePaths(List<String[]> featurePaths) {
		this.featurePaths = featurePaths;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public GeoavailabilityGrid getGrid() {
		return grid;
	}

	public void setGrid(GeoavailabilityGrid grid) {
		this.grid = grid;
	}

	public Bitmap getQueryBitmap() {
		return queryBitmap;
	}

	public void setQueryBitmap(Bitmap queryBitmap) {
		this.queryBitmap = queryBitmap;
	}

	public GeospatialFileSystem getGfs() {
		return gfs;
	}

	public void setGfs(GeospatialFileSystem gfs) {
		this.gfs = gfs;
	}

	public int[] getSummaryPosns() {
		return summaryPosns;
	}

	public void setSummaryPosns(int[] summaryPosns) {
		this.summaryPosns = summaryPosns;
	}

}
