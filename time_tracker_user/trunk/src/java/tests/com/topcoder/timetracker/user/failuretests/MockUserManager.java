/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.security.authorization.SecurityRole;
import com.topcoder.timetracker.user.BatchOperationException;
import com.topcoder.timetracker.user.DataAccessException;
import com.topcoder.timetracker.user.UnrecognizedEntityException;
import com.topcoder.timetracker.user.User;
import com.topcoder.timetracker.user.UserFilterFactory;
import com.topcoder.timetracker.user.UserManager;

/**
 * <p>
 * Mock implementation only for testing purpose.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class MockUserManager implements UserManager {

    /**
     * <p>
     * Stub method.
     * </p>
     */
    public void addRoleToUser(User user, SecurityRole role) throws UnrecognizedEntityException, DataAccessException {
        // TODO Auto-generated method stub

    }

    /**
     * <p>
     * Stub method.
     * </p>
     */
    public void addUsers(User[] users, boolean audit) throws DataAccessException, BatchOperationException {
        // TODO Auto-generated method stub

    }

    /**
     * <p>
     * Stub method.
     * </p>
     */
    public boolean authenticateUser(String username, String password) throws DataAccessException {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * <p>
     * Stub method.
     * </p>
     */
    public void clearRolesFromUser(User user) throws DataAccessException, UnrecognizedEntityException {
        // TODO Auto-generated method stub

    }

    /**
     * <p>
     * Stub method.
     * </p>
     */
    public void createUser(User user, boolean audit) throws DataAccessException {
        // TODO Auto-generated method stub

    }

    /**
     * <p>
     * Stub method.
     * </p>
     */
    public User[] getAllUsers() throws DataAccessException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * <p>
     * Stub method.
     * </p>
     */
    public User getUser(long usertId) throws UnrecognizedEntityException, DataAccessException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * <p>
     * Stub method.
     * </p>
     */
    public UserFilterFactory getUserFilterFactory() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * <p>
     * Stub method.
     * </p>
     */
    public User[] getUsers(long[] userIds) throws DataAccessException, BatchOperationException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * <p>
     * Stub method.
     * </p>
     */
    public void removeRoleFromUser(User user, SecurityRole role) throws UnrecognizedEntityException,
        DataAccessException {
        // TODO Auto-generated method stub

    }

    /**
     * <p>
     * Stub method.
     * </p>
     */
    public void removeUser(long userId, boolean audit) throws UnrecognizedEntityException, DataAccessException {
        // TODO Auto-generated method stub

    }

    /**
     * <p>
     * Stub method.
     * </p>
     */
    public void removeUsers(long[] userIds, boolean audit) throws DataAccessException, BatchOperationException {
        // TODO Auto-generated method stub

    }

    /**
     * <p>
     * Stub method.
     * </p>
     */
    public SecurityRole[] retrieveRolesForUser(User user) throws UnrecognizedEntityException, DataAccessException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * <p>
     * Stub method.
     * </p>
     */
    public User[] searchUsers(Filter filter) throws DataAccessException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * <p>
     * Stub method.
     * </p>
     */
    public void updateUser(User user, boolean audit) throws UnrecognizedEntityException, DataAccessException {
        // TODO Auto-generated method stub

    }

    /**
     * <p>
     * Stub method.
     * </p>
     */
    public void updateUsers(User[] users, boolean audit) throws DataAccessException, BatchOperationException {
        // TODO Auto-generated method stub

    }

}
