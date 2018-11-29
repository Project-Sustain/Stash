package galileo.dht;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import galileo.bmp.CorrectedBitmap;
import galileo.graph.SubBlockLevelBitmaps;

import java.util.Set;

public class RikiTest {
	
	public static void main1(String arg[]) throws FileNotFoundException, IOException {
		
		Map<String, Float> map = new HashMap<String, Float>();
        map.put("1@stKey1", 20f);
        map.put("2@stKey2", 45f);
        map.put("1@stKey3", 2f);
        map.put("3@stKey4", 67f);
        map.put("4@stKey5", 93f);
        map.put("4@stKey6", 11f);
        map.put("2@stKey7", 19f);
        map.put("2@stKey8", 1.1f);
        map.put("3@stKey9", 0.9f);
        map.put("3@stKey10", 77f);
        map.put("1@stKey11", 88f);
        
		
		RikiTest r = new RikiTest();
		r.getElementsToTrim(map,5);
		
	}
	
	/**
	 * 
	 * @author sapmitra
	 * @param cacheEntries
	 * @param entriesAllowed - The number of entries allowed in the cache
	 */
	public Map<Integer, List<String>> getElementsToTrim(Map<String, Float> cacheEntries, int entriesAllowed) {
		
		Set<Entry<String, Float>> set = cacheEntries.entrySet();
        List<Entry<String, Float>> list = new ArrayList<Entry<String, Float>>(set);
        Collections.sort( list, new Comparator<Map.Entry<String, Float>>()
        {
            public int compare( Map.Entry<String, Float> o1, Map.Entry<String, Float> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );
        
        int i=0;
        
        Map<Integer, List<String>> entriesToRemove = new HashMap<Integer, List<String>>();
        
        for(Map.Entry<String, Float> entry:list){
        	
        	i++;
        	
        	if(i > entriesAllowed) {
        		String key = entry.getKey();
        		
        		String[] tokens = key.split("@");
        		
        		int level = Integer.valueOf(tokens[0]);
        		String stKey = tokens[1];
        		
        		List<String> keys = entriesToRemove.get(level);
        		
        		if(keys == null) {
        			keys = new ArrayList<String>();
        			entriesToRemove.put(level, keys);
        		}
        		
        		keys.add(stKey);
        		
        	}
        }
        
        System.out.println(entriesToRemove);
		
		return entriesToRemove;
	}
	
	
	public static void main(String arg[]) {
		
		Map<String, SubBlockLevelBitmaps> populateMap = populateMap(5);
		
		RikiTest rt = new RikiTest();
		
		JSONObject objectToJSON = rt.objectToJSON(populateMap);
		
		System.out.println(objectToJSON.toString());
		
		
		Map<String, SubBlockLevelBitmaps> extractdMap = rt.jsonToObject(objectToJSON);
		
		System.out.println(extractdMap);
		
		for(String p : extractdMap.keySet()) {
			
			SubBlockLevelBitmaps subBlockLevelBitmaps = extractdMap.get(p);
			
			System.out.println(subBlockLevelBitmaps);
			
		}
		
	}
	
	
	public static Map<String, SubBlockLevelBitmaps> populateMap(int totals) {
		
		Map<String, SubBlockLevelBitmaps> blockBitmaps = new HashMap<String, SubBlockLevelBitmaps>();
		
		Random r = new Random();
		for(int k = 0; k< totals; k++) {
			
			SubBlockLevelBitmaps sbms = new SubBlockLevelBitmaps(2, 1, 4, 3);
			
			CorrectedBitmap[] spatioTemporalBitmaps = sbms.getSpatioTemporalBitmaps();
			
			for(int i=0; i < 3; i++) {
				spatioTemporalBitmaps[i] = new CorrectedBitmap();
				
				for(int j=0; j< 12; j++) {
					
					int ind = Math.abs(r.nextInt());
					spatioTemporalBitmaps[i].set(ind);
				}
				spatioTemporalBitmaps[i].applyUpdates();
				
			}
			
			blockBitmaps.put("myfile"+k, sbms);
			
		}
		
		return blockBitmaps;
	}
	
	
	
	public JSONObject objectToJSON(Map<String, SubBlockLevelBitmaps> blockBitmaps) {
		
		JSONObject state = new JSONObject();
		JSONArray sbMaps = new JSONArray();
		for(String path : blockBitmaps.keySet()) {
			
			SubBlockLevelBitmaps sbm = blockBitmaps.get(path);
			
			JSONObject jsonSBM = sbm.createJsonObject(path);
			
			sbMaps.put(jsonSBM);
		}
		state.put("subBlockBitmaps", sbMaps);
		return state;
		
	}
	
	public Map<String, SubBlockLevelBitmaps> jsonToObject(JSONObject state) {
		
		Map<String, SubBlockLevelBitmaps> blockBitmaps = new HashMap<String, SubBlockLevelBitmaps>();
		
		JSONArray sbMaps = state.getJSONArray("subBlockBitmaps");
		
		if (sbMaps != null && sbMaps.length() > 0) {
			
			for (int i = 0; i < sbMaps.length(); i++) {
				
				JSONObject jsonObject = sbMaps.getJSONObject(i);
				String path = jsonObject.getString("path");
				SubBlockLevelBitmaps sbm = new SubBlockLevelBitmaps();
				sbm.populateFromJson(jsonObject);
				blockBitmaps.put(path, sbm);
				
			}
		}
		
		return blockBitmaps;
	}
	

}
