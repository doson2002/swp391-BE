package com.example.swp.services;

public interface IForgotPasswordService {
    void verifyEmailAndSendOTP(String email);
    void verifyOTP(String email, Integer otp);
}
