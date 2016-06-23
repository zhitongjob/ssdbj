package com.lovver.ssdbj.core;

/**
 * 结果集处理器接口，用来对结果集进行转换
 * 
 * @author jobell.jiang <jobell@qq.com> 
 */
public interface ResultHandler<T> {
	public T handler(BaseResultSet resultSet);
}
