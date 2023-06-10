package com.lsd.web.service;

import com.lsd.web.persistance.model.AdminModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService , BaseService<AdminModel, String> {
}
