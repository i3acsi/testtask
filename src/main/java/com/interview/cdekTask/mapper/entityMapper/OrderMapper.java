package com.interview.cdekTask.mapper.entityMapper;

import com.interview.cdekTask.entity.Order;
import com.interview.cdekTask.entity.Role;
import com.interview.cdekTask.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;
import java.util.List;
import java.util.Set;

@Mapper
public interface OrderMapper {
//    final String orderParams = "id, name, description, clientName, complete, CAST(updated AS DATE), CAST(created AS DATE), "

    @Insert("insert into orders (name, description, client_name, client_telephone, created, updated, complete, holder_id, history) " +
            "values (#{name}, #{description}, #{client_name}, #{client_telephone}, now(), now(), #{complete}, #{holder_id}, #{history});")
    void addNewOrder(
            @Param("name") String name,
            @Param("description") String description,
            @Param("client_name") String clientName,
            @Param("client_telephone") String clientTelephone,
//            @Param("created") Timestamp created,
//            @Param("updated") Timestamp updated,
            @Param("complete") Boolean complete,
            @Param("holder_id") Long holderId,
            @Param("history") String history
    );

    @Results(value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "clientName", column = "client_name"),
            @Result(property = "clientTelephone", column = "client_telephone"),
            @Result(property = "created", column = "created"),
            @Result(property = "updated", column = "updated"),
            @Result(property = "complete", column = "complete"),
            @Result(property = "holder", column = "holder_id", one = @One(select = "findUserById")),
            @Result(property = "history", column = "history")
    })
    @Select("select * from orders where id = #{id};")
    Order getOrderById(Long id);

    @Update("update orders set holder_id = #{holder_id}," +
            " history = #{history}," +
            " complete = #{complete}," +
            " updated = #{updated}" +
            " where id = #{id}")
    void updateOrder(@Param("id") Long id,
                     @Param("holder_id") Long holderId,
                     @Param("history") String history,
                     @Param("complete") Boolean complete,
                     @Param("updated") Timestamp updated);

    @Results(value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "clientName", column = "client_name"),
            @Result(property = "clientTelephone", column = "client_telephone"),
            @Result(property = "created", column = "created"),
            @Result(property = "updated", column = "updated"),
            @Result(property = "complete", column = "complete"),
            @Result(property = "holder", column = "holder_id", one = @One(select = "findUserById")),
            @Result(property = "history", column = "history")

    })
    @Select("select * from orders where holder_id in (select user_id from user_roles where roles like 'ROLE_OPERATOR');")
    List<Order> todoListCourier();

    @Results(value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "clientName", column = "client_name"),
            @Result(property = "clientTelephone", column = "client_telephone"),
            @Result(property = "created", column = "created"),
            @Result(property = "updated", column = "updated"),
            @Result(property = "complete", column = "complete"),
            @Result(property = "holder", column = "holder_id", one = @One(select = "findUserById")),
            @Result(property = "history", column = "history"),

    })
    @Select("select * from orders where updated between #{from} and #{to} and holder_id in (select user_id from user_roles where roles like 'ROLE_OPERATOR');")
    List<Order> todoListCourierWithDateBetween(@Param("from") Timestamp from, @Param("to") Timestamp to);


    @Results(id = "allOrdersBetweenDateResult", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "clientName", column = "client_name"),
            @Result(property = "clientTelephone", column = "client_telephone"),
            @Result(property = "created", column = "created"),
            @Result(property = "updated", column = "updated"),
            @Result(property = "complete", column = "complete"),
            @Result(property = "holder", column = "holder_id", one = @One(select = "findUserById")),
            @Result(property = "history", column = "history"),

    })
    @Select("select * from orders where updated between #{from} and #{to};")
    List<Order> getOrdersByUpdatedBetweenForAdmin(@Param("from") Timestamp from, @Param("to") Timestamp to);

    @Results(id = "allOrdersResult", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "clientName", column = "client_name"),
            @Result(property = "clientTelephone", column = "client_telephone"),
            @Result(property = "created", column = "created"),
            @Result(property = "updated", column = "updated"),
            @Result(property = "complete", column = "complete"),
            @Result(property = "holder", column = "holder_id", one = @One(select = "findUserById")),
            @Result(property = "history", column = "history"),

    })
    @Select("select * from orders;")
    List<Order> findAllOrdersForAdmin();


    @Results(id = "userResult", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "password", column = "password"),
            @Result(property = "telephone", column = "telephone"),
            @Result(property = "roles", javaType = Set.class,
                    column = "id", many = @Many(select = "findRolesByUserId"))
    })
    @Select("select id, username, password, telephone from usr where id = #{id};")
    User findUserById(Long id);


    @Results(id = "roleResult", value = {
            @Result(property = "name", column = "roles", typeHandler = EnumTypeHandler.class, javaType = Role.class),
    })
    @Select("select roles from user_roles where user_id = #{id};")
    Set<Role> findRolesByUserId(Long id);


    @Results(value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "clientName", column = "client_name"),
            @Result(property = "clientTelephone", column = "client_telephone"),
            @Result(property = "created", column = "created"),
            @Result(property = "updated", column = "updated"),
            @Result(property = "complete", column = "complete"),
            @Result(property = "holder", column = "holder_id", one = @One(select = "findUserById")),
            @Result(property = "history", column = "history")
    })
    @Select("select * from orders where updated between #{from} and #{to} and id between #{idFrom} and #{idTo} and holder_id is null and complete is false order by updated")
    List<Order> getOrdersByUpdatedBetweenAndIdBetweenAndHolderIsNullAndCompleteIsFalseOrderByUpdated(@Param("from") Timestamp from,
                                                                                                     @Param("to") Timestamp to,
                                                                                                     @Param("idFrom") Long idFrom,
                                                                                                     @Param("idTo") Long idTo);

    @Results(value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "clientName", column = "client_name"),
            @Result(property = "clientTelephone", column = "client_telephone"),
            @Result(property = "created", column = "created"),
            @Result(property = "updated", column = "updated"),
            @Result(property = "complete", column = "complete"),
            @Result(property = "holder", column = "holder_id", one = @One(select = "findUserById")),
            @Result(property = "history", column = "history")
    })
    @Select("select * from orders where updated between #{from} and #{to} and holder_id is null and complete is false order by updated")
    List<Order> getOrdersByUpdatedBetweenAndHolderIsNullAndCompleteIsFalseOrderByUpdated(Timestamp f, Timestamp t);

    @Results(value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "clientName", column = "client_name"),
            @Result(property = "clientTelephone", column = "client_telephone"),
            @Result(property = "created", column = "created"),
            @Result(property = "updated", column = "updated"),
            @Result(property = "complete", column = "complete"),
            @Result(property = "holder", column = "holder_id", one = @One(select = "findUserById")),
            @Result(property = "history", column = "history")
    })
    @Select("select * from orders where id between #{idFrom} and #{idTo} and holder_id is null and complete is false order by updated")
    List<Order> getOrdersByHolderIsNullAndIdBetweenAndCompleteIsFalseOrderByUpdated(Long idFrom, Long idTo);

    @Results(value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "clientName", column = "client_name"),
            @Result(property = "clientTelephone", column = "client_telephone"),
            @Result(property = "created", column = "created"),
            @Result(property = "updated", column = "updated"),
            @Result(property = "complete", column = "complete"),
            @Result(property = "holder", column = "holder_id", one = @One(select = "findUserById")),
            @Result(property = "history", column = "history")
    })
    @Select("select * from orders where holder_id is null and complete is false order by updated")
    List<Order> getOrdersByHolderIsNullAndCompleteIsFalseOrderByUpdated();

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

