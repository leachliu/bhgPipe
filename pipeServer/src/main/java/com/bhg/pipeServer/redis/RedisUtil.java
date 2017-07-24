package com.bhg.pipeServer.redis;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bhg.pipeServer.config.AppConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

@Component
public class RedisUtil {

	private static final Logger log = LoggerFactory.getLogger(RedisUtil.class);

	private static RedisUtil redis;

	private JedisPool pool;

	private JedisShardInfo shardInfo;

	Jedis jredis;

	@Autowired
	AppConfig config;

	@PostConstruct
	void init() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(config.REDIS_POOL_MAX_ACTIVE);
		jedisPoolConfig.setMaxIdle(config.REDIS_POOL_MAX_IDLE);
		jedisPoolConfig.setMaxWaitMillis(config.REDIS_POOL_MAX_WAIT);
		jedisPoolConfig.setTestOnBorrow(config.REDIS_POOL_TEST_ON_BORROW);
		shardInfo = new JedisShardInfo(config.REDIS_HOST, config.REDIS_PORT, config.REDIS_PASSWORD);
		if (StringUtils.isNotBlank(config.REDIS_PASSWORD)) {
			shardInfo.setPassword(config.REDIS_PASSWORD);
		}

		pool = new JedisPool(jedisPoolConfig, shardInfo.getHost(), shardInfo.getPort(),
				shardInfo.getConnectionTimeout(), shardInfo.getPassword(), config.REDIS_DEFAULT_DATABASE);
	}

	public static synchronized RedisUtil getInstance() {
		if (redis == null) {
			redis = new RedisUtil();
		}
		return redis;
	}

	public long hSet(String key, String field, String value) {
		Jedis jredis = pool.getResource();
		try {
			return jredis.hset(key, field, value);
		} catch (Throwable e) {
			close(jredis);
			return 0;
		} finally {
			close(jredis);
		}
	}

	public long hIncrby(String key, String field, long value) {
		Jedis jredis = pool.getResource();
		try {
			return jredis.hincrBy(key, field, value);
		} catch (Throwable e) {
			close(jredis);
			return 0;
		} finally {
			close(jredis);
		}
	}

	public Set<String> keys(String startStr) {
		Jedis jredis = pool.getResource();
		try {
			return jredis.keys(startStr);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			close(jredis);

			return null;
		} finally {
			close(jredis);
		}
	}

	public Set<String> hKeys(String key) {
		Jedis jredis = pool.getResource();
		try {
			return jredis.hkeys(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			close(jredis);
			return null;
		} finally {
			close(jredis);
		}
	}

	public long del(String key) {
		Jedis jredis = pool.getResource();
		try {
			return jredis.del(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			close(jredis);
			return 0;
		} finally {
			close(jredis);
		}
	}

	public long del(String[] keys) {
		Jedis jredis = pool.getResource();
		try {
			return jredis.del(keys);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			close(jredis);
			return 0;
		} finally {
			close(jredis);
		}
	}

	private Jedis getJedis() {
		if (null == jredis) {
			jredis = pool.getResource();
		}
		return jredis;
	}

	public long sadd(String key, String value) {
		Jedis jredis = getJedis();
		try {
			return jredis.sadd(key, value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			close(jredis);
			return 0;
		} finally {
		}
	}

	public void set(String key, String value) {
		Jedis jredis = getJedis();
		try {
			jredis.sadd(key, value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			close(jredis);
		} finally {
		}
	}

	private void close(Jedis jredis) {
		try {
			jredis = pool.getResource();
		} finally {
			if (jredis != null) {
				jredis.close();
			}
		}
	}
}
