package com.interview.testtask.service;

import com.interview.testtask.entity.Order;
import com.interview.testtask.repository.OrderRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;

    public List<Order> getOrdersByCreatedDateRange(LocalDate from, LocalDate to) {
        return orderRepo.getOrdersByCreatedDateBetween(from, to);
    }

    public List<Order> getOrdersByLastDateRange(LocalDate from, LocalDate to) {
        return orderRepo.getOrdersByUpdatedDateBetween(from, to);
    }

    public List<Order> findAll(){
        return orderRepo.findAll();
    }
}
