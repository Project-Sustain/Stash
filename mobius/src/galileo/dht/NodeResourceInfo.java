package galileo.dht;

public class NodeResourceInfo {
	
	private boolean isBeingUpdated;
	private float cpuUtil;
	private float heapUsage;
	private float guestTreeSize;
	
	public boolean isBeingUpdated() {
		return isBeingUpdated;
	}
	public void setBeingUpdated(boolean isBeingUpdated) {
		this.isBeingUpdated = isBeingUpdated;
	}
	public float getCpuUtil() {
		return cpuUtil;
	}
	public void setCpuUtil(float cpuUtil) {
		this.cpuUtil = cpuUtil;
	}
	public float getHeapUsage() {
		return heapUsage;
	}
	public void setHeapUsage(float heapUsage) {
		this.heapUsage = heapUsage;
	}
	public float getGuestTreeSize() {
		return guestTreeSize;
	}
	public void setGuestTreeSize(float guestTreeSize) {
		this.guestTreeSize = guestTreeSize;
	}

}
