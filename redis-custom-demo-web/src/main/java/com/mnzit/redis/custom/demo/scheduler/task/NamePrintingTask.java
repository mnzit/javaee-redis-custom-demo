package com.mnzit.redis.custom.demo.scheduler.task;

import com.mnzit.redis.custom.demo.dto.CacheObject;
import com.mnzit.redis.custom.demo.repository.UserTokenRepository;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
@Slf4j
public class NamePrintingTask implements Runnable {
    private UserTokenRepository userTokenRepository;
    private String id;
    private String name;

    public NamePrintingTask(UserTokenRepository userTokenRepository, String id, String name) {
        this.userTokenRepository = userTokenRepository;

        this.id = id;
        this.name = name;
    }

    @Override
    public void run() {
        CacheObject cacheObject = new CacheObject();
        cacheObject.setId(id);
        cacheObject.setName(name);
        userTokenRepository.create(cacheObject);

        CacheObject retrievedObject = userTokenRepository.get(id);
        log.info("Retrieved object : " + retrievedObject);
    }
}
