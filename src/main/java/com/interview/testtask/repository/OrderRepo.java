package com.interview.testtask.repository;

import com.interview.testtask.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


public interface OrderRepo extends JpaRepository<Order, Long> {

    List<Order> getOrdersByCreatedDateBetween(LocalDate from, LocalDate to);

    List<Order> getOrdersByUpdatedDateBetween(LocalDate from, LocalDate to);

    List<Order> getOrdersByUpdatedDateBetweenAndHolderAuthoritiesOrderByUpdatedDate(LocalDate from, LocalDate to, Collection<GrantedAuthority> role);

    List<Order> getOrdersByHolderAuthoritiesOrderByUpdatedDate(Collection<GrantedAuthority> role);

    List<Order> getOrdersByUpdatedDateBetweenAndHolderIsNullAndActiveIsTrueOrderByUpdatedDate(LocalDate from, LocalDate to);

    List<Order> getOrdersByHolderIsNullAndActiveIsTrueOrderByUpdatedDate();

    List<Order> getOrdersByUpdatedDateBetweenAndIdBetweenAndHolderIsNullAndActiveIsTrueOrderByUpdatedDate(LocalDate from, LocalDate to, Long idFrom, Long idTo);

    List<Order> getOrdersByHolderIsNullAndIdBetweenAndActiveIsTrueOrderByUpdatedDate(Long idFrom, Long idTo);

}
