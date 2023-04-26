package com.training.rledenev.dao.h2;

import com.training.rledenev.dao.UserDao;
import com.training.rledenev.enums.Role;
import com.training.rledenev.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoH2 extends CrudDaoH2<User> implements UserDao {

    public UserDaoH2() {
        setClazz(User.class);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            return Optional.ofNullable(getEntityManager().createQuery("from User user " +
                            "where user.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult());
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAllManagers() {
        return getEntityManager().createQuery("from User user where user.role = :role", User.class)
                .setParameter("role", Role.MANAGER)
                .getResultList();
    }

    @Override
    public List<User> findAllEngineers() {
        return getEntityManager().createQuery("from User user where user.role = :role", User.class)
                .setParameter("role", Role.ENGINEER)
                .getResultList();
    }
}
