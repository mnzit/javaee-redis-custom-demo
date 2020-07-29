package com.mnzit.redis.custom.demo.dto;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
@Getter
@Setter
public class RedisServerConfig extends ModelBase {

    private String enableCaching;
    private String host;
    private String password;
    private int port;
    private int timeout;
    private int maxTotal;
    private int maxIdle;
    private int minIdle;
    private int maxWaitMillis;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean testWhileIdle;
    private int minEvictableIdleTimeMillis;
    private int timeBetweenEvictionRunsMillis;
    private int numTestsPerEvictionRun;
}
