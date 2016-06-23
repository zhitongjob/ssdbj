package com.lovver.ssdbj.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Cluster implements Serializable {
	
	private String id;
	private boolean notfound_master_retry;
	private String balance;
	private boolean error_master_retry;
	private int error_retry_times;
	private int retry_interval;
	
	private List<ClusterSsdbNode> lstSsdbNode=new ArrayList<ClusterSsdbNode>(2);
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isNotfound_master_retry() {
		return notfound_master_retry;
	}
	public void setNotfound_master_retry(boolean notfound_master_retry) {
		this.notfound_master_retry = notfound_master_retry;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public boolean isError_master_retry() {
		return error_master_retry;
	}
	public void setError_master_retry(boolean error_master_retry) {
		this.error_master_retry = error_master_retry;
	}
	public int getError_retry_times() {
		return error_retry_times;
	}
	public void setError_retry_times(int error_retry_times) {
		this.error_retry_times = error_retry_times;
	}
	public int getRetry_interval() {
		return retry_interval;
	}
	public void setRetry_interval(int retry_interval) {
		this.retry_interval = retry_interval;
	}
	public List<ClusterSsdbNode> getLstSsdbNode() {
		return lstSsdbNode;
	}
	public void setLstSsdbNode(List<ClusterSsdbNode> lstSsdbNode) {
		this.lstSsdbNode = lstSsdbNode;
	}
	
	public void addNode(ClusterSsdbNode node){
		this.lstSsdbNode.add(node);
	}
	
	public void removeNode(ClusterSsdbNode node){
		this.lstSsdbNode.remove(node);
	}
	
	public void removeNode(String node_id){
		for(SsdbNode node:lstSsdbNode){
			if(node.getId().equals(node_id)){
				this.lstSsdbNode.remove(node);
			}
		}
	}
	
	
}
