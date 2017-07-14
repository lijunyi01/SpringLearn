package com.allcom.dao;

import com.allcom.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ljy on 17/7/7.
 * ok
 */
public interface UserRepository extends MongoRepository<User, Integer> {

    User findByUsername(String username);

    List<User> findAll();

}
