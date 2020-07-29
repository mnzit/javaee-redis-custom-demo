package com.mnzit.redis.custom.demo.config;

import com.mnzit.redis.custom.demo.dto.RedisServerConfig;
import com.mnzit.redis.custom.demo.loader.RedisResourceLoader;
import com.mnzit.redis.custom.demo.qualifier.FromJedisPool;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
@Slf4j
@ApplicationScoped
public class RedisConfig {

    private JedisPool pool = null;

    private static RedisServerConfig redisServerConfig = RedisResourceLoader.getRedisServerConfig();

    @PostConstruct
    public void initializePool() {
        pool = new JedisPool(configureJedisPool(), redisServerConfig.getHost(), redisServerConfig.getPort(), redisServerConfig.getTimeout(), redisServerConfig.getPassword());
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

    @RequestScoped
    @Produces
    @FromJedisPool
    public Jedis get() {
        return pool.getResource();
    }

    @PreDestroy
    public void closePool() {
        pool.close();
    }
}
