package com.mnzit.redis.custom.demo.dto;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 *
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
public class ModelBase implements Serializable {

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
