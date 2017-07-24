package com.bhg.pipeServer.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PipeLogger {
	public static final Logger Visitor = LogManager.getLogger("dmp.visitor");
	public static final Logger Info = LogManager.getLogger("dmp.info");
	public static final Logger Exception = LogManager.getLogger("dmp.error");
}
