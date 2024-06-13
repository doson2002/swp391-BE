package com.example.swp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomersDTO {

    private String fullName;
    @Email
    private String email;
    private String phone;
    private String address;
    @JsonProperty(value = "accumulated_point")
    private double accumulatedPoint;
}
