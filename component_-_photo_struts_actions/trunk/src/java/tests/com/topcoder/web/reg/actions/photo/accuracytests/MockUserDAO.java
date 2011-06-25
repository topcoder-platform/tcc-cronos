/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.photo.accuracytests;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.Email;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserProfile;

/**
 * Mock class for test.
 *
 * @author extra
 * @version 1.0
 */
public class MockUserDAO implements UserDAO {
    public User find(Long id) {
        User user = new User();
        user.setId(10L);
        user.setHandle("handle");
        Set<Email> emailAddresses = new HashSet<Email>();
        Email email = new Email();
        email.setPrimary(Boolean.TRUE);
        email.setAddress("accuracy@topcoder.com");
        emailAddresses.add(email);
        user.setEmailAddresses(emailAddresses);
        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName("firstName");
        userProfile.setLastName("lastName");

        user.setUserProfile(userProfile);
        return user;
    }

    public User find(String userName, boolean ignoreCase) {
        return null;
    }

    public User find(String userName, boolean ignoreCase, boolean activeRequired) {
        return null;
    }

    public List find(String handle, String firstName, String lastName, String email) {
        return null;
    }

    public void saveOrUpdate(User u) {
    }
}
