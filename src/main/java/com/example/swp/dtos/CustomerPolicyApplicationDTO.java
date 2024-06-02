package com.example.swp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPolicyApplicationDTO {

    @JsonProperty(value = "publishing_status")
    private String publishingStatus;

}
