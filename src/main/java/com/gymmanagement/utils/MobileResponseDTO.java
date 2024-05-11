package com.gymmanagement.utils;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@JsonPropertyOrder({"success", "code", "message"})
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
@Getter
@Setter
public class MobileResponseDTO {
    private boolean success;
    private Integer code;
    private String message;
    private List<Map<String,Object>> errors;

}
