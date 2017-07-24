package com.bhg.posServer.redis;

import java.util.Set;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

public class RedisUtil {

    private static RedisUtil redis;

    private JedisPool pool;

    private JedisShardInfo shardInfo;

    private RedisUtil() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxActive(RedisConfig.poolMaxActive);
        jedisPoolConfig.setMaxIdle(RedisConfig.poolMaxIdle);
        jedisPoolConfig.setMaxWait(RedisConfig.poolMaxWait);
        jedisPoolConfig.setTestOnBorrow(RedisConfig.poolTestOnBorrow);
        shardInfo = new JedisShardInfo(RedisConfig.host, RedisConfig.port, RedisConfig.timeout);
        if (StringUtils.isNotBlank(RedisConfig.password)) {
            shardInfo.setPassword(RedisConfig.password);
        }
        pool = new JedisPool(jedisPoolConfig, shardInfo.getHost(), shardInfo.getPort(), shardInfo.getTimeout(), shardInfo.getPassword(), RedisConfig.database);
    }

    public static synchronized RedisUtil getInstance() {
        if (redis == null) {
            redis = new RedisUtil();
        }
        return redis;
    }
    
    public long hIncrby(String key, String field, long value) throws Throwable {
    	return RedisCommonUtil.hIncrby(pool, key, field, value);
    }
    
    public static void main(String[] args) throws Throwable {
		System.out.println(RedisUtil.getInstance().hIncrby("Test", "test", 1));
		System.out.println(RedisUtil.getInstance().hIncrby("Test", "test", 0));
	}

    public boolean sAdd(String key, String... values) {
        Jedis jedis = pool.getResource();
        try {
            jedis.sadd(key, values);
            return true;
        } catch (Throwable e) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
            return false;
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }
        }
    }

    public Set<String> sMembers(String key) {
        Jedis jedis = pool.getResource();
        try {

            return jedis.smembers(key);
        } catch (Throwable e) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
            return null;
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }
        }
    }

    public Long getSetCount(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.scard(key);
        } catch (Throwable e) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
            return 0L;
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }
        }
    }

    public String hGet(String key, String field) throws Throwable {
        return RedisCommonUtil.hGet(pool,key,field);
    }

    public boolean hSet(String key, String field, String str) {
        Jedis jedis = pool.getResource();
        try {
            jedis.hset(key, field, str);
            return true;
        } catch (Throwable e) {
            e.getStackTrace();
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
            return false;
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }
        }
    }

    public void hDel(String key, String field) {
        Jedis jedis = pool.getResource();
        try {
            jedis.hdel(key, field);
        } catch (Throwable e) {

            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }
        }
    }

    public Set<String> keys(String startStr) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.keys(startStr);
        } catch (Throwable e) {

            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
            return null;
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }
        }
    }

    public Set<String> hKeys(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.hkeys(key);
        } catch (Throwable e) {

            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
            return null;
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }
        }
    }

    public void del(String key) {
        Jedis jedis = pool.getResource();
        try {
            jedis.del(key);
        } catch (Throwable e) {

            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }
        }
    }

    public void rename(String oldKey, String newKey) {
        Jedis jedis = pool.getResource();
        try {
            if (jedis.exists(oldKey)) {
                jedis.rename(oldKey, newKey);
            }
        } catch (Throwable e) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }
        }
    }

    public void replace(String key, String tempKey) {
        del(key);
        rename(tempKey, key);
    }

    public void close() {
        redis = null;
        shardInfo = null;
        pool.destroy();
        pool = null;
    }

}
