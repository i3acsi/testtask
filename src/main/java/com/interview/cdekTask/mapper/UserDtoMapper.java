package com.interview.cdekTask.mapper;

import com.interview.cdekTask.dto.OrderDto;
import com.interview.cdekTask.dto.OrderDtoWithDetails;
import com.interview.cdekTask.entity.Order;
import com.interview.cdekTask.entity.User;
import com.interview.cdekTask.dto.UserDto;
import com.interview.cdekTask.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class UserDtoMapper extends AbstractMapper{
    private final ModelMapper mapper;


    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(User.class, UserDto.class)
                .addMappings(m -> m.skip(UserDto::setRoles)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(UserDto.class, User.class)
                .addMappings(m -> m.skip(User::setRoles)).setPostConverter(toEntityConverter());
    }

//    public User toEntity(OrderDto dto) {
//        Order order = Objects.isNull(dto)
//                ? null
//                : mapper.map(dto, Order.class);
//        if (!Objects.isNull(order)){
//            userRepo.findById(dto.getId()).ifPresent(order::setHolder);
//        }
//        return order;
//    }

    public UserDto toDto(User entity) {
        UserDto userDto = Objects.isNull(entity)
                ? null
                : mapper.map(entity, UserDto.class);
        if (!Objects.isNull(userDto)){
            List<String> roles = new ArrayList<>();
            entity.getRoles().forEach(role -> roles.add(role.name()));
            userDto.setRoles(roles);
        }
        return userDto;
    }
}
