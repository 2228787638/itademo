package com.example.itademo.service.impl;

import com.example.itademo.dao.UserRepository;
import com.example.itademo.model.User;
import com.example.itademo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Override
    public Page<User> getAllUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User createUser(User user) {
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser, "id");
        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(User user) {
        User oldUser = userRepository.findById(user.getId()).get();
        BeanUtils.copyProperties(user, oldUser, "id");
        return userRepository.save(oldUser);
    }

    @Override
    public User deleteUser(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userRepository.delete(user);
        }
        return user;
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }
}
