package galileo.util;

import java.util.ArrayList;
import java.util.List;

public class SpatialBorderIndices {
	
	private List<Integer> n;
	private List<Integer> s;
	private List<Integer> e;
	private List<Integer> w;
	private Integer ne;
	private Integer se;
	private Integer nw;
	private Integer sw;
	private boolean sameSize;
	
	public SpatialBorderIndices() {
		n = new ArrayList<Integer>();
		s = new ArrayList<Integer>();
		e = new ArrayList<Integer>();
		w = new ArrayList<Integer>();
	}
	
	public List<Integer> getN() {
		return n;
	}
	public void setN(List<Integer> n) {
		this.n = n;
	}
	public void addN(int n) {
		this.n.add(n);
	}
	public List<Integer> getS() {
		return s;
	}
	public void setS(List<Integer> s) {
		this.s = s;
	}
	public void addS(int n) {
		this.s.add(n);
	}
	public List<Integer> getE() {
		return e;
	}
	public void setE(List<Integer> e) {
		this.e = e;
	}
	public void addE(int n) {
		this.e.add(n);
	}
	public List<Integer> getW() {
		return w;
	}
	public void setW(List<Integer> w) {
		this.w = w;
	}
	public void addW(int n) {
		this.w.add(n);
	}
	public Integer getNe() {
		return ne;
	}
	public void setNe(int ne) {
		this.ne = ne;
	}
	public Integer getSe() {
		return se;
	}
	public void setSe(int se) {
		this.se = se;
	}
	public Integer getNw() {
		return nw;
	}
	public void setNw(int nw) {
		this.nw = nw;
	}
	public Integer getSw() {
		return sw;
	}
	public void setSw(int sw) {
		this.sw = sw;
	}

	public boolean isSameSize() {
		return sameSize;
	}

	public void setSameSize(boolean sameSize) {
		this.sameSize = sameSize;
	}

}
