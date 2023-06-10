package com.lsd.web.persistance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.Date;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private String token;
    private String type = "Bearer";
    private Date expired;
    private Date issuedAt;
}
