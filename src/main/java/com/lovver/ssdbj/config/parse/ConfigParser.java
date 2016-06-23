package com.lovver.ssdbj.config.parse;

import java.util.List;

import com.lovver.ssdbj.config.Cluster;
import com.lovver.ssdbj.exception.SSDBJConfigException;

public interface ConfigParser {
	public List<Cluster> loadSSDBJ(String conf_file) throws SSDBJConfigException;
}
 