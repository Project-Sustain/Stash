package galileo.util;

import java.util.ArrayList;
import java.util.List;

public class SpatialBorder {
	
	private List<String> n;
	private List<String> s;
	private List<String> e;
	private List<String> w;
	private String ne;
	private String se;
	private String nw;
	private String sw;
	
	public SpatialBorder() {
		n = new ArrayList<String>();
		s = new ArrayList<String>();
		e = new ArrayList<String>();
		w = new ArrayList<String>();
	}
	
	public List<String> getN() {
		return n;
	}
	public void setN(List<String> n) {
		this.n = n;
	}
	public void addN(String n) {
		this.n.add(n);
	}
	public List<String> getS() {
		return s;
	}
	public void setS(List<String> s) {
		this.s = s;
	}
	public void addS(String n) {
		this.s.add(n);
	}
	public List<String> getE() {
		return e;
	}
	public void setE(List<String> e) {
		this.e = e;
	}
	public void addE(String n) {
		this.e.add(n);
	}
	public List<String> getW() {
		return w;
	}
	public void setW(List<String> w) {
		this.w = w;
	}
	public void addW(String n) {
		this.w.add(n);
	}
	public String getNe() {
		return ne;
	}
	public void setNe(String ne) {
		this.ne = ne;
	}
	public String getSe() {
		return se;
	}
	public void setSe(String se) {
		this.se = se;
	}
	public String getNw() {
		return nw;
	}
	public void setNw(String nw) {
		this.nw = nw;
	}
	public String getSw() {
		return sw;
	}
	public void setSw(String sw) {
		this.sw = sw;
	}

}
