package com.bhg.pipeServer.config;

import java.util.ResourceBundle;

import org.springframework.stereotype.Service;

@Service
public class ErrorCode {

	private static final ResourceBundle bundle = ResourceBundle.getBundle("error");

	/**
	 * 成功
	 */
	public static final int SUCCESS = 0;

	/**
	 * 文件读取失败
	 */
	public static final int FILE_LOAD_FAILED = 1;

	/**
	 * 表结构解析失败
	 */
	public static final int TABLE_DESCRIPTOR_PARSE_FAILED = 2;

	/**
	 * 数据库连接失败
	 */
	public static final int DATABASE_CONNECT_FAILED = 3;

	/**
	 * 数据库表创建失败
	 */
	public static final int DATA_TABLE_CREATE_FAILED = 4;

	/**
	 * 数据文件结构校验失败
	 */
	public static final int DATA_PARSE_FAILED = 5;

	/**
	 * 数据库表插入失败
	 */
	public static final int DATA_INSERT_FAILED = 6;

	/**
	 * SSH鉴权失败
	 */
	public static final int SSH_REMOTE_FAILED = 7;

	/**
	 * SSH传输失败
	 */
	public static final int SSH_TRANSFER_FAILED = 8;

	/**
	 * 服务器异常
	 */
	public static final int SERVER_ERROR = 9;
	
	
	/**
	 * 输入参数错误
	 */
	public static final int PARAM_ERROR = 10;

	public static String getErrorMessage(int errorCode) {
		return bundle.getString(String.valueOf(errorCode));
	}

}
