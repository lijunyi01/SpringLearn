package allcom.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by ljy on 15/5/14
 */

@Component
public class SysConfig {
    @Value("${system.schedulerate}")
    private String schedulerate;

    public long getScheduleRate(){
        return Long.parseLong(schedulerate);
    }

}
