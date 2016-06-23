import java.util.ArrayList;

import com.lovver.ssdbj.SSDBJ;
import com.lovver.ssdbj.core.BaseResultSet;
import com.lovver.ssdbj.core.SSDBCmd;


public class SSDBClusterTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		ArrayList params=new ArrayList();
		params.add("joliny");
		params.add("kkk");
		SSDBJ.executeUpdate("userinfo_cluster",SSDBCmd.SET,params);
		BaseResultSet<byte[]> rs= SSDBJ.execute("userinfo_cluster",SSDBCmd.GET,params);
		System.out.println(new String(rs.getResult()));
//		
//		ArrayList params=new ArrayList();
//		params.add("joliny");
//		params.add("kkk");
//		BaseResultSet<byte[]> rs= SSDBJ.execute("userinfo_cluster",SSDBCmd.HGET,params);
//		System.out.println(new String(rs.getResult()));
	}

}
