package allcom.hello;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ljy on 15/5/14
 */
@Component
public class ScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //(fixedRate = ...),等号后只能是常量
    //以一个固定速率5s来调用一次执行，这个周期是以上一个任务开始时间为基准，从上一任务开始执行后5s再次调用
    //@Scheduled(fixedRate = rate)
    @Scheduled(fixedRateString = "${jobs.schedule1}")
    public void reportCurrentTime() {
        System.out.println("The time is now " + dateFormat.format(new Date()));
    }

    //以一个固定速率5s来调用一次执行，这个周期是以上一个调用任务的完成时间为基准，在上一个任务完成之后，5s后再次执行
    //@Scheduled(fixedDelay = rate)
    @Scheduled(fixedDelayString = "${jobs.schedule2}")
    public void reportCurrentTime2() {
        System.out.println("The time2 is now " + dateFormat.format(new Date()));
    }

    //@Scheduled(cron = "0/1 * 16-18 * * MON-THU")
    @Scheduled(cron = "${jobs.schedule3}")
    public void reportCurrentTime3() {
        System.out.println("The time3 is now " + dateFormat.format(new Date()));
    }

}
