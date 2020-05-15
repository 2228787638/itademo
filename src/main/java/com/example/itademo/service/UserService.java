package com.example.itademo.service;

import com.example.itademo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    Page<User> getAllUser(Pageable pageable);

    User createUser(User user);

    User updateUser(User user);

    User deleteUser(Integer id);

    User getUserById(Integer id);
}
