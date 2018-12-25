package com.allcom.service;

import com.allcom.dao.ds1.Ds1Dao;
import com.allcom.dao.ds2.Ds2Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    Ds1Dao ds1Dao;
    @Autowired
    Ds2Dao ds2Dao;

    public void test() {
        List<String> list1 = ds1Dao.findTestAll();
        List<String> list2 = ds2Dao.findTestAll();
        System.out.print("end");
    }
}
