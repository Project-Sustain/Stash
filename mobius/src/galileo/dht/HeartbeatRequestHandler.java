package galileo.dht;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import galileo.comm.DataIntegrationFinalResponse;
import galileo.comm.DataIntegrationResponse;
import galileo.comm.GalileoEventMap;
import galileo.comm.HeartbeatResponse;
import galileo.comm.MetadataResponse;
import galileo.comm.QueryResponse;
import galileo.comm.VisualizationEventResponse;
import galileo.comm.VisualizationResponse;
import galileo.event.BasicEventWrapper;
import galileo.event.Event;
import galileo.event.EventContext;
import galileo.graph.SummaryStatistics;
import galileo.graph.SummaryWrapper;
import galileo.net.ClientMessageRouter;
import galileo.net.GalileoMessage;
import galileo.net.MessageListener;
import galileo.net.NetworkDestination;
import galileo.net.RequestListener;
import galileo.serialization.SerializationException;

/**
 * This class will collect the responses from all the nodes of galileo and then
 * transfers the result to the listener. Used by the {@link StorageNode} class.
 * 
 * @author sapmitra
 */
public class HeartbeatRequestHandler implements MessageListener {

	private static final Logger logger = Logger.getLogger("galileo");
	private GalileoEventMap eventMap;
	private BasicEventWrapper eventWrapper;
	private ClientMessageRouter router;
	private AtomicInteger expectedResponses;
	private Collection<NetworkDestination> nodes;
	private List<GalileoMessage> responses;
	private Event response;
	private long elapsedTime;
	private long reqId;
	private boolean currentlyBusy;
	
	
	private String currentHostString;
	private NodeResourceInfo currentHostResourceInfo;
	
	private Map<String, NodeResourceInfo> nodesResourceMap;

	public HeartbeatRequestHandler(List<NetworkDestination> nodes, Map<String, NodeResourceInfo> nodesResourceMap) throws IOException {
		this.nodes = nodes;

		this.router = new ClientMessageRouter(true);
		this.router.addListener(this);
		this.responses = new ArrayList<GalileoMessage>();
		this.eventMap = new GalileoEventMap();
		this.eventWrapper = new BasicEventWrapper(this.eventMap);
		
		// ONE EXTRA FOR HOT CLIQUES CALCULATION
		this.expectedResponses = new AtomicInteger(this.nodes.size());
		this.currentlyBusy = false;
		this.nodesResourceMap = nodesResourceMap;
	}

	public void closeRequest() {
		
		silentClose(); // closing the router to make sure that no new responses are added.
		
		int responseCount = 0;
		
		// LOCK nodesResourceMap BEFORE ANY UPDATES ARE MADE TO IT
		synchronized (nodesResourceMap) {
			
			for (GalileoMessage gresponse : this.responses) {
				
				responseCount++;
				Event event;
				
				try {
					event = this.eventWrapper.unwrap(gresponse);
					
					if (event instanceof HeartbeatResponse) {
						
						HeartbeatResponse eventResponse = (HeartbeatResponse) event;
						
						logger.info("RIKI: HEARTBEAT RESPONSE RECEIVED....FROM "+eventResponse.getHostString());
						
						NodeResourceInfo nr = new NodeResourceInfo(eventResponse.getCpuUtil(), eventResponse.getGuestTreeSize(),
								eventResponse.getHeapMem());
						nodesResourceMap.put(eventResponse.getHostString(), nr);
					
					}
				} catch (IOException | SerializationException e) {
					logger.log(Level.SEVERE, "An exception occurred while processing the response message. Details follow:"
							+ e.getMessage(), e);
				} catch (Exception e) {
					logger.log(Level.SEVERE, "An unknown exception occurred while processing the response message. Details follow:"
									+ e.getMessage(), e);
				}
			}
			
			nodesResourceMap.put(currentHostString, currentHostResourceInfo);
			
		}
		
		currentlyBusy = false;
		logger.info("RIKI: HEARTBEAT COMPILED WITH "+responseCount+" MESSAGES");
		
	}

	@Override
	public void onMessage(GalileoMessage message) {
		
		logger.info("RIKI: HEARTBEAT RESPONSE RECEIVED");
		
		if (null != message)
			this.responses.add(message);
		
		int awaitedResponses = this.expectedResponses.decrementAndGet();
		logger.log(Level.INFO, "Awaiting " + awaitedResponses + " more message(s)");
		
		
		if (awaitedResponses <= 0) {
			this.elapsedTime = System.currentTimeMillis() - this.elapsedTime;
			logger.log(Level.INFO, "Closing the request and sending back the response.");
			new Thread() {
				public void run() {
					HeartbeatRequestHandler.this.closeRequest();
				}
			}.start();
		}
	}

	/**
	 * Handles the client request on behalf of the node that received the
	 * request
	 * 
	 * @param request
	 *            - This must be a server side event: Generic Event or
	 *            QueryEvent
	 * @param nr_current 
	 * @param hostString 
	 * @param response
	 */
	public void handleRequest(Event request, String hostString, NodeResourceInfo nr_current) {
		
		try {
			currentlyBusy = true;
			reqId = System.currentTimeMillis();
			
			this.currentHostString = hostString;
			this.currentHostResourceInfo = nr_current;
			
			GalileoMessage mrequest = this.eventWrapper.wrap(request);
			for (NetworkDestination node : nodes) {
				
				this.router.sendMessage(node, mrequest);
				logger.info("RIKI: HEARTBEAT REQUEST SENT TO " + node.toString());
				
			}
			this.elapsedTime = System.currentTimeMillis();
		} catch (IOException e) {
			logger.log(Level.INFO,
					"Failed to send request to other nodes in the network. Details follow: " + e.getMessage());
		}
		
		
		
	}

	public void silentClose() {
		try {
			this.router.forceShutdown();
		} catch (Exception e) {
			logger.log(Level.INFO, "Failed to shutdown the completed client request handler: ", e);
		}
	}

	@Override
	public void onConnect(NetworkDestination endpoint) {

	}

	@Override
	public void onDisconnect(NetworkDestination endpoint) {

	}

	public boolean isCurrentlyBusy() {
		return currentlyBusy;
	}

	public void setCurrentlyBusy(boolean currentlyBusy) {
		this.currentlyBusy = currentlyBusy;
	}
}
