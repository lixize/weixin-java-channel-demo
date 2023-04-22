package com.github.binarywang.demo.wx.channel.open.config;

import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 *
 * 用于处理推送任务的线程池配置
 *
 * @author <a href="https://github.com/lixize">Zeyes</a>
 */
@Configuration
public class TaskConfig {

    @Bean("taskExecutor")
    public ThreadPoolTaskExecutor batchTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(1);
        // 设置最大线程数
        executor.setMaxPoolSize(100);
        // 设置队列容量
        executor.setQueueCapacity(100000);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 设置线程名称
        executor.setThreadNamePrefix("task-thread-");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }


}
