package com.example.swp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CounterDTO {
    private String location;
    @JsonProperty(value = "counter_name")
    private String counterName;
}
