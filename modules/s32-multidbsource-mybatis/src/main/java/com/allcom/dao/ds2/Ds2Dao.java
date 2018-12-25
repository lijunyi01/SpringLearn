package com.allcom.dao.ds2;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface Ds2Dao {

    @Select("select col1 from test")
    List<String> findTestAll();
}
