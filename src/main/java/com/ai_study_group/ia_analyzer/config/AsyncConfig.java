package com.ai_study_group.ia_analyzer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {

    @Value("${spring.recordScheduler.pool.core-size:5}")
    private int corePoolSize;

    @Value("${spring.recordScheduler.pool.max-size:10}")
    private int maxPoolSize;

    @Value("${spring.recordScheduler.pool.queue-capacity:100}")
    private int queueCapacity;

    @Bean(name = "asyncConfigTaskExecutor")
    @Primary
    public Executor asyncConfigTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("RecordScheduler-");
        executor.initialize();
        return executor;
    }
}
