/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.Email;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserProfile;
import com.topcoder.web.reg.Constants;

/**
 * Mock class for test.
 *
 * @author extra
 * @version 1.0
 */
public class MockUserDAO implements UserDAO {
    /**
     * Mock method.
     */
    public User find(Long id) {
        User user = new User();
        user.setId(10L);

        user.setHandle("handle");
        Set<Email> emailAddresses = new HashSet<Email>();
        Email email = new Email();
        email.setPrimary(Boolean.TRUE);
        email.setAddress("accuracy@topcoder.com");
        email.setStatusId(2);
        emailAddresses.add(email);
        user.setEmailAddresses(emailAddresses);
        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName("firstName");
        userProfile.setLastName("lastName");

        user.setUserProfile(userProfile);

        user.setStatus(Constants.ACTIVE_STATI[0]);
        return user;
    }

    /**
     * Mock method.
     */
    public User find(String userName, boolean ignoreCase) {
        return find(userName);
    }

    /**
     * Mock method.
     */
    public User find(String userName, boolean ignoreCase, boolean activeRequired) {
        return null;
    }

    /**
     * Mock method.
     */
    public List find(String handle, String firstName, String lastName, String email) {
        return null;
    }

    /**
     * Mock method.
     */
    public void saveOrUpdate(User u) {
    }

    /**
     * Mock method.
     */
    public User find(String email) {
        if ("no@topcoder.com".equals(email)) {
            return null;
        }
        User user = new User();
        user.setId(10L);

        user.setHandle("handle");
        Set<Email> emailAddresses = new HashSet<Email>();
        Email email0 = new Email();
        email0.setPrimary(Boolean.TRUE);
        email0.setAddress(email);
        email0.setStatusId(2);
        emailAddresses.add(email0);
        user.setEmailAddresses(emailAddresses);
        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName("firstName");
        userProfile.setLastName("lastName");

        user.setUserProfile(userProfile);

        user.setStatus(Constants.ACTIVE_STATI[0]);
        return user;
    }

    /**
     * Mock method.
     */
    public boolean canChangeHandle(String handle) {
        return false;
    }
}
