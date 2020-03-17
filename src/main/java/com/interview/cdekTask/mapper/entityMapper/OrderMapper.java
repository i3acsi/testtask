package com.interview.cdekTask.mapper.entityMapper;

import com.interview.cdekTask.entity.Order;
import com.interview.cdekTask.entity.Role;
import com.interview.cdekTask.entity.User;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface OrderMapper {
    @Results(id = "allOrdersResult", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "clientName", column = "client_name"),
            @Result(property = "clientTelephone", column = "client_telephone"),
            @Result(property = "created", column = "created"),
            @Result(property = "updated", column = "updated"),
            @Result(property = "complete", column = "complete"),
            @Result(property = "holder", column = "id", one=@One(select = "findUserById")),
            @Result(property = "history", column = "history"),

    })
    @Select("select * from orders;")
    public List<Order> findAllOrders();

    @Results( id = "userResult", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "password", column = "password"),
            @Result(property = "telephone", column = "telephone"),
            @Result(property = "roles", javaType = List.class,
                    column = "id",  many=@Many(select="findRoleById") )
    })
    @Select("select id, username, password, telephone from usr;")
    public User findUserById(Long id);


    @Results( id = "roleResult", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "name", column = "name"),
    })
    @Select("select id, name from roles where id = #{id};")
    public List<Role> findRoleById(Long id);


}

