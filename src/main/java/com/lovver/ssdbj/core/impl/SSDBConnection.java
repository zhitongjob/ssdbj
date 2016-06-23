package com.lovver.ssdbj.core.impl;

import java.util.List;
import java.util.Properties;

import com.lovver.ssdbj.core.BaseConnection;
import com.lovver.ssdbj.core.BaseResultSet;
import com.lovver.ssdbj.core.CommandExecutor;
import com.lovver.ssdbj.core.ConnectionFactory;
import com.lovver.ssdbj.core.ProtocolConnection;
import com.lovver.ssdbj.exception.SSDBException;

public class SSDBConnection implements  BaseConnection {
	
    private ProtocolConnection protoConnection;
	
	public SSDBConnection(String host, int port, String user, Properties props) {
		try {
			this.protoConnection=ConnectionFactory.openConnection(host, port, user,  props);
			
		} catch (SSDBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		protoConnection.close();
	}

	@Override
	public boolean isClose() {
		return protoConnection.isClose();
	}

	@Override
	public boolean isConnection() {
		return protoConnection.isConnection();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SSDBException {
		return protoConnection.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SSDBException {
		return protoConnection.isWrapperFor(iface);
	}

	@Override
	public CommandExecutor getCommandExecutor() {
		return protoConnection.getCommandExecutor();
	}

	@Override
	public BaseResultSet execute(String cmd,List<byte[]> params) throws SSDBException {
		return  protoConnection.execute(cmd, params);
		
	}

	@Override
	public boolean executeUpdate(String cmd, List<byte[]> params)
			throws SSDBException {
		return protoConnection.executeUpdate(cmd, params);
	}
}
