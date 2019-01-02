package com._4paradigm;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wxm.service.lock.RedisLockService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ApplicationTests {
    @Resource
    private RedisLockService redisLockService;
    
    @Test
    public void test(){
        String key = "12223";
        System.out.println(redisLockService.lockJob(key));
        System.out.println(redisLockService.lockJob(key));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(redisLockService.lockJob(key));
        redisLockService.unlock(key);
        System.out.println(redisLockService.lockJob(key));
    }
}