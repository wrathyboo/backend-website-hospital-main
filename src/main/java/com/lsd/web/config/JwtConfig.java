package com.lsd.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(value = "jwt")
public class JwtConfig {
    private String secret;
    private long expires;
}
