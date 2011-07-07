/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.failuretests;

import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.User;

import java.util.List;

/**
 * <p>This is mock implementation of {@link UserDAO} class, used for testing.</p>
 *
 * @author jmn
 * @version 1.0
 */
public class MockUserDAO implements UserDAO {

    /**
     * <p>Creates new instance of {@link MockUserDAO} class.</p>
     */
    public MockUserDAO() {
        // empty constructor
    }

    /**
     * {@inheritDoc}
     */
    public User find(Long aLong) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public User find(String s, boolean b) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public User find(String s, boolean b, boolean b1) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public List find(String s, String s1, String s2, String s3) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void saveOrUpdate(User user) {
        // empty method
    }
}
