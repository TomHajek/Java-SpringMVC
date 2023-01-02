package com.springmvc.service;

import com.springmvc.persistence.entity.User;
import com.springmvc.web.dto.RegistrationDto;


public interface UserService {

    void saveUser(RegistrationDto registrationDto);
    User findByEmail(String email);
    User findByUsername(String username);

}
