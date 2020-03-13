package com.interview.cdekTask.repository;

import com.interview.cdekTask.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;


public interface OrderRepo extends JpaRepository<Order, Long> {

    List<Order> getOrdersByCreatedBetween(LocalDateTime from, LocalDateTime  to);

    List<Order> getOrdersByUpdatedBetween(LocalDateTime from, LocalDateTime to);


    List<Order> getOrdersByUpdatedBetweenAndHolderRolesOrderByUpdated(LocalDateTime from, LocalDateTime to, GrantedAuthority holder_authorities);

    List<Order> getOrdersByHolderRolesOrderByUpdated(GrantedAuthority holder_authorities);

    List<Order> getOrdersByUpdatedBetweenAndHolderIsNullAndCompleteIsFalseOrderByUpdated(LocalDateTime  from, LocalDateTime  to);

    List<Order> getOrdersByHolderIsNullAndCompleteIsFalseOrderByUpdated();

    List<Order> getOrdersByUpdatedBetweenAndIdBetweenAndHolderIsNullAndCompleteIsFalseOrderByUpdated(LocalDateTime  from, LocalDateTime  to, Long idFrom, Long idTo);

    List<Order> getOrdersByHolderIsNullAndIdBetweenAndCompleteIsFalseOrderByUpdated(Long idFrom, Long idTo);

}
