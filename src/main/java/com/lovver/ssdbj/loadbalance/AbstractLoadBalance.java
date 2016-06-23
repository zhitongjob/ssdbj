package com.lovver.ssdbj.loadbalance;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import com.lovver.ssdbj.cluster.SSDBCluster;
import com.lovver.ssdbj.config.Cluster;
import com.lovver.ssdbj.config.ClusterSsdbNode;
import com.lovver.ssdbj.pool.SSDBDataSource;

public abstract class AbstractLoadBalance implements LoadBalance {

	
	private List<Cluster> clusters = LoadBalanceFactory.getInstance().getClusterConfig();
	
	protected  Map<String,Vector<String>> clusterReadQueue = new ConcurrentHashMap<String,Vector<String>>();
	protected  Map<String,Vector<String>> clusterWriteQueue = new ConcurrentHashMap<String,Vector<String>>();
	
	protected  Map<String,Map<String,Integer>> clusterReadQueueWeight = new ConcurrentHashMap<String,Map<String,Integer>>();
	protected  Map<String,Map<String,Integer>> clusterWriteQueueWeight = new ConcurrentHashMap<String,Map<String,Integer>>();

	public AbstractLoadBalance() {
		for(Cluster clusterConfig:clusters){
			String cluster_id=clusterConfig.getId();
			Vector<String> readQueue= null;
			Vector<String> writeQueue= null;
			Map<String,Integer> readQueueWeight= null;
			Map<String,Integer> writeQueueWeight= null;
			readQueue = new Vector<String>();
			writeQueue = new Vector<String>();
			readQueueWeight=new ConcurrentHashMap<String,Integer>();
			writeQueueWeight=new ConcurrentHashMap<String,Integer>();
			
			List<ClusterSsdbNode> lstCNode=clusterConfig.getLstSsdbNode();
			for(ClusterSsdbNode cNode:lstCNode){
				String ds_id=cNode.getId();
				if(cNode.getRw().contains("r")){
					readQueue.add(ds_id);
					readQueueWeight.put(ds_id, cNode.getWeight());
				}
				if(cNode.getRw().contains("w")){
					writeQueue.add(ds_id);
					writeQueueWeight.put(ds_id, cNode.getWeight());
				}
			}
			clusterReadQueue.put(cluster_id, readQueue);
			clusterWriteQueue.put(cluster_id, writeQueue);
			clusterReadQueueWeight.put(cluster_id, readQueueWeight);
			clusterWriteQueueWeight.put(cluster_id, writeQueueWeight);
		}
		
	}

	public abstract String selectRead(String cluster_id);
	
	public abstract String selectWrite(String cluster_id);
	
	@Override
	public  SSDBDataSource getReadDataSource(String cluster_id){
		String ds_id=selectRead(cluster_id);
		return SSDBCluster.getDataSource(cluster_id,ds_id);
	}

	@Override
	public  SSDBDataSource getWriteDataSource(String cluster_id){
		String ds_id=selectWrite(cluster_id);
		return SSDBCluster.getDataSource(cluster_id,ds_id);
	}
	
}
