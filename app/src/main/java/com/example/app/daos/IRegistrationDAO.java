package com.example.app.daos;

import com.example.app.models.UserAccount;

public interface IRegistrationDAO {
    boolean validateUser(String username);

    void registerUser(UserAccount userAccount);

    boolean activateUser(String ac);
}
