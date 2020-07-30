package com.mnzit.redis.custom.demo.repository;

import com.mnzit.redis.custom.demo.dto.CacheObject;
import com.mnzit.redis.custom.demo.manager.RedisTemplate;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
@Slf4j
@ApplicationScoped
public class UserTokenRepository {

    @Inject
    private RedisTemplate redisTemplate;

    private String keyPrefix = "HASH_";
    private int ttlInSecond = 60;

    public void create(CacheObject user) {
        String key = keyPrefix + user.getId();
        String hashKey = user.getId();

        redisTemplate.hset(key, hashKey, user);
        redisTemplate.expire(key, ttlInSecond);

        log.info(String.format("Token %s will expire at %s seconds", key, redisTemplate.ttl(key)));
        log.info(String.format("Token %s saved for user id %s", key, user.getId()));
    }

    public CacheObject get(String token) {
        String key = keyPrefix + token;
        String hashKey = token;

        CacheObject cachedTokenDetail = (CacheObject) redisTemplate.hget(key, hashKey);
        log.info(String.format("Token %s read for user id %s", key, cachedTokenDetail.getId()));
        return cachedTokenDetail;
    }

}
