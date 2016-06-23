package com.lovver.ssdbj.loadbalance;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

import com.lovver.ssdbj.cluster.SSDBCluster;
import com.lovver.ssdbj.config.Cluster;
import com.lovver.ssdbj.loadbalance.impl.RandomLoadBalance;
import com.lovver.ssdbj.loadbalance.impl.RandomWeightLoadBalance;
import com.lovver.ssdbj.loadbalance.impl.RoundRobinLoadBalance;
import com.lovver.ssdbj.loadbalance.impl.RoundRobinWeightLoadBalance;

public class LoadBalanceFactory {
	private List<Cluster> clusters ;
	private Map<String,LoadBalance> cachedLoadBalance = new ConcurrentHashMap<String,LoadBalance>();
	
	private static LoadBalanceFactory instance = new LoadBalanceFactory();
	public static LoadBalanceFactory getInstance(){
		return instance;
	}
	
	public void setClusterConfig(List<Cluster> clusters){
		this.clusters=clusters;
	}
	
	public List<Cluster> getClusterConfig(){
		return this.clusters;
	}
	public LoadBalance createLoadBalance(String cluster_id){
		LoadBalance lb = cachedLoadBalance.get(cluster_id);
		if(null!=lb){
			return lb;
		}
		synchronized (cachedLoadBalance) {
			for(Cluster cluster:clusters){
				if(cluster.getId().equals(cluster_id)){
					String loadBalance=cluster.getBalance();
					if("round_robin".equals(loadBalance.toLowerCase())||StringUtils.isEmpty(loadBalance)){
						lb= new RoundRobinLoadBalance();
						cachedLoadBalance.put(cluster_id, lb);
						return lb;
					}
					if("round_robin_weight".equals(loadBalance.toLowerCase())){
						lb= new RoundRobinWeightLoadBalance();
						cachedLoadBalance.put(cluster_id, lb);
						return lb;
					}
					
					if("random_weight".equals(loadBalance.toLowerCase())){
						lb= new RandomWeightLoadBalance();
						cachedLoadBalance.put(cluster_id, lb);
						return lb;
					}
					
					if("random".equals(loadBalance.toLowerCase())){
						lb= new RandomLoadBalance();
						cachedLoadBalance.put(cluster_id, lb);
						return lb;
					}
				}
			}
			
		}
		
		throw new UnsupportedOperationException();
	}
}
