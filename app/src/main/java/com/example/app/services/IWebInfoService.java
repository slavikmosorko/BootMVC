package com.example.app.services;

import com.example.app.models.WebInfoResponse;

import java.util.List;

public interface IWebInfoService {
    List<WebInfoResponse> getInfoForUser(String userId);
}
