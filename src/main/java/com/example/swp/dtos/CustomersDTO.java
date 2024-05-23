package com.example.swp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomersDTO {
    private String fullName;
    private String email;
    private String phone;
    private String address;
    @JsonProperty(value = "accumulated_point")
    private double accumulatedPoint;
}
