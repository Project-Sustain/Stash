package galileo.dht;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;

public class NodeResourceInfo {
	
	private float queueSize;
	private float heapUsage;
	private float guestTreeSize;
	
	public NodeResourceInfo() {}
	
	public NodeResourceInfo(float cpuUtil2, float guestTreeSize2, float heapMem) {
		this.queueSize = cpuUtil2;
		this.guestTreeSize = guestTreeSize2;
		this.heapUsage = heapMem;
	}
	
	public float getQueueSize() {
		return queueSize;
	}
	public void setQueueSize(float cpuUtil) {
		this.queueSize = cpuUtil;
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
	
	public void decrementGuestTreeSize(int cliqueSize) {
		this.guestTreeSize-=cliqueSize;
	}
	
	
	public static NodeResourceInfo getMachineStats(int queueSize, int totalGuestTreeSize) {
		NodeResourceInfo nr = new NodeResourceInfo();
		
		/*OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	    RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
	    int availableProcessors = operatingSystemMXBean.getAvailableProcessors();
	    long prevUpTime = runtimeMXBean.getUptime();
	    long prevProcessCpuTime = operatingSystemMXBean.getProcessCpuTime();
	    double cpuUsage;
	    
	    try
	    {
	        Thread.sleep(500);
	    }
	    catch (Exception ignored) { }

	    operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	    long upTime = runtimeMXBean.getUptime();
	    long processCpuTime = operatingSystemMXBean.getProcessCpuTime();
	    long elapsedCpu = processCpuTime - prevProcessCpuTime;
	    long elapsedTime = upTime - prevUpTime;

	    cpuUsage = Math.min(99F, elapsedCpu / (elapsedTime * 10000F * availableProcessors));
	    System.out.println("Java CPU: " + cpuUsage);*/
	    
	    
	    nr.setGuestTreeSize(totalGuestTreeSize);
	    nr.setQueueSize(queueSize);
	    
		Runtime rt = Runtime.getRuntime();
		
		nr.setHeapUsage(rt.freeMemory());
		
		return nr;
	}
	
	
	public static void main(String arg[]) {
		
		OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	    RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
	    int availableProcessors = operatingSystemMXBean.getAvailableProcessors();
	    long prevUpTime = runtimeMXBean.getUptime();
	    System.out.println(operatingSystemMXBean.getSystemLoadAverage());
		
		
		
	}

}
