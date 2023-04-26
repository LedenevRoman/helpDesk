package com.training.rledenev.services.impl;

import com.training.rledenev.dao.h2.UserDaoH2;
import com.training.rledenev.enums.Role;
import com.training.rledenev.exception.WrongPasswordException;
import com.training.rledenev.model.User;
import com.training.rledenev.provider.UserProvider;
import com.training.rledenev.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDaoH2 userDao;
    private final PasswordEncoder passwordEncoder;
    private final UserProvider userProvider;

    public UserServiceImpl(UserDaoH2 userDao, PasswordEncoder passwordEncoder, UserProvider userProvider) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.userProvider = userProvider;
    }

    @Transactional
    @Override
    public User findByEmailAndPassword(String email, String password) {
        Optional<User> userOptional = userDao.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        throw new WrongPasswordException("Email or password is not correct");
    }

    @Override
    public Role getAuthorizedUserRole() {
        return userProvider.getCurrentUser().getRole();
    }
}