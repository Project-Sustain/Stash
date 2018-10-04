package galileo.graph;

import org.joda.time.DateTime;

import galileo.bmp.Bitmap;
import galileo.comm.TemporalType;
import galileo.util.GeoHash;

public class SubBlockLevelBitmaps {
	
	// Actual Number of sub-levels. 
	private int spatialFSLevel;
	private int temporalFSLevel;
	
	// Number of sub block spatial levels
	private int spatialLevels;
	// Number of sub block level temporalLevels
	private int temporalLevels;
	private Bitmap[] spatioTemporalBitmaps;
	
	public SubBlockLevelBitmaps(int spatialSubLevels, int temporalSubLevels, int geohashPrecision, int temporalFSLevel) {
		
		this.spatialLevels = spatialSubLevels;
		this.temporalLevels = temporalSubLevels;
		this.spatialFSLevel = geohashPrecision;
		this.temporalFSLevel = temporalFSLevel;
		this.spatioTemporalBitmaps = new Bitmap[(spatialLevels+1)*(temporalLevels+1)];
		
	}
	
	/**
	 * RETURNS THE BITMAP FOR A REQUESTED SPATIOTEMPORAL LEVEL
	 * @author sapmitra
	 * @param spatialLevel
	 * @param temporalLevel
	 * @return
	 */
	public Bitmap getBitMapForParticularLevel(int spatialLevel, int temporalLevel) {
		int indx = getMapIndex(spatialLevel, temporalLevel);
		
		return spatioTemporalBitmaps[indx];
		
	}
	
	private int getMapIndex(int spatialLevel, int temporalLevel) {
		int spLvl = spatialLevel - spatialFSLevel;
		int tLvl = temporalLevel - temporalFSLevel;
		
		int indx = (tLvl+1)*spatialFSLevel + (spLvl+1);
		return indx;
	}
	
	
	public void populateBitmapUsingRecord(DateTime startTime, long recordTimestamp, String choppedGeohash) {
		
		
		// Update each bitmap corresponding to a record
		for(int i=0; i<= spatialLevels; i++) {
			
			for(int j=0; j <= temporalLevels; j++) {
				
				if(i==0 && j == 0) {
					continue;
				}
				
				int currentSpatialLevel = spatialFSLevel+i;
				int currentTemporalLevel = temporalFSLevel+j;
				
				int indx = getMapIndex(currentSpatialLevel, currentTemporalLevel);
				
				long spatialIndex = 0;
				
				// if we do not go down the spatial resolution but only in temporal resolution
				if(i > 0) {
					
					String partialGeohash = choppedGeohash.substring(0, i+1);
					
					// returns a number between 0 and 31 for single character
					spatialIndex = GeoHash.hashToLong(partialGeohash);
					
				}
				
				long temporalIndex = 0;
				
				if(j > 0)
					temporalIndex = TemporalType.getTemporalIndex(startTime, recordTimestamp, currentTemporalLevel);
				
				Bitmap bm = spatioTemporalBitmaps[indx];
				
				if(bm == null) {
					bm = new Bitmap();
					spatioTemporalBitmaps[indx] = bm;
				}
				
				int spatialSize = (int)java.lang.Math.pow(32, i);
				
				int spatiotemporalIndex = (int)(temporalIndex*spatialSize + spatialIndex);
				
				bm.set(spatiotemporalIndex);
				
			}
		}
		
	}
	
	/**
	 * Given a bitmap index and resolution, this returns the key for a cell
	 * @author sapmitra
	 * @param bitmapIndex
	 * @param resolution
	 * @return
	 */
	public static String getKeyFromBitmapIndex(int bitmapIndex, int sresolution, int tresolution, int sFSLevel, int tFSLevels) {
		
		int spatialSize = (int)java.lang.Math.pow(32, sresolution - sFSLevel);
		
		int spatialIndex = bitmapIndex%spatialSize;
		int temporalIndex = bitmapIndex/spatialSize;
		
		//GeoHash.hashToLong(hash);
		
		return null;
	}
	
	
	
	public static void main(String arg[]) {
		
		//long hashToLong = GeoHash.hashToLong("0");
		//System.out.println("0 "+hashToLong+GeoHash.fromLongToString(hashToLong));
		
		
		long hashToLong = GeoHash.hashToLong("cx");
		System.out.println("cx "+hashToLong+" "+GeoHash.fromLongToString(hashToLong));
		
		
		//hashToLong = GeoHash.hashToLong("f");
		//System.out.println("f "+hashToLong+GeoHash.fromLongToString(hashToLong));
		//hashToLong = GeoHash.hashToLong("g");
		//System.out.println("g "+hashToLong+GeoHash.fromLongToString(hashToLong));
		//hashToLong = GeoHash.hashToLong("u");
		//System.out.println("u "+hashToLong+GeoHash.fromLongToString(hashToLong));
		//hashToLong = GeoHash.hashToLong("z");
		//System.out.println("z "+hashToLong+GeoHash.fromLongToString(hashToLong));
		
	}

}
