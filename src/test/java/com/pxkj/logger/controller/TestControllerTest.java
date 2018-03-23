package com.pxkj.logger.controller;

import com.pxkj.logger.model.LogModel;
import com.pxkj.logger.util.SpringContextUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.ContextLoader;

import javax.sql.DataSource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void index() throws Exception {
        String forObject = testRestTemplate.getForObject("/index/tinnkm", String.class);
        assertEquals("hello,tinnkm",forObject);
    }

}