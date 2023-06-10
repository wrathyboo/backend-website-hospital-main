package com.lsd.web.service.impl;

import com.lsd.web.config.JwtAuthenticationFilter;
import com.lsd.web.exception.ErrorCode;
import com.lsd.web.exception.WebException;
import com.lsd.web.persistance.entity.Admin;
import com.lsd.web.persistance.mapper.AdminAdapter;
import com.lsd.web.persistance.model.AdminModel;
import com.lsd.web.persistance.repository.UserRepository;
import com.lsd.web.service.UserService;
import com.lsd.web.util.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Tìm admin theo user name, load DB
        LOGGER.info("loadUserByUsername username: {}", username);
        Admin admin = userRepository.findByUsername(username);
        LOGGER.info("Admin: {}", admin);

        if (admin == null){
            LOGGER.error("Admin is null");
            throw new WebException(ErrorCode.UN_AUTHORIZED);
        }
        LOGGER.error("Admin out WebException");
        return new AdminAdapter(admin);
    }

    @Override
    public AdminModel add(AdminModel a) {
        try {
            Admin admin = AdminAdapter.admin(a);

            Admin save = userRepository.save(admin);

            AdminModel adminModel = AdminAdapter.adminModel(save);

            return adminModel;
        }catch (Exception ex){
            throw new WebException(ErrorCode.INTERNAL_SERVER);
        }
    }

    @Override
    public List<AdminModel> findAll() {
        List<Admin> list = userRepository.findAll();
        return list.stream().map(AdminAdapter::adminModel)
                .collect(Collectors.toList());
    }

    @Override
    public AdminModel findById(String id) {

        Admin admin = userRepository.findById(id).orElse(null);
        if(admin == null){
            throw new WebException(ErrorCode.NOT_FOUND);
        }
        AdminModel adminModel = AdminAdapter.adminModel(admin);

        return adminModel;
    }

    @Override
    public AdminModel edit(AdminModel a) {
        try {
            Admin admin = AdminAdapter.admin(a);

            Admin save = userRepository.save(admin);

            AdminModel adminModel = AdminAdapter.adminModel(save);

            return adminModel;
        }catch (Exception ex){
            throw new WebException(ErrorCode.INTERNAL_SERVER);
        }
    }

    @Override
    public boolean remove(String id) {
        try {
            userRepository.deleteById(id);
            return true;
        }catch (Exception ex){
            throw new WebException(ErrorCode.INTERNAL_SERVER);
        }
    }

}
