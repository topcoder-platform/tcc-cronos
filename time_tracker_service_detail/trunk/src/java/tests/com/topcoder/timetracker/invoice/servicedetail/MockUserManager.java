/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.security.authorization.SecurityRole;
import com.topcoder.timetracker.user.User;
import com.topcoder.timetracker.user.UserFilterFactory;
import com.topcoder.timetracker.user.UserManager;

/**
 * Mock for <code>UserManager</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockUserManager implements UserManager {

    /**
     * Mock method.
     *
     * @param users
     *            not used
     * @param audit
     *            not used
     */
    public void addUsers(User[] users, boolean audit) {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @param username
     *            not used
     * @param password
     *            not used
     *
     * @return false
     */
    public boolean authenticateUser(String username, String password) {
        return false;
    }

    /**
     * Mock method.
     *
     * @param user
     *            not used
     */
    public void clearRolesFromUser(User user) {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @param user
     *            not used
     * @param audit
     *            not used
     */
    public void createUser(User user, boolean audit) {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @return null
     */
    public User[] getAllUsers() {
        return null;
    }

    /**
     * Mock method.
     *
     * @param userId
     *            not used
     *
     * @return a user
     */
    public User getUser(long userId) {
        User user = new User();
        if (userId == 1) {
            user.setUsername("testuser2");
        } else {
            user.setUsername("testuser");
        }
        return user;
    }

    /**
     * Mock method.
     *
     * @return null
     */
    public UserFilterFactory getUserFilterFactory() {
        return null;
    }

    /**
     * Mock method.
     *
     * @param userIds
     *            not used
     *
     * @return null
     */
    public User[] getUsers(long[] userIds) {
        return null;
    }

    /**
     * Mock method.
     *
     * @param userId
     *            not used
     * @param audit
     *            not used
     */
    public void removeUser(long userId, boolean audit) {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @param userIds
     *            not used
     * @param audit
     *            not used
     */
    public void removeUsers(long[] userIds, boolean audit) {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @param user
     *            not used
     *
     * @return null
     */
    public SecurityRole[] retrieveRolesForUser(User user) {
        return null;
    }

    /**
     * Mock method.
     *
     * @param filter
     *            not used
     *
     * @return null
     */
    public User[] searchUsers(Filter filter) {
        return null;
    }

    /**
     * Mock method.
     *
     * @param user
     *            not used
     * @param audit
     *            not used
     */
    public void updateUser(User user, boolean audit) {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @param users
     *            not used
     * @param audit
     *            not used
     */
    public void updateUsers(User[] users, boolean audit) {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @param user
     *            not used
     * @param role
     *            not used
     * @param audit
     *            not used
     */
    public void addRoleToUser(User user, SecurityRole role, boolean audit) {
        // nothing to do
    }

    /**
     * Mock method.
     *
     * @param user
     *            not used
     * @param role
     *            not used
     * @param audit
     *            not used
     */
    public void removeRoleFromUser(User user, SecurityRole role, boolean audit) {
        // nothing to do
    }

}
