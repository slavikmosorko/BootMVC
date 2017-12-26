package com.example.app.controllers;

import com.example.app.models.UserAccount;
import com.example.app.models.WebInfoResponse;
import com.example.app.services.IValidationService;
import com.example.app.services.IWebInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GlobalWebInfoController {
    private IValidationService validationService;
    private IWebInfoService webInfoService;

    @Autowired
    public GlobalWebInfoController(IValidationService validationService,
                                   IWebInfoService webInfoService) {
        this.validationService = validationService;
        this.webInfoService = webInfoService;
    }

    @GetMapping(value = "/webInfo/webPushes/info")
    public ResponseEntity<WebInfoResponse> getWebPushes() {
        UserAccount userAccount = validationService.getUserAccount();
        return new ResponseEntity(webInfoService.getInfoForUser(userAccount.getId()), HttpStatus.OK);
    }

}
