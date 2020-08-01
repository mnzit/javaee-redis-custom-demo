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

    private String enableCaching = "Y";
    private String host = "localhost";
    private String password = "cardserver";
    private int port = 6379;
    private int timeout = 200;
    private int maxTotal = 50;
    private int maxIdle = 30;
    private int minIdle = 10;
    private int maxWaitMillis = 200;
    private boolean testOnBorrow = true;
    private boolean testOnReturn = true;
    private boolean testWhileIdle = true;
    private int minEvictableIdleTimeMillis = 60000;
    private int timeBetweenEvictionRunsMillis = 30000;
    private int numTestsPerEvictionRun = -1;
}
