/*
Copyright (c) 2014, Colorado State University
All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

This software is provided by the copyright holders and contributors "as is" and
any express or implied warranties, including, but not limited to, the implied
warranties of merchantability and fitness for a particular purpose are
disclaimed. In no event shall the copyright holder or contributors be liable for
any direct, indirect, incidental, special, exemplary, or consequential damages
(including, but not limited to, procurement of substitute goods or services;
loss of use, data, or profits; or business interruption) however caused and on
any theory of liability, whether in contract, strict liability, or tort
(including negligence or otherwise) arising in any way out of the use of this
software, even if advised of the possibility of such damage.
*/

package galileo.bmp;

import java.util.Arrays;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import com.googlecode.javaewah.EWAHCompressedBitmap;

/**
 * A thin wrapper around {@link com.googlecode.javaewah.EWAHCompressedBitmap}
 * to enable us to change bitmap implementations if need be.
 *
 * @author malensek
 */
public class CorrectedBitmap implements Iterable<Integer> {
	
	private static final Logger logger = Logger.getLogger("galileo");
	private SortedSet<Integer> pendingUpdates = new TreeSet<>();
	
    public EWAHCompressedBitmap bmp;

    public CorrectedBitmap() {
        bmp = new EWAHCompressedBitmap();
    }

    public CorrectedBitmap(EWAHCompressedBitmap bmp) {
        this.bmp = bmp;
    }

    /**
     * Sets the specified bit(s) in the index.
     *
     * @param bit bit to set (to 1, 'on', etc.)
     *
     * @return true if the bit could be set, false otherwise.  In some cases,
     * the underlying bitmap implementation may disallow updates, causing this
     * method to return false.
     */
    public void set(int bit) {
    	pendingUpdates.add(bit);
    }
    
    public JSONObject createJsonObject() {
    	
    	JSONObject jarray = new JSONObject();
    	jarray.put("bitmap",Arrays.asList(bmp.toArray()));
    	return jarray;
    }
    
    public void populateFromJson(JSONObject jsonObj) {
    	
    	JSONArray inds = jsonObj.getJSONArray("bitmap");
		for (int i = 0; i < inds.length(); i++)
			pendingUpdates.add(inds.getInt(i));
		applyUpdates();
    	
    }
    
    
    
    /**
     * Creates a bitmap of updates and returns it
     * @author sapmitra
     * @return
     */
    /*public EWAHCompressedBitmap getBitmap() {
    	
    	EWAHCompressedBitmap updateBitmap = new EWAHCompressedBitmap();
    	
		for (int i : pendingUpdates) {
			updateBitmap.set(i);
		}
		
		return updateBitmap;
    }*/
    
    public EWAHCompressedBitmap getBitmap() {
    	
		return bmp;
    }
    
    /**
     * Apply updates occassionally from the pending updates object
     * @author sapmitra
     */
    public void applyUpdates() {
    	
    	EWAHCompressedBitmap updateBitmap = new EWAHCompressedBitmap();
		for (int i : pendingUpdates) {
			updateBitmap.set(i);
		}
		
		bmp = bmp.or(updateBitmap);
		pendingUpdates.clear();
		
    }
    
    /**
     * Apply updates from another bitmap
     * @author sapmitra
     */
    public void applyUpdates(EWAHCompressedBitmap updateBitmap) {
		
		bmp = bmp.or(updateBitmap);
		//pendingUpdates.clear();
		
    }
    
    
    
    public EWAHCompressedBitmap or(CorrectedBitmap otherBitmap) {
        return getBitmap().or(otherBitmap.getBitmap());
    }

    public EWAHCompressedBitmap xor(CorrectedBitmap otherBitmap) {
        return getBitmap().xor(otherBitmap.getBitmap());
    }
    
    public EWAHCompressedBitmap andNot(CorrectedBitmap otherBitmap) {
    	return getBitmap().andNot(otherBitmap.getBitmap());
    }

    public EWAHCompressedBitmap and(CorrectedBitmap otherBitmap) {
        return getBitmap().and(otherBitmap.getBitmap());
    }

    public boolean intersects(CorrectedBitmap otherBitmap) {
        return getBitmap().intersects(otherBitmap.getBitmap());
    }

    public int[] toArray() {
        return bmp.toArray();
    }
    
    public String toString() {
    	String retStr = "[";
    	for(int i : bmp.toArray()) {
    		retStr+=i+",";
    	}
    	return retStr+"]";
    }
    
    
    public static CorrectedBitmap fromBytes(byte[] bytes, int x, int y,
            int width, int height,
            int canvasWidth, int canvasHeight) {

        EWAHCompressedBitmap bmp = new EWAHCompressedBitmap();
        //if the bounded rectangle of the polygon does not intersect the grid canvas
        if(x >= canvasWidth || y >= canvasHeight || x + width < 0 || y + height < 0) {
        	for(int line = 0; line < canvasHeight; line++)
        		bmp.addStreamOfEmptyWords(false, canvasWidth/64);
        	return new CorrectedBitmap(bmp);
        }
        
        int intersectedX = (x < 0) ? 0 : x;
        int intersectedY = (y < 0) ? 0 : y;
        //intersection width of the bounding rectangle and the grid
        int intersectedWidth = (x < 0) ? ((width + x > canvasWidth) ? canvasWidth : width + x) 
        							: ((x + width > canvasWidth)? canvasWidth - x : width);
        if(intersectedWidth > canvasWidth)
        	intersectedWidth = canvasWidth;

        /* Shift through the bitmap to the first place we need to draw. */
        bmp.addStreamOfEmptyWords(false, canvasWidth * intersectedY / 64);
        
        int shift = intersectedX % 64;
        if(intersectedWidth % 64 != 0)
        	intersectedWidth += shift;
        int skipWords = (intersectedX - shift) / 64; //definitely positive or zero
        /* Convert raw image data to binary words */
        long[] words = bytesToWords(bytes);

        int wordsPerLine = width / 64;
        for (int line = intersectedY; line < canvasHeight; line++) {
        	int transformedLine = line - y;
        	int transformedX = intersectedX - x;
            int wordIdx = transformedLine * wordsPerLine + transformedX/64;
            bmp.addStreamOfEmptyWords(false, skipWords);
            if(transformedLine >= 0 && transformedLine < height)
            	bmp.addStreamOfLiteralWords(words, wordIdx, intersectedWidth/64);
            else
            	bmp.addStreamOfEmptyWords(false, intersectedWidth/64);
            bmp.addStreamOfEmptyWords(false, (canvasWidth - intersectedWidth - skipWords*64)/64);
        }
        return new CorrectedBitmap(bmp);
    }

    /**
     * Do a direct conversion of a byte array to 64-bit words (longs).
     *
     * @return an array of 64-bit words.
     */
    private static long[] bytesToWords(byte[] bytes) {
        long[] words = new long[bytes.length / 64];
        for (int i = 0; i < bytes.length / 64; ++i) {
            for (int j = 0; j < 64; ++j) {
                if (bytes[i * 64 + j] < 0) {
                    words[i] |= (1l << j);
                }
            }
        }
        return words;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bmp == null) ? 0 : bmp.hashCode());
		return result;
	}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        CorrectedBitmap b = (CorrectedBitmap) obj;
        return this.bmp.equals(b.bmp);
    }

    @Override
    public Iterator<Integer> iterator() {
        return bmp.iterator();
    }
    
    
    public static void main(String arg[]) {
    	
    	CorrectedBitmap m1 = new CorrectedBitmap();
    	CorrectedBitmap m2 = new CorrectedBitmap();
    	
    	m1.set(7);
    	m1.set(0);
    	m1.set(1);
    	m1.set(3);
    	//m1.set(7);
    	
    	
    	m2.set(3);
    	
    	
    	EWAHCompressedBitmap andNot = m1.andNot(m2);
    	int[] array = andNot.toArray();
    	System.out.println(array.length);
    	
    	
    	
    }
}
