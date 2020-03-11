package com.interview.testtask.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.interview.testtask.entity.Order;
import com.interview.testtask.entity.Views;
import com.interview.testtask.repository.OrderRepo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class OrderController {
    private final OrderRepo orderRepo;

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    @JsonView(Views.SimpleView.class)
    public List<Order> list(){
        return orderRepo.findAll();
    }

    @RequestMapping(value = "/order-manage", method = RequestMethod.GET)
    @JsonView(Views.SimpleView.class)
    public List<Order> listManage(){
        return orderRepo.findAll();
    }
}
