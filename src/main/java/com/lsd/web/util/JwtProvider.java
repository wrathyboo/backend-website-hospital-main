package com.lsd.web.util;

import com.lsd.web.config.JwtConfig;
import com.lsd.web.persistance.model.TokenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    @Autowired
    private JwtConfig config;

    public TokenResponse generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        claims.put("roles", roles);
        long issueAt = System.currentTimeMillis();
        String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date(issueAt))
                    .setExpiration(new Date(issueAt + config.getExpires()))
                    .signWith(SignatureAlgorithm.HS512, config.getSecret())
                    .compact();
        return new TokenResponse()
                .withToken(token)
                .withExpired(new Date(issueAt + config.getExpires()))
                .withIssuedAt(new Date(issueAt));
    }
    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Throwable ex) {
            return false;
        }
    }
    public String getUsername(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Jws<Claims> getClaims(String token) {
        return Jwts.parser().setSigningKey(config.getSecret()).parseClaimsJws(token);
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaims(token).getBody();
        return claimsResolver.apply(claims);
    }

    public boolean isTokenExpired(String token) {
        return getClaims(token).getBody().getExpiration().before(new Date());
    }
}
