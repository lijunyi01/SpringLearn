package com.allcom.spring_mybatis.dao;

import com.allcom.spring_mybatis.entity.Tb1Entity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AppDao {

//    @Select("select * from t_activity_statistics where scan_date >= #{startDate} and scan_date <#{endDate} ")
//    List<TActivityStatisticsEntity> getActivities(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @ResultType(value = Tb1Entity.class)
    @Select("select * from tb1")
    // 分批拉取的关键，否则全部拉取，数据量大时一样会导致oom
    @Options(fetchSize = -2147483638,resultSetType = ResultSetType.FORWARD_ONLY)
    void getAllTb1Info(ResultHandler<Tb1Entity> handler);
}
