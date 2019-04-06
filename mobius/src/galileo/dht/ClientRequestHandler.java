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
import galileo.comm.MetadataResponse;
import galileo.comm.QueryResponse;
import galileo.comm.VisualizationEvent;
import galileo.comm.VisualizationEventResponse;
import galileo.comm.VisualizationRequest;
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
 * @author kachikaran
 */
public class ClientRequestHandler implements MessageListener {

	private static final Logger logger = Logger.getLogger("galileo");
	private GalileoEventMap eventMap;
	private BasicEventWrapper eventWrapper;
	private ClientMessageRouter router;
	private AtomicInteger expectedResponses;
	private Collection<NetworkDestination> nodes;
	private EventContext clientContext;
	private List<GalileoMessage> responses;
	private RequestListener requestListener;
	private Event response;
	private long elapsedTime;
	private long reqId;
	
	//private Map<String, NetworkDestination> nodeStringToNodeMap;
	
	private VisualizationEvent initialReq;

	public ClientRequestHandler(Collection<NetworkDestination> nodes, EventContext clientContext,
			RequestListener listener) throws IOException {
		this.nodes = nodes;
		
		/*nodeStringToNodeMap = new HashMap<String, NetworkDestination>();
		
		for(NetworkDestination nd : this.nodes) {
			
			nodeStringToNodeMap.put(nd.stringRepresentation(), nd);
			
		}*/
		
		
		this.clientContext = clientContext;
		this.requestListener = listener;

		this.router = new ClientMessageRouter(true);
		this.router.addListener(this);
		this.responses = new ArrayList<GalileoMessage>();
		this.eventMap = new GalileoEventMap();
		this.eventWrapper = new BasicEventWrapper(this.eventMap);
		this.expectedResponses = new AtomicInteger(this.nodes.size());
		
		logger.info("RIKI: HANDLER NEEDS BACK "+expectedResponses+" RESPONSES");
	}

	public void closeRequest() {
		
		silentClose(); // closing the router to make sure that no new responses are added.
		
		Map<String, SummaryWrapper> accumulatedSummaries = new HashMap<String, SummaryWrapper>();

		for (GalileoMessage gresponse : this.responses) {
			Event event;
			try {
				
				event = this.eventWrapper.unwrap(gresponse);
				
				
				if (event instanceof VisualizationEventResponse && this.response instanceof VisualizationResponse) {
					
					VisualizationEventResponse eventResponse = (VisualizationEventResponse) event;
					
					logger.info("RIKI: VISUALIZATION RESPONSE RECEIVED....FROM "+eventResponse.getHostName()+":"+eventResponse.getHostPort());
					
					if(eventResponse.isNeedsRedirection()) {
						
						logger.info("RIKI: THIS IS A REDIRECTION RESPONSE FOR THE FOLLOWING NODES "+ eventResponse.getHelperNodes());
						continue;
					}
					
					if(eventResponse.getKeys() != null && eventResponse.getKeys().size() > 0) {
						
						List<String> keys = eventResponse.getKeys();
						List<SummaryWrapper> summaries = eventResponse.getSummaries();
						
						int num = 0;
						for(String key : keys) {
							
							SummaryWrapper eventSumm = summaries.get(num);
							
							if(accumulatedSummaries.containsKey(key)) {
								SummaryWrapper oldSumm = accumulatedSummaries.get(key);
								
								SummaryStatistics[] mergeSummaries = SummaryStatistics.mergeSummaries(oldSumm.getStats(), eventSumm.getStats());
								oldSumm.setStats(mergeSummaries);
								
							} else {
								eventSumm.cleanHouse();
								accumulatedSummaries.put(key, eventSumm);
							}
							
							num++;
						}
						
					}
				} else if(event instanceof DataIntegrationResponse && this.response instanceof DataIntegrationFinalResponse) {
					DataIntegrationFinalResponse actualResponse = (DataIntegrationFinalResponse) this.response;
					
					DataIntegrationResponse eventResponse = (DataIntegrationResponse) event;
					
					logger.info("RIKI: DATA INTEGRATION RESPONSE RECEIVED....FROM "+eventResponse.getNodeName()+":"+eventResponse.getNodePort() +" "+eventResponse.getResultPaths());
					
					if(eventResponse.getResultPaths() != null && eventResponse.getResultPaths().size() > 0) {
						for(String path: eventResponse.getResultPaths()) {
							//logger.info("RIKI: DATA INTEGRATION RESPONSE :"+eventResponse.getResultPaths());
							String newPath = eventResponse.getNodeName()+":"+eventResponse.getNodePort()+"$$"+path;
							actualResponse.addResultPath(newPath);
						}
					}
					
					
				} else if (event instanceof QueryResponse && this.response instanceof QueryResponse) {
					QueryResponse actualResponse = (QueryResponse) this.response;
					actualResponse.setElapsedTime(elapsedTime);
					QueryResponse eventResponse = (QueryResponse) event;
					JSONObject responseJSON = actualResponse.getJSONResults();
					JSONObject eventJSON = eventResponse.getJSONResults();
					if (responseJSON.length() == 0) {
						for (String name : JSONObject.getNames(eventJSON))
							responseJSON.put(name, eventJSON.get(name));
					} else {
						if (responseJSON.has("queryId") && eventJSON.has("queryId")
								&& responseJSON.getString("queryId").equalsIgnoreCase(eventJSON.getString("queryId"))) {
							if (actualResponse.isDryRun()) {
								JSONObject actualResults = responseJSON.getJSONObject("result");
								JSONObject eventResults = eventJSON.getJSONObject("result");
								if (null != JSONObject.getNames(eventResults)) {
									for (String name : JSONObject.getNames(eventResults)) {
										if (actualResults.has(name)) {
											JSONArray ar = actualResults.getJSONArray(name);
											JSONArray er = eventResults.getJSONArray(name);
											for (int i = 0; i < er.length(); i++) {
												ar.put(er.get(i));
											}
										} else {
											actualResults.put(name, eventResults.getJSONArray(name));
										}
									}
								}
							} else {
								JSONArray actualResults = responseJSON.getJSONArray("result");
								JSONArray eventResults = eventJSON.getJSONArray("result");
								for (int i = 0; i < eventResults.length(); i++)
									actualResults.put(eventResults.getJSONObject(i));
							}
							if (responseJSON.has("hostProcessingTime")) {
								JSONObject aHostProcessingTime = responseJSON.getJSONObject("hostProcessingTime");
								JSONObject eHostProcessingTime = eventJSON.getJSONObject("hostProcessingTime");

								JSONObject aHostFileSize = responseJSON.getJSONObject("hostFileSize");
								JSONObject eHostFileSize = eventJSON.getJSONObject("hostFileSize");

								for (String key : eHostProcessingTime.keySet())
									aHostProcessingTime.put(key, eHostProcessingTime.getLong(key));
								for (String key : eHostFileSize.keySet())
									aHostFileSize.put(key, eHostFileSize.getLong(key));

								responseJSON.put("totalFileSize",
										responseJSON.getLong("totalFileSize") + eventJSON.getLong("totalFileSize"));
								responseJSON.put("totalNumPaths",
										responseJSON.getLong("totalNumPaths") + eventJSON.getLong("totalNumPaths"));
								responseJSON.put("totalProcessingTime",
										java.lang.Math.max(responseJSON.getLong("totalProcessingTime"),
												eventJSON.getLong("totalProcessingTime")));
								responseJSON.put("totalBlocksProcessed", responseJSON.getLong("totalBlocksProcessed")
										+ eventJSON.getLong("totalBlocksProcessed"));
							}
						}
					}
				} 
			} catch (IOException | SerializationException e) {
				logger.log(Level.SEVERE, "An exception occurred while processing the response message. Details follow:"
						+ e.getMessage(), e);
			} catch (Exception e) {
				logger.log(Level.SEVERE,
						"An unknown exception occurred while processing the response message. Details follow:"
								+ e.getMessage(), e);
			}
		}
		
		// COMBINE HERE INTO A SINGLE JSON STRING
		if (this.response instanceof VisualizationResponse) {
			
			VisualizationResponse finalResponse = (VisualizationResponse) this.response;
			
			List<SummaryWrapper> summaries = new ArrayList<SummaryWrapper>();
			List<String> keys = new ArrayList<String>();
			
			for(String key: accumulatedSummaries.keySet()) {
				
				keys.add(key);
				summaries.add(accumulatedSummaries.get(key));
			}
			
			
			finalResponse.setSummaries(summaries);
			finalResponse.setKeys(keys);
			
			
		}
		
		
		long diff = System.currentTimeMillis() - reqId;
		logger.info("RIKI: ENTIRE THING FINISHED IN: "+ diff);
		this.requestListener.onRequestCompleted(this.response, clientContext, this);
	}

	@Override
	public void onMessage(GalileoMessage message) {
		
		logger.info("RIKI: SOMETHING RECEIVED HERE....LET'S SEE....");
		
		boolean summaryFound = true;
		
		try {
			VisualizationEventResponse eventResponse = (VisualizationEventResponse)this.eventWrapper.unwrap(message);
			
			logger.info("RIKI: VISUALIZATION RESPONSE RECEIVED....FROM "+eventResponse.getHostName()+":"+eventResponse.getHostPort());
			
			if(eventResponse.isNeedsRedirection()) {
				
				summaryFound = false;
				
				logger.info("RIKI: THIS IS A REDIRECTION RESPONSE FOR THE FOLLOWING NODES "+ eventResponse.getHelperNodes());
				
				// SEND GUEST CACHE REQUEST TO THESE NODES
				
				// helperNode are nodes to redirect response to
				for(String helperNode : eventResponse.getHelperNodes()) {
					
					String[] tokens = helperNode.split(":");
					
					NetworkDestination nd = new NetworkDestination(tokens[0], Integer.valueOf(tokens[1]));
					
					logger.info("RIKI: ABOUT TO REDIRECT TO "+nd+" FOR KEY: "+helperNode);
					
					VisualizationRequest vreq = new VisualizationRequest(eventResponse.getHostName()+":"+eventResponse.getHostPort(), initialReq);
					
					this.expectedResponses.incrementAndGet();
						
					GalileoMessage mrequest = this.eventWrapper.wrap(vreq);
						
					this.router.sendMessage(nd, mrequest);
					
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SerializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		if (null != message && summaryFound)
			this.responses.add(message);
		
		int awaitedResponses = this.expectedResponses.decrementAndGet();
		
		
		logger.log(Level.INFO, "Awaiting " + awaitedResponses + " more message(s)");
		if (awaitedResponses <= 0) {
			this.elapsedTime = System.currentTimeMillis() - this.elapsedTime;
			logger.log(Level.INFO, "Closing the request and sending back the response.");
			new Thread() {
				public void run() {
					ClientRequestHandler.this.closeRequest();
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
	 * @param response
	 */
	public void handleRequest(Event request, Event response) {
		try {
			reqId = System.currentTimeMillis();
			
			initialReq = (VisualizationEvent)request;
			
			logger.info("RIKI: VISUALIZATION REQUEST RECEIVED AT TIME: "+System.currentTimeMillis());
			this.response = response;
			GalileoMessage mrequest = this.eventWrapper.wrap(request);
			for (NetworkDestination node : nodes) {
				this.router.sendMessage(node, mrequest);
				//logger.info("Request sent to " + node.toString());
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
}
