package com.interview.cdekTask.controller;

import com.interview.cdekTask.dto.OrderDto;
import com.interview.cdekTask.dto.OrderDtoAdmin;
import com.interview.cdekTask.dto.OrderDtoCourier;
import com.interview.cdekTask.dto.OrderDtoOperator;
import com.interview.cdekTask.entity.Order;
import com.interview.cdekTask.entity.User;
import com.interview.cdekTask.mapper.dtoMapper.OrderDtoMapper;
import com.interview.cdekTask.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final OrderDtoMapper orderDtoMapper;

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public List<OrderDto> list(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to) {
        List<Order> result = orderService.todoListCourier(from, to);
        return result.stream()
                .map(orderDtoMapper::toDtoCourier)
                .collect(Collectors.toList());
    }


    @RequestMapping(value = "/order-manage", method = RequestMethod.GET)
    public List<OrderDtoOperator> listManage(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to) {
        List<Order> result = orderService.getOrdersByLastDateRange(from, to);
        return result.stream()
                .map(orderDtoMapper::toDtoOperator)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public List<OrderDtoAdmin> fullList(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to) {
        List<Order> result = orderService.getOrdersByLastDateRange(from, to);
        return result.stream()
                .map(orderDtoMapper::toDtoAdmin)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public OrderDto acceptOrder(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        return orderDtoMapper.toDtoCourier(orderService.courierAcceptsOrder(id, user));
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.DELETE)
    public String completeOrder(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        orderService.courierCompleteOrder(id, user);
        return "redirect:/order";
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.POST)
    public String cancelOrder(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        orderService.courierCanceledOrder(id, user);
        return "redirect:/order";
    }

    @RequestMapping(value = "/order-manage/toCall", method = RequestMethod.GET)
    public List<OrderDtoOperator> listToCall(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(required = false) String nomFrom,
            @RequestParam(required = false) String nomTo) {

        return orderService.toCallListOperator(from, to, nomFrom, nomTo).stream()
                .map(orderDtoMapper::toDtoOperator)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/order-manage", method = RequestMethod.POST)
    public String saveOrder(
            @RequestBody OrderDtoCourier orderDto,
            @RequestBody(required = false) String from,
            @RequestBody(required = false) String to,
            @AuthenticationPrincipal User user) {
        orderService.saveOrder(orderDto, user);
        return (from != null && to != null) ?
                String.format("forward:/order-manage?from=%s&to=%s", from, to)
                : "forward:/order-manage";
    }

}
