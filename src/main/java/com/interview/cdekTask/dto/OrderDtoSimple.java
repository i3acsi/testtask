package com.interview.cdekTask.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDtoSimple extends AbstractDto {
    private String name;
    private String description;
    private String clientName;
    private String clientTelephone;
    private boolean complete = false;
    private String history;
}
