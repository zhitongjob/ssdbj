package com.lovver.ssdbj.core.protocol;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.lovver.ssdbj.core.BaseResultSet;
import com.lovver.ssdbj.core.CommandExecutor;
import com.lovver.ssdbj.core.MemoryStream;
import com.lovver.ssdbj.core.Protocol;
import com.lovver.ssdbj.core.SSDBCmd;
import com.lovver.ssdbj.core.Stream2ResultSet;
import com.lovver.ssdbj.core.impl.SSDBResultSet;
import com.lovver.ssdbj.exception.SSDBException;

public class SSDBProtocolImpl implements Protocol{
	private String protocolName="ssdb";
	private String protocolVersion="1.0v";
	
	private MemoryStream input = new MemoryStream();
	private OutputStream outputStream;
	private InputStream inputStream;
	private Properties props;
//    private Protocol protocol;
	
	public SSDBProtocolImpl(OutputStream os,InputStream is,Properties infos){
		this.outputStream=os;
		this.inputStream=is;
		this.props=infos;
//		protocol=SSDBProtocolFactory.createSSDBProtocolImpl(protocolName,outputStream,inputStream);
	}
	
	public void sendCommand(String cmd,List<byte[]> params)throws SSDBException {
    	MemoryStream buf = new MemoryStream(4096);
		Integer len = cmd.length();
		buf.write(len.toString());
		buf.write('\n');
		buf.write(cmd);
		buf.write('\n');
		for(byte[] bs : params){
			len = bs.length;
			buf.write(len.toString());
			buf.write('\n');
			buf.write(bs);
			buf.write('\n');
		}
		buf.write('\n');
		try{
			outputStream.write(buf.buf, buf.data, buf.size);
			outputStream.flush();
		}catch(Exception e){
			throw new SSDBException(e);
		}
    }
	
    
	public List<byte[]> receive() throws SSDBException{
		try{
			input.nice();
			while(true){
				List<byte[]> ret = parse();
				if(ret != null){
					return ret;
				}
				byte[] bs = new byte[8192];
				int len = inputStream.read(bs);
				//System.out.println("<< " + (new MemoryStream(bs, 0, len)).printable());
				input.write(bs, 0, len);
			}
		}catch(Exception e){
			throw new SSDBException(e);
		}
	}
	
	private List<byte[]> parse(){
		ArrayList<byte[]> list = new ArrayList<byte[]>();
		byte[] buf = input.buf;
		
		int idx = 0;
		while(true){
			int pos = input.memchr('\n', idx);
			//System.out.println("pos: " + pos + " idx: " + idx);
			if(pos == -1){
				break;
			}
			if(pos == idx || (pos == idx + 1 && buf[idx] == '\r')){
				// ignore empty leading lines
				if(list.isEmpty()){
					idx += 1; // if '\r', next time will skip '\n'
					continue;
				}else{
					input.decr(idx + 1);
					return list;
				}
			}
			String str = new String(buf, input.data + idx, pos - idx);
			int len = Integer.parseInt(str);
			idx = pos + 1;
			if(idx + len >= input.size){
				break;
			}
			byte[] data = Arrays.copyOfRange(buf, input.data + idx, input.data + idx + len);
			//System.out.println("len: " + len + " data: " + data.length);
			idx += len + 1; // skip '\n'
			list.add(data);
		}
		return null;		
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
	public CommandExecutor getCommandExecutor() {
		return new CommandExecutor(){

			@Override
			public BaseResultSet execute(final String cmd, List<byte[]> params)
					throws SSDBException {
//				if("del".equals(cmd.toLowerCase())){
//					System.out.println("//////////////////////////////////////////////////del/////////////////////////");
//				}
				sendCommand(cmd,params);
				final List<byte[]> result=receive();
				return (new Stream2ResultSet(){

					@Override
					public BaseResultSet execute() {
						try{
							String cmd_t=cmd.toLowerCase();
							if(cmd_t.equals(SSDBCmd.GET.getCmd())){
								String status=new String(result.get(0));
								if(result.size() != 2){
									if("not_found".equals(status)){
										return new SSDBResultSet<byte[]>(status,result,null); 
									}
									throw new Exception("Invalid response");
								}
								
								return new SSDBResultSet<byte[]>(status,result,result.get(1)); 
							}
							if(cmd_t.equals(SSDBCmd.SCAN.getCmd())){
								String status=new String(result.get(0));
								buildMap();
								return new SSDBResultSet<Map<byte[], byte[]>>(status,result,items);
							}
							if(cmd_t.equals(SSDBCmd.RSCAN.getCmd())){
								String status=new String(result.get(0));
								buildMap();
								return new SSDBResultSet<Map<byte[], byte[]>>(status,result,items);
							}
							if(cmd_t.equals(SSDBCmd.INCR.getCmd())){
								String status=new String(result.get(0));
								if(result.size() != 2){
									if("not_found".equals(status)){
										return new SSDBResultSet<byte[]>(status,result,null); 
									}
									throw new Exception("Invalid response");
								}
								long ret = 0;
								ret = Long.parseLong(new String(result.get(1)));
								
								return new SSDBResultSet<Long>(status,result,ret);
							}
							if(cmd_t.equals(SSDBCmd.HGET.getCmd())){
								String status=new String(result.get(0));
								if(result.size() != 2){
									if("not_found".equals(status)){
										return new SSDBResultSet<byte[]>(status,result,null); 
									}
									throw new Exception("Invalid response");
								}
								return new SSDBResultSet<byte[]>(status,result,result.get(1)); 
							}
							if(cmd_t.equals(SSDBCmd.HSCAN.getCmd())){
								String status=new String(result.get(0));
								buildMap();
								return new SSDBResultSet<Map<byte[], byte[]>>(status,result,items);
							}
							if(cmd_t.equals(SSDBCmd.HRSCAN.getCmd())){
								String status=new String(result.get(0));
								buildMap();
								return new SSDBResultSet<Map<byte[], byte[]>>(status,result,items);
							}
							if(cmd_t.equals(SSDBCmd.HINCR.getCmd())){
								String status=new String(result.get(0));
								if(result.size() != 2){
									if("not_found".equals(status)){
										return new SSDBResultSet<byte[]>(status,result,null); 
									}
									throw new Exception("Invalid response");
								}
								long ret = 0;
								ret = Long.parseLong(new String(result.get(1)));
								
								return new SSDBResultSet<Long>(status,result,ret);
							}
							if(cmd_t.equals(SSDBCmd.ZGET.getCmd())){
								String status=new String(result.get(0));
								if(result.size() != 2){
									if("not_found".equals(status)){
										return new SSDBResultSet<byte[]>(status,result,null); 
									}
									throw new Exception("Invalid response");
								}
								long ret = 0;
								ret = Long.parseLong(new String(result.get(1)));
								
								return new SSDBResultSet<Long>(status,result,ret);
							}
							if(cmd_t.equals(SSDBCmd.ZSCAN.getCmd())){
								String status=new String(result.get(0));
								buildMap();
								return new SSDBResultSet<Map<byte[], byte[]>>(status,result,items);
							}
							if(cmd_t.equals(SSDBCmd.ZRSCAN.getCmd())){
								String status=new String(result.get(0));
								buildMap();
								return new SSDBResultSet<Map<byte[], byte[]>>(status,result,items);
							}
							if(cmd_t.equals(SSDBCmd.ZINCR.getCmd())){
								String status=new String(result.get(0));
								if(result.size() != 2){
									if("not_found".equals(status)){
										return new SSDBResultSet<byte[]>(status,result,null); 
									}
									throw new Exception("Invalid response");
								}
								long ret = 0;
								ret = Long.parseLong(new String(result.get(1)));
								
								return new SSDBResultSet<Long>(status,result,ret);
							}
							
							if(cmd_t.equals(SSDBCmd.MULTI_GET.getCmd())){
								String status=new String(result.get(0));
								buildMap();
								return new SSDBResultSet<Map<byte[], byte[]>>(status,result,items);
							}
							
							if(cmd_t.equals(SSDBCmd.MULTI_DEL.getCmd())){
								String status=new String(result.get(0));
								buildMap();
								return new SSDBResultSet<Map<byte[], byte[]>>(status,result,items);
							}
							
							if(cmd_t.equals(SSDBCmd.PING.getCmd())){
								String status=new String(result.get(0));
								return new SSDBResultSet<Map<byte[], byte[]>>(status,result,null);
							}
							
						}catch(Exception e){
							return new SSDBResultSet("error",e);
						}
						
//						String status=new String(result.get(0));
						return null;//new SSDBResultSet<Map<byte[], byte[]>>(status,result,null);
					}
					
					private List<byte[]> keys = new ArrayList<byte[]>();
					private Map<byte[], byte[]> items = new LinkedHashMap<byte[], byte[]>();
					private void buildMap(){
						for(int i=1; i<result.size(); i+=2){
							byte[] k = result.get(i);
							byte[] v = result.get(i+1);
							keys.add(k);
							items.put(k, v);
						}
					}
					
				}).execute();
			}

			@Override
			public boolean executeUpdate(String cmd, List<byte[]> params)
					throws SSDBException {
				try{
					sendCommand(cmd,params);
					List<byte[]> result=receive();
					return "ok".equals(new String(result.get(0)));
				}catch(Exception e){
					return false;
				}
			}

			
		};
	}

	@Override
	public void auth() {
		final String sauth=props.getProperty("password");
		List<byte[]> auth=new ArrayList<byte[]>(){{
			add(sauth.getBytes());
		}};
		try {
			sendCommand("auth",auth);
			List<byte[]> authResult=receive();
			if(!"ok".equals(new String(authResult.get(0)))){
				System.out.println("auth failed");
				throw new RuntimeException("auth failed");
			}
		} catch (SSDBException e) {
			System.out.println("auth failed");
			throw new RuntimeException("auth failed");
		}
	} 
}
