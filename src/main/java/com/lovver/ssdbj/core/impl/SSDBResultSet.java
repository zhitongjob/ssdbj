package com.lovver.ssdbj.core.impl;

import java.util.List;

import com.lovver.ssdbj.core.BaseResultSet;

public class SSDBResultSet<T> implements BaseResultSet<T> {
	public List<byte[]> raw;
	private String status;
	private T result;
	private Exception e;
	
	public SSDBResultSet(String status, List<byte[]> raw,T result) {
		super();
		this.status = status;
		this.raw = raw;
		this.result=result;
	}
	
	public SSDBResultSet(String status, Exception e) {
		this.status = status;
		this.e = e;
	}

	@Override
	public T getResult() {
		return result;
	}
	
	@Override
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public Exception getE() {
		return e;
	}

	public void setE(Exception e) {
		this.e = e;
	}
}
