package com.cloudrail.si.unifiedpointsofinterest;

import com.cloudrail.si.types.POI;

public class POIWrapper {
	private POI poi;
	private int dist;
	private String service;
	
	public POIWrapper(POI p, int d, String s) {
		poi = p;
		dist = d;
		service = s;
	}
	
	public POI getPoi() {
		return poi;
	}
	public void setPoi(POI poi) {
		this.poi = poi;
	}
	public int getDist() {
		return dist;
	}
	public void setDist(int dist) {
		this.dist = dist;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
}
