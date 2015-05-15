package allcom.hello;

import allcom.conf.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ljy on 15/5/14
 */
@Component
public class ScheduledTasks {
    //想实现从配置文件读时间间隔用于注释，但最终无法注入常量
    @Autowired
    private SysConfig sysConf;

    private final long rate=5000;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //(fixedRate = ...),等号后只能是常量
    //以一个固定速率5s来调用一次执行，这个周期是以上一个任务开始时间为基准，从上一任务开始执行后5s再次调用
    @Scheduled(fixedRate = rate)
    public void reportCurrentTime() {
        long rate2=sysConf.getScheduleRate();
        System.out.print("rate2 is:"+rate2);
        System.out.println("The time is now " + dateFormat.format(new Date()));
    }

    //以一个固定速率5s来调用一次执行，这个周期是以上一个调用任务的完成时间为基准，在上一个任务完成之后，5s后再次执行
    @Scheduled(fixedDelay = rate)
    public void reportCurrentTime2() {
        System.out.println("The time2 is now " + dateFormat.format(new Date()));
    }

    @Scheduled(cron = "0/1 * 16-18 * * MON-THU")
    //@Scheduled(cron = "0/5 * *  * * ?")
    public void reportCurrentTime3() {
        System.out.println("The time3 is now " + dateFormat.format(new Date()));
    }

}
