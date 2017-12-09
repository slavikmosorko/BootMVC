package com.example.worker;

import com.example.worker.daos.IEmailDao;
import com.example.worker.models.EmailDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkerApplicationTests {
    @Autowired
    IEmailDao emailDao;

    @Test
    public void contextLoads() {
    }

    @Test
    public void emailDaoTest() {
        EmailDto emailDto = new EmailDto();
        emailDto.setId(1);
        emailDao.sendEmail(emailDto);
    }
}
