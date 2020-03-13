package com.interview.cdekTask.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.interview.cdekTask.entity.Order;
import com.interview.cdekTask.entity.User;
import com.interview.cdekTask.entity.Views;
import com.interview.cdekTask.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    @JsonView(Views.CourierView.class)
    public List<Order> list(@RequestParam(required = false) String from,
                            @RequestParam(required = false) String to) {
        return orderService.todoListCourier(from, to);
    }

    @RequestMapping(value = "/order-manage", method = RequestMethod.GET)
    @JsonView(Views.OperatorView.class)
    public List<Order> listManage(@RequestParam(required = false) String from,
                                  @RequestParam(required = false) String to) {
        return orderService.getOrdersByLastDateRange(from, to);
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    @JsonView(Views.FullView.class)
    public List<Order> fullList(@RequestParam(required = false) String from,
                                @RequestParam(required = false) String to) {
        return orderService.getOrdersByLastDateRange(from, to);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    @JsonView(Views.CourierView.class)
    public Order acceptOrder(@PathVariable Long id,
                             @AuthenticationPrincipal User user) {
        return orderService.courierAcceptsOrder(id, user);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.DELETE)
    @JsonView(Views.CourierView.class)
    public String completeOrder(@PathVariable Long id,
                                @AuthenticationPrincipal User user) {
        orderService.courierCompleteOrder(id, user);
        return "redirect:/order";
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.POST)
    @JsonView(Views.CourierView.class)
    public String cancelOrder(@PathVariable Long id,
                              @AuthenticationPrincipal User user) {
        orderService.courierCanceledOrder(id, user);
        return "redirect:/order";
    }

    @RequestMapping(value = "/order-manage/toCall", method = RequestMethod.GET)
    @JsonView(Views.OperatorView.class)
    public List<Order> listToCall(@RequestParam(required = false) String from,
                                  @RequestParam(required = false) String to,
                                  @RequestParam(required = false) String nomFrom,
                                  @RequestParam(required = false) String nomTo) {
        return orderService.toCallListOperator(from, to, nomFrom, nomTo);
    }

}