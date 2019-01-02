package com.wxm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.wxm.config.VisiableThreadPoolTaskExecutor;


@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
public class Application{
	
	 //根据需要初始化线程池
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
        executor.setCorePoolSize(50);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(1000000);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("async-service");
        
        return executor;
    }
    
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

