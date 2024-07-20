package com.example.swp.responses;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentStatusResponse {
    private String orderId;
    private String requestId;
    private int resultCode;
    private String message;
    public PaymentStatusResponse(Map<String, Object> responseMap) {
        this.orderId = (String) responseMap.get("orderId");
        this.requestId = (String) responseMap.get("requestId");
        this.resultCode = (Integer) responseMap.get("resultCode");
        this.message = (String) responseMap.get("message");
    }

}
