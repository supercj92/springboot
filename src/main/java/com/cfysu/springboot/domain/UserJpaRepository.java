package com.cfysu.springboot.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserJpaRepository extends JpaRepository<User,Integer>{

    List<User> findFirst10ByUserName(String userName);
}
