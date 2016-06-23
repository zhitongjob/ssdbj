package com.lovver.ssdbj.core;

import com.lovver.ssdbj.exception.SSDBException;

public interface Wrapper {
	
	public <T> T unwrap(java.lang.Class<T> iface) throws SSDBException;

	public boolean isWrapperFor(java.lang.Class<?> iface) throws SSDBException;
}
