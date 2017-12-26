package com.example.app.services;

import com.example.app.daos.IWebInfoDAO;
import com.example.app.models.WebInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebInfoService implements IWebInfoService {

    @Autowired
    IWebInfoDAO webInfoDAO;

    @Override
    public List<WebInfoResponse> getInfoForUser(String userId) {
        List<WebInfoResponse> infoForUser = webInfoDAO.getInfoForUser(userId);
        webInfoDAO.receiveMessagesForUser(infoForUser);
        return infoForUser;
    }
}
