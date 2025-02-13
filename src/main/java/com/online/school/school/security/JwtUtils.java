package com.online.school.school.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.function.Function;

import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtils {

    private static final String PRIVATE_KEY_STR  = "";

    private static final String PUBLIC_KEY_STR  = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvfzhHi/WF9dtDyEHI8Cx\r\n"
            + //
            "brvbYLJhmRleej+E817selE1QANzj1/Qv9ghRFWf64r52RuRORw07hRLqzHvfTZH\r\n" + //
            "MoaP3dK4QTLw2u5Td6AteUmegGZchRHJJo9U6Q8Pwh57OLcVc1xO0kOwH/DyvhQf\r\n" + //
            "nt5+N+C2sV0XTKRd6fHPHGAVXtpyR8NOUEsPmYDtv+X53KlJ1zhy5TKOPQ8s9JVJ\r\n" + //
            "aX5U23aluGi7fHOzU76W84xVhsHpghCmBY12mdrvF8NxbKJtedIeJxiN0Yg5dlVL\r\n" + //
            "8sKWSAWBFBtE6vQ/TKB3uWueq/z3fh1Z5NRpM+qKjxFq624w1aHcvuESOGDpYUyD\r\n" + //
            "qQIDAQAB";

   

    private PrivateKey getPrivateKey() throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(PRIVATE_KEY_STR.replaceAll("\\s+", ""));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }
    private PublicKey getPublicKey() throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(PUBLIC_KEY_STR.replaceAll("\\s+", ""));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return generateToken(claims, userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        try {
            return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1-hour expiry
                .signWith(getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
        } catch (Exception e) {
            throw new RuntimeException("Failed to sign JWT token", e);
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExperation(token).before(new Date());
    }

    private Date extractExperation(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Claims extractAllClaims(String token) {
        try {System.out.println("Using public key: " + PUBLIC_KEY_STR); 
            return Jwts.parserBuilder()
                .setSigningKey(getPublicKey()) // Verify with Public Key
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }


    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(PRIVATE_KEY_STR.replaceAll("\\s+", ""));
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
