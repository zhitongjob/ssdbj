package com.lovver.ssdbj.core;

public enum SSDBCmd {
	
	SET("set",false),
	DEL("del",false),
	GET("get",true),
	SCAN("scan",true),
	RSCAN("rscan",true),
	INCR("incr",false),
	HSET("hset",false),
	HDEL("hdel",false),
	HGET("hget",true),
	HSCAN("hscan",true),
	HRSCAN("hrscan",true),
	HINCR("hincr",false),
	ZSET("zset",false),
	ZDEL("zdel",false),
	ZGET("zget",true),
	ZSCAN("zscan",true),
	ZRSCAN("zrscan",true),
	ZINCR("zincr",false),
	MULTI_GET("multi_get",true),
	MULTI_SET("multi_set",false),
	MULTI_DEL("multi_del",false),
	PING("ping",false),
	;
	
	private SSDBCmd(String cmd,boolean slave){
		this.cmd=cmd;
		this.slave=slave;
	}
	private String  cmd;
	private boolean slave;
	
	public String getCmd(){
		return cmd;
	}
	public boolean getSlave(){
		return slave;
	}
	
}
