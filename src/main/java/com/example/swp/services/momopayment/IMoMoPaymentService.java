package com.example.swp.services.momopayment;

public interface IMoMoPaymentService {
    String createPaymentRequest(String orderId, long amount, String orderInfo) throws Exception;
}
