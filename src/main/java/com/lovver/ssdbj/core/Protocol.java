package com.lovver.ssdbj.core;

import com.lovver.ssdbj.exception.SSDBException;


public interface Protocol {
	public String getProtocol();
	public String getProtocolVersion();
	public CommandExecutor getCommandExecutor();
	public void auth() throws SSDBException ;
}
