package com.example.worker;




import com.example.app.models.Message;
import com.example.app.services.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private static List<Message> messageList = new ArrayList<>();

    private IMessageService messageService;

    @Autowired
    public ScheduledTasks(IMessageService messageService) {
        this.messageService = messageService;
    }

    @Scheduled(fixedRate = 5000)
    public void sendMassaging() {
        if (messageList.isEmpty()) {
            List<Message> allUnprocessedMessages = messageService.getAllUnprocessedMessages();
            messageList.addAll(allUnprocessedMessages);
            log.info("[MESSENGER] Added messages: " + allUnprocessedMessages.size());
        }
        log.info("[MESSENGER] Messages amount: " + messageList.size());
        sendMessages();
    }

    private void sendMessages() {
        if (!messageList.isEmpty()) {
            messageList.stream()
                    .filter(message -> !message.isSent())
                    .forEach(
                            message -> {
                                log.info("\n[MESSAGE SENT]\n ID:" + message.getId() + "\n SENDING DATE: " + message.getSendingDate() + "\n CONTENT: " + message.getContent());
                                message.setSent(true);
                                try {
                                    messageService.updateMessage(message.getId());
                                    WorkerInfo.doneTasksAmount++;
                                } catch (Exception e) {
                                    log.error("Cant send message ID: " + message.getId());
                                }
                            }
                    );
            messageList.clear();
        }
    }
}
