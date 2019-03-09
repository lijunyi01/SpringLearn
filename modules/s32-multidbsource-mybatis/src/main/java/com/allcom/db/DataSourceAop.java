package com.allcom.db;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAop {


    public static final Logger logger = LoggerFactory.getLogger(DataSourceAop.class);


    //@Before("execution(* com.allcom.dao.ds1..*.*(..)) || execution(* com.hui.readwrite.mapper..*.get*(..))")
    @Before("execution(* com.allcom.dao.ds1..*.*(..))")
    public void setDataSource1() {
        DataSourceContextHolder.setDbType("db1");
        logger.info("dataSource切换到：ds1");
    }


    //@Before("execution(* com.hui.readwrite.mapper..*.insert*(..)) || execution(* com.hui.readwrite.mapper..*.update*(..))")
    @Before("execution(* com.allcom.dao.ds2..*.*(..))")
    public void setDataSource2() {
        DataSourceContextHolder.setDbType("db2");
        logger.info("dataSource切换到：ds2");
    }
}
