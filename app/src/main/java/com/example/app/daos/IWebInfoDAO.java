package com.example.app.daos;

import com.example.app.models.WebInfoResponse;

import java.util.List;

public interface IWebInfoDAO {
    List<WebInfoResponse> getInfoForUser(String userId);

    void receiveMessagesForUser(List<WebInfoResponse> messages);
}
