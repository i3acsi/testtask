package com.interview.testtask.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.interview.testtask.entity.Order;
import com.interview.testtask.entity.Views;
import com.interview.testtask.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    @JsonView(Views.SimpleView.class)
    public List<Order> list(@RequestParam(required = false) LocalDate from,
                            @RequestParam(required = false) LocalDate to){
        return orderService.getOrdersByLastDateRange(from,to);
    }

    @RequestMapping(value = "/order-manage", method = RequestMethod.GET)
    @JsonView(Views.CommonView.class)
    public List<Order> listManage(@RequestParam(required = false) LocalDate from,
                                  @RequestParam(required = false) LocalDate to){
        return orderService.getOrdersByLastDateRange(from,to);
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    @JsonView(Views.FullView.class)
    public List<Order> fullList(@RequestParam(required = false) LocalDate from,
                                  @RequestParam(required = false) LocalDate to){
        return orderService.getOrdersByLastDateRange(from,to);
    }



}
