package com.lsd.web.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Admin")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @Id
    @Column(name = "Id")
    private String id;
    @Column(name = "username")
    private String username;
    @Column(name = "fullname")
    private String fullname;
    @Column(name = "password")
    private String password;
    //ROLE_ADMIN,ROLE_MEMBER,ROLE_CUSTOMER
    @Column(name = "roles")
    private String roles = "ROLE_MEMBER,ROLE_CUSTOMER";
    @Column(name = "status")
    private boolean status;
}
