package com.interview.cdekTask.mapper.entityMapper;

import com.interview.cdekTask.entity.Order;
import com.interview.cdekTask.entity.Role;
import com.interview.cdekTask.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;


import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

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
            @Result(property = "holder", column = "holder_id", one=@One(select = "findUserById")),
            @Result(property = "history", column = "history"),

    })
    @Select("select * from orders;")
    public List<Order> findAllOrders();

    @Results( id = "userResult", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "password", column = "password"),
            @Result(property = "telephone", column = "telephone"),
            @Result(property = "roles", javaType = Set.class ,
                    column = "id",  many=@Many(select="findRolesByUserId") )
    })
    @Select("select id, username, password, telephone from usr where id = #{id};")
    public User findUserById(Long id);


    @Results( id = "roleResult", value = {
            @Result(property = "name", column = "roles", typeHandler = EnumTypeHandler.class, javaType = Role.class),
    })
    @Select("select roles from user_roles where user_id = #{id};")
    public Set<Role> findRolesByUserId(Long id);


    class RoleTypeHandler implements TypeHandler<Role> {


        @Override
        public void setParameter(PreparedStatement preparedStatement, int i, Role role, JdbcType jdbcType) throws SQLException {

        }

        @Override
        public Role getResult(ResultSet resultSet, String s) throws SQLException {
            return null;
        }

        @Override
        public Role getResult(ResultSet resultSet, int i) throws SQLException {
            return null;
        }

        @Override
        public Role getResult(CallableStatement callableStatement, int i) throws SQLException {
            return null;
        }
    }
}

