package com.training.rledenev.services.security;

import com.training.rledenev.dao.UserDao;
import com.training.rledenev.model.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    public CustomUserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userDao.findByEmail(email)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new RuntimeException("UserNotFound with email " + email));
    }
}