package com.lovver.ssdbj.loadbalance;

import com.lovver.ssdbj.pool.SSDBDataSource;

public interface LoadBalance {
	
	public SSDBDataSource getWriteDataSource(String cluster_id);
	public SSDBDataSource getReadDataSource(String cluster_id);
}
