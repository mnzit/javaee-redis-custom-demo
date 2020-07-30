package com.mnzit.redis.custom.demo.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
@Getter
@Setter
public class CacheObject implements Serializable {

    private String id;
    private String name;

}
