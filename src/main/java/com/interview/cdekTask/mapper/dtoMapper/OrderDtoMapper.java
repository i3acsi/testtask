package com.interview.cdekTask.mapper.dtoMapper;

import com.interview.cdekTask.dto.OrderDtoAdmin;
import com.interview.cdekTask.dto.OrderDtoCourier;
import com.interview.cdekTask.dto.OrderDtoOperator;
import com.interview.cdekTask.entity.Order;
import com.interview.cdekTask.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
@Slf4j
public class OrderDtoMapper extends AbstractMapper {
    private final ModelMapper mapper;
    private final UserDtoMapper userDtoMapper;

    @Autowired
    OrderDtoMapper(ModelMapper mapper, UserDtoMapper userDtoMapper) {
        this.mapper = mapper;
        this.userDtoMapper = userDtoMapper;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Order.class, OrderDtoCourier.class)
                .addMappings(m -> {
                    m.skip(OrderDtoCourier::setHolderName);
                    m.skip(OrderDtoCourier::setHolderTelephone);
                }).setPostConverter(toDtoConverter());
        mapper.createTypeMap(OrderDtoCourier.class, Order.class)
                .addMappings(m -> m.skip(Order::setHolder)).setPostConverter(toEntityConverter());

        mapper.createTypeMap(Order.class, OrderDtoAdmin.class)
                .addMappings(m -> {
                    m.skip(OrderDtoAdmin::setHolder);
                }).setPostConverter(toDtoConverter());
        mapper.createTypeMap(OrderDtoAdmin.class, Order.class)
                .addMappings(m -> m.skip(Order::setHolder)).setPostConverter(toEntityConverter());
    }

    public OrderDtoCourier toDtoCourier(Order entity) {
        OrderDtoCourier orderDto = Objects.isNull(entity)
                ? null
                : mapper.map(entity, OrderDtoCourier.class);
        if (!Objects.isNull(orderDto)) {
            log.info("&&&&");
            log.info(entity.getHolder().getUsername());
            orderDto.setHolderName(entity.getHolder().getUsername());
            orderDto.setHolderTelephone(entity.getHolder().getTelephone());
        }
        return orderDto;
    }

    public OrderDtoOperator toDtoOperator(Order entity) {
        OrderDtoOperator orderDto = Objects.isNull(entity)
                ? null
                : mapper.map(entity, OrderDtoOperator.class);
        User holder = entity.getHolder();
        if (!Objects.isNull(orderDto) && !Objects.isNull(holder)) {
            orderDto.setHolderName(holder.getUsername());
            orderDto.setHolderTelephone(holder.getTelephone());
        }
        return orderDto;
    }

    public OrderDtoAdmin toDtoAdmin(Order entity) {
        OrderDtoAdmin orderDto = Objects.isNull(entity)
                ? null
                : mapper.map(entity, OrderDtoAdmin.class);
        if (!Objects.isNull(orderDto)) {
            User holder = entity.getHolder();
            orderDto.setHolder(userDtoMapper.toDto(holder));
        }
        return orderDto;
    }

}
