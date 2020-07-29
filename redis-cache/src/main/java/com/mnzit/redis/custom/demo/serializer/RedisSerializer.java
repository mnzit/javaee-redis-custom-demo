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

    /**
     * Serialize the given object to binary data.
     *
     * @param t object to serialize. Can be {@literal null}.
     * @return the equivalent binary data. Can be {@literal null}.
     */
    @Nullable
    public abstract byte[] serialize(@Nullable T value);

    /**
     * Deserialize an object from the given binary data.
     *
     * @param bytes object binary representation. Can be {@literal null}.
     * @return the equivalent object instance. Can be {@literal null}.
     */
    @Nullable
    public abstract T deserialize(@Nullable byte[] bytes);
}
