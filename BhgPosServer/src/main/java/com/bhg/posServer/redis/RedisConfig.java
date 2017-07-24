package com.bhg.posServer.redis;

import java.util.ResourceBundle;

public class RedisConfig {

	private static final ResourceBundle bundle = ResourceBundle.getBundle("redis");
	
	public static String host = getString("redis.host");
	
	public static int port = getInt("redis.port");
	
	public static String password = getString("redis.password");
	
	public static int timeout = getInt("redis.timeout");
	
	public static int database = getInt("redis.default.db");
	
	public static int poolMaxActive = getInt("redis.pool.maxActive");
	
	public static int poolMaxIdle = getInt("redis.pool.maxIdle");
	
	public static int poolMaxWait = getInt("redis.pool.maxWait");
	
	public static int batchSize = 20;
	
	public static boolean poolTestOnBorrow = getBoolean("redis.pool.testOnBorrow");
	
	
	
	public static String advertisementHost = getString("advertisement.redis.host");
	
	public static int advertisementPort = getInt("advertisement.redis.port");
	
	public static String advertisementPassword = getString("advertisement.redis.password");
	
	public static int advertisementTimeout = getInt("advertisement.redis.timeout");
	
	public static int advertisementDatabase = getInt("advertisement.redis.default.db");
	
	public static int advertisementPoolMaxActive = getInt("advertisement.redis.pool.maxActive");
	
	public static int advertisementPoolMaxIdle = getInt("advertisement.redis.pool.maxIdle");
	
	public static int advertisementPoolMaxWait = getInt("advertisement.redis.pool.maxWait");
	
    public static int advertisementBatchSize = 20;
	
	public static boolean advertisementPoolTestOnBorrow = getBoolean("advertisement.redis.pool.testOnBorrow");

	private static String getString(String key) {
		return bundle.getString(key);
	}
	
	private static int getInt(String key) {
		return Integer.parseInt(bundle.getString(key));
	}
	
	private static boolean getBoolean(String key) {
		return Boolean.parseBoolean(bundle.getString(key));
	}
}
