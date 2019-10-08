package com.appsheet.sample.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.appsheet.sample.helper.HttpHelper;
import com.appsheet.sample.helper.JsonHelper;
import com.appsheet.sample.helper.UserHelper;
import com.appsheet.sample.model.User;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Kansen Wu
 */
@RunWith(SpringRunner.class)
public class UserServiceMockTest {

    @MockBean
    UserHelper userHelper;

    @Mock
    HttpURLConnection connection;

    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserServiceImpl(userHelper);
    }

    @Test
    public void testGetUserIdList() throws IOException {
        List<String> userIdList = setUpTestingUserIdList();
        User user = setUpTestingUser();
        User[] orderedUserList = setUpOrderedUsers();
        Mockito.when(userHelper.getUserIdList()).thenReturn(userIdList);
        Mockito.when(userHelper.getUserDetail(Mockito.anyString())).thenReturn(user);
        Mockito.when(userHelper.orderedUserList(Mockito.anyList())).thenReturn(orderedUserList);
        User[] users = userService.getUserOrderedList();
        Mockito.verify(userHelper).getUserIdList();
        Mockito.verify(userHelper, Mockito.times(2)).getUserDetail(Mockito.anyString());
        Mockito.verify(userHelper).orderedUserList(Mockito.anyList());
        assertThat(orderedUserList).isEqualTo(users);
    }

    private List<String> setUpTestingUserIdList() {
        List<String> userIdList = new ArrayList<String>();
        userIdList.add("1");
        userIdList.add("2");
        return userIdList;
    }

    private User setUpTestingUser() {
        return new User(1, "Jphn Doe", 32, "555-555-5555", "photo.jpg", "developer");
    }

    private User[] setUpOrderedUsers() {
        List<User> userList = new ArrayList<User>();
        userList.add(new User(1, "John Doe", 32, "555-555-5555", "photo.jpg", "developer"));
        userList.add(new User(2, "Jane Doe", 53, "555-555-5555", "photo.jpg", "tester"));
        return userList.toArray(new User[userList.size()]);
    }
}

