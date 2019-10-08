package com.appsheet.sample.helper;

import com.appsheet.sample.model.User;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Kansen Wu
 */
@Component
public class UserHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserHelper.class);

    private final HttpHelper httpHelper;

    private final JsonHelper jsonHelper;

    @Autowired
    public UserHelper(HttpHelper httpHelper, JsonHelper jsonHelper) {
        this.httpHelper = httpHelper;
        this.jsonHelper = jsonHelper;
    }

    /**
     * This function sort users in the order of the age then again sort users
     * in the order of their name for only the 5 youngest users.
     *
     * @param List<User> list of user detail
     * @return Users[] sorted user array
     */
    public User[] orderedUserList(List<User> userList) {
        User[] users = userList.toArray(new User[0]);
        Arrays.sort(users, User.AgeComparator);
        users = Arrays.copyOfRange(users, 0, 5);
        Arrays.sort(users, User.NameComparator);
        return users;
    }

    /**
     * Retrieve user ids from the list endpoint.
     * Continue doing so until there is no token returned.
     *
     * @return List<String> list of ids
     * @throws IOException when retrieve from service endpoint failed
     */
    public List<String> getUserIdList() throws IOException {
        List<String> idList = new ArrayList<String>();
        String listUserUrl = HttpHelper.SERVICE_END_POINT + HttpHelper.LIST_PATH;
        String token = null;
        do {
            JsonObject response = httpHelper.doConnection(listUserUrl);
            List<String> currentIdList = httpHelper.getResult(response);
            idList.addAll(currentIdList);
            token = httpHelper.getToken(response);
            if (token != null) listUserUrl = httpHelper.getNewUserDetailUrl(token);
        } while (token != null);
        return idList;
    }

    public User getUserDetail(String idStr) {
        String userDetailUrl = String.format(HttpHelper.userDetailUrl, idStr);
        try {
            JsonObject response = httpHelper.doConnection(userDetailUrl);
            return getUserFromResponse(response);
        } catch (IOException e) {
            LOGGER.warn("{} for: {}", e.getMessage(), userDetailUrl);
            return null;
        }
    }

    private User getUserFromResponse(JsonObject response) {
        int id = Integer.parseInt(response.get("id").getAsString());
        String name = response.get("name").getAsString();
        int age = Integer.parseInt(response.get("age").getAsString());
        String number = response.get("number").getAsString();
        String photo = response.get("photo").getAsString();
        String bio = response.get("bio").getAsString();
        return new User(id, name, age, number, photo, bio);
    }
}
