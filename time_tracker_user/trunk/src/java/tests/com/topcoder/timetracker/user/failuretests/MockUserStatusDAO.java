/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.user.DataAccessException;
import com.topcoder.timetracker.user.UserStatus;
import com.topcoder.timetracker.user.UserStatusDAO;

/**
 * <p>
 * Mock implementation of UserStatusDAO, only for testing purpose.
 * </p>
 *
 * @author FireIce
 * @version 3.2.1
 * @since 3.2.1
 */
public class MockUserStatusDAO implements UserStatusDAO {

    /**
     * <p>
     * mock method.
     * </p>
     *
     * @throws DataAccessException
     *             always.
     */
    public void addUserStatuses(UserStatus[] userStatuses) throws DataAccessException {
        throw new DataAccessException("Failure test");
    }

    /**
     * <p>
     * mock method.
     * </p>
     *
     * @throws DataAccessException
     *             always.
     */
    public UserStatus[] getAllUserStatuses() throws DataAccessException {
        throw new DataAccessException("Failure test");
    }

    /**
     * <p>
     * mock method.
     * </p>
     *
     * @throws DataAccessException
     *             always.
     */
    public UserStatus[] getUserStatuses(long[] userStatusIds) throws DataAccessException {
        throw new DataAccessException("Failure test");
    }

    /**
     * <p>
     * mock method.
     * </p>
     *
     * @throws DataAccessException
     *             always.
     */
    public void removeUserStatuses(long[] userStatusIds) throws DataAccessException {
        throw new DataAccessException("Failure test");
    }

    /**
     * <p>
     * mock method.
     * </p>
     *
     * @throws DataAccessException
     *             always.
     */
    public UserStatus[] searchUserStatuses(Filter filter) throws DataAccessException {
        throw new DataAccessException("Failure test");
    }

    /**
     * <p>
     * mock method.
     * </p>
     *
     * @throws DataAccessException
     *             always.
     */
    public void updateUserStatuses(UserStatus[] userStatuses) throws DataAccessException {
        throw new DataAccessException("Failure test");
    }

}
