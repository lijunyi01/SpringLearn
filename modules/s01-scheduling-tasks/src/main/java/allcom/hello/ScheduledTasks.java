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
    @Autowired
    private SysConfig sysConf;

    private final long rate=5000;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //(fixedRate = ...),等号后只能是常量
    //@Scheduled(fixedRate = 3000)
    @Scheduled(fixedRate = rate)
    public void reportCurrentTime() {
        long rate2=sysConf.getScheduleRate();
        System.out.print("rate2 is:"+rate2);
        System.out.println("The time is now " + dateFormat.format(new Date()));
    }

    @Scheduled(cron = "0/1 * 16-18 * * MON-THU")
    //@Scheduled(cron = "0/5 * *  * * ?")
    public void reportCurrentTime2() {
        System.out.println("The time is now " + dateFormat.format(new Date()));
    }

}
