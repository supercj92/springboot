package com.cfysu.springboot.mapper;

import com.cfysu.springboot.domain.User;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {

    @Select("select * from user")
    @Results({
            @Result(property="id", column="id"),
            @Result(property="userName", column="user_name"),
            @Result(property="pwd", column="pwd")
    })
    List<User> getAll();
}
