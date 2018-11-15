/*
Copyright (c) 2013, Colorado State University
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

package galileo.dht;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import galileo.dataset.Coordinates;
import galileo.dataset.Metadata;
import galileo.dataset.SpatialProperties;
import galileo.dataset.SpatialRange;
import galileo.dataset.TemporalProperties;
import galileo.dataset.feature.Feature;
import galileo.dht.hash.BalancedHashRing;
import galileo.dht.hash.ConstrainedGeohash;
import galileo.dht.hash.HashException;
import galileo.dht.hash.HashRing;
import galileo.dht.hash.HashTopologyException;
import galileo.dht.hash.SHA1;
import galileo.util.GeoHash;

/**
 * Implements a spatial partitioner that creates a two-tiered hierarchical DHT.
 *
 * @author malensek
 */
public class StandardDHTPartitioner extends Partitioner<Metadata> {

	private static final Logger logger = Logger.getLogger("galileo");

	private ConstrainedGeohash nodeHash;
	private BalancedHashRing<Metadata> nodeHashRing;
	private Map<BigInteger, BalancedHashRing<byte[]>> nodeHashRings = new HashMap<>();
	private Map<BigInteger, NodeInfo> nodePositions = new HashMap<>();
	private int spatialpartitioningType;

	public StandardDHTPartitioner(StorageNode storageNode, NetworkInfo network, int spatialHashType)
			throws PartitionException, HashException, HashTopologyException {

		super(storageNode, network);

		spatialpartitioningType = spatialHashType;
		String[] geohashes = null;
		String[] geohashes_2char = {"b","c","f","g","u","v","y","z","8","9","d","e","s","t","w","x",
				"2","3","6","7","k","m","q","r","0","1","4","5","h","j","n","p"};

		Arrays.sort(geohashes_2char);
		
		if(spatialHashType == 1) {
			// Geohashes for US region.
			
			geohashes = geohashes_2char;
		} else if(spatialHashType > 1) {
		
			geohashes = generateGeohashes(geohashes_2char, spatialHashType);
		} else {
			logger.severe("GEOHASH PARTITIONING FAILED. INVALID LENGTH: "+spatialHashType);
		}
		
		Arrays.sort(geohashes);
		
		nodeHash = new ConstrainedGeohash(geohashes);
		nodeHashRing = new BalancedHashRing<>(nodeHash);
		
		List<NodeInfo> nodes = network.getAllNodes();

		if (nodes.size() == 0) {
			throw new PartitionException("At least one node must exist in "
					+ "the current network configuration to use this " + "partitioner.");
		}

		for (NodeInfo node : nodes) {
			placeNode(node);
		}
		
		Map<BigInteger, List<String>> geohashMap = new HashMap<BigInteger, List<String>>();
		for(String g : geohashes) {
			BigInteger pos = nodeHash.getHashMappings().get(g);
			
			List<String> ghs = geohashMap.get(pos);
			
			if(ghs == null) {
				ghs = new ArrayList<String>();
				geohashMap.put(pos, ghs);
			}
			
			ghs.add(g);
		}
		
		
		logger.info("THE GEOHASH PARTITIONING IS AS FOLLOWS: \n");
		
		for(BigInteger b: geohashMap.keySet()) {
			logger.info(b + "::" + geohashMap.get(b));
		}
	}
	
	private static String[] generateGeohashes(String[] geohashes_2char, int spatialHashType) {
		List<String> allGeoHashes = new ArrayList<String>(Arrays.asList(geohashes_2char));
		
		for(int i = 1; i < spatialHashType; i++) {
			
			List<String> currentGeohashes = new ArrayList<String>();
			
			for(String geoHash : allGeoHashes) {
				
				
				SpatialRange range1 = GeoHash.decodeHash(geoHash);
				
				Coordinates c1 = new Coordinates(range1.getLowerBoundForLatitude(), range1.getLowerBoundForLongitude());
				Coordinates c2 = new Coordinates(range1.getUpperBoundForLatitude(), range1.getLowerBoundForLongitude());
				Coordinates c3 = new Coordinates(range1.getUpperBoundForLatitude(), range1.getUpperBoundForLongitude());
				Coordinates c4 = new Coordinates(range1.getLowerBoundForLatitude(), range1.getUpperBoundForLongitude());
				
				ArrayList<Coordinates> cs1 = new ArrayList<Coordinates>();
				cs1.add(c1);cs1.add(c2);cs1.add(c3);cs1.add(c4);
				
				currentGeohashes.addAll(Arrays.asList(GeoHash.getIntersectingGeohashesForConvexBoundingPolygon(cs1, i+1)));
				
			}
			allGeoHashes = currentGeohashes;
			
		}
		Collections.shuffle(allGeoHashes);
		String[] returnArray = allGeoHashes.toArray(new String[allGeoHashes.size()]);
		return returnArray;
	}

	private void placeNode(NodeInfo node) throws HashException, HashTopologyException {
		
		BigInteger nodePosition = nodeHashRing.addNode(null);

		logger.info(String.format("Node [%s] placed at position %d", node, nodePosition));

		if (nodePositions.get(nodePosition) == null) {
			nodePositions.put(nodePosition, node);
		}
		
	}

	/**
	 * Used for storage of a single block
	 * metadata name is just the geohash
	 */
	@Override
	public NodeInfo locateData(Metadata metadata) throws HashException, PartitionException {

		BigInteger node = nodeHashRing.locate(metadata);
		NodeInfo info = nodePositions.get(node);
		
		if (info == null) {
			throw new PartitionException("Could not locate specified data");
		}
		return info;
	}

	/**
	 * Used to locate a spatiotemporal query region
	 */
	@Override
	public List<NodeInfo> findDestinations(Metadata data) throws HashException, PartitionException {
		return null;
	}
	
	
	/**
	 * Querying based on a few required geohashes
	 * 
	 * @author sapmitra
	 * @param geohashes
	 * @return
	 * @throws HashException
	 * @throws PartitionException
	 */
	public List<NodeInfo> findDestinations(List<Coordinates> polygon) throws HashException, PartitionException {
		
		if (polygon == null || polygon.size() == 0)
			return network.getAllNodes();

		Set<NodeInfo> destinations = new HashSet<NodeInfo>();
		if (polygon.size() > 0) {
			String[] geohashes = GeoHash.getIntersectingGeohashes(polygon,spatialpartitioningType);
			for (String hash : geohashes) {
				Metadata metadata = new Metadata();
				metadata.setSpatialProperties(new SpatialProperties(GeoHash.decodeHash(hash)));
				
				BigInteger nodeId = nodeHashRing.locate(metadata);
				
				NodeInfo node = nodePositions.get(nodeId);
				
				destinations.add(node);
			}
		}
		
		return new ArrayList<NodeInfo>(destinations);
		
	}
	
	/**
	 * Querying based on a few required geohashes
	 * 
	 * @author sapmitra
	 * @param geohashes
	 * @return
	 * @throws HashException
	 * @throws PartitionException
	 */
	/*public List<NodeInfo> findDestinations(List<String> geohashes) throws HashException, PartitionException {
		
		
		if (geohashes == null || geohashes.size() == 0)
			return network.getAllNodes();

		Set<NodeInfo> destinations = new HashSet<NodeInfo>();
		if (geohashes.size() > 0) {
			
			for (String hash : geohashes) {
				Metadata metadata = new Metadata();
				metadata.setSpatialProperties(new SpatialProperties(GeoHash.decodeHash(hash)));
				
				BigInteger nodeId = nodeHashRing.locate(metadata);
				
				NodeInfo node = nodePositions.get(nodeId);
				
				destinations.add(node);
			}
		}
		
		return new ArrayList<NodeInfo>(destinations);
	}*/

	@Override
	public List<NodeInfo> findDestinationsForFS2(SpatialProperties searchSp, List<TemporalProperties> tprops,
			int geohashPrecision, String[] validNeighbors) throws HashException, PartitionException {
		// TODO Auto-generated method stub
		return null;
	}
	


	/*public static void main(String arg[]) throws PartitionException, HashException, HashTopologyException {
		
		NetworkInfo network = new NetworkInfo();
		
		GroupInfo g = new GroupInfo("gr");
		network.addGroup(g);
		
		int port = 5634;
		
		for(int i=1; i <= 10; i++) {
			
			NodeInfo n = new NodeInfo("lattice-"+i,	 port);
			g.addNode(n);
		}
		
		StandardDHTPartitioner std = new StandardDHTPartitioner(null, network, 2);
		
		String[] geohashes_2char = {"b","c","f","g","u","v","y","z","8","9","d","e","s","t","w","x",
				"2","3","6","7","k","m","q","r","0","1","4","5","h","j","n","p"};
		
		String[] geohashes = generateGeohashes(geohashes_2char, 2);
		
		for(String gg: geohashes) {
			Metadata m = new Metadata();
			m.setSpatialProperties(new SpatialProperties(GeoHash.decodeHash(gg)));
			
			NodeInfo locateData = std.locateData(m);
			
			System.out.println(gg+"==="+locateData);
		}
		
		Metadata m = new Metadata();
		m.setSpatialProperties(new SpatialProperties(GeoHash.decodeHash("h")));
		System.out.println("=========");
		System.out.println(std.locateData(m));
		
		
	}*/
	
	
	
	
	
	
	
	
}
