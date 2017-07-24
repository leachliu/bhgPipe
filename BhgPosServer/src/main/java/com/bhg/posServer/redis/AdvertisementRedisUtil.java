package com.bhg.posServer.redis;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

public class AdvertisementRedisUtil {

    private static AdvertisementRedisUtil redis;

    private JedisPool pool;

    private JedisShardInfo shardInfo;

    private AdvertisementRedisUtil() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxActive(RedisConfig.advertisementPoolMaxActive);
        jedisPoolConfig.setMaxIdle(RedisConfig.advertisementPoolMaxIdle);
        jedisPoolConfig.setMaxWait(RedisConfig.advertisementPoolMaxWait);
        jedisPoolConfig.setTestOnBorrow(RedisConfig.advertisementPoolTestOnBorrow);
        shardInfo = new JedisShardInfo(RedisConfig.advertisementHost, RedisConfig.advertisementPort, RedisConfig.advertisementTimeout);
        if (StringUtils.isNotBlank(RedisConfig.advertisementPassword)) {
            shardInfo.setPassword(RedisConfig.advertisementPassword);
        }
        pool = new JedisPool(jedisPoolConfig, shardInfo.getHost(), shardInfo.getPort(), shardInfo.getTimeout(), shardInfo.getPassword(), RedisConfig.advertisementDatabase);
    }

    public static synchronized AdvertisementRedisUtil getInstance() {
        if (redis == null) {
            redis = new AdvertisementRedisUtil();
        }
        return redis;
    }
    
    public long hIncrby(String key, String field, long value) throws Throwable {
        return RedisCommonUtil.hIncrby(pool, key, field, value);
    }


    public String hGet(String key, String field) throws Throwable {
        return RedisCommonUtil.hGet(pool,key,field);
    }

}
