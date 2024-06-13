package com.example.swp.dtos;

public record ChangePasswordDTO(String oldPassword,String password, String retypePassword) {
}