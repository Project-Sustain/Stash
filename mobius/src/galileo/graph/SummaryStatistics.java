package galileo.graph;

public class SummaryStatistics {
	
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

}
