package com.example.swp.controllers;

import com.example.swp.services.momopayment.IMoMoPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final IMoMoPaymentService moMoPaymentService;
    @GetMapping("/createPayment")
    public String createPayment(@RequestParam String orderId, @RequestParam long total, @RequestParam String orderInfo) {
        try {
            String payUrl = moMoPaymentService.createPaymentRequest(orderId, total, orderInfo);
            return "Redirect to: " + payUrl;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
