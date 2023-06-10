package com.lsd.web.persistance.mapper;
import com.lsd.web.persistance.entity.Admin;
import com.lsd.web.persistance.model.AdminModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdminAdapter implements UserDetails{
    private Admin admin;

    public AdminAdapter(Admin admin) {
        this.admin = admin;
    }
    public static AdminModel adminModel(Admin admin){
        return new AdminModel().builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .fullname(admin.getFullname())
                .roles(admin.getRoles())
                .status(admin.isStatus())
                .build();
    }
    public static Admin admin(AdminModel adminModel){
        return new Admin().builder()
                .id(adminModel.getId())
                .username(adminModel.getUsername())
                .password(adminModel.getPassword())
                .fullname(adminModel.getFullname())
                .roles(adminModel.getRoles())
                .status(adminModel.isStatus())
                .build();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        // "ROLE_ADMIN,ROLE_CUSTOMER"
        for (String role : this.admin.getRoles().split(",")) { // => ["ROLE_ADMIN","ROLE_CUSTOMER"]
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    @Override
    public String getPassword() {
        return this.admin.getPassword();
    }

    @Override
    public String getUsername() {
        return this.admin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.admin.isStatus();
    }

}
