package com.appsheet.sample.helper;

import com.appsheet.sample.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class UserHelperTest {

    @Mock
    private HttpHelper httpHelper;

    @Mock
    private JsonHelper jsonHelper;

    private UserHelper userHelper;

    @Before
    public void setUp() {
        userHelper = new UserHelper(httpHelper, jsonHelper);
    }

    @Test
    public void orderedUserList() {
        List<User> userList = setUpListUsers();
        User[] expectedUsers = setUpExpectedUsers();
        User[] users = userHelper.orderedUserList(userList);
        assertThat(users).isEqualTo(expectedUsers);
    }

    private List<User> setUpListUsers() {
        List<User> userList = new ArrayList<User>();
        userList.add(new User(1, "John Doe", 32, "555-555-5555", "photo.jpg", "developer"));
        userList.add(new User(2, "Jane Doe", 53, "555-555-5555", "photo.jpg", "tester"));
        userList.add(new User(3, "Tom Doe", 24, "555-555-5555", "photo.jpg", "tester"));
        userList.add(new User(4, "Michelle Doe", 75, "555-555-5555", "photo.jpg", "tester"));
        userList.add(new User(5, "Emily Doe", 19, "555-555-5555", "photo.jpg", "tester"));
        userList.add(new User(6, "Peter Doe", 28, "555-555-5555", "photo.jpg", "tester"));
        return userList;
    }

    private User[] setUpExpectedUsers() {
        List<User> userList = new ArrayList<User>();
        userList.add(new User(5, "Emily Doe", 19, "555-555-5555", "photo.jpg", "tester"));
        userList.add(new User(2, "Jane Doe", 53, "555-555-5555", "photo.jpg", "tester"));
        userList.add(new User(1, "John Doe", 32, "555-555-5555", "photo.jpg", "developer"));
        userList.add(new User(6, "Peter Doe", 28, "555-555-5555", "photo.jpg", "tester"));
        userList.add(new User(3, "Tom Doe", 24, "555-555-5555", "photo.jpg", "tester"));
        return userList.toArray(new User[userList.size()]);
    }
}
