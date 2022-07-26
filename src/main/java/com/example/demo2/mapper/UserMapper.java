package com.example.demo2.mapper;

import com.example.demo2.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    User findNameByName(String name);
    int addUser(User user);
}

