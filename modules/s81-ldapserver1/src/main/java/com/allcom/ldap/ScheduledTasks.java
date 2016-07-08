package com.allcom.ldap;

//import com.allcom.email.EmailSend;
//import com.allcom.service.TaskService;
//import com.allcom.toolkit.GlobalTools;
import com.allcom.service.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ScheduledTasks {
    private static Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

//    @Value("${email.initOKFaxEndTemplatePath}")
//    private String initOKFaxEndTemplatePath;
//    @Value("${email.initErrorFaxEndTemplatePath}")
//    private String initErrorFaxEndTemplatePath;

//    //线程安全
//    public static List<String> mailToSendList = new CopyOnWriteArrayList<String>();
//
//    @Autowired
//    private TaskService taskService;
    @Autowired
    private Test test;

    public ScheduledTasks(){
    }

    //回任务处理
//    @Scheduled(fixedDelayString = "${jobs.schedule1}")
    @Scheduled(fixedDelayString = "600000")
    public void task1() throws Exception{
        log.info("**********************************************************");
        log.info("************************ 测试ldap请求 **************************");
        test.testClient();
        log.info("************************end 测试ldap请求**************************");

    }

    //每10秒检查邮件发送队列list
    //@Scheduled(fixedDelay = 10*1000)
//    @Scheduled(fixedDelayString = "${jobs.schedule2}")
//    public void sendmail() throws Exception{
//        String subject;
//        String mailto;
//        String message;
//        String[] strs;
//        String templatePath;
//        //templateMap 用于填充模版，存的是数据
//        Map<String,String> templateMap = new HashMap<>();
//        for(String mailMessage:mailToSendList) {
//            strs = mailMessage.split("\\<\\[CDATA\\]\\>");
//            subject = strs[0];
//            mailto = strs[1];
//            message = strs[2];
//            log.debug("subject:"+subject +" mailto:"+mailto +" message:"+message);
//
//            if(subject.indexOf("创建失败")>0){
//                templatePath = initErrorFaxEndTemplatePath;
//            }else{
//                templatePath = initOKFaxEndTemplatePath;
//            }
//            templateMap = GlobalTools.parseInput(message,"\\<\\[CDATA2\\]\\>");
//
//            if(emailSend.mailSend(subject, mailto, templatePath,templateMap)){
//                log.info("send email success! mailto:"+mailto);
//                mailToSendList.remove(mailMessage);
//            }else{
//                log.error("send notify email failed! mailto:"+mailto);
//                //邮件发失败了会导致邮件累积，越积越多，都应删除
//                mailToSendList.remove(mailMessage);
//            }
//        }
//    }

}
