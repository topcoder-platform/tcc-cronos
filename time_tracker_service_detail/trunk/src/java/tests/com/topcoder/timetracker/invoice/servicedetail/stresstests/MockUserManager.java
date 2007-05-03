/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.stresstests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.security.authorization.SecurityRole;
import com.topcoder.timetracker.user.User;
import com.topcoder.timetracker.user.UserFilterFactory;
import com.topcoder.timetracker.user.UserManager;

/**
 * This is a mock implementation of <code>UserManager</code>. It is only used in stress tests.
 *
 * @author vividmxx
 * @version 3.2
 */
public class MockUserManager implements UserManager {

    /**
     * The default constructor.
     */
    public MockUserManager() {
    }

    /**
     * Returns directly.
     *
     * @param user
     *            ignored
     * @param audit
     *            ignored
     */
    public void createUser(User user, boolean audit) {
    }

    /**
     * Returns directly.
     *
     * @param user
     *            ignored
     * @param audit
     *            ignored
     */
    public void updateUser(User user, boolean audit) {
    }

    /**
     * Returns directly.
     *
     * @param userId
     *            ignored
     * @param audit
     *            ignored
     */
    public void removeUser(long userId, boolean audit) {
    }

    /**
     * Returns an <code>User</code>.
     *
     * @param userId
     *            the id of the <code>User</code>
     * @return an <code>User</code>
     */
    public User getUser(long userId) {
        User user = new User();
        user.setId(userId);
        user.setUsername("Tester" + userId);
        return user;
    }

    /**
     * Returns null.
     *
     * @param filter
     *            ignored
     * @return null
     */
    public User[] searchUsers(Filter filter) {
        return null;
    }

    /**
     * Returns directly.
     *
     * @param users
     *            ignored
     * @param audit
     *            ignored
     */
    public void addUsers(User[] users, boolean audit) {
    }

    /**
     * Returns directly.
     *
     * @param users
     *            ignored
     * @param audit
     *            ignored
     */
    public void updateUsers(User[] users, boolean audit) {
    }

    /**
     * Returns directly.
     *
     * @param userIds
     *            ignored
     * @param audit
     *            ignored
     */
    public void removeUsers(long[] userIds, boolean audit) {
    }

    /**
     * Returns null.
     *
     * @param userIds
     *            ignored
     * @return null
     */
    public User[] getUsers(long[] userIds) {
        return null;
    }

    /**
     * Returns directly.
     *
     * @param user
     *            ignored
     * @param role
     *            ignored
     */
    public void addRoleToUser(User user, SecurityRole role) {
    }

    /**
     * Returns directly.
     *
     * @param user
     *            ignored
     * @param role
     *            ignored
     */
    public void removeRoleFromUser(User user, SecurityRole role) {
    }

    /**
     * Returns null.
     *
     * @param user
     *            ignored
     * @return null
     */
    public SecurityRole[] retrieveRolesForUser(User user) {
        return null;
    }

    /**
     * Returns directly.
     *
     * @param user
     *            ignored
     */
    public void clearRolesFromUser(User user) {
    }

    /**
     * Returns false.
     *
     * @param username
     *            ignored
     * @param password
     *            ignored
     * @return false
     */
    public boolean authenticateUser(String username, String password) {
        return false;
    }

    /**
     * Returns null.
     *
     * @return null
     */
    public User[] getAllUsers() {
        return null;
    }

    /**
     * Returns null.
     *
     * @return null
     */
    public UserFilterFactory getUserFilterFactory() {
        return null;
    }
}
