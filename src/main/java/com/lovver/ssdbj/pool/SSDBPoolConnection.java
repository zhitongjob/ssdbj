package com.lovver.ssdbj.pool;

import java.util.List;

import com.lovver.ssdbj.core.BaseConnection;
import com.lovver.ssdbj.core.BaseResultSet;
import com.lovver.ssdbj.core.CommandExecutor;
import com.lovver.ssdbj.core.impl.SSDBConnection;
import com.lovver.ssdbj.exception.SSDBException;

public class SSDBPoolConnection<T extends SSDBConnection> implements  BaseConnection{
	private SSDBConnectionPools pools;
	
	private T ori_conn=null;
	public SSDBPoolConnection(T ssdb_conn){
		this.ori_conn=ssdb_conn;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SSDBException {
		return ori_conn.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SSDBException {
		return ori_conn.isWrapperFor(iface);
	}

	@Override
	public void close() {
		pools.returnObject(this);
	}

	@Override
	public boolean isClose() {
		return ori_conn.isClose();
	}

	@Override
	public boolean isConnection() {
		return ori_conn.isConnection();
	}

	@Override
	public CommandExecutor getCommandExecutor() {
		return ori_conn.getCommandExecutor();
	}

	@Override
	public BaseResultSet execute(String cmd, List<byte[]> params)
			throws SSDBException {
		return ori_conn.execute(cmd, params);
	}

	@Override
	public boolean executeUpdate(String cmd, List<byte[]> params)
			throws SSDBException {
		return ori_conn.executeUpdate(cmd, params);
	}

	public void setPools(SSDBConnectionPools pools) {
		this.pools = pools;
	}
	
}
