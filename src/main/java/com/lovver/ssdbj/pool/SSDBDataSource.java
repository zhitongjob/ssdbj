package com.lovver.ssdbj.pool;

import java.util.Properties;

public class SSDBDataSource  {
	
	private SSDBConnectionPools pools;
	
	public SSDBDataSource(String host, int port, String user, Properties props){
		pools = SSDBConnectionPools.createPool(host,port,user,props);
	}
	
	@SuppressWarnings({ "rawtypes"})
	public SSDBPoolConnection getConnection() throws Exception {
		SSDBPoolConnection conn=pools.borrowObject();
		conn.setPools(pools);
		return conn;
	}

	public void distory(){
		this.pools.close();
	}
}
