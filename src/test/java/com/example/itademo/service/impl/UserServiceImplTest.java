package com.example.itademo.service.impl;

import com.example.itademo.dao.UserRepository;
import com.example.itademo.model.User;
import com.example.itademo.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void should_return_user_list_when_get_all_users() throws Exception {
        //given
        Page<User> page = new PageImpl<>(Collections.singletonList(new User()));
        //when
        when(userRepository.findAll(PageRequest.of(1, 10))).thenReturn(page);
        //then
        Page<User> allUser = userService.getAllUser(PageRequest.of(1, 10));
        Assert.assertEquals(1, allUser.getTotalElements());
    }

    @Test
    public void should_return_a_new_user_when_create_a_user() throws Exception {
        //given
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        //when
        when(userRepository.save(any())).thenReturn(user);
        //then
        Assert.assertEquals(user.getUsername(), userService.createUser(user).getUsername());
    }

    @Test
    public void should_return_a_update_user_when_update_a_user() throws Exception {
        //given
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        //when
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);
        //then
        Assert.assertEquals(user.getUsername(), userService.updateUser(user).getUsername());
    }

    @Test
    public void should_no_return_when_delete_a_user() throws Exception {
        //given
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        //when
        doNothing().when(userRepository).delete(user);
    }

    @Test
    public void should_return_a_user_when_get_user_by_id() throws Exception {
        //given
        User user = new User();
        user.setId(1);
        user.setUsername("test");
        user.setPassword("test");
        //when
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        //then
        Assert.assertEquals(user.getUsername(), userService.getUserById(user.getId()).getUsername());
    }

}