package com.interview.testtask.repository;

import com.interview.testtask.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {

    List<Order> getOrdersByCreatedDateBetween(LocalDate from, LocalDate to);

    List<Order> getOrdersByUpdatedDateBetween(LocalDate from, LocalDate to);

}
