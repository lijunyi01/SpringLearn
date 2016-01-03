package allcom.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by ljy on 16/1/3.
 * ok
 */
@Configuration
@EnableScheduling
public class ScheduledTaskConfig implements SchedulingConfigurer {
    //@Value("${jobs.scheduledthreadpoolsize}")
    //private int scheduledThreadPoolSize;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    //使用的@bean(destroyMethod="shutdown")。这样是为了确保当Spring应用上下文关闭的时候任务执行者也被正确地关闭。
    @Bean(destroyMethod="shutdown")
    public Executor taskExecutor() {
        //return Executors.newScheduledThreadPool(scheduledThreadPoolSize);
        //设置线程池线程数
        return Executors.newScheduledThreadPool(2);
    }
}
