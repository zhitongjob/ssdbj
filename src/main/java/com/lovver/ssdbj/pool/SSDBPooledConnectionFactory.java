package com.lovver.ssdbj.pool;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import com.lovver.ssdbj.core.BaseConnection;
import com.lovver.ssdbj.core.BaseResultSet;
import com.lovver.ssdbj.core.SSDBDriver;
import com.lovver.ssdbj.core.impl.SSDBConnection;
import com.lovver.ssdbj.exception.SSDBException;

public class SSDBPooledConnectionFactory<T> extends BasePooledObjectFactory<T> {
	private static SSDBDriver driver= new SSDBDriver();
	
	private Properties props;
	public SSDBPooledConnectionFactory(String host, int port, String user, Properties props){
		
		this.props=props;
		this.props.setProperty("SSDB_HOST", host);
		this.props.setProperty("SSDB_PORT", port+"");
		if(null!=user){
			this.props.setProperty("user", user);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T create() throws Exception {
		return (T) driver.connect(props);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PooledObject<T> wrap(T obj) {
		return new DefaultPooledObject<T>((T) new SSDBPoolConnection((SSDBConnection)obj));
	}

	@Override
	public boolean validateObject(PooledObject<T> p) {
		BaseConnection conn = (BaseConnection) p.getObject();
		try {
			BaseResultSet rs=conn.execute("ping", new ArrayList<byte[]>());
			return "ok".equals(rs.getStatus());
		} catch (SSDBException e) {
			return false;
		}
	}
}
