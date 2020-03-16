package com.interview.cdekTask.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto extends AbstractDto{
    private Long id;
    private String username;
    private String telephone;
    private List<String> roles;
}
