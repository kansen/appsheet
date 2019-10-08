package com.appsheet.sample.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.appsheet.sample.model.User;
import com.appsheet.sample.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kansen Wu
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testUserList() throws Exception {
        User[] users = setUpOrderedUsers();
        Mockito.when(userService.getUserOrderedList()).thenReturn(users);
        ResultActions resultActions = mockMvc.perform(get("/"));

        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("users", users));

        Mockito.verify(userService, Mockito.times(1)).getUserOrderedList();
        Mockito.verifyNoMoreInteractions(userService);
    }

    private User[] setUpOrderedUsers() {
        List<User> userList = new ArrayList<User>();
        userList.add(new User(1, "John Doe", 32, "555-555-5555", "photo.jpg", "developer"));
        userList.add(new User(2, "Jane Doe", 53, "555-555-5555", "photo.jpg", "tester"));
        return userList.toArray(new User[userList.size()]);
    }
}
