package galileo.graph;

import com.googlecode.javaewah.EWAHCompressedBitmap;

public class SubBlockLevelBitmaps {
	
	private int spatialLevels;
	private int temporalLevels;
	private EWAHCompressedBitmap[] spatialBitmaps;
	private EWAHCompressedBitmap[] temporalBitmaps;
	
	public SubBlockLevelBitmaps(int spatialSubLevels, int temporalSubLevels) {
		
		this.spatialLevels = spatialSubLevels;
		this.temporalLevels = temporalSubLevels;
		if(spatialLevels > 0)
			spatialBitmaps = new EWAHCompressedBitmap[spatialLevels];
		if(temporalLevels > 0)
			temporalBitmaps = new EWAHCompressedBitmap[temporalLevels];
		
	}

}
