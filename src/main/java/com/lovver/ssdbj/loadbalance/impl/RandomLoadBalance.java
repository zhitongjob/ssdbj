package com.lovver.ssdbj.loadbalance.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.math.RandomUtils;

import com.lovver.ssdbj.loadbalance.AbstractLoadBalance;

public class RandomLoadBalance extends AbstractLoadBalance {

protected  Map<String,RandomBalanceQueue> balanceQueue = new ConcurrentHashMap<String,RandomBalanceQueue>();
	
	public RandomLoadBalance(){
		super();
		Iterator<String> iteCluster=clusterReadQueue.keySet().iterator();
		while(iteCluster.hasNext()){
			String __key=iteCluster.next();
			Vector<String> groupReadQueue = clusterReadQueue.get(__key);
			Vector<String> groupWriteQueue = clusterWriteQueue.get(__key);
			balanceQueue.put(__key, new RandomBalanceQueue(groupReadQueue,groupWriteQueue));
		}
	}
	
	@Override
	public synchronized String selectRead(String cluster_id) {
		RandomBalanceQueue queue=balanceQueue.get(cluster_id);
		return queue.getReadDataSource();
	}

	@Override
	public synchronized String selectWrite(String cluster_id) {
		RandomBalanceQueue queue=balanceQueue.get(cluster_id);
		return queue.getWriteDataSource();
	}


	class RandomBalanceQueue{
		
		protected  Vector<String> readQueue = new Vector<String>();
		protected  Vector<String> writeQueue = new Vector<String>();
		
		private int rCount=0;
		private int wCount=0;
		
//		private AtomicInteger rIndex = new AtomicInteger(0);
//		private AtomicInteger wIndex = new AtomicInteger(0);
		
		public RandomBalanceQueue(Vector<String> readQueue,Vector<String> writeQueue){
			this.readQueue=readQueue;
			this.writeQueue=writeQueue;
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
