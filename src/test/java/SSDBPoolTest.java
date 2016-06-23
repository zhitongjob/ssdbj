import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.lovver.ssdbj.core.BaseResultSet;
import com.lovver.ssdbj.pool.SSDBDataSource;
import com.lovver.ssdbj.pool.SSDBPoolConnection;


public class SSDBPoolTest {
	static SSDBDataSource ds=null; 
	static{
		Properties info = new Properties();
		info.setProperty("password", "ddd");
		info.setProperty("loginTimeout", "300");
		info.setProperty("tcpKeepAlive", "true");
		info.setProperty("protocolName", "ssdb");
		info.setProperty("protocolVersion", "1.0");
		ds = new SSDBDataSource("192.168.0.226",8888,null,info);
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	@SuppressWarnings({  "rawtypes", "unchecked" })
	public static void main(String[] args) throws Exception {
		SSDBPoolConnection conn=null;
		for(int i=0;i<100000;i++){
			try{
				conn= ds.getConnection();
				ArrayList params=new ArrayList();
				params.add("joliny".getBytes());
				params.add("kkk".getBytes());
				BaseResultSet<byte[]> rs= conn.execute("hget",params );
				System.out.println(new String(rs.getResult()));
				
				
				ArrayList mset_params=new ArrayList();
				mset_params.add("a".getBytes());
				mset_params.add("aaaaa1".getBytes());
				mset_params.add("b".getBytes());
				mset_params.add("bbbbbb2".getBytes());
				conn.executeUpdate("multi_set", mset_params);
				
				ArrayList mget_params=new ArrayList();
				mget_params.add("a".getBytes());
				mget_params.add("b".getBytes());
				
				BaseResultSet<Map<byte[],byte[]>> m_rs= conn.execute("multi_get",mget_params );
				Map<byte[],byte[]> items=m_rs.getResult();
				Iterator<byte[]> ite=items.keySet().iterator();
				while(ite.hasNext()){
					byte[] key=ite.next();
					System.out.println(new String(key)+"====="+new String(items.get(key)));
				}
				
				ArrayList<byte[]> scan_params=new ArrayList();
				scan_params.add("".getBytes());
				scan_params.add("".getBytes());
				scan_params.add("10".getBytes());
				BaseResultSet<Map<byte[],byte[]>> scan_rs=conn.execute("scan",scan_params );
				Map<byte[],byte[]> scan_items=scan_rs.getResult();
				Iterator<byte[]> scan_ite=scan_items.keySet().iterator();
				while(scan_ite.hasNext()){
					byte[] key=scan_ite.next();
					System.out.println(new String(key)+"====="+new String(scan_items.get(key)));
				}
			}finally{
				conn.close();
			}
		}
	}

}
