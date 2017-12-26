package com.example.worker.services;

import com.example.worker.models.Message;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProcessingFacade {
    private static final Logger log = Logger.getLogger(ProcessingFacade.class);
    public volatile static List<Message> messageList = Collections.EMPTY_LIST;
    public volatile static Set<String> processedEmails = new HashSet<>();

    public static void deleteEmailFromQueue(String emailId) {
        processedEmails.remove(emailId);
    }
}
