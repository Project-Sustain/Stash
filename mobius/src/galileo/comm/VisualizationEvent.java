package galileo.comm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import galileo.dataset.Coordinates;
import galileo.event.Event;
import galileo.query.Expression;
import galileo.query.Operation;
import galileo.query.Operator;
import galileo.query.Query;
import galileo.serialization.SerializationException;
import galileo.serialization.SerializationInputStream;
import galileo.serialization.SerializationOutputStream;

public class VisualizationEvent implements Event{

	// TIME$$RANDOM_NUM
	private String eventId;
	private String fsName;
	private Query featureQuery;
	private List<Coordinates> polygon;
	private String timeString;
	private int spatialResolution;
	private int temporalResolution;
	private List<String> reqFeatures;

	private void validate(String fsName) {
		if (fsName == null || fsName.trim().length() == 0 || !fsName.matches("[a-z0-9-]{5,50}"))
			throw new IllegalArgumentException("invalid filesystem name");
		this.fsName = fsName;
	}

	private void validate(Query query) {
		if (query == null || query.getOperations().isEmpty())
			throw new IllegalArgumentException("illegal query. must have at least one operation");
		Operation operation = query.getOperations().get(0);
		if (operation.getExpressions().isEmpty())
			throw new IllegalArgumentException("no expressions found for an operation of the query");
		Expression expression = operation.getExpressions().get(0);
		if (expression.getOperand() == null || expression.getOperand().trim().length() == 0
				|| expression.getOperator() == Operator.UNKNOWN || expression.getValue() == null)
			throw new IllegalArgumentException("illegal expression for an operation of the query");
	}

	public void setFeatureQuery(Query query) {
		validate(query);
		this.featureQuery = query;
	}

	public void setTime(String time) {
		if (time != null) {
			if (time.length() != 13)
				throw new IllegalArgumentException(
						"time must be of the form yyyy-mm-dd-hh with missing values replaced as x");
			this.timeString = time;
		}
	}

	public VisualizationEvent(String fsName, Query featureQuery, Query metadataQuery) {
		
		validate(fsName);
		if (featureQuery == null && metadataQuery == null)
			throw new IllegalArgumentException("Atleast one of the queries must be present");
		if (featureQuery != null)
			setFeatureQuery(featureQuery);
		
	}

	public String getFilesystemName() {
		return this.fsName;
	}

	public Query getFeatureQuery() {
		return this.featureQuery;
	}
	
	public String getTime() {
		return this.timeString;
	}

	public boolean isSpatial() {
		return polygon != null;
	}

	public boolean isTemporal() {
		return timeString != null;
	}

	public boolean hasFeatureQuery() {
		return this.featureQuery != null;
	}

	public String getFeatureQueryString() {
		if (this.featureQuery != null)
			return featureQuery.toString();
		return "";
	}
	
	@Deserialize
	public VisualizationEvent(SerializationInputStream in) throws IOException, SerializationException {
		
		fsName = in.readString();
		boolean isTemporal = in.readBoolean();
		if (isTemporal)
			timeString = in.readString();
		boolean isSpatial = in.readBoolean();
		if (isSpatial) {
			List<Coordinates> poly = new ArrayList<Coordinates>();
			in.readSerializableCollection(Coordinates.class, poly);
			polygon = poly;
		}
		boolean hasFeatureQuery = in.readBoolean();
		if (hasFeatureQuery)
			this.featureQuery = new Query(in);
		this.spatialResolution = in.readInt();
		this.temporalResolution = in.readInt();
		
		//this cannot be null
		reqFeatures = new ArrayList<String>();
		in.readStringCollection(reqFeatures);
		
	}

	@Override
	public void serialize(SerializationOutputStream out) throws IOException {
		out.writeString(fsName);
		out.writeBoolean(isTemporal());
		if (isTemporal())
			out.writeString(timeString);
		out.writeBoolean(isSpatial());
		if (isSpatial())
			out.writeSerializableCollection(polygon);
		out.writeBoolean(hasFeatureQuery());
		if (hasFeatureQuery())
			out.writeSerializable(this.featureQuery);
		out.writeInt(spatialResolution);
		out.writeInt(temporalResolution);

		//this cannot be null
		out.writeStringCollection(reqFeatures);
		
	}

	public String getFsName() {
		return fsName;
	}

	public void setFsName(String fsName) {
		this.fsName = fsName;
	}

	public List<Coordinates> getPolygon() {
		return polygon;
	}

	public void setGeohashes(List<Coordinates> polygon) {
		this.polygon = polygon;
	}

	public String getTimeString() {
		return timeString;
	}

	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}


	/**
	 * Cloning Visualization Request
	 * @param queryId
	 * @param request
	 */
	public VisualizationEvent(String queryId, VisualizationRequest request) {
		
		fsName = request.getFsName();
		featureQuery = request.getFeatureQuery();
		polygon = request.getPolygon();
		timeString = request.getTimeString();
		spatialResolution = request.getSpatialResolution();
		temporalResolution = request.getTemporalResolution();
		reqFeatures = request.getReqFeatures();
		
	}

	public int getSpatialResolution() {
		return spatialResolution;
	}

	public void setSpatialResolution(int spatialResolution) {
		this.spatialResolution = spatialResolution;
	}

	public int getTemporalResolution() {
		return temporalResolution;
	}

	public void setTemporalResolution(int temporalResolution) {
		this.temporalResolution = temporalResolution;
	}

	public void setPolygon(List<Coordinates> polygon) {
		this.polygon = polygon;
	}

	public List<String> getReqFeatures() {
		return reqFeatures;
	}

	public void setReqFeatures(List<String> reqFeatures) {
		this.reqFeatures = reqFeatures;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}



}
