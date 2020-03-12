package com.interview.testtask.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
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
//    private final ObjectMapper dateMapper;

    public List<Order> getOrdersByCreatedDateRange(LocalDate from, LocalDate to) {
        return orderRepo.getOrdersByCreatedDateBetween(from, to);
    }

    public List<Order> getOrdersByLastDateRange(@RequestParam(required = false) String from,
                                                @RequestParam(required = false) String to) {
        List<Order> result = new ArrayList<>();
        if (from != null && to != null) {
            LocalDate f = LocalDate.parse(from);
            LocalDate t = LocalDate.parse(to);
            result = orderRepo.getOrdersByUpdatedDateBetween(f, t);
        } else {
            result = orderRepo.findAll();
        }
        return result;
    }

    public List<Order> findAll() {
        return orderRepo.findAll();
    }
}
