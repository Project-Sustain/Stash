package galileo.graph;

import java.io.IOException;

import galileo.serialization.ByteSerializable;
import galileo.serialization.SerializationException;
import galileo.serialization.SerializationInputStream;
import galileo.serialization.SerializationOutputStream;

public class SummaryStatistics implements ByteSerializable{
	
	private int count;
	private float max;
	private float min;
	private float avg;
	private float tmpSum;
	
	private boolean resolved = false;
	
	public SummaryStatistics() {
		count = 0;
		max = 0;
		min = 0;
		avg = 0;
		tmpSum = 0;
		resolved = false;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public void increaseCount() {
		this.count++;
	}
	
	public void increaseCount(int cnt) {
		this.count+= cnt;
	}
	public float getMax() {
		return max;
	}
	public void setMax(float max) {
		this.max = max;
	}
	public float getMin() {
		return min;
	}
	public void setMin(float min) {
		this.min = min;
	}
	public float getAvg() {
		return avg;
	}
	public void setAvg(float avg) {
		this.avg = avg;
	}
	public float getTmpSum() {
		return tmpSum;
	}
	public void setTmpSum(float tmpSum) {
		this.tmpSum = tmpSum;
	}
	
	public void addToTmpSum(float val) {
		this.tmpSum += val;
	}
	
	public boolean isResolved() {
		return resolved;
	}
	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

	/**
	 * 
	 * @author sapmitra
	 * @param oldStats
	 * @param statsUpdate
	 * @return
	 */
	public static SummaryStatistics[] mergeSummaries(SummaryStatistics[] oldStats, SummaryStatistics[] statsUpdate) {
		
		SummaryStatistics[] newStats = new SummaryStatistics[oldStats.length];
		for(int i=0; i< oldStats.length; i++) {
			
			SummaryStatistics old = oldStats[i];
			SummaryStatistics upd = statsUpdate[i];
			
			SummaryStatistics newstat = mergeSummary(old, upd);
			newStats[i] = newstat;
		}
		return newStats;
	}
	
	/**
	 * NOT FULL MERGING, ATTRIBUTES SUCH AS AVG ARE LEFT OUT AND ONLY TOTAL AND COUNTE ARE UPDATED
	 * @author sapmitra
	 * @param oldStats
	 * @param statsUpdate
	 * @return
	 */
	public static SummaryStatistics[] preMergeSummaries(SummaryStatistics[] oldStats, SummaryStatistics[] statsUpdate) {
		
		SummaryStatistics[] newStats = new SummaryStatistics[oldStats.length];
		for(int i=0; i< oldStats.length; i++) {
			
			SummaryStatistics old = oldStats[i];
			SummaryStatistics upd = statsUpdate[i];
			
			SummaryStatistics newstat = preMergeSummary(old, upd);
			newStats[i] = newstat;
		}
		return newStats;
	}
	
	/**
	 * 
	 * @author sapmitra
	 * @param old
	 * @param upd
	 * @return
	 */
	public static SummaryStatistics mergeSummary(SummaryStatistics old, SummaryStatistics upd) {
		
		if(old.getMax() < upd.getMax()) {
			old.setMax(upd.getMax());
		}
		if(old.getMin() < upd.getMin()) {
			old.setMin(upd.getMin());
		}
		old.addToTmpSum(upd.getTmpSum());
		old.increaseCount(upd.getCount());
		
		return old;
		
	}
	
	public static SummaryStatistics preMergeSummary(SummaryStatistics old, SummaryStatistics upd) {
		
		if(old.getMax() < upd.getMax()) {
			old.setMax(upd.getMax());
		}
		if(old.getMin() < upd.getMin()) {
			old.setMin(upd.getMin());
		}
		old.addToTmpSum(upd.getTmpSum());
		old.increaseCount(upd.getCount());
		
		return old;
		
	}
	
	@Override
	public String toString() {
		String retStr = "{"+min+","+max+","+avg+","+count+"}";
		return retStr;
	}

	
	@Override
	public void serialize(SerializationOutputStream out) throws IOException {
		
		out.writeInt(count);
		out.writeFloat(max);
		out.writeFloat(min);
		out.writeFloat(avg);
		out.writeFloat(tmpSum);
		
	}
	
	@Deserialize
	public SummaryStatistics(SerializationInputStream in) throws IOException, SerializationException {
		
		this.count = in.readInt();
		this.max = in.readFloat();
		this.min = in.readFloat();
		this.avg = in.readFloat();
		this.tmpSum = in.readFloat();
		
	}

}
