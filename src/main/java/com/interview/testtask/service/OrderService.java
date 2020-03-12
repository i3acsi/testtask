package com.interview.testtask.service;

import com.interview.testtask.entity.Order;
import com.interview.testtask.entity.Role;
import com.interview.testtask.entity.User;
import com.interview.testtask.repository.OrderRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.time.LocalDate.now;
import static java.time.LocalDate.parse;

@Service
@AllArgsConstructor
public class OrderService {
    private static final String DELIMITER = "###";
    private static final String LN = System.lineSeparator();

    private final OrderRepo orderRepo;

    /**
     * The method returns a list of orders with an holder having "operator" authority.
     * Which means that order should be accepted by any courier.
     *
     * @param from - LocalDate from (not required)
     * @param to   - LocalDate to (not required) if from and to are null, than'll be shown all orders.
     * @return filtered list of Orders
     */
    public List<Order> todoListCourier(@RequestParam(required = false) String from,
                                       @RequestParam(required = false) String to) {
        List<Order> result = new ArrayList<>();
        Set<GrantedAuthority> role = new HashSet<>();
        role.add(Role.ROLE_OPERATOR);
        if (from != null && to != null) {
            LocalDate f = parse(from);
            LocalDate t = parse(to);
            result = orderRepo.getOrdersByUpdatedDateBetweenAndHolderAuthoritiesOrderByUpdatedDate(f, t, role);
        } else {
            result = orderRepo.getOrdersByHolderAuthoritiesOrderByUpdatedDate(role);
        }
        return result;
    }

    /**
     * The method returns a list of orders with an empty holder and active status.
     * Which means the order has been canceled by courier
     *
     * @param from    - LocalDate from (not required)
     * @param to      - LocalDate to (not required) if from and to are null, than'll be shown all orders.
     * @param nomFrom - Long id From - to filter orders with id
     * @param nomTo   - Long id To - to filter orders with id
     * @return filtered list of Orders
     */
    public List<Order> toCallListOperator(@RequestParam(required = false) String from,
                                          @RequestParam(required = false) String to,
                                          @RequestParam(required = false) String nomFrom,
                                          @RequestParam(required = false) String nomTo) {
        List<Order> result = new ArrayList<>();
        if (from != null && to != null) {
            LocalDate f = parse(from);
            LocalDate t = parse(to);
            if (nomFrom != null && nomTo != null) {
                Long idFrom = Long.valueOf(nomFrom);
                Long idTo = Long.valueOf(nomTo);
                result = orderRepo.getOrdersByUpdatedDateBetweenAndIdBetweenAndHolderIsNullAndActiveIsTrueOrderByUpdatedDate(f, t, idFrom, idTo);
            } else {
                result = orderRepo.getOrdersByUpdatedDateBetweenAndHolderIsNullAndActiveIsTrueOrderByUpdatedDate(f, t);
            }
        } else {
            if (nomFrom != null && nomTo != null) {
                Long idFrom = Long.valueOf(nomFrom);
                Long idTo = Long.valueOf(nomTo);
                result = orderRepo.getOrdersByHolderIsNullAndIdBetweenAndActiveIsTrueOrderByUpdatedDate(idFrom, idTo);
            } else {
                result = orderRepo.getOrdersByHolderIsNullAndActiveIsTrueOrderByUpdatedDate();
            }
        }
        return result;
    }

    /**
     * The method returns a list of orders. List could be filtered by date
     *
     * @param from - LocalDate from (not required)
     * @param to   - LocalDate to (not required) if from and to are null, than'll be shown all orders.
     * @return filtered list of Orders
     */
    public List<Order> getOrdersByLastDateRange(@RequestParam(required = false) String from,
                                                @RequestParam(required = false) String to) {
        List<Order> result = new ArrayList<>();
        if (from != null && to != null) {
            LocalDate f = parse(from);
            LocalDate t = parse(to);
            result = orderRepo.getOrdersByUpdatedDateBetween(f, t);
        } else {
            result = orderRepo.findAll();
        }
        return result;
    }

    public Order courierAcceptsOrder(Long id, User courier) {
        Order order = orderRepo.findById(id).orElse(null);
        if (order != null) {
            StringBuilder history = new StringBuilder(order.getHistory());
            LocalDate date = now();
            LocalTime time = LocalTime.now();
            history.append(date.toString());
            history.append(DELIMITER);
            history.append(time.toString());
            history.append(" ORDER ACCEPTED BY COURIER : ");
            history.append(courier.getUsername()).append(DELIMITER).append(" ID:").append(courier.getId());
            history.append(LN);
            order.setHolder(courier);
            order.setUpdatedDate(date);
            order.setUpdatedTime(time);
            order.setHistory(history.toString());
            order.setActive(true);
            orderRepo.save(order);
        }
        return order;
    }

    public void courierCompleteOrder(Long id, User courier) {
        Order order = orderRepo.findById(id).orElse(null);
        if (order != null) {
            StringBuilder history = new StringBuilder(order.getHistory());
            LocalDate date = now();
            LocalTime time = LocalTime.now();
            history.append(date.toString());
            history.append(DELIMITER);
            history.append(time.toString());
            history.append(" ORDER COMPLETED BY COURIER : ");
            history.append(courier.getUsername()).append(DELIMITER).append(" ID:").append(courier.getId());
            history.append(LN);
            order.setHolder(null);
            order.setUpdatedDate(date);
            order.setUpdatedTime(time);
            order.setHistory(history.toString());
            order.setActive(false);
            orderRepo.save(order);
        }
    }

    public void courierCanceledOrder(Long id, User courier) {
        Order order = orderRepo.findById(id).orElse(null);
        if (order != null) {
            StringBuilder history = new StringBuilder(order.getHistory());
            LocalDate date = now();
            LocalTime time = LocalTime.now();
            history.append(date.toString());
            history.append(DELIMITER);
            history.append(time.toString());
            history.append(" ORDER CANCELED BY COURIER : ");
            history.append(courier.getUsername()).append(DELIMITER).append(" ID:").append(courier.getId());
            history.append(LN);
            order.setHolder(null);
            order.setUpdatedDate(date);
            order.setUpdatedTime(time);
            order.setHistory(history.toString());
            order.setActive(true);
            orderRepo.save(order);
        }
    }
}
