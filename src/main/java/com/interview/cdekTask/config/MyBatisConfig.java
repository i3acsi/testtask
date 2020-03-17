package com.interview.cdekTask.config;

import com.interview.cdekTask.entity.Order;
import com.interview.cdekTask.entity.Role;
import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;

@Configuration
@MappedTypes({User.class, Order.class, Role.class})
@MapperScan("com.interview.cdekTask.mapper.entityMapper")
public class MyBatisConfig {
}
