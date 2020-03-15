package com.interview.cdekTask.mapper;

import com.interview.cdekTask.dto.OrderDto;
import com.interview.cdekTask.dto.OrderDtoWithDetails;
import com.interview.cdekTask.entity.Order;
import com.interview.cdekTask.entity.User;
import com.interview.cdekTask.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
public class OrderDtoMapper extends AbstractMapper{
    private final ModelMapper mapper;
    private final UserRepo userRepo;
    private final UserDtoMapper userDtoMapper;

    @Autowired
    OrderDtoMapper(ModelMapper mapper, UserRepo userRepo, UserDtoMapper userDtoMapper) {
        this.mapper = mapper;
        this.userRepo = userRepo;
        this.userDtoMapper= userDtoMapper;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Order.class, OrderDto.class)
                .addMappings(m -> m.skip(OrderDto::setHolderId)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(OrderDto.class, Order.class)
                .addMappings(m -> m.skip(Order::setHolder)).setPostConverter(toEntityConverter());
        mapper.createTypeMap(Order.class, OrderDtoWithDetails.class)
                .addMappings(m -> m.skip(OrderDtoWithDetails::setHolder)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(OrderDtoWithDetails.class, Order.class)
                .addMappings(m -> m.skip(Order::setHolder)).setPostConverter(toEntityConverter());

    }

    public Order toEntity(OrderDto dto) {
        Order order = Objects.isNull(dto)
                ? null
                : mapper.map(dto, Order.class);
        if (!Objects.isNull(order)){
            userRepo.findById(dto.getId()).ifPresent(order::setHolder);
        }
        return order;
    }

    public OrderDto toDto(Order entity) {
        OrderDto orderDto = Objects.isNull(entity)
                ? null
                : mapper.map(entity, OrderDto.class);
        if (!Objects.isNull(orderDto)){
            orderDto.setHolderId(entity.getHolder().getId());
        }
        return orderDto;
    }

    public OrderDtoWithDetails toDtoWithDetails(Order entity) {
        OrderDtoWithDetails orderDto = Objects.isNull(entity)
                ? null
                : mapper.map(entity, OrderDtoWithDetails.class);
        if (!Objects.isNull(orderDto)){
            User holder = entity.getHolder();
            orderDto.setHolder(userDtoMapper.toDto(holder));
        }
        return orderDto;
    }

}
