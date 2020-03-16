package com.interview.cdekTask.mapper.dtoMapper;

import com.interview.cdekTask.entity.User;
import com.interview.cdekTask.dto.UserDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class UserDtoMapper extends AbstractMapper {
    private final ModelMapper mapper;


    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(User.class, UserDto.class)
                .addMappings(m -> m.skip(UserDto::setRoles)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(UserDto.class, User.class)
                .addMappings(m -> m.skip(User::setRoles)).setPostConverter(toEntityConverter());
    }

    public UserDto toDto(User entity) {
        UserDto userDto = Objects.isNull(entity)
                ? null
                : mapper.map(entity, UserDto.class);
        if (!Objects.isNull(userDto)) {
            List<String> roles = new ArrayList<>();
            entity.getRoles().forEach(role -> roles.add(role.name()));
            userDto.setRoles(roles);
        }
        return userDto;
    }
}
