package com.example.worker;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WorkerInfo {
    private final Logger log = Logger.getLogger(this.getClass());
    private static long doneTasksAmount = 0;

    @Scheduled(fixedRate = 30000)
    public void workerInfo() {
        log.info("Tasks done: " + doneTasksAmount);
    }

    public static void addProcessedTask() {
        doneTasksAmount++;
    }

}
