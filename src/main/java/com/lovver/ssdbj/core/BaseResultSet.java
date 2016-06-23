package com.lovver.ssdbj.core;


/**
 * 基础结果集接口
 * 
 * @author jobell.jiang <jobell@qq.com>
 */
public interface BaseResultSet<T> {
	public T getResult();
	public String getStatus();
}
