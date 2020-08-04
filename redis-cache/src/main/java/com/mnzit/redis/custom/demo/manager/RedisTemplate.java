package com.mnzit.redis.custom.demo.manager;

import com.mnzit.redis.custom.demo.SerializerEnum;
import com.mnzit.redis.custom.demo.config.RedisConfig;
import com.mnzit.redis.custom.demo.qualifier.Serializer;
import com.mnzit.redis.custom.demo.serializer.RedisSerializer;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

/**
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
@Slf4j
@ApplicationScoped
public class RedisTemplate {

    @Inject
    private RedisConfig redisConfig;

    @Inject
    @Serializer(SerializerEnum.JDK_SERIALIZER)
    private RedisSerializer serializer;

    public synchronized String set(Object key, Object value) {
        Jedis jedis = redisConfig.getJedis();
        try {
            return jedis.set(keyToBytes(key), valueToBytes(value));
        } finally {
            close(jedis);
        }
    }

    public synchronized Long del(Object key) {
        Jedis jedis = redisConfig.getJedis();
        try {
            return jedis.del(keyToBytes(key));
        } finally {
            close(jedis);
        }
    }

    private static final String DELETE_SCRIPT_IN_LUA
            = "local keys = redis.call('keys', '%s')"
            + "  for i,k in ipairs(keys) do"
            + "    local res = redis.call('del', k)"
            + "  end";

    public synchronized Object deleteKeys(String pattern) {
        Jedis jedis = redisConfig.getJedis();
        try {
            return jedis.eval(String.format(DELETE_SCRIPT_IN_LUA, pattern));
        } finally {
            close(jedis);
        }
    }

    public synchronized Long hdel(Object key, List<String> subKeys) {
        Jedis jedis = redisConfig.getJedis();
        try {
            return jedis.hdel(keyToBytes(key), keyToBytesArray(subKeys));
        } finally {
            close(jedis);
        }
    }

    public synchronized Long hdel(Object key, Object subKeys) {

        log.info("Removing Key from redis :{} and subkeys {}", key, subKeys);
        Jedis jedis = redisConfig.getJedis();
        try {
            return jedis.hdel(keyToBytes(key), keyToByteSingle(subKeys));
        } finally {
            close(jedis);
        }
    }

    public synchronized void setex(Object key, int seconds, Object value) {
        Jedis jedis = redisConfig.getJedis();

        try {
            jedis.setex(keyToBytes(key), seconds, valueToBytes(value));

        } finally {
            close(jedis);
        }

    }

    public synchronized <T> T get(Object key) {
        Jedis jedis = redisConfig.getJedis();
        try {
            T t = (T) valueFromBytes(jedis.get(keyToBytes(key)));
            return t;
        } finally {
            close(jedis);
        }
    }

    public synchronized Long hset(Object key, Object field, Object value) {
        Jedis jedis = redisConfig.getJedis();
        try {
            return jedis.hset(keyToBytes(key), keyToBytes(field), valueToBytes(value));
        } finally {
            close(jedis);
        }
    }

    public synchronized <T> T hget(Object key, Object field) {
        Jedis jedis = redisConfig.getJedis();
        try {
            return (T) valueFromBytes(jedis.hget(keyToBytes(key), keyToBytes(field)));
        } finally {
            close(jedis);
        }
    }

    public synchronized Long expire(Object key, int seconds) {
        Jedis jedis = redisConfig.getJedis();
        try {
            return jedis.expire(keyToBytes(key), seconds);
        } finally {
            close(jedis);
        }
    }

    protected byte[][] keyToBytesArray(List<String> subKeys) {
        byte[][] data = new byte[subKeys.size()][];
        for (int i = 0; i < data.length; i++) {
            data[i] = keyToBytes(subKeys.get(i));
        }
        return data;
    }

    protected byte[][] keyToBytesArray(Object subKeys) {
        byte[][] data = new byte[0][];
        data[0] = keyToBytes(subKeys);
        return data;
    }

    protected byte[][] keyToByteSingle(Object subKey) {
        byte[][] data = new byte[1][];
        data[0] = keyToBytes(subKey);
        return data;
    }

    private byte[] keyToBytes(Object key) {
        String keyStr = key.toString();
        return serializer.keyToBytes(keyStr);
    }

    private byte[] valueToBytes(Object value) {
        return serializer.valueToBytes(value);
    }

    private Object valueFromBytes(byte[] bytes) {
        return serializer.valueFromBytes(bytes);
    }

    public void close(Jedis jedis) {
        if (jedis != null) {
            try {
                jedis.close();
            } catch (Exception e) {
                log.error("Exception closing jedis connection: {}", e.getMessage());
            }
        }
    }

    public synchronized Long ttl(String key) {
        Jedis jedis = redisConfig.getJedis();
        try {
            return jedis.ttl(key);
        } finally {
            close(jedis);
        }

    }

    public Long flushAll() {
        Jedis jedis = redisConfig.getJedis();
        try {
            return jedis.flushAll().equals("OK") ? 1L : 0L;
        } finally {
            close(jedis);
        }
    }

}
