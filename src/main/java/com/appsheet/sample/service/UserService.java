package com.appsheet.sample.service;

import com.appsheet.sample.model.User;

import java.io.IOException;

/**
 * @author Kansen Wu
 */
public interface UserService {
    User[] getUserOrderedList() throws IOException;
}
