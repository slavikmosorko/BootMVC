package com.example.app.services;

import com.example.app.models.UserAccount;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ValidationService implements IValidationService {

    @Override
    public UserAccount getUserAccount() {
        return (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
