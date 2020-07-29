package com.mnzit.redis.custom.demo.loader;

import com.mnzit.redis.custom.demo.util.YamlReader;
import com.mnzit.redis.custom.demo.dto.RedisServerConfig;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
@Slf4j
public class RedisResourceLoader {

    @Getter
    private static RedisServerConfig redisServerConfig;
    private YamlReader reader = null;

    @PostConstruct
    public void bootstrap() {
        PropertyConfigurator.configure("log4j.properties");
        reader = new YamlReader();
        redisServerConfig = reader.load("redis-server-config.yml", RedisServerConfig.class);
        log.info("Redis Server config : {}", redisServerConfig);
    }
}
