package com.interview.cdekTask.controller;

import com.interview.cdekTask.dto.OrderDto;
import com.interview.cdekTask.dto.OrderDtoWithDetails;
import com.interview.cdekTask.entity.Order;
import com.interview.cdekTask.entity.User;
import com.interview.cdekTask.mapper.OrderDtoMapper;
import com.interview.cdekTask.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderDtoMapper orderDtoMapper;


    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public List<OrderDto> list(@RequestParam(required = false) String from,
                               @RequestParam(required = false) String to) {
        List<Order> result = orderService.todoListCourier(from, to);
        return result.stream()
                .map(orderDtoMapper::toDto)
                .collect(Collectors.toList());
    }


    @RequestMapping(value = "/order-manage", method = RequestMethod.GET)
    public List<OrderDtoWithDetails> listManage(@RequestParam(required = false) String from,
                                                @RequestParam(required = false) String to) {
        List<Order> result = orderService.getOrdersByLastDateRange(from, to);
        return result.stream()
                .map(orderDtoMapper::toDtoWithDetails)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public List<Order> fullList(@RequestParam(required = false) String from,
                                 @RequestParam(required = false) String to) {
        return  orderService.getOrdersByLastDateRange(from, to);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public OrderDto acceptOrder(@PathVariable Long id,
                             @AuthenticationPrincipal User user) {

        return orderDtoMapper.toDto(orderService.courierAcceptsOrder(id, user));
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.DELETE)
    public String completeOrder(@PathVariable Long id,
                                @AuthenticationPrincipal User user) {
        orderService.courierCompleteOrder(id, user);
        return "redirect:/order";
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.POST)
    public String cancelOrder(@PathVariable Long id,
                              @AuthenticationPrincipal User user) {
        orderService.courierCanceledOrder(id, user);
        return "redirect:/order";
    }

    @RequestMapping(value = "/order-manage/toCall", method = RequestMethod.GET)
    public List<OrderDtoWithDetails> listToCall(@RequestParam(required = false) String from,
                                  @RequestParam(required = false) String to,
                                  @RequestParam(required = false) String nomFrom,
                                  @RequestParam(required = false) String nomTo) {

        return orderService.toCallListOperator(from, to, nomFrom, nomTo).stream()
                .map(orderDtoMapper::toDtoWithDetails)
                .collect(Collectors.toList());
    }

}
