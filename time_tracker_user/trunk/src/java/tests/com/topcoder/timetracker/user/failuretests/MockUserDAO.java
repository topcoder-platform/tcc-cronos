/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.user.DataAccessException;
import com.topcoder.timetracker.user.User;
import com.topcoder.timetracker.user.UserDAO;
import com.topcoder.timetracker.user.UserFilterFactory;

/**
 * <p>
 * Mock implementation of UserDAO interface.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class MockUserDAO implements UserDAO {

    /**
     * <p>
     * stub method.
     * </p>
     */
    public void addUsers(User[] users, boolean audit) throws DataAccessException {
        throw new DataAccessException("mock");
    }

    /**
     * <p>
     * stub method.
     * </p>
     */
    public User[] getAllUsers() throws DataAccessException {
        throw new DataAccessException("mock");
    }

    /**
     * <p>
     * stub method.
     * </p>
     */
    public UserFilterFactory getUserFilterFactory() {
        return null;
    }

    /**
     * <p>
     * stub method.
     * </p>
     */
    public User[] getUsers(long[] userIds) throws DataAccessException {
        throw new DataAccessException("mock");
    }

    /**
     * <p>
     * stub method.
     * </p>
     */
    public void removeUsers(long[] userId, boolean audit) throws DataAccessException {
        throw new DataAccessException("mock");
    }

    /**
     * <p>
     * stub method.
     * </p>
     */
    public User[] searchUsers(Filter filter) throws DataAccessException {
        throw new DataAccessException("mock");
    }

    /**
     * <p>
     * stub method.
     * </p>
     */
    public void updateUsers(User[] user, boolean audit) throws DataAccessException {
        throw new DataAccessException("mock");
    }

}
