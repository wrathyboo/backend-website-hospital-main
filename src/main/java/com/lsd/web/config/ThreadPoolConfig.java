package com.lsd.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class ThreadPoolConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolConfig.class);

    @Value("${lsd.async.corePoolSize}")
    private int corePoolSize;
    @Value("${lsd.async.maxPoolSize}")
    private int maxPoolSize;
    @Value("${lsd.async.queueCapacity}")
    private int queueCapacity;

    @Bean(name = "processAsync")
    public Executor executor() {
        LOGGER.info("Process async executor");
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("ProcessAsync_");
        return executor;
    }
}