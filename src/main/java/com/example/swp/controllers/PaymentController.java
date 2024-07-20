package com.example.swp.controllers;

import com.example.swp.responses.PaymentResponse;
import com.example.swp.responses.PaymentStatusResponse;
import com.example.swp.services.momopayment.IMoMoPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final IMoMoPaymentService moMoPaymentService;
    @GetMapping("/createPayment")
    public PaymentResponse createPayment(@RequestParam String orderId, @RequestParam long total, @RequestParam String orderInfo) {
        try {
            return moMoPaymentService.createPaymentRequest(orderId, total, orderInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return new PaymentResponse(null, null, "Error: " + e.getMessage());
        }
    }

    @GetMapping("/checkPaymentStatus")
    public PaymentStatusResponse checkPaymentStatus(@RequestParam String orderId, @RequestParam String requestId) {
        try {
            return moMoPaymentService.checkPaymentStatus(orderId, requestId);
        } catch (Exception e) {
            return new PaymentStatusResponse(Map.of(
                    "orderId", orderId,
                    "requestId", requestId,
                    "resultCode", -1,
                    "message", "Error: " + e.getMessage()
            ));
        }
    }
}
