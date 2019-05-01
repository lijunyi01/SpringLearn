package com.allcom.spring_mybatis.service;

import com.allcom.spring_mybatis.dao.AppDao;
import com.allcom.spring_mybatis.entity.Tb1Entity;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class AppService {
    private static Logger log = LoggerFactory.getLogger(AppService.class);

    @Autowired
    AppDao appDao;

    int i = 1;

    public void testStream(){


        appDao.getAllTb1Info(new ResultHandler<Tb1Entity>() {
            @Override
            public void handleResult(ResultContext<? extends Tb1Entity> resultContext) {
                // 获取数据后的回调处理
                Tb1Entity tb1Entity = resultContext.getResultObject();
                System.out.printf("sequence:" + i + "\n");
                i++;
            }
        });
    }

}
