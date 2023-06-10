package com.lsd.web.persistance.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminModel {
    private String id;
    private String username;
    private String fullname;
    private String password;
    private String roles;
    private boolean status;
}
