package com.training.rledenev.dao;

import com.training.rledenev.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends CrudDao<User> {

    Optional<User> findByEmail(String email);

    List<User> findAllManagers();

    List<User> findAllEngineers();
}

