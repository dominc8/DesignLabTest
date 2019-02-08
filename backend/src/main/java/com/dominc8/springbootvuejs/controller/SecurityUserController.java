package com.dominc8.springbootvuejs.controller;

import com.dominc8.springbootvuejs.entity.SecurityUser;
import com.dominc8.springbootvuejs.repository.SecurityUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class SecurityUserController {

    @Autowired
    SecurityUserRepository securityUserRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/checkuser")
    public String whatever () {
        return "Logged in successfully!";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user")
    public List<SecurityUser> getAllUsers() {
        return securityUserRepository.findAllByOrderByIdAsc();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user")
    public void addUser(@RequestBody SecurityUser securityUser) {
        securityUser.changePassword(securityUser.getPassword());
        securityUserRepository.save(securityUser);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/user/{id}")
    public void updateUser(@PathVariable("id") long id, @RequestBody SecurityUser updatedSecurityUser) {
        SecurityUser securityUser = securityUserRepository.findById(id).orElseThrow(() -> new RuntimeException());

        if (updatedSecurityUser.getUsername() != null) securityUser.setUsername(updatedSecurityUser.getUsername());
        if (updatedSecurityUser.getPassword() != null) securityUser.changePassword(updatedSecurityUser.getPassword());
        if (updatedSecurityUser.getAuthority() != null) securityUser.setAuthority(updatedSecurityUser.getAuthority());

        securityUserRepository.save(securityUser);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/user/{id}")
    public void deleteUser(@PathVariable("id") long id) {
        securityUserRepository.deleteById(id);
    }


}
