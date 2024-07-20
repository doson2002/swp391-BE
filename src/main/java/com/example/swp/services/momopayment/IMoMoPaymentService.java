package com.example.swp.services.momopayment;

import com.example.swp.responses.PaymentResponse;
import com.example.swp.responses.PaymentStatusResponse;

import java.util.Map;

public interface IMoMoPaymentService {
    PaymentResponse createPaymentRequest(String orderId, long amount, String orderInfo) throws Exception;
    PaymentStatusResponse checkPaymentStatus(String orderId, String requestId) throws Exception;
}
