package com.example.itademo.controller;

import com.example.itademo.model.User;
import com.example.itademo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    @ResponseBody
    public User saveUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @GetMapping("/users/{id}")
    @ResponseBody
    public User getUserById(@PathVariable Integer id){
        return userService.getUserById(id);
    }

    @GetMapping("/users")
    @ResponseBody
    public Page<User> getUsers(@RequestParam Integer page, @RequestParam Integer size){
        return userService.getAllUser(PageRequest.of(page - 1, size));
    }

    @DeleteMapping("/users/{id}")
    @ResponseBody
    public User deleteUserById(@PathVariable Integer id){
        return userService.deleteUser(id);
    }

    @PutMapping("/users")
    @ResponseBody
    public User updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }
}
