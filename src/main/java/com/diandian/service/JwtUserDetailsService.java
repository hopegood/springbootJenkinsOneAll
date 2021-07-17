package com.diandian.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service
public class JwtUserDetailsService implements UserDetailsService {

    //private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String usercode) throws UsernameNotFoundException {
       /* SystemUserModel user = userService.getUserByUsercode(usercode);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", usercode));
        } else {
            String role = userService.getRole(usercode).getRolename();
            return JwtUserFactory.create(user, role);
        }*/
    	return null;
    }

    /*@Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }*/
}