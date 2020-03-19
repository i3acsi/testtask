package com.interview.cdekTask.service;

import com.interview.cdekTask.dto.OrderDto;
import com.interview.cdekTask.dto.OrderDtoCourier;
import com.interview.cdekTask.dto.OrderDtoOperator;
import com.interview.cdekTask.entity.Order;
import com.interview.cdekTask.entity.User;
import com.interview.cdekTask.mapper.entityMapper.OrderMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {
    private static final String DELIMITER = "###";
    private static final String LN = System.lineSeparator();

    private final OrderMapper orderMapper;

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
        if (from != null && to != null) {
            Timestamp f = Timestamp.valueOf(LocalDateTime.parse(from));
            Timestamp t = Timestamp.valueOf(LocalDateTime.parse(to));
            result = orderMapper.todoListCourierWithDateBetween(f, t);
        } else {
            result = orderMapper.todoListCourier();
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
            Timestamp f = Timestamp.valueOf(LocalDateTime.parse(from));
            Timestamp t = Timestamp.valueOf(LocalDateTime.parse(to));
            if (nomFrom != null && nomTo != null) {
                Long idFrom = Long.valueOf(nomFrom);
                Long idTo = Long.valueOf(nomTo);
                result = orderMapper.getOrdersByUpdatedBetweenAndIdBetweenAndHolderIsNullAndCompleteIsFalseOrderByUpdated(f, t, idFrom, idTo);
            } else {
                result = orderMapper.getOrdersByUpdatedBetweenAndHolderIsNullAndCompleteIsFalseOrderByUpdated(f, t);
            }
        } else {
            if (nomFrom != null && nomTo != null) {
                Long idFrom = Long.valueOf(nomFrom);
                Long idTo = Long.valueOf(nomTo);
                result = orderMapper.getOrdersByHolderIsNullAndIdBetweenAndCompleteIsFalseOrderByUpdated(idFrom, idTo);
            } else {
                result = orderMapper.getOrdersByHolderIsNullAndCompleteIsFalseOrderByUpdated();
            }
        }
        if (result == null || result.isEmpty()) {
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
            Timestamp f = Timestamp.valueOf(LocalDateTime.parse(from));
            Timestamp t = Timestamp.valueOf(LocalDateTime.parse(to));
            result = orderMapper.getOrdersByUpdatedBetweenForAdmin(f, t);
        } else {
            result = orderMapper.findAllOrdersForAdmin();
        }
        return result;
    }

    public Order courierAcceptsOrder(Long id, User courier) {
        Order order = orderMapper.getOrderById(id);
        if (order != null) order = updateParamsAndSave("accept", courier, order);
        return order;
    }

    public void courierCompleteOrder(Long id, User courier) {
        updateParamsAndSave("complete", courier, orderMapper.getOrderById(id));
    }


    public void courierCanceledOrder(Long id, User courier) {
        updateParamsAndSave("cancel", courier, orderMapper.getOrderById(id));
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
        orderMapper.updateOrder(order.getId(),
                Objects.isNull(order.getHolder()) ? null : order.getHolder().getId(),
                order.getHistory(),
                order.isComplete(),
                Timestamp.valueOf(order.getUpdated()));
        return orderMapper.getOrderById(order.getId());
    }

    private boolean checkHolder(User courier, Order order) {
        return courier.getId().equals(order.getHolder().getId());
    }

    public void saveOrder(OrderDtoCourier order, User user) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        Long holderId = user.getId();
        orderMapper.addNewOrder(
                order.getName(),
                order.getDescription(),
                order.getClientName(),
                order.getClientTelephone(),
                false,
                holderId,
                String.format("ORDER CREATED BY OPERATOR %s WITH ID: %d at %s ", user.getUsername(), holderId, now)
        );


    }
}
