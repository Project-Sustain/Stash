package galileo.bq;
import java.io.IOException;
import java.util.List;

import galileo.client.EventPublisher;
import galileo.comm.DataIntegrationRequest;
import galileo.comm.FilesystemAction;
import galileo.comm.FilesystemRequest;
import galileo.comm.StorageRequest;
import galileo.comm.SurveyRequest;
import galileo.comm.TemporalType;
import galileo.comm.VisualizationRequest;
import galileo.dataset.Block;
import galileo.dataset.SpatialHint;
import galileo.dataset.feature.FeatureType;
import galileo.net.ClientMessageRouter;
import galileo.net.NetworkDestination;
import galileo.util.Pair;

abstract class GalileoConnectorInterface {
	private ClientMessageRouter messageRouter;
	private EventPublisher publisher;
	public NetworkDestination server;
	
	public GalileoConnectorInterface(String serverHostName, int serverPort) throws IOException {
		messageRouter = new ClientMessageRouter();
		publisher = new EventPublisher(messageRouter);
		server = new NetworkDestination(serverHostName, serverPort);
	}
	
	public void store(Block fb) throws Exception {
		StorageRequest store = new StorageRequest(fb);
		publisher.publish(server, store);
	}
	
	
	
	public void createFSTB(String name, SpatialHint sh,List<Pair<String, FeatureType>> featureList, String temporalHint, int mode) throws IOException {
		int spUnc = 0;
		int tempUnc = 0;
		if(mode == 1) {
			
			spUnc = 6;
			// 5 min
			tempUnc = 5*60*1000;
			
		} 
		
		FilesystemRequest fsRequest = new FilesystemRequest(name, FilesystemAction.CREATE, featureList, sh, spUnc, tempUnc,  false, temporalHint);
		fsRequest.setSpatialPartitioningType(3);
		fsRequest.setNodesPerGroup(30);
		//fsRequest.setPrecision(6);
		fsRequest.setTemporalType(TemporalType.DAY_OF_MONTH);
		
		publisher.publish(server, fsRequest);
	}
	
	
	
	public void createFS(String name, SpatialHint sh,List<Pair<String, FeatureType>> featureList, String temporalHint, int mode) throws IOException {
		int maxSpatialResolution = 6;
		int maxTemporalResolution = 4;
		
		FilesystemRequest fsRequest = new FilesystemRequest(name, FilesystemAction.CREATE, featureList, sh, maxSpatialResolution, maxTemporalResolution,  false, temporalHint);
		fsRequest.setSpatialPartitioningType(3);
		fsRequest.setNodesPerGroup(30);
		fsRequest.setPrecision(4);
		fsRequest.setTemporalType(TemporalType.DAY_OF_MONTH);
		publisher.publish(server, fsRequest);
	}
	
	/* THIS IS THE ONE WE USE */
	public void createFSViz(String name, SpatialHint sh,List<Pair<String, FeatureType>> featureList, String temporalHint, List<String> summaryHints) throws IOException {
		
		int maxSpatialResolution = 6;
		int maxTemporalResolution = 4;
		
		FilesystemRequest fsRequest = new FilesystemRequest(name, FilesystemAction.CREATE, featureList, sh, maxSpatialResolution, maxTemporalResolution,  false, temporalHint);
		fsRequest.setSpatialPartitioningType(2);
		fsRequest.setNodesPerGroup(30);
		fsRequest.setPrecision(4);
		fsRequest.setTemporalType(TemporalType.DAY_OF_MONTH);
		fsRequest.setSummaryHints(summaryHints);
		
		publisher.publish(server, fsRequest);
		
	}
	
	
	
	public void createFSNoaa(String name, SpatialHint sh,List<Pair<String, FeatureType>> featureList, String temporalHint, int mode) throws IOException {
		int spUnc = 0;
		int tempUnc = 0;
		if(mode == 1) {
			
			spUnc = 4;
			// 1hr
			tempUnc = 60*60*1000 + 1;
			
		} 
		FilesystemRequest fsRequest = new FilesystemRequest(name, FilesystemAction.CREATE, featureList, sh, spUnc, tempUnc,  false, temporalHint);
		fsRequest.setSpatialPartitioningType(1);
		fsRequest.setNodesPerGroup(30);
		// determines spatial precision of the geohashes stored on each file in a node
		fsRequest.setPrecision(2);
		fsRequest.setTemporalType(TemporalType.DAY_OF_MONTH);
		
		publisher.publish(server, fsRequest);
	}
	
	public void createFSNAM(String name, SpatialHint sh,List<Pair<String, FeatureType>> featureList, String temporalHint, int mode) throws IOException {
		int spUnc = 0;
		int tempUnc = 0;
		if(mode == 1) {
			
			spUnc = 4;
			// 1hr
			tempUnc = 60*60*1000 + 1;
			
		} 
		FilesystemRequest fsRequest = new FilesystemRequest(name, FilesystemAction.CREATE, featureList, sh, spUnc, tempUnc,  false, temporalHint);
		fsRequest.setSpatialPartitioningType(1);
		fsRequest.setNodesPerGroup(30);
		
		//determines spatial precision of the geohashes stored on each file in a node
		fsRequest.setPrecision(2);
		fsRequest.setTemporalType(TemporalType.DAY_OF_MONTH);
		
		publisher.publish(server, fsRequest);
	}
	
	public void integrate(DataIntegrationRequest dr) throws IOException {
		publisher.publish(server, dr);
	}
	

	public void integrate(VisualizationRequest vr) throws IOException {
		publisher.publish(server, vr);
	}
	
	public void survey(SurveyRequest sr) throws IOException {
		publisher.publish(server, sr);
	}
	
	public void disconnect() {
		messageRouter.shutdown();
	}

}
