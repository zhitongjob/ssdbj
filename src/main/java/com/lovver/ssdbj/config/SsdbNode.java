package com.lovver.ssdbj.config;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SsdbNode implements Serializable {
	private String id;
	private boolean master;
	private String host;
	private int port;
	private String user;
	private String password;
	private int loginTimeout;
	private boolean tcpKeepAlive;
	private String protocolName;
	
	
	
	private String minIdle;
	private String maxTotal;
	private String maxIdle;
	private String maxWaitMillis;
	private String minEvictableIdleTimeMillis;
	private String timeBetweenEvictionRunsMillis;
	private String testWhileIdle;
	private String testOnReturn;
	private String testOnCreate;
	private String testOnBorrow;
	private String softMinEvictableIdleTimeMillis;
	private String numTestsPerEvictionRun;
	private String blockWhenExhausted;
	private String removeAbandonedOnBorrow;
	private String removeAbandonedTimeout;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isMaster() {
		return master;
	}
	public void setMaster(boolean master) {
		this.master = master;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getLoginTimeout() {
		return loginTimeout;
	}
	public void setLoginTimeout(int loginTimeout) {
		this.loginTimeout = loginTimeout;
	}
	public boolean isTcpKeepAlive() {
		return tcpKeepAlive;
	}
	public void setTcpKeepAlive(boolean tcpKeepAlive) {
		this.tcpKeepAlive = tcpKeepAlive;
	}
	public String getProtocolName() {
		return protocolName;
	}
	public void setProtocolName(String protocolName) {
		this.protocolName = protocolName;
	}
	public String getMinIdle() {
		return minIdle;
	}
	public void setMinIdle(String minIdle) {
		this.minIdle = minIdle;
	}
	public String getMaxTotal() {
		return maxTotal;
	}
	public void setMaxTotal(String maxTotal) {
		this.maxTotal = maxTotal;
	}
	public String getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(String maxIdle) {
		this.maxIdle = maxIdle;
	}
	public String getMaxWaitMillis() {
		return maxWaitMillis;
	}
	public void setMaxWaitMillis(String maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}
	public String getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}
	public void setMinEvictableIdleTimeMillis(String minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}
	public String getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}
	public void setTimeBetweenEvictionRunsMillis(
			String timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}
	public String getTestWhileIdle() {
		return testWhileIdle;
	}
	public void setTestWhileIdle(String testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}
	public String getTestOnReturn() {
		return testOnReturn;
	}
	public void setTestOnReturn(String testOnReturn) {
		this.testOnReturn = testOnReturn;
	}
	public String getTestOnCreate() {
		return testOnCreate;
	}
	public void setTestOnCreate(String testOnCreate) {
		this.testOnCreate = testOnCreate;
	}
	public String getTestOnBorrow() {
		return testOnBorrow;
	}
	public void setTestOnBorrow(String testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}
	public String getSoftMinEvictableIdleTimeMillis() {
		return softMinEvictableIdleTimeMillis;
	}
	public void setSoftMinEvictableIdleTimeMillis(
			String softMinEvictableIdleTimeMillis) {
		this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
	}
	public String getNumTestsPerEvictionRun() {
		return numTestsPerEvictionRun;
	}
	public void setNumTestsPerEvictionRun(String numTestsPerEvictionRun) {
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}
	public String getBlockWhenExhausted() {
		return blockWhenExhausted;
	}
	public void setBlockWhenExhausted(String blockWhenExhausted) {
		this.blockWhenExhausted = blockWhenExhausted;
	}
	public String getRemoveAbandonedOnBorrow() {
		return removeAbandonedOnBorrow;
	}
	public void setRemoveAbandonedOnBorrow(String removeAbandonedOnBorrow) {
		this.removeAbandonedOnBorrow = removeAbandonedOnBorrow;
	}
	public String getRemoveAbandonedTimeout() {
		return removeAbandonedTimeout;
	}
	public void setRemoveAbandonedTimeout(String removeAbandonedTimeout) {
		this.removeAbandonedTimeout = removeAbandonedTimeout;
	}
	
}
