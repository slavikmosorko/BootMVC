package com.example.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WorkerInfo {
    private static final Logger log = LoggerFactory.getLogger(WorkerInfo.class);
    private static long doneTasksAmount = 0;

    @Scheduled(fixedRate = 10000)
    public void workerInfo() {
        log.info("[WORKER] Tasks done: " + doneTasksAmount);
    }

    public static void addProcessedTask() {
        doneTasksAmount++;
    }

}
