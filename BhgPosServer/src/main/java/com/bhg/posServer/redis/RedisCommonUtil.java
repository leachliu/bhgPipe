package com.bhg.posServer.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisCommonUtil {
	
	public static long hIncrby(JedisPool pool, String key, String field, long value) throws Throwable {
		Jedis jedis = pool.getResource();
		try {
			return jedis.hincrBy(key, field, value);
		} catch (Throwable e) {
			if (null != jedis) {
				pool.returnBrokenResource(jedis);
				jedis = null;
			}
			throw e;
		} finally {
			if (null != jedis) {
				pool.returnResource(jedis);
			}
		}
	}
	
    public static String hGet(JedisPool pool,String key, String field) throws Throwable {
        Jedis jedis = pool.getResource();
        try {
            return jedis.hget(key, field);
        } catch (Throwable e) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
            throw e;
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }
        }
    }
}
