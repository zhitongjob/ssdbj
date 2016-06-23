package com.lovver.ssdbj.core.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import com.lovver.ssdbj.core.BaseResultSet;
import com.lovver.ssdbj.core.CommandExecutor;
import com.lovver.ssdbj.core.Protocol;
import com.lovver.ssdbj.core.ProtocolConnection;
import com.lovver.ssdbj.core.SSDBStream;
import com.lovver.ssdbj.exception.SSDBException;

/**
 * 协议级别连接，根据不同的协议产生不同的连接
 * 
 * @author jobell.jiang
 */
public class ProtocolConnectionImpl implements ProtocolConnection {

	private SSDBStream stream;
	private Properties props;
	private String user;
	private CommandExecutor executor;
    private Protocol protocol;
    private String protocolName;
    private String protocolVersion;

	private boolean closed = false;

	public ProtocolConnectionImpl(String protocolName,String protocolVersion,SSDBStream stream, String user,
			Properties infos) {
		this.protocolName=protocolName;
		this.protocolVersion=protocolVersion;
		this.stream = stream;
		this.user = user;
		this.props = infos;
		this.protocol=ProtocolFactory.createSSDBProtocolImpl(protocolName,stream.getOutputStream(),stream.getInputStream(),infos);
		this.executor=protocol.getCommandExecutor();
	}

	@Override
	public void close() {
		if (closed)
			return;

		try {
			stream.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		closed = true;
	}

	@Override
	public boolean isClose() {
		return closed;
	}

	@Override
	public boolean isConnection() {
		return stream.isConnection();
	}

	@Override
	public CommandExecutor getCommandExecutor() {
		return executor;
	}

	@Override
	public BaseResultSet execute(String cmd,List<byte[]> params) throws SSDBException{
		BaseResultSet resultSet=executor.execute(cmd, params);
		return resultSet;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> iface) throws SSDBException {
		if (iface.isAssignableFrom(getClass()))        {
            return (T) this;
        }
        throw new SSDBException("Cannot unwrap to " + iface.getName());
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SSDBException {
		 return iface.isAssignableFrom(getClass());
	}

	@Override
	public String getProtocol() {

		return protocolName;
	}

	@Override
	public String getProtocolVersion() {
		return protocolVersion;
	}

	@Override
	public Protocol getProtocolImpl() {
		return protocol;
	}

	@Override
	public boolean executeUpdate(String cmd, List<byte[]> params)
			throws SSDBException {
		return executor.executeUpdate(cmd, params); 
	}
}
