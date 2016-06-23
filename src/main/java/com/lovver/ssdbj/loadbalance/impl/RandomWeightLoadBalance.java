package com.lovver.ssdbj.loadbalance.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.math.RandomUtils;

import com.lovver.ssdbj.loadbalance.AbstractLoadBalance;


public class RandomWeightLoadBalance extends AbstractLoadBalance  {

	protected  Map<String,RandomWeightLoadBalanceQueue> balanceQueue = new ConcurrentHashMap<String,RandomWeightLoadBalanceQueue>();
	
	public RandomWeightLoadBalance(){
		super();
		Iterator<String> iteCluster=clusterReadQueue.keySet().iterator();
		while(iteCluster.hasNext()){
			String __key=iteCluster.next();
			Vector<String> groupReadQueue = clusterReadQueue.get(__key);
			Vector<String> groupWriteQueue = clusterWriteQueue.get(__key);
			Map<String,Integer> groupReadQueueWeight = clusterReadQueueWeight.get(__key);
			Map<String,Integer> groupWriteQueueWeight = clusterWriteQueueWeight.get(__key);
			balanceQueue.put(__key, new RandomWeightLoadBalanceQueue(groupReadQueue,groupWriteQueue,groupReadQueueWeight,groupWriteQueueWeight));
		}
	}
	
	@Override
	public synchronized String selectRead(String cluster_id) {
		RandomWeightLoadBalanceQueue queue=balanceQueue.get(cluster_id);
		return queue.getReadDataSource();
	}

	@Override
	public synchronized String selectWrite(String cluster_id) {
		RandomWeightLoadBalanceQueue queue=balanceQueue.get(cluster_id);
		return queue.getWriteDataSource();
	}

	
	class RandomWeightLoadBalanceQueue{
		
		protected  Vector<String> readQueue = new Vector<String>();
		protected  Vector<String> writeQueue = new Vector<String>();
		
		private int rCount=0;
		private int wCount=0;
//		
//		private AtomicInteger rIndex = new AtomicInteger(0);
//		private AtomicInteger wIndex = new AtomicInteger(0);
		
		public RandomWeightLoadBalanceQueue(Vector<String> readQueue,Vector<String> writeQueue,
				Map<String,Integer> groupReadQueueWeight,Map<String,Integer> groupWriteQueueWeight){
			for(String ds_id:readQueue){
				int weight=groupReadQueueWeight.get(ds_id);
				for(int i=0;i<weight;i++){
					this.readQueue.add(ds_id);
				}
			}
			for(String ds_id:writeQueue){
				int weight=groupWriteQueueWeight.get(ds_id);
				for(int i=0;i<weight;i++){
					this.writeQueue.add(ds_id);
				}
			}
			this.rCount=this.readQueue.size();
			this.wCount=this.writeQueue.size();
		}
		
		public synchronized String getReadDataSource(){
			int rIndex=RandomUtils.nextInt(rCount);
			String ds_id=this.readQueue.get(rIndex);
//			if(rIndex.get()>=rCount){
//				rIndex.set(0); 
//			}
			System.out.println(ds_id);
			return ds_id;
		}
		
		public synchronized String getWriteDataSource(){
			int wIndex=RandomUtils.nextInt(wCount);
			String ds_id=this.writeQueue.get(wIndex);
//			String ds_id=this.writeQueue.get(wIndex.getAndAdd(1));
//			if(wIndex.get()>=wCount){
//				wIndex.set(0); 
//			}
			System.out.println(ds_id);
			return ds_id;
		}
	}

}
