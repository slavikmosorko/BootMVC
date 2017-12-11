package com.example.app.services;

public interface IRegistrationService {
    boolean validateUser(String username);
    boolean registerUser(String username, String password);
    boolean activateUser(String ac);
}
