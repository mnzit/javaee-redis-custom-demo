package com.mnzit.redis.custom.demo.serializer;

import javax.annotation.Nullable;
import redis.clients.jedis.util.SafeEncoder;

/**
 *
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
public abstract class RedisSerializer<T> {

    public byte[] keyToBytes(String key) {
        return SafeEncoder.encode(key);
    }

    public String keyFromBytes(byte[] bytes) {
        return SafeEncoder.encode(bytes);
    }

    public byte[] valueToBytes(T value) {
        return serialize(value);
    }

    public T valueFromBytes(byte[] bytes) {
        return deserialize(bytes);
    }

    @Nullable
    public abstract byte[] serialize(@Nullable T value);

    @Nullable
    public abstract T deserialize(@Nullable byte[] bytes);
}
