package com.example.itademo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.itademo.model.User;
import com.example.itademo.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_return_a_new_user_when_post_a_user() throws Exception {
        //given
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        //when
        when(userService.createUser(any())).thenReturn(user);
        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assert.assertEquals(user.getUsername(), JSON.parseObject(content, User.class).getUsername());
    }

    @Test
    public void should_return_a_user_when_call_get_a_user_by_id() throws Exception {
        //given
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        //when
        when(userService.getUserById(any())).thenReturn(user);
        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assert.assertEquals(user.getUsername(), JSON.parseObject(content, User.class).getUsername());
    }

    @Test
    public void should_return_user_list_when_call_getUsers() throws Exception {
        //given
        Page<User> page = new PageImpl<>(Collections.singletonList(new User()));
        //when
        when(userService.getAllUser(any())).thenReturn(page);
        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users?page=1&size=10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assert.assertEquals(page.getContent().size(), Integer.parseInt(JSON.parseObject(content).getString("totalElements")));
    }

    @Test
    public void should_return_a_delete_user_when_call_deleteUserById() throws Exception {
        //given
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        //when
        when(userService.deleteUser(any())).thenReturn(user);
        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/users/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assert.assertEquals(user.getUsername(), JSON.parseObject(content, User.class).getUsername());
    }

    @Test
    public void updateUser() throws Exception {
        //given
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");

        //when
        when(userService.updateUser(any())).thenReturn(user);
        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assert.assertEquals(user.getUsername(), JSON.parseObject(content, User.class).getUsername());
    }

}