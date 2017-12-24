package com.example.worker.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;

@Entity
@Proxy(lazy = false)
@Table(name = "messages")
public class Message {
    @Column(name = "`sent`")
    boolean sent = false;
    @Column(name = "`deleted`")
    boolean deleted = false;
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "`id`", updatable = false, nullable = false)
    private String id;
    @Column(name = "`content`", nullable = false, length = 65535, columnDefinition = "TEXT")
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull
    @Column(name = "`sending_date`")
    private Date sendingDate;
    @Column(name = "`addressee`")
    private String addressee;
    @Column(name = "`subject`")
    private String subject;
    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "`message_parameters`")
    @MapKeyColumn(name = "`key`")
    @Column(name = "`value`")
    private Map<String, String> parameters = null;
    @Column(name = "`user_id`")
    private String userId;

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(Date sendingDate) {
        this.sendingDate = sendingDate;
    }
}
