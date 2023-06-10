package com.lsd.web.persistance.repository;

import com.lsd.web.persistance.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository  extends JpaRepository<Admin, String> {
    Admin findByUsername(String username);
}
