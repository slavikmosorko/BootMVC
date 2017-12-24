package com.example.app.app.daos;

import com.example.app.daos.IRegistrationDAO;
import com.example.app.models.UserAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RegistrationDAOTest {
    @Autowired
    IRegistrationDAO registrationDAO;

    @Test
    public void registerUserTest() {
        UserAccount userAccount = new UserAccount("TestUser",
                "testpass",
                true,
                true,
                true,
                true,
                Collections.EMPTY_LIST,
                "ac");
        registrationDAO.registerUser(userAccount);
    }
}
