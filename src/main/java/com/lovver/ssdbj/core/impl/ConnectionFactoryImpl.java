package com.lovver.ssdbj.core.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.lovver.ssdbj.core.ConnectionFactory;
import com.lovver.ssdbj.core.ProtocolConnection;
import com.lovver.ssdbj.core.SSDBStream;
import com.lovver.ssdbj.exception.SSDBException;

public class ConnectionFactoryImpl extends ConnectionFactory{

	@Override
	public ProtocolConnection openConnectionImpl(String host, int port,
			String user,Properties info)
			throws SSDBException {
		
        //  - the TCP keep alive setting
        boolean requireTCPKeepAlive = (Boolean.valueOf(info.getProperty("tcpKeepAlive")).booleanValue());
        int so_timeout=Integer.parseInt(info.getProperty("so_timeout", "-1"));
        
		try {
			SSDBStream ssdbStream=new SSDBStream(host,port);
			 // Enable TCP keep-alive probe if required.
			ssdbStream.getSocket().setKeepAlive(requireTCPKeepAlive);
			if(so_timeout>0){
				ssdbStream.getSocket().setSoTimeout(so_timeout);
			}

            // Do authentication (until AuthenticationOk).
            
            String protocolName=info.getProperty("protocolName","ssdb");
            String protocolVersion=info.getProperty("protocolVersion","1.0v");
			ProtocolConnection conn=new ProtocolConnectionImpl(protocolName,protocolVersion,ssdbStream,user,info);
			doAuthentication(conn, user, info.getProperty("password"));
			return conn;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void doAuthentication(ProtocolConnection conn, final String user,
			final String password) throws SSDBException {
		conn.getProtocolImpl().auth();
	}

}
