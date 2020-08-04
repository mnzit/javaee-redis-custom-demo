package com.mnzit.redis.custom.demo.config;

import com.mnzit.redis.custom.demo.dto.RedisServerConfig;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

/**
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
@Slf4j
@Singleton
public class RedisConfig {

    private JedisPool jedisPool = null;

    private static final RedisServerConfig redisServerConfig = new RedisServerConfig();

    @PostConstruct
    public void initializePool() {
        log.info("Initalizing Jedis Pool");
        initializeJedis();
    }

    private JedisPool initializeJedis() {
        jedisPool = new JedisPool(configureJedisPool(), redisServerConfig.getHost(), redisServerConfig.getPort(), redisServerConfig.getTimeout(), redisServerConfig.getPassword());
        return jedisPool;
    }

    private JedisPoolConfig configureJedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        jedisPoolConfig.setMaxTotal(redisServerConfig.getMaxTotal());
        jedisPoolConfig.setMaxIdle(redisServerConfig.getMaxIdle());
        jedisPoolConfig.setMinIdle(redisServerConfig.getMinIdle());
        jedisPoolConfig.setMaxWaitMillis(redisServerConfig.getMaxWaitMillis());
        jedisPoolConfig.setTestOnBorrow(redisServerConfig.isTestOnBorrow());
        jedisPoolConfig.setTestOnReturn(redisServerConfig.isTestOnReturn());
        jedisPoolConfig.setTestWhileIdle(redisServerConfig.isTestWhileIdle());
        jedisPoolConfig.setMinEvictableIdleTimeMillis(redisServerConfig.getMinEvictableIdleTimeMillis());
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(redisServerConfig.getTimeBetweenEvictionRunsMillis());
        jedisPoolConfig.setNumTestsPerEvictionRun(redisServerConfig.getNumTestsPerEvictionRun());

        return jedisPoolConfig;
    }

    public synchronized Jedis getJedis() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        } catch (Exception e) {
            log.error("Get jedis error : " + e);
        }
        log.info("jedis obj : {}", jedis);
        return jedis;
    }
}
