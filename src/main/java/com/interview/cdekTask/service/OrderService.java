package com.interview.cdekTask.service;

import com.interview.cdekTask.entity.Order;
import com.interview.cdekTask.entity.Role;
import com.interview.cdekTask.entity.User;
import com.interview.cdekTask.repository.OrderRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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
     * @param from - LocalDateTime from (not required)
     * @param to   - LocalDateTime to (not required) if from and to are null, than'll be shown all orders.
     * @return filtered list of Orders
     */
    public List<Order> todoListCourier(String from,
                                       String to) {
        List<Order> result = new ArrayList<>();
        Collection<GrantedAuthority> role = new HashSet<>();
        role.add(Role.ROLE_OPERATOR);
        if (from != null && to != null) {
            LocalDateTime f = LocalDateTime.parse(from);
            LocalDateTime t = LocalDateTime.parse(to);
            result = orderRepo.getOrdersByUpdatedBetweenAndHolderRolesOrderByUpdated(f, t, Role.ROLE_OPERATOR);
        } else {
            result = orderRepo.getOrdersByHolderRolesOrderByUpdated(Role.ROLE_OPERATOR);
        }
        return result;
    }

    /**
     * The method returns a list of orders with an empty holder and active status.
     * Which means the order has been canceled by courier
     *
     * @param from    - LocalDateTime from (not required)
     * @param to      - LocalDateTime to (not required) if from and to are null, than'll be shown all orders.
     * @param nomFrom - Long id From - to filter orders with id
     * @param nomTo   - Long id To - to filter orders with id
     * @return filtered list of Orders
     */
    public List<Order> toCallListOperator(String from,
                                          String to,
                                          String nomFrom,
                                          String nomTo) {
        List<Order> result = new ArrayList<>();
        if (from != null && to != null) {
            LocalDateTime f = LocalDateTime.parse(from);
            LocalDateTime t = LocalDateTime.parse(to);
            if (nomFrom != null && nomTo != null) {
                Long idFrom = Long.valueOf(nomFrom);
                Long idTo = Long.valueOf(nomTo);
                result = orderRepo.getOrdersByUpdatedBetweenAndIdBetweenAndHolderIsNullAndCompleteIsFalseOrderByUpdated(f, t, idFrom, idTo);
            } else {
                result = orderRepo.getOrdersByUpdatedBetweenAndHolderIsNullAndCompleteIsFalseOrderByUpdated(f, t);
            }
        } else {
            if (nomFrom != null && nomTo != null) {
                Long idFrom = Long.valueOf(nomFrom);
                Long idTo = Long.valueOf(nomTo);
                result = orderRepo.getOrdersByHolderIsNullAndIdBetweenAndCompleteIsFalseOrderByUpdated(idFrom, idTo);
            } else {
                result = orderRepo.getOrdersByHolderIsNullAndCompleteIsFalseOrderByUpdated();
            }
        }
        if (result == null || result.isEmpty()) { ;
            return Collections.emptyList();
        }
        return result;
    }

    /**
     * The method returns a list of orders. List could be filtered by date
     *
     * @param from - LocalDateTime from (not required)
     * @param to   - LocalDateTime to (not required) if from and to are null, than'll be shown all orders.
     * @return filtered list of Orders
     */
    public List<Order> getOrdersByLastDateRange(String from,
                                                String to) {
        List<Order> result = new ArrayList<>();
        if (from != null && to != null) {
            LocalDateTime f = LocalDateTime.parse(from);
            LocalDateTime t = LocalDateTime.parse(to);
            result = orderRepo.getOrdersByUpdatedBetween(f, t);
        } else {
            result = orderRepo.findAll();
        }
        return result;
    }

    public Order courierAcceptsOrder(Long id, User courier) {
        Order order = orderRepo.findById(id).orElse(null);
        if (order != null) order = updateParamsAndSave("accept", courier, order);
        return order;
    }

    public void courierCompleteOrder(Long id, User courier) {
        orderRepo.findById(id).ifPresent(
                order -> updateParamsAndSave("complete", courier, order));
    }


    public void courierCanceledOrder(Long id, User courier) {
        orderRepo.findById(id).ifPresent(
                order -> updateParamsAndSave("cancel", courier, order));
    }

    /*
    difference between canceled Order, accepted Order, completed Order:
    accepted: completed: false, holder: courier
    cancelled: completed: false, holder: null
    completed: completed: true, holder: null
     */
    private final Map<String, Object[]> params = new HashMap<>();

    {
//        msg, complete, hasHolder
        params.put("accept", new Object[]{" ORDER ACCEPTED BY COURIER : ", false, true});
        params.put("complete", new Object[]{" ORDER COMPLETED BY COURIER : ", true, false});
        params.put("cancel", new Object[]{" ORDER CANCELED BY COURIER : ", false, false});
    }

    private Order updateParamsAndSave(String act, User courier, Order order) {
        Object[] parameters = params.get(act);
        String msg = (String) parameters[0];
        boolean complete = (boolean) parameters[1];
        boolean hasHolder = (boolean) parameters[2];
        if (hasHolder) {
            order.setHolder(courier);
        } else {
            if (checkHolder(courier, order)) {
                order.setHolder(null);
            } else {
                return order;
            }
        }

        StringBuilder history = new StringBuilder(order.getHistory());
        LocalDateTime now = LocalDateTime.now();
        history.append(now.toString()).append(LN);
        history.append(DELIMITER);
        history.append(msg);
        history.append(courier.getUsername()).append(DELIMITER).append(" ID:").append(courier.getId());

        order.setUpdated(now);
        order.setHistory(history.toString());
        order.setComplete(complete);
        return orderRepo.save(order);
    }

    private boolean checkHolder(User courier, Order order) {
        return courier.getId().equals(order.getHolder().getId());
    }
}
