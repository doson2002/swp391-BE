package com.example.swp.responses;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private String payUrl;
    private Map<String, Object> payload;
    private String errorMessage;

}
