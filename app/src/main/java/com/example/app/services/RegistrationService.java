package com.example.app.services;

import com.example.app.config.Constants;
import com.example.app.daos.IRegistrationDAO;
import com.example.app.models.Message;
import com.example.app.models.UserAccount;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegistrationService implements IRegistrationService {
    private final Logger logger = Logger.getLogger(this.getClass());
    //private final String REGISTRATION_LINK = "http://localhost:8080/register/activate/";
    private final String REGISTRATION_LINK = "http://mvc-boot-application.193b.starter-ca-central-1.openshiftapps.com/register/activate/";
    private final String REGISTRATION_EMAIL = "" +
            "<table data-module=\"activationEmail\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
            "\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t<td data-bgcolor=\"bg-module\" bgcolor=\"#eaeced\">\n" +
            "\t\t\t\t\t\t\t\t<table class=\"flexible\" width=\"600\" align=\"center\" style=\"margin:0 auto;\" cellpadding=\"0\" cellspacing=\"0\">\n" +
            "\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t<td data-bgcolor=\"bg-block\" class=\"holder\" style=\"padding:58px 60px 52px;\" bgcolor=\"#f9f9f9\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t<td data-color=\"title\" data-size=\"size title\" data-min=\"25\" data-max=\"45\" data-link-color=\"link title color\" data-link-style=\"text-decoration:none; color:#292c34;\" class=\"title\" align=\"center\" style=\"font:35px/38px Arial, Helvetica, sans-serif; color:#292c34; padding:0 0 24px;\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\tHello, ${username}.\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t<td data-color=\"text\" data-size=\"size text\" data-min=\"10\" data-max=\"26\" data-link-color=\"link text color\" data-link-style=\"font-weight:bold; text-decoration:underline; color:#40aceb;\" align=\"center\" style=\"font:bold 16px/25px Arial, Helvetica, sans-serif; color:#888; padding:0 0 23px;\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\tPlease, activate your account, to continue working with us.\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding:0 0 20px;\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table width=\"134\" align=\"center\" style=\"margin:0 auto;\" cellpadding=\"0\" cellspacing=\"0\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td data-bgcolor=\"bg-button\" data-size=\"size button\" data-min=\"10\" data-max=\"16\" class=\"btn\" align=\"center\" style=\"font:12px/14px Arial, Helvetica, sans-serif; color:#f8f9fb; text-transform:uppercase; mso-padding-alt:12px 10px 10px; border-radius:2px;\" bgcolor=\"#7bb84f\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<a target=\"_blank\" style=\"text-decoration:none; color:#f8f9fb; display:block; padding:12px 10px 10px;\" href=\"${link}\">Activate</a>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t</table>";

    PasswordEncoder passwordEncoder;
    IRegistrationDAO registrationDAO;
    IMessageService messageService;

    @Autowired
    public RegistrationService(PasswordEncoder passwordEncoder,
                               IRegistrationDAO registrationDAO,
                               IMessageService messageService) {
        this.passwordEncoder = passwordEncoder;
        this.registrationDAO = registrationDAO;
        this.messageService = messageService;
    }

    @Override
    public boolean validateUser(String username) {
        try {
            if (!registrationDAO.validateUser(username)) {
                logger.info("User already exist: " + username);
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error("Error while validate user: " + username);
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class, propagation= Propagation.REQUIRED)
    public boolean registerUser(String username, String password) {
        logger.info("Try to register user: " + username);
        try {
            String activationCode = RandomStringUtils.randomAlphabetic(32);
            UserAccount userAccount = new UserAccount(username,
                    passwordEncoder.encode(password),
                    false,
                    true,
                    true,
                    true,
                    Collections.EMPTY_LIST,
                    activationCode);
            registrationDAO.registerUser(userAccount);
            logger.info(username + " successfully registered.");
            messageService.addMessage(createActivationEmail(userAccount));
            logger.info("Activation message for [" + username + "] successfully scheduled.");
            return true;
        } catch (Exception e) {
            logger.error("Error while register user: " + username);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class, propagation= Propagation.REQUIRED)
    public boolean activateUser(String ac) {
        try {
            if(!registrationDAO.activateUser(ac)) {
                logger.info("User already active: " + ac);
                return false;
            }
            logger.info("User activated: " + ac);
            return true;
        } catch (Exception e) {
            logger.error("Error while activate user with key: " + ac);
            return false;
        }
    }

    private Message createActivationEmail(UserAccount userAccount) {
        Message message = new Message();
        message.setSendingDate(new Date());
        message.setSubject("Account activation.");
        message.setAddressee(userAccount.getUsername());
        message.setContent(REGISTRATION_EMAIL);
        message.setUserId(Constants.ACTIVATION_EMAIL_ID);
        Map<String, String> parameters = new HashMap();
        parameters.put("username", userAccount.getUsername());
        parameters.put("link", REGISTRATION_LINK + userAccount.getActivationCode());
        message.setParameters(parameters);
        return  message;
    }
}
