package com.training.rledenev.factory;

import com.training.rledenev.enums.Role;
import com.training.rledenev.model.User;

public class UserFactory {

    public static User getUser() {
        User user = new User();
        user.setEmail("fake@email.com");
        user.setFirstName("FakeName");
        user.setLastName("FakeLastName");
        user.setPassword("FakePassword");
        user.setRole(Role.ENGINEER);
        return user;
    }

    public static User getUserForConverter() {
        User user = new User();
        user.setEmail("user1_mogilev@yopmail.com");
        user.setFirstName("FakeName");
        user.setLastName("FakeLastName");
        user.setPassword("FakePassword");
        user.setRole(Role.EMPLOYEE);
        return user;
    }
}
