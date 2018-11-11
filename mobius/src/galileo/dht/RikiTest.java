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
import java.util.Set;

public class RikiTest {
	
	public static void main(String arg[]) throws FileNotFoundException, IOException {
		
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
	

}
