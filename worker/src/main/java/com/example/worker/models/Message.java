package com.example.worker.models;

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
    boolean sent;
    @Column(name = "`deleted`")
    boolean deleted;
    @Id
    @GeneratedValue
    @Column(name = "`id`")
    private long id;
    @Column(name = "`content`", nullable = false, length = 65535, columnDefinition="TEXT")
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
    @MapKeyColumn(name="`key`")
    @Column(name="`value`")
    private Map<String, String> parameters = null;

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
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