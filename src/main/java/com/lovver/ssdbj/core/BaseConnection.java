package com.lovver.ssdbj.core;

import java.util.List;

import com.lovver.ssdbj.exception.SSDBException;

/**
 * 基础数据库连接接口
 * 
 * @author jobell.jiang <jobell@qq.com>
 */
public interface BaseConnection extends Wrapper {
	/**
	 * 关闭连接
	 */
	public void close();
	/**
	 * 判断连接是否关闭
	 * 
	 * @return
	 */
	public boolean isClose();
	/**
	 * 判断连接是否正常
	 */
	public boolean isConnection();
	/**
	 * 获取指令执行器
	 * 
	 * @return
	 */
	public CommandExecutor getCommandExecutor();
	
	/**
	 * 执行指令并且返回结果
	 * 
	 * @param cmd
	 * @param params
	 * @return
	 * @throws SSDBException
	 */
	public BaseResultSet execute(String cmd,List<byte[]> params)throws SSDBException;
	
	/**
	 * 执行命令并且更新db
	 * 
	 * @param cmd
	 * @param params
	 * @throws SSDBException
	 */
	public boolean executeUpdate(String cmd,List<byte[]> params)throws SSDBException;
}
