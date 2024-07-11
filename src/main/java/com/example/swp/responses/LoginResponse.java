package com.example.swp.responses;

import com.example.swp.entities.Counters;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("token")
    private String token;

    @JsonProperty("refresh_token")
    private String refreshToken;
    private String tokenType = "Bearer";
    //user's detail
    private Long id;
    private String email;
    private String name;

    @JsonProperty("first_login")
    private boolean firstLogin;
    private List<String> roles;

    private Counters counter;
}
