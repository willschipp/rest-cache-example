package com.example.code;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StopWatch;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class CacheTimeITest {

    private static final Log logger = LogFactory.getLog(CacheTimeITest.class);

    @Autowired
    WebApplicationContext context;

    MockMvc mvc;

    String url = "/cache";

    @Before
    public void before() throws Exception {
        mvc = webAppContextSetup(context).build();
    }

    @Test
    public void testTimedCache() throws Exception {
        //init
        long firstTime;
        long secondTime;
        //setup a timer
        StopWatch timer = new StopWatch();
        //invoke a url
        timer.start();
        mvc.perform(get(url)).andExpect(status().isOk());
        timer.stop();
        //check the timer
        firstTime = timer.getLastTaskTimeMillis();
        //invoke the url again
        timer.start();
        mvc.perform(get(url)).andExpect(status().isOk());
        timer.stop();
        //check the time
        secondTime = timer.getLastTaskTimeMillis();
        //assert true that the second time is less than the first
        assertTrue(secondTime < firstTime);
        //log
        logger.info("first run time: " + firstTime);
        logger.info("second run time: " + secondTime);
    }
}
