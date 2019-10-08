package com.appsheet.sample.service;

import com.appsheet.sample.helper.UserHelper;
import com.appsheet.sample.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kansen Wu
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserHelper userHelper;

    @Autowired
    public UserServiceImpl(UserHelper userHelper) {
        this.userHelper = userHelper;
    }

    public User[] getUserOrderedList() throws IOException {
        List<User> userList = new ArrayList<User>();
        List<String> idList = userHelper.getUserIdList();
        for (String id: idList) {
            User user = userHelper.getUserDetail(id);
            if (user != null) userList.add(user);
        }
        return userHelper.orderedUserList(userList);
    }
}
