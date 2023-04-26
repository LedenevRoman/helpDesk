package com.training.rledenev.services;

import com.training.rledenev.enums.Role;
import com.training.rledenev.model.User;

public interface UserService {

    User findByEmailAndPassword(String email, String password);

    Role getAuthorizedUserRole();
}

