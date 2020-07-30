package com.mnzit.redis.custom.demo.scheduler;

import com.mnzit.redis.custom.demo.repository.UserTokenRepository;
import com.mnzit.redis.custom.demo.scheduler.task.NamePrintingTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
@Slf4j
@Singleton
public class DeclarativeScheduler {

    @Inject
    private UserTokenRepository userTokenRepository;

    @Schedule(second = "*", minute = "*", hour = "*", persistent = false)
    public void atSchedule() throws InterruptedException {
        long now = System.currentTimeMillis() / 1000;
        log.info("Fixed rate task with one second initial delay - " + now);

        ExecutorService executor = Executors.newFixedThreadPool(50);

        int count = 1000;
        for (int i = 1; i <= count; i++) {
            //String id = String.valueOf(i);
            String id = "1";
            executor.execute(new NamePrintingTask(userTokenRepository, id, "name" + i));
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }
    }
}