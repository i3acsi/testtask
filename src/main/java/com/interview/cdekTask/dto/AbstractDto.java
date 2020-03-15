package com.interview.cdekTask.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class AbstractDto implements Serializable {
    private Long id;
}
