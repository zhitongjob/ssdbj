package com.lovver.ssdbj.config;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ClusterSsdbNode extends SsdbNode implements Serializable {
	
	private int weight;
	private String rw;
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getRw() {
		return rw;
	}
	public void setRw(String rw) {
		this.rw = rw;
	}
}
