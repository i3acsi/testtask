package com.interview.cdekTask.mapper;

import com.interview.cdekTask.entity.AbstractEntity;
import com.interview.cdekTask.dto.AbstractDto;
import lombok.NoArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Author: Vyacheslav Chernyshov
 * Date: 04.02.19
 * Time: 21:55
 * e-mail: 2262288@gmail.com
 */

@NoArgsConstructor
public abstract class AbstractMapper<E extends AbstractEntity, D extends AbstractDto> {

    @Autowired
    ModelMapper mapper;


    Converter<E, D> toDtoConverter() {
        return context -> {
            E source = context.getSource();
            D destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    Converter<D, E> toEntityConverter() {
        return context -> {
            D source = context.getSource();
            E destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    void mapSpecificFields(E source, D destination) {
    }

    void mapSpecificFields(D source, E destination) {
    }
}
