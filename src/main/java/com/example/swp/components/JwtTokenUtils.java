package com.example.swp.components;

import com.example.swp.entities.Users;
import com.example.swp.entities.Token;
import com.example.swp.repositories.TokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {
    @Value("${jwt.expiration}")
    private int expiration; //save to an environment variable
    @Value("${jwt.secretKey}")
    private String secretKey;
    private final TokenRepository tokenRepository;
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtils.class);
    public String generateToken(Users user) throws Exception{
        Map<String, Object> claims = new HashMap<>();
//        this.generateSecretKey();
        claims.put("email", user.getEmail());
        claims.put("userId",user.getId());
        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getEmail())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
            return token;
        }catch (Exception e) {
            throw new InvalidParameterException("Cannot create jwt token, error: "+ e.getMessage());
        }
    }
    public String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32]; // 256-bit key
        random.nextBytes(keyBytes);
        String secretKey = Encoders.BASE64.encode(keyBytes);
        return secretKey;
    }
    private Key getSignInKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }
    public boolean isTokenExpired(String token) {
        Date expirationDate = this.extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }
    public  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey()) //giải mã token
                .build()
                .parseClaimsJws(token) //Parse chuỗi token để lấy ra tất cả các claims
                .getBody(); //Lấy phần payload của token, nơi chứa các claims
    }
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        //trích xuất thôn tin(phoneNumber) từ token và thông tin userDetails(Username = phoneNumber) từ spring security
        try {
            String email = extractEmail(token);
            Token existingToken = tokenRepository.findByToken(token);
            if(existingToken == null || existingToken.isRevoked() == true) {
                return false;
            }
            return (email.equals(userDetails.getUsername()))
                    && !isTokenExpired(token);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
