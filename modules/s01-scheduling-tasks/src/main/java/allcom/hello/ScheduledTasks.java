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

//    @Scheduled(fixedRate = 5000)
    @Scheduled(fixedRate = rate)
    public void reportCurrentTime() {
        long l=sysConf.getScheduleRate();
        System.out.print("l is:"+l);
        System.out.println("The time is now " + dateFormat.format(new Date()));
    }

}
