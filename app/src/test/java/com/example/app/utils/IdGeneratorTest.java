package com.example.app.utils;

import com.example.utils.IdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IdGeneratorTest {

    @Test
    public void uniqueTest() {
        assertThat(IdGenerator.unique(), is(not(nullValue())));
    }

    @Test
    public void uniqueUUIDTest() {
        assertThat(IdGenerator.uniqueUUID(), is(not(nullValue())));
    }
}
