package com.interview.testtask.service;

import com.interview.testtask.entity.Order;
import com.interview.testtask.repository.OrderRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;

    public List<Order> getOrdersByCreatedDateRange(LocalDate from, LocalDate to) {
        return orderRepo.getOrdersByCreatedDateBetween(from, to);
    }

    public List<Order> getOrdersByLastDateRange(@RequestParam(required = false) LocalDate from,
                                                @RequestParam(required = false) LocalDate to) {
        List<Order> result = new ArrayList<>();
        if (from != null && to != null) {
            result = orderRepo.getOrdersByUpdatedDateBetween(from, to);
        } else {
            result = orderRepo.findAll();
        }
        return result;
    }

    public List<Order> findAll() {
        return orderRepo.findAll();
    }
}
