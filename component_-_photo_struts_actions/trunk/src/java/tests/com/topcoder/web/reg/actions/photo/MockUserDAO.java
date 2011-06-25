/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.photo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.Email;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserProfile;

/**
 * <p>
 * A simple mock of {@link UserDAO}.
 * </p>
 *
 * @author mumujava
 * @version 1.0
 */
public class MockUserDAO implements UserDAO {
    /**
     * Returns a User.
     * @param id not used
     * @return the user found.
     */
    public User find(Long id) {
        User user = new User();
        user.setId(id);
        user.setHandle("handle" + id);
        user.setLastName("lastname" + id);
        user.setFirstName("Firstname" + id);
        Set<Email> emailset = new HashSet<Email>();
        Email email = new Email();
        email.setAddress("handle" + id + "@gmail.com");
        emailset.add(email);
        email.setPrimary(false);
        email = new Email();
        email.setAddress("handle" + id + "@topcoder.com");
        email.setPrimary(true);
        emailset.add(email);
        email = new Email();
        email.setAddress("handle" + id + "@sohu.com");
        email.setPrimary(false);
        emailset.add(email);
        user.setEmailAddresses(emailset);
        UserProfile profile = new UserProfile();
        profile.setFirstName("Firstname" + id);
        profile.setLastName("lastname" + id);
        profile.setUser(user);
        profile.setId(id);
        user.setUserProfile(profile);
        return user;
    }


    /**
     * Does nothing.
     * @param  userName not used
     * @param  ignoreCase not used
     * @return null
     */
    public User find(String userName, boolean ignoreCase) {
        return null;
    }


    /**
     * Does nothing.
     * @param  userName not used
     * @param  ignoreCase not used
     * @param  activeRequired not used
     * @return null
     */
    public User find(String userName, boolean ignoreCase, boolean activeRequired) {
        return null;
    }


    /**
     * Does nothing.
     * @param  handle not used
     * @param  firstName not used
     * @param  lastName not used
     * @param  email not used
     * @return null
     */
    public List<User> find(String handle, String firstName, String lastName, String email) {
        return null;
    }

    /**
     * Does nothing.
     * @param  u not used
     */
    public void saveOrUpdate(User u) {
    }

}
