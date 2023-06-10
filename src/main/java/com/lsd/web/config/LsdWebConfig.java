package com.lsd.web.config;

import com.lsd.lib.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LsdWebConfig {
    @Autowired
    private JwtConfig config;

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil(config.getSecret());
    }
}
