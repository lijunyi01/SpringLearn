package com.allcom.spring_mybatis.dao;

import com.allcom.spring_mybatis.entity.Tb1Entity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
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
    void getAllTb1Info(ResultHandler<Tb1Entity> handler);
}
