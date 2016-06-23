import java.util.ArrayList;
import java.util.Properties;

import com.lovver.ssdbj.core.BaseResultSet;
import com.lovver.ssdbj.core.SSDBDriver;
import com.lovver.ssdbj.core.impl.SSDBConnection;



public class SSDBJTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
	public static void main(String[] args) throws Exception {
		SSDBDriver dd= new SSDBDriver();
		Properties info = new Properties();
		info.setProperty("user", "test");
		info.setProperty("password", "ddd");
		info.setProperty("loginTimeout", "300");
		info.setProperty("so_timeout", "3000");
		info.setProperty("tcpKeepAlive", "true");
		info.setProperty("protocolName", "ssdb"); 
		info.setProperty("protocolVersion", "ddd");
		
		info.setProperty("SSDB_HOST", "192.168.0.226");
		info.setProperty("SSDB_PORT", "8888");
		SSDBConnection conn= dd.connect(info);
//		ArrayList<byte[]> setparams=new ArrayList<byte[]>(){
//			{
//				add("joliny".getBytes());
//				add("kkk".getBytes());
//				add("是的发生地发生1231sdfsfg23".getBytes());
//			}
//		};
//		conn.execute("hset",setparams);
//		
//		ArrayList params=new ArrayList();
//		params.add("joliny".getBytes());
//		params.add("kkk".getBytes());
//		BaseResultSet<byte[]> rs=conn.execute("hget",params );
//		System.out.println(new String(rs.getResult()));
		
		
		ArrayList<byte[]> setparams=new ArrayList<byte[]>(){
			{
				add("joliny".getBytes());
				add("是的发生地发生1231sdfsfg23".getBytes());
			}
		};
		conn.execute("set",setparams);
		
		ArrayList params=new ArrayList();
		params.add("joliny".getBytes());
		BaseResultSet<byte[]> rs=conn.execute("get",params );
		System.out.println(new String(rs.getResult()));
	}

}
