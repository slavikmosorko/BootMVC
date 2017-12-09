package com.example.app.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "messages")
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    boolean sent = false;
    boolean deleted = false;
    @Id
    @GeneratedValue
    private long id;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull
    private Date sendingDate;

    public static long getSerialVersionUID() {
        return serialVersionUID;
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
